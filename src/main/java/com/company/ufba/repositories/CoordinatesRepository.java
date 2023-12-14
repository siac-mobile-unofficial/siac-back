package com.company.ufba.repositories;

import com.company.ufba.buzufba.CoordinatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<CoordinatesEntity,Integer> {
}
