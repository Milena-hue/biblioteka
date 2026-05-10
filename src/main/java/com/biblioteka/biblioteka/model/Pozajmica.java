package com.biblioteka.biblioteka.model;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
public class Pozajmica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate datumPozajmice;
    private LocalDate datumVracanja;
    private Boolean vracena;

    @ManyToOne
    @JoinColumn(name = "knjiga_id")
    private Knjiga knjiga;

    @ManyToOne
    @JoinColumn(name = "clan_id")
    private Clan clan;

    public Pozajmica() {}

    public Pozajmica(LocalDate datumPozajmice, LocalDate datumVracanja, Boolean vracena, Knjiga knjiga, Clan clan) {
        this.datumPozajmice = datumPozajmice;
        this.datumVracanja = datumVracanja;
        this.vracena = vracena;
        this.knjiga = knjiga;
        this.clan = clan;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDatumPozajmice() { return datumPozajmice; }
    public void setDatumPozajmice(LocalDate datumPozajmice) { this.datumPozajmice = datumPozajmice; }
    public LocalDate getDatumVracanja() { return datumVracanja; }
    public void setDatumVracanja(LocalDate datumVracanja) { this.datumVracanja = datumVracanja; }
    public Boolean getVracena() { return vracena; }
    public void setVracena(Boolean vracena) { this.vracena = vracena; }
    public Knjiga getKnjiga() { return knjiga; }
    public void setKnjiga(Knjiga knjiga) { this.knjiga = knjiga; }
    public Clan getClan() { return clan; }
    public void setClan(Clan clan) { this.clan = clan; }
}
