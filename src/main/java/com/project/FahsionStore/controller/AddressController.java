package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.Address;
import com.project.FahsionStore.model.User;
import com.project.FahsionStore.security.CustomUserDetails;
import com.project.FahsionStore.service.AddressService;
import com.project.FahsionStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AddressController {
    @Autowired
    AddressService addressService;
    @Autowired
    UserService userService;

    private User getUserRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(customUserDetails.getEmail());
    }

    @GetMapping("/address")
    public ResponseEntity<?> getAddressByUserId() {
        return ResponseEntity.ok().body(addressService.getAddressByUserId(getUserRequest().getId()));
    }

    @PostMapping("/address")
    public ResponseEntity<?> saveAddress(@RequestBody Address address) {
        address.setUser(getUserRequest());
        return ResponseEntity.ok().body(addressService.saveAddress(address));
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddressById(@PathVariable("id") int id) {
        addressService.deleteAddress(id);
    }
}
