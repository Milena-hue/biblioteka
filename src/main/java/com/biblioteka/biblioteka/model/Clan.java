package com.biblioteka.biblioteka.model;
import jakarta.persistence.*;
@Entity
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ime;
    private String prezime;
    private String email;
    private String brojClanskeKarte;

    public Clan() {}

    public Clan(String ime, String prezime, String email, String brojClanskeKarte) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.brojClanskeKarte = brojClanskeKarte;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getBrojClanskeKarte() { return brojClanskeKarte; }
    public void setBrojClanskeKarte(String brojClanskeKarte) { this.brojClanskeKarte = brojClanskeKarte; }
}
