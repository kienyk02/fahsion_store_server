package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.User;
import com.project.FahsionStore.security.CustomUserDetails;
import com.project.FahsionStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping("/test")
@RestController
public class TestController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "public content";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userAccess() {
        return "user content";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess() {
        return "admin content";
    }

    private User getUserRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(customUserDetails.getEmail());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-request")
    public User neededLogin() {
        return getUserRequest();
    }
}
