package com.project.FahsionStore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int price;
    private int isAvailable = 1;
    private int sales;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private List<Color> colors;

    private String material;
    private String style;
    private String gender;

    private Double weight;
    private Double height;
    private Double width;
    private Double depth;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ElementCollection
    private List<String> tags;

    private Double discount;

    private int clickAfterSuggestByAI;

    @ManyToMany
    private List<Category> categories;

}
