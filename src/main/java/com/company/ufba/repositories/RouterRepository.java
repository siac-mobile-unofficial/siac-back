package com.company.ufba.repositories;

import com.company.ufba.buzufba.RouterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouterRepository extends JpaRepository<RouterEntity,Integer> {
}
