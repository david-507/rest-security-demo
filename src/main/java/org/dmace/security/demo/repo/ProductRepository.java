package org.dmace.security.demo.repo;

import org.dmace.security.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findByNameContainsIgnoreCase(String txt, Pageable pageable);

    @Query("select p from Product p LEFT JOIN FETCH p.batches WHERE p.id = :id")
    Optional<Product> findByIdJoinFetch(Long id);
}
