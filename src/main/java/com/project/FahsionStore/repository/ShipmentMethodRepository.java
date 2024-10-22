package com.project.FahsionStore.repository;

import com.project.FahsionStore.model.ShipmentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentMethodRepository extends JpaRepository<ShipmentMethod,Integer> {
}
