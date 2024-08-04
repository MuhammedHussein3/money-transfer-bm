package com.bm.transfer.Favorite.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record FavoriteGetResponse(

         String recipientName
) {
}
