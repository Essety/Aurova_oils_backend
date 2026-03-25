package com.aurava.aurava_backend.entity;


import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Cart cart;

     @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Product product;
    
     @NotNull
     @Min(1)
    @Column(nullable = false)
    private Integer quantity;
    
     @NotNull
     @DecimalMin("0.0")
    @Column(nullable = false)
    private Double price; // snapshot price
}