package org.dmace.security.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Product.class )
public class Product {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private float price;

    private String image;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy="products")
    @Builder.Default
    private Set<Batch> batches = new HashSet<>();
}
