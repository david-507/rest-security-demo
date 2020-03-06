package org.dmace.security.demo.service;

import lombok.RequiredArgsConstructor;
import org.dmace.security.demo.controller.FilesController;
import org.dmace.security.demo.dto.CreateProductDTO;
import org.dmace.security.demo.model.Product;
import org.dmace.security.demo.repo.ProductRepository;
import org.dmace.security.demo.service.base.BaseService;
import org.dmace.security.demo.service.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseService<Product, Long, ProductRepository> {
    private final CategoryService categoryService;
    private final StorageService storageService;


    public Product createProduct(CreateProductDTO nuevo, MultipartFile file) {
        String urlImagen = null;

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "serveFile", imagen, null)
                    .build().toUriString();
        }

        Product nuevoProduct = Product.builder()
                .name(nuevo.getName())
                .price(nuevo.getPrice())
                .image(urlImagen)
                .category(categoryService.findById(nuevo.getCategoryId()).orElse(null))
                .build();

        return this.save(nuevoProduct);

    }

    public Page<Product> findByName(String name, Pageable pageable) {
        return this.repository.findByNameContainsIgnoreCase(name, pageable);
    }

    public Page<Product> findByArgs(final String name, final BigDecimal price, Pageable pageable) {

        Specification<Product> productNameSpec = (Specification<Product>) (root, query, criteriaBuilder) -> {
            if (Objects.isNull(name))
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            else
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),"%" + name + "%");
        };

        Specification<Product> priceLessThanSpec = (Specification<Product>) (root, query, criteriaBuilder) -> {
            if (Objects.isNull(price))
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            else
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
        };

        Specification<Product> both = productNameSpec.and(priceLessThanSpec);

        return this.repository.findAll(both, pageable);
    }

    public Optional<Product> findByIdWithBatches(Long id) {
        return repository.findByIdJoinFetch(id);
    }

}
