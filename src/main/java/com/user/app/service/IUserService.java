package com.user.app.service;

import com.user.app.dto.UserDTORequest;
import com.user.app.jpa.model.User;

import java.util.List;

public interface IUserService {

    User createUser(UserDTORequest userDTORequest);

    User updateUser(UserDTORequest userDTORequest);

    List<User> findAllUsers();

    User findUserByEmailId(String emailId);

    boolean passwordEncodeMatches(String rawPassword, String encodedPassword);

    User findUserById(Long id);

    void deleteByUserId(Long id);

}
