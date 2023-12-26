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

    public Set<PointEntity> pointMaxRanger(Buzufba infos) {
        var values = infos.getLocale();
        var lat = (double) values.get("lat");
        var log = (double) values.get("long");
        verifyZoom(mapsValues.valueOf((String) infos.getLocale().get("zoom")));
        var latExtraPlus = lat + zoom;
        var latExtraMinus = lat - zoom;
        var longExtraPlus = log + zoom;
        var longExtraMinus = log - zoom;
        var result = pointRepository
                .findAllByLocale_LatitudeBetweenAndLocale_LongitudeBetween(latExtraMinus,
                        latExtraPlus, longExtraMinus, longExtraPlus);
        result.forEach(System.out::println);
        return result;
    }

    public RouterEntity router() {
        return routerRepository.findById(1).get();
    }

    public BusEntity bus() {
        return busRepository.findById(1).get();
    }

    public void verifyZoom(mapsValues zoom) {
        switch (zoom) {
            case X1 -> this.zoom = 0.01;
            case X2 -> this.zoom = 0.02;
            case X3 -> this.zoom = 0.03;
        }

    }
}

