package com.bm.transfer.transaction.dto;

import com.bm.transfer.transaction.dto.response.TransactionResponseDto;
import lombok.Builder;

import java.util.List;

@Builder
public record TransactionPageResponse(

        List<TransactionResponseDto> transactionsHistoryForThisAccount,

        Integer pageNumber,

        Integer pageSize,

        int totalElement,

        int totalPages,

        boolean isLast
) {
}
