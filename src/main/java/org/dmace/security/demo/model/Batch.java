package org.dmace.security.demo.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
public class Batch {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    //@JsonManagedReference
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="batch_id"),
            inverseJoinColumns = @JoinColumn(name="product_id")
    )
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    /**
     * Métodos helper
     */
    public void addProduct(Product p) {
        this.products.add(p);
        p.getBatches().add(this);
    }

    public void deleteProducto(Product p) {
        this.products.remove(p);
        p.getBatches().remove(this);
    }
}
