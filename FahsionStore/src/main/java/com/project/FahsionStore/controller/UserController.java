package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.User;
import com.project.FahsionStore.payload.response.MessageResponse;
import com.project.FahsionStore.security.CustomUserDetails;
import com.project.FahsionStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    private User getUserRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.findByAccountEmail(customUserDetails.getEmail());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-info")
    public Optional<User> getUserInfo(){
        return userService.getUserById(getUserRequest().getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/user/update-info")
    public MessageResponse updateUserInfo(
            @RequestParam String name,
            @RequestParam String gender,
            @RequestParam String phoneNumber,
            @RequestParam MultipartFile avatarImage
    ) throws IOException {
        User user = userService.getUserById(getUserRequest().getId()).get();
        user.setName(name);
        user.setGender(gender);
        user.setPhoneNumber(phoneNumber);
        user.setAvatarImage(avatarImage.getBytes());
        userService.saveOrUpdate(user);
        return new MessageResponse("Cập nhật thông tin thành công!");
    }

}
