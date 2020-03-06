package org.dmace.security.demo.controller.product;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.dmace.security.demo.dto.CreateProductDTO;
import org.dmace.security.demo.dto.EditProductDTO;
import org.dmace.security.demo.dto.ProductDTO;
import org.dmace.security.demo.dto.converter.ProductConverter;
import org.dmace.security.demo.errors.exceptions.ProductNotFoundException;
import org.dmace.security.demo.errors.exceptions.SearchProductNoResultException;
import org.dmace.security.demo.model.Product;
import org.dmace.security.demo.service.ProductService;
import org.dmace.security.demo.utils.PaginationLinksUtils;
import org.dmace.security.demo.views.ProductViews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter converter;
    private final PaginationLinksUtils paginationLinksUtils;

    @JsonView(ProductViews.DtoWithPrice.class)
    @GetMapping(value = "/products")
    public ResponseEntity<Page<ProductDTO>> findProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "price", required = false) BigDecimal price,
            Pageable pageable,
            HttpServletRequest request) {

        Page<Product> result = productService.findByArgs(name, price, pageable);

        if (result.isEmpty()) {
            throw new SearchProductNoResultException();
        } else {

            Page<ProductDTO> dtoList = result.map(converter::dto);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

            return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(dtoList, uriBuilder))
                    .body(dtoList);
        }
    }

    @GetMapping("/product/{id}")
    public Product find(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> create(@RequestPart("newProduct") CreateProductDTO newProduct, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(newProduct, file));
    }

    @PutMapping("/product/{id}")
    public Product editarProduct(@RequestBody EditProductDTO editProductDTO, @PathVariable Long id) {
        return productService.findById(id).map(p -> {
            p.setName(editProductDTO.getName());
            p.setPrice(editProductDTO.getPrice());
            return productService.save(p);
        }).orElseThrow(() -> new ProductNotFoundException(id));

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        productService.delete(product);
        return ResponseEntity.noContent().build();
    }
    
}
