package com.project.FahsionStore.service.serviceImpl;

import com.project.FahsionStore.model.Address;
import com.project.FahsionStore.repository.AddressRepository;
import com.project.FahsionStore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;


    @Override
    public List<Address> getAddressByUserId(int userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(int addressId) {
        addressRepository.deleteById(addressId);
    }
}
