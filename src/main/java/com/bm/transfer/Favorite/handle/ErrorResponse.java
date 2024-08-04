package com.bm.transfer.Favorite.handle;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
){
}
