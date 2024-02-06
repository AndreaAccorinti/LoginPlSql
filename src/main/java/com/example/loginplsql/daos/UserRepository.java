package com.example.loginplsql.daos;

import com.example.loginplsql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
@Query(value = "SELECT*FROM users WHERE username =:username AND password = :password", nativeQuery = true)
    User userLogin(String username, String password);
    @Query(value = "select * from users where username =:username", nativeQuery = true)
    User findByUsername(String username);
}
