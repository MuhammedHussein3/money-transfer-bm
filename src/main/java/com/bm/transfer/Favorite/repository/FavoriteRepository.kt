package com.bm.transfer.Favorite.repository

import com.bm.transfer.Favorite.dto.response.FavoriteGetResponse
import com.bm.transfer.Favorite.entity.Favorite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
interface FavoriteRepository : JpaRepository<Favorite, Long> {

    @Query("""
    SELECT f
    FROM Favorite f
    WHERE f.user.accountNumber = :accountNumber
   """)
    fun getFavoriteRecipients(@Param("accountNumber") accountNumber: String) : List<Favorite>


    @Modifying
    fun deleteFavoritesByUserAccountNumberAndRecipientAccountNumber(
        @Param("accountNumber") accountNumber: String,
        @Param("recipientName") recipientName: String
    ): Int
}

