package com.bm.transfer.transaction.dto.request;

import org.springframework.data.domain.Sort;

public record TransactionRequestHistory(
        int pageNumb,

        int pageSize,

        Sort sort
) {
}
