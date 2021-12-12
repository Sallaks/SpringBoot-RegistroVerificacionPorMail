package com.example.demo.Repositories;

import com.example.demo.Entitys.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    @Query("SELECT c FROM User c where c.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);
    
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.email = :email")
    public int enableUser(@Param("email") String email);
    
}
