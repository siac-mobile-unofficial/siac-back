package com.company.ufba.repositories;

import com.company.ufba.buzufba.RouterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RouterRepository extends JpaRepository<RouterEntity,Integer> {
    Set<RouterEntity> findAllById(int id);
}
