package com.user.app.controller;

import com.user.app.dto.APIResponse;
import com.user.app.dto.UserDTORequest;
import com.user.app.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.user.app.dto.UserLoginRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.user.app.jpa.model.User;
import java.util.List;

@RestController
@RequestMapping("/UserApp/api/Users")
@Tag(name = "User Registration and Management", description = "APIs for user registration, retrieval, update, delete and login")
@CrossOrigin("http://localhost:4200")
public class RegistrationController {

    @Autowired
    private IUserService userService;


    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping(value = "/CreateNewUser", consumes = "application/json")
    public ResponseEntity<APIResponse> createUser(@RequestBody UserDTORequest userDTORequest) {
        User savedUser = userService.createUser(userDTORequest);
        return new ResponseEntity<>(
                new APIResponse("User created successfully", true, savedUser),
                HttpStatus.CREATED
        );
    }

    @PutMapping(value = "/updateUser", consumes = "application/json")
    public ResponseEntity<APIResponse> updateUser(@RequestBody UserDTORequest userDTORequest) {
        User savedUser = userService.updateUser(userDTORequest);
        return new ResponseEntity<>(
                new APIResponse("User updated successfully", true, savedUser),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Retrieve all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class)))
    })
    @GetMapping(value = "/GetAllUsers")
    public ResponseEntity<APIResponse> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(
                new APIResponse("Users retrieved successfully", true, users),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Login a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid password",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<APIResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            User user =  userService.findUserByEmailId(userLoginRequest.emailId());

            if (user != null && !userService.passwordEncodeMatches(userLoginRequest.password(), user.getPassword())) {
                return new ResponseEntity<>(
                        new APIResponse("Invalid password", false),
                        HttpStatus.UNAUTHORIZED
                );
            }
            return new ResponseEntity<>(
                    new APIResponse("Login successful", true, user),
                    HttpStatus.OK
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(
                    new APIResponse(e.getMessage(), false),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Operation(summary = "Find a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found",
                content = @Content)
    })
    @GetMapping(value = "/GetUserById", produces = "application/json")
    public ResponseEntity<APIResponse> getUserById(@RequestParam Long id) {
        User user = userService.findUserById(id);
        if (user != null) {
            return new ResponseEntity<>(
                    new APIResponse("User found with id: " + id, true, user),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new APIResponse("Invalid userId: " + id, false),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Operation(summary = "Delete a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found",
                content = @Content)
    })
    @DeleteMapping(value = "/DeleteUserById")
    public ResponseEntity<?> deleteUserById(@RequestParam Long id) {
        try {
            userService.deleteByUserId(id);
            return new ResponseEntity<>(
                    new APIResponse("User deleted with id: " + id, true),
                    HttpStatus.NO_CONTENT
            );
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(
                    new APIResponse("Invalid userId: " + id, false),
                    HttpStatus.NOT_FOUND
            );
        }
    }

}