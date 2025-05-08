package com.user.app.service;

import java.util.List;
import java.util.Optional;

import com.user.app.dto.UserDTORequest;
import com.user.app.jpa.repository.UserRepository;
import com.user.app.jpa.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public User createUser(UserDTORequest userDTORequest) {
        User user = new User();
        user.setEmailId(userDTORequest.emailId());
        user.setFullName(userDTORequest.fullName());
        user.setRole(userDTORequest.role());
        user.setPassword(passwordEncoder.encode(userDTORequest.password()));
        user.setAddressLine1(userDTORequest.addressLine1());
        user.setAddressLine2(userDTORequest.addressLine2());
        user.setCity(userDTORequest.city());
        user.setMobileNo(userDTORequest.mobileNo());
        user.setProfilePicUrl(userDTORequest.profilePicUrl());
        return repository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(UserDTORequest userDTORequest) {
        User dbUser = this.findUserById(userDTORequest.userId());
        dbUser.setEmailId(userDTORequest.emailId());
        dbUser.setFullName(userDTORequest.fullName());
        dbUser.setRole(userDTORequest.role());
        dbUser.setAddressLine1(userDTORequest.addressLine1());
        dbUser.setAddressLine2(userDTORequest.addressLine2());
        dbUser.setCity(userDTORequest.city());
        dbUser.setMobileNo(userDTORequest.mobileNo());
        dbUser.setProfilePicUrl(userDTORequest.profilePicUrl());
        return repository.save(dbUser);
    }

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User findUserByEmailId(String emailId) {
        return repository.findByEmailId(emailId).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + emailId));
    }

    @Override
    public boolean passwordEncodeMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public User findUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteByUserId(Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }


}
