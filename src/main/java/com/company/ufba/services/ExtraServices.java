package com.company.ufba.services;

import com.company.ufba.buzufba.BusEntity;
import com.company.ufba.buzufba.PointEntity;
import com.company.ufba.buzufba.RouterEntity;
import com.company.ufba.dto.Buzufba;
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
    public PointEntity point(){

        return pointRepository.findById(1).get();}
    public Set<PointEntity> pointMaxRanger(Buzufba infos){

        var values = infos.getLocale();
        return pointRepository
                .findAllByLocale_LatitudeBetweenAndLocale_LongitudeBetween(values.get("latitudeRef"),
                        values.get("latitudeAlgo"),values.get("longitudeRef"),values.get("longitudeAlgo"));

    }
    public RouterEntity router(){
        return routerRepository.findById(1).get();}
    public BusEntity bus(){
        return busRepository.findById(1).get();
    }
}

