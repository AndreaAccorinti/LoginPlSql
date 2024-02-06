package com.example.loginplsql.daos;

import com.example.loginplsql.models.Presenze;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresenzeRepository extends JpaRepository<Presenze, Integer> {
}
