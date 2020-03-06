package org.dmace.security.demo.dto.converter;

import lombok.RequiredArgsConstructor;
import org.dmace.security.demo.dto.ProductDTO;
import org.dmace.security.demo.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
//@RequiredArgsConstructor
public class ProductConverter {

//    private final ModelMapper modelMapper;

//    @PostConstruct
//    public void init() {
//        modelMapper.addMappings(new PropertyMap<Product, ProductDTO>() {
//
//            @Override
//            protected void configure() {
//                map().setCategory(source.getCategory().getName());
//            }
//
//        });
//    }

//    public ProductDTO convert(Product product) {
//        return modelMapper.map(product, ProductDTO.class);
//
//    }

    public ProductDTO dto(Product producto) {
        return ProductDTO.builder()
                .name(producto.getName())
                .image(producto.getImage())
                .category(producto.getCategory().getName())
                .id(producto.getId())
                .build();
    }

}
