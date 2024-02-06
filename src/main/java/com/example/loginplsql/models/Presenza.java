package com.example.loginplsql.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "PRESENZE")
public class Presenza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRESENZE", nullable = true)
    private Integer id;

    @Column(name = "DATA")
    private Instant data;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "USERNAME")
    private User username;

    @Size(max = 100)
    @Column(name = "DESCRIZIONE", length = 100)
    private String descrizione;

    @Column(name = "INIZIO_MATTINA")
    private Instant inizioMattina;

    @Column(name = "FINE_MATTINA")
    private Instant fineMattina;

    @Column(name = "INIZIO_POMERIGGIO")
    private Instant inizioPomeriggio;

    @Column(name = "FINE_POMERIGGIO")
    private Instant finePomeriggio;

    @Column(name = "RIMBORSO_SPESE")
    private Long rimborsoSpese;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Instant getInizioMattina() {
        return inizioMattina;
    }

    public void setInizioMattina(Instant inizioMattina) {
        this.inizioMattina = inizioMattina;
    }

    public Instant getFineMattina() {
        return fineMattina;
    }

    public void setFineMattina(Instant fineMattina) {
        this.fineMattina = fineMattina;
    }

    public Instant getInizioPomeriggio() {
        return inizioPomeriggio;
    }

    public void setInizioPomeriggio(Instant inizioPomeriggio) {
        this.inizioPomeriggio = inizioPomeriggio;
    }

    public Instant getFinePomeriggio() {
        return finePomeriggio;
    }

    public void setFinePomeriggio(Instant finePomeriggio) {
        this.finePomeriggio = finePomeriggio;
    }

    public Long getRimborsoSpese() {
        return rimborsoSpese;
    }

    public void setRimborsoSpese(Long rimborsoSpese) {
        this.rimborsoSpese = rimborsoSpese;
    }

}
