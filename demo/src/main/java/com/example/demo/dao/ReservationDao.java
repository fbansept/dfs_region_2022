package com.example.demo.dao;

import com.example.demo.model.CleReservation;
import com.example.demo.model.Reservation;
import com.example.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, CleReservation> {

    Optional<Reservation> findByEmprunteurIdAndMaterielIdAndDate(
            Integer emprunteurId,
            Integer materielId,
            Date date
    );

    void deleteByEmprunteurIdAndMaterielIdAndDate(
            Integer emprunteurId,
            Integer materielId,
            Date date
    );

}