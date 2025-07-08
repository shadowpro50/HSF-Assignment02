package com.se192491.assignment02.repository;

import com.se192491.assignment02.pojo.Orchid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrchidRepository extends JpaRepository<Orchid, Integer> {
    Page<Orchid> findAll(Pageable pageable);
}
