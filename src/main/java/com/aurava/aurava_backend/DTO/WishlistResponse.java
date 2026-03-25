package com.aurava.aurava_backend.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WishlistResponse {

    private Long wishlistId;
    private List<WishlistItemResponse> products; // simple (you can expand later)
}