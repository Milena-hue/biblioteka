package com.biblioteka.biblioteka.model;

import jakarta.persistence.*;
@Entity
public class Autor {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String nacionalnost;

    public Autor() {}

    public Autor(String ime, String prezime, String nacionalnost) {
        this.ime = ime;
        this.prezime = prezime;
        this.nacionalnost = nacionalnost;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getNacionalnost() { return nacionalnost; }
    public void setNacionalnost(String nacionalnost) { this.nacionalnost = nacionalnost; }
}
