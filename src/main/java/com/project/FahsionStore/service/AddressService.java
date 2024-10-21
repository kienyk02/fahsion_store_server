package com.project.FahsionStore.service;

import com.project.FahsionStore.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressByUserId(int userId);

    Address saveAddress(Address address);

    void deleteAddress(int addressId);
}
