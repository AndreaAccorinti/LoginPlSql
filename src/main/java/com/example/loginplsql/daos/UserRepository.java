package com.example.loginplsql.daos;

import com.example.loginplsql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    String USER_LOGIN = "SELECT * FROM user_f WHERE username = :username AND password = :password";
    String FIND_BY_USERNAME = "SELECT * FROM user_f WHERE username = :username";
    String FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user_f WHERE email = :email AND password = :password";

    @Query(value = USER_LOGIN, nativeQuery = true)
    User userLogin(String username, String password);

    @Query(value = FIND_BY_USERNAME, nativeQuery = true)
    User findByUsername(String username);

    @Query(value = FIND_BY_EMAIL_AND_PASSWORD, nativeQuery = true)
    User findByEmailAndPassword(String email, String password);
}
