package org.dmace.security.demo.service;

import lombok.RequiredArgsConstructor;
import org.dmace.security.demo.dto.CreateBatchDTO;
import org.dmace.security.demo.errors.exceptions.BatchCreateException;
import org.dmace.security.demo.model.Batch;
import org.dmace.security.demo.model.Product;
import org.dmace.security.demo.repo.BatchRepository;
import org.dmace.security.demo.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService extends BaseService<Batch, Long, BatchRepository> {

    private final ProductService service;

    @Override
    public Optional<Batch> findById(Long id) {
        return repository.findByIdJoinFetch(id);
    }

    public Batch nuevoLote(CreateBatchDTO dto) {

        Batch batch = Batch.builder()
                .nombre(dto.getName())
                .build();

        dto.getProducts().stream()
                .map(id -> service.findById(id).orElseThrow(BatchCreateException::new))
                .forEach(batch::addProduct);

        return save(batch);

    }

}
