package com.company.ufba.repositories;

import com.company.ufba.buzufba.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<PointEntity,Integer> {}
