package org.dmace.security.demo.repo;

import org.dmace.security.demo.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    @Query("select b from Batch b JOIN FETCH b.products WHERE b.id = :id")
    Optional<Batch> findByIdJoinFetch(Long id);
}
