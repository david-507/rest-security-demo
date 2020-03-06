package org.dmace.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.dmace.security.demo.views.ProductViews;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @JsonView(ProductViews.Dto.class)
    private long id;

    @JsonView(ProductViews.Dto.class)
    private String name;

    @JsonView(ProductViews.Dto.class)
    private String image;

    @JsonView(ProductViews.DtoWithPrice.class)
    private float price;

    @JsonView(ProductViews.Dto.class)
    private String category;
}
