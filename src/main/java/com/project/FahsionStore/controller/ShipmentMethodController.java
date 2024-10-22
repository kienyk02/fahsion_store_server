package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.ShipmentMethod;
import com.project.FahsionStore.repository.ShipmentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ShipmentMethodController {
    @Autowired
    ShipmentMethodRepository shipmentMethodRepository;

    @GetMapping("/shipmentmethods")
    public List<ShipmentMethod> getAll(){
        return shipmentMethodRepository.findAll();
    }
}
