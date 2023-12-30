package com.company.ufba.services;

import com.company.ufba.buzufba.BusEntity;
import com.company.ufba.buzufba.PointEntity;
import com.company.ufba.buzufba.RouterEntity;
import com.company.ufba.dto.Buzufba;
import com.company.ufba.dto.Type;
import com.company.ufba.repositories.BusRepository;
import com.company.ufba.repositories.PointRepository;
import com.company.ufba.repositories.RouterRepository;
import com.company.ufba.utils.values.mapsValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class ExtraServices {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    RouterRepository routerRepository;
    @Autowired
    BusRepository busRepository;

    Double zoom;

    public PointEntity point() {

        return pointRepository.findById(1).get();
    }

    public Set<PointEntity> pointMaxRanger(Map<String,?> infos) {

        var lat = (double) infos.get("lat");
        var log = (double) infos.get("long");

        verifyZoom(mapsValues.valueOf((String) infos.get("zoom")));
        var latExtraPlus = lat + zoom;
        var latExtraMinus = lat - zoom;
        var longExtraPlus = log + (zoom+0.008);
        var longExtraMinus = log - (zoom+0.008);
        var result = pointRepository
                .findAllByLocale_LatitudeBetweenAndLocale_LongitudeBetween(latExtraMinus,
                        latExtraPlus, longExtraMinus, longExtraPlus);
        result.forEach(System.out::println);
        return result;
    }

    public RouterEntity router() {
        return routerRepository.findById(1).get();
    }

    public Set<BusEntity> bus(String name) {
        System.out.println();
        var result = busRepository.findBusEntitiesByPoint_Name(name);
        return result;
    }

    public void verifyZoom(mapsValues zoom) {
        switch (zoom) {
            case X1 -> this.zoom = 0.008;
            case X2 -> this.zoom = 0.008;
            case X3 -> this.zoom = 0.008;
        }

    }

    public Set<RouterEntity> getRouter(int id) {
        var result = routerRepository.findAllById(id);
        System.out.println(result);
        return result;
    }
}

