package com.project.FahsionStore.repository;

import com.project.FahsionStore.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    public List<Address> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE Address a SET a.active = 0 WHERE a.user.id = :userId")
    public void resetAddressActive(int userId);
}
