package com.biblioteka.biblioteka.model;
import jakarta.persistence.*;
@Entity
public class Knjiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naslov;
    private String isbn;
    private Integer godinaIzdanja;
    private Boolean dostupna;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Knjiga() {}

    public Knjiga(String naslov, String isbn, Integer godinaIzdanja, Boolean dostupna, Autor autor) {
        this.naslov = naslov;
        this.isbn = isbn;
        this.godinaIzdanja = godinaIzdanja;
        this.dostupna = dostupna;
        this.autor = autor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNaslov() { return naslov; }
    public void setNaslov(String naslov) { this.naslov = naslov; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getGodinaIzdanja() { return godinaIzdanja; }
    public void setGodinaIzdanja(Integer godinaIzdanja) { this.godinaIzdanja = godinaIzdanja; }
    public Boolean getDostupna() { return dostupna; }
    public void setDostupna(Boolean dostupna) { this.dostupna = dostupna; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
}
