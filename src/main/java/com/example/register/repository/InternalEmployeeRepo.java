package com.example.register.repository;

import com.example.register.entity.InternalEmployeees;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternalEmployeeRepo extends PagingAndSortingRepository<InternalEmployeees, Long> {
    Optional<InternalEmployeees> findById(Long id);

    boolean existsById(Long id);
    List<InternalEmployeees> findAll();

    void deleteById(Long id);

    InternalEmployeees save(InternalEmployeees employee);

    InternalEmployeees findByEmailId(String email);
}
