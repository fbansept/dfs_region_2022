package com.example.demo.controller;

import com.example.demo.dao.UtilisateurDao;
import com.example.demo.model.Role;
import com.example.demo.model.Utilisateur;
import com.example.demo.security.JwtUtils;
import com.example.demo.security.UserDetailsDemo;
import com.example.demo.security.UserDetailsServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UtilisateurController {

    private UtilisateurDao utilisateurDao;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceDemo userDetailsServiceDemo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UtilisateurController(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }



    @PostMapping("/initialisation")
    public String initialisation() {

        for(int i = 1; i <= 100; i ++) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("Utilisateur fictif " + i);
            utilisateur.setMotDePasse("root");
            utilisateurDao.save(utilisateur);
        }

        return "utilisateurs créé";
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur) {

        utilisateur.setMotDePasse(encoder.encode(utilisateur.getMotDePasse()));

        utilisateurDao.save(utilisateur);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/connexion")
    public String connexion(@RequestBody Utilisateur utilisateur) throws Exception {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getNom(),
                            utilisateur.getMotDePasse()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception(e);
        }

        UserDetails userDetails = userDetailsServiceDemo
                .loadUserByUsername(utilisateur.getNom());

        return jwtUtils.generateToken(userDetails);

    }

    @GetMapping("/liste-utilisateur")
    public List<Utilisateur> listeUtilisateur () {

        return this.utilisateurDao.findAll();
    }

    @GetMapping("/utilisateur-par-nom/{nom}")
    public Utilisateur utilisateurParNom(@PathVariable String nom) {

        return this.utilisateurDao.findByNom(nom).orElse(null);
    }

    @GetMapping("/utilisateur/{id}")
    public Utilisateur utilisateur(@PathVariable Integer id) {

        return this.utilisateurDao.findById(id).orElse(null);

    }

    @PostMapping("/utilisateur")
    public String createUtilisateur(@RequestBody Utilisateur utilisateur) {

        this.utilisateurDao.save(utilisateur);

        return "ok";
    }

    @DeleteMapping("/utilisateur/{id}")
    public String deleteUtilisateur(@PathVariable int id){

        this.utilisateurDao.deleteById(id);

        return "ok";
    }

}
