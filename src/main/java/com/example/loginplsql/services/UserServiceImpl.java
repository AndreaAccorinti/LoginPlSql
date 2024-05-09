package com.example.loginplsql.services;

import com.example.loginplsql.daos.UserRepository;
import com.example.loginplsql.exception.UserNotFoundException;
import com.example.loginplsql.models.LoginResponse;
import com.example.loginplsql.models.User;
import io.jsonwebtoken.Jwts;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository daoUser;
    private String token;
    private boolean isLogged;


    /**
     * Authenticate a user and generate a JWT token.
     *
     * @param user User to authenticate.
     */

    public void authenticate(User user) {
        this.setToken(Jwts.builder()
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .setSubject(user.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(7200000)))
                .compact());
        this.setLogged(true);
    }

    /**
     * Update user details.
     *
     * @param id          The ID of the user to update.
     * @param userDetails Details to be updated.
     * @return Updated user.
     */
    public User updateUser(int id, User userDetails) {
        User user = daoUser.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUsername(userDetails.getUsername() != null ? userDetails.getUsername() : user.getUsername());
        user.setPassword(userDetails.getPassword() != null ? userDetails.getPassword() : user.getPassword());
        user.setName(userDetails.getName() != null ? userDetails.getName() : user.getName());
        user.setSurname(userDetails.getSurname() != null ? userDetails.getSurname() : user.getSurname());
        user.setEmail(userDetails.getEmail() != null ? userDetails.getEmail() : user.getEmail());
        user.setTelephone(userDetails.getTelephone() != null ? userDetails.getTelephone() : user.getTelephone());
        user.setRole(userDetails.getRole() != null ? userDetails.getRole() : user.getRole());
        return daoUser.save(user);
    }

    /**
     * Find all users.
     *
     * @return List of all users.
     */
    public List<User> findAllUsers() {
        return daoUser.findAll();
    }

    /**
     * Add a new user.
     *
     * @param user User to add.
     * @return Added user.
     */
    public User addUser(User user) {
        return daoUser.save(user);
    }

    /**
     * Find a user by ID.
     *
     * @param id User ID to find.
     * @return Found user.
     */
    public User findUserById(int id) {
        return daoUser.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Find a user by username.
     *
     * @param username Username to search for.
     * @return Found user.
     */
    public User findUserByUsername(String username) {
        return daoUser.findByUsername(username);
    }

    /**
     * Delete a user by ID.
     *
     * @param id User ID to delete.
     */
    public void deleteUserById(int id) {
        daoUser.deleteById(id);
    }

    /**
     * Handle user login.
     *
     * @param request Login request details.
     * @return ResponseEntity with login response.
     */
    public ResponseEntity<LoginResponse> login(User request) {
        User user = daoUser.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid username or password!"));
        }
        authenticate(user);
        user.setPassword(null); // Ensure password is not sent back
        return ResponseEntity.ok(new LoginResponse("Login successful", getToken(), user));
    }
    /**
     * Verifies whether the user is currently logged in and if the provided token is valid.
     * This method combines checks for both the logged-in state and token validity to ensure
     * that the user session is both authenticated and authorized to perform operations.
     *
     * @param token The token to be validated against the stored token.
     * @return {@code true} if the user is logged in and the token matches the stored token;
     *         {@code false} otherwise.
     */
    public boolean verifyUserAndToken(String token) {
        return isLogged() && checkToken(token);
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean checkToken(String token) {
        return this.token.equals(token);
    }
}

