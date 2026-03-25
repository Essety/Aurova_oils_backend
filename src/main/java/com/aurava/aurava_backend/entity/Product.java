package com.aurava.aurava_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

     @Column(nullable = false)
    private Double price;

    private String category;

    private String imageUrl;
    
    @JsonIgnore
    @Column(nullable = false)
    @Builder.Default
    private Integer stock= 0;

    @Version
    private Integer version;
}