package com.company.ufba.services;

import com.company.ufba.buzufba.BusEntity;
import com.company.ufba.buzufba.PointEntity;
import com.company.ufba.buzufba.RouterEntity;
import com.company.ufba.repositories.BusRepository;
import com.company.ufba.repositories.PointRepository;
import com.company.ufba.repositories.RouterRepository;
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
    public Set<PointEntity> pointMaxRanger(){ return null;}
    public RouterEntity router(){
        return routerRepository.findById(1).get();}
    public BusEntity bus(){
        return busRepository.findById(1).get();
    }
}

