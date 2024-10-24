package com.project.FahsionStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String phoneNumber;
    private String gender;

    @Lob
    @Column(
            name = "avatar_image",
            columnDefinition = "MEDIUMBLOB"
    )
    private byte[] avatarImage;

    @OneToMany(
            mappedBy = "user"
    )
    private List<Address> address;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "account_id"
    )
    @JsonIgnore
    private Account account;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "setting_id"
    )
    @JsonIgnore
    private UserSetting setting;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Favorite> favoriteList;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Search> searchList;

}
