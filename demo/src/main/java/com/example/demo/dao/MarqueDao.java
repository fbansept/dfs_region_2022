package com.example.demo.dao;

import com.example.demo.model.Marque;
import com.example.demo.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueDao extends JpaRepository<Marque, Integer> {

}