package com.company.ufba.repositories;

import com.company.ufba.buzufba.CoordinatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CoordinatesRepository extends JpaRepository<CoordinatesEntity,Integer> {
Set<CoordinatesEntity> findAllByRouterId(int id);
}
