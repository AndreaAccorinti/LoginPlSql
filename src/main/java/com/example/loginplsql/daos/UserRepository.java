package com.example.loginplsql.daos;

import com.example.loginplsql.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
@Query(value = "SELECT*FROM user_f WHERE username =:username AND password = :password", nativeQuery = true)
    User userLogin(String username, String password);
    @Query(value = "select * from user_f where username =:username", nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "select * from user_f where email =:email and password =:password", nativeQuery = true)
    User findByEmailAndPassword(String email, String password);
}
