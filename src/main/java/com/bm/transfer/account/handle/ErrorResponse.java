package com.bm.transfer.account.handle;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors
){
}
