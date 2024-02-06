package com.example.loginplsql.daos;

import com.example.loginplsql.models.Presenza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenzaRepository extends JpaRepository<Presenza, Integer> {
}
