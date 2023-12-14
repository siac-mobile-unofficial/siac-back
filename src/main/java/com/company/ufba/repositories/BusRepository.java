package com.company.ufba.repositories;

import com.company.ufba.buzufba.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository  extends JpaRepository<BusEntity,Integer> {


}
