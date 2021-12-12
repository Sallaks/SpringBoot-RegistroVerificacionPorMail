package com.example.demo.Repositories;

import com.example.demo.Entitys.ConfirmationToken;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String>{
    
    @Query("SELECT c FROM ConfirmationToken c WHERE c.token = :token")
    public Optional<ConfirmationToken> findByToken(@Param("token") String token);
    
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.comfirmDate = :confirmedDate " +
            "WHERE c.token = :token")
    public int updateConfirmedDate(@Param("token") String token,
                          @Param("confirmedDate") LocalDateTime confirmedDate);
}
