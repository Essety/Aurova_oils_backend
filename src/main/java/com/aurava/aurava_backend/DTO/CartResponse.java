package com.aurava.aurava_backend.DTO;
import java.util.List;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CartResponse {

    private Long cartId;
    private List<CartItemResponse> items;
    private Double totalPrice;
}
