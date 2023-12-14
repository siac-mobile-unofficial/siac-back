package com.company.ufba.repositories;

import com.company.ufba.buzufba.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PointRepository extends JpaRepository<PointEntity, Integer> {
    Set<PointEntity> findAllByLocale_LatitudeIsBetweenAndLocale_LongitudeIsBetween(Float latitudeRef, Float latitudeAlgo, Float longitudeRef, Float logintudeAlgo);
}
