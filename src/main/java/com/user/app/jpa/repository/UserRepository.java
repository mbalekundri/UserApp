package com.user.app.jpa.repository;

import java.util.Optional;

import com.user.app.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByEmailId(String emailId);

}
