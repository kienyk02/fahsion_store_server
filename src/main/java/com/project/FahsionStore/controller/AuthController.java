package com.project.FahsionStore.controller;

import com.project.FahsionStore.model.*;
import com.project.FahsionStore.payload.request.LoginRequest;
import com.project.FahsionStore.payload.request.SignUpRequest;
import com.project.FahsionStore.payload.response.JwtResponse;
import com.project.FahsionStore.payload.response.MessageResponse;
import com.project.FahsionStore.security.CustomUserDetails;
import com.project.FahsionStore.security.jwt.JwtTokenProvider;
import com.project.FahsionStore.service.AccountService;
import com.project.FahsionStore.service.RoleService;
import com.project.FahsionStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
        if (accountService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email đã được sử dụng!"));
        }
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setGender(signUpRequest.getGender());
        user.setPhoneNumber("");
        Account account = new Account();
        account.setEmail(signUpRequest.getEmail());
        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Set<String> roles = signUpRequest.getRoles();
        Set<Role> listRole = new HashSet<>();
        if (roles == null) {
            Role role = roleService.findByRoleName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRole.add(role);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRole.add(adminRole);
                    case "user":
                        Role userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRole.add(userRole);
                }
            });
        }
        account.setRoles(listRole);
        user.setAccount(account);

        UserSetting userSetting = new UserSetting();
        userSetting.setEnableFingerPrint(0);
        userSetting.setEnableNotification(0);
        userSetting.setEnableLocationService(0);
        user.setSetting(userSetting);

        String imgUrl = "";
        if(signUpRequest.getGender().equals("Nam")) imgUrl = "https://img.freepik.com/free-vector/smiling-young-man-illustration_1308-173524.jpg";
        else imgUrl = "https://img.freepik.com/free-vector/young-woman-with-glasses-avatar_1308-174364.jpg?t=st=1728784792~exp=1728788392~hmac=d9ba6ce113cde8c0b5e3c28cf721cc8ea4e9b7ff37608ab1161287f37697f7bf&w=740";
        user.setAvatarImage(readImage(imgUrl));

        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("Đăng ký thành công!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateToken(customUserDetails);
            List<String> listRoles = customUserDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority()).toList();
            User user = userService.findByAccountEmail(customUserDetails.getEmail());

            return ResponseEntity.ok(
                    new JwtResponse(jwt,
                            user.getId(),
                            user.getName(),
                            customUserDetails.getEmail(),
                            listRoles
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Thông tin đăng nhập không chính xác!"));
        }

    }

    private byte[] readImage(String url) throws Exception{
        URL imgUrl = new URL(url);
        BufferedImage bImg = ImageIO.read(imgUrl);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImg, "jpg", bos);
        return bos.toByteArray();
    }

}
