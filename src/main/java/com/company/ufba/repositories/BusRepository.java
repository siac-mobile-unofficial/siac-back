package com.company.ufba.repositories;

import com.company.ufba.buzufba.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface BusRepository  extends JpaRepository<BusEntity,Integer> {
    Set<BusEntity> findBusEntitiesByPoint_Name(String name);
}
