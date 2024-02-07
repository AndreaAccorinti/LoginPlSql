package com.example.loginplsql.daos;

import com.example.loginplsql.models.Presenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;

public interface PresenzaRepository extends JpaRepository<Presenza, Integer> {
    @Query(value = "select * from presenze where trunc(data) = trunc(:data) and username = :id", nativeQuery = true)
    Presenza findByDateAndUser(Instant data, Number id);
}
