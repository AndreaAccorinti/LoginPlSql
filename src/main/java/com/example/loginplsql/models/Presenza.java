package com.example.loginplsql.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "PRESENZE")
public class Presenza {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "presenze_seq_gen")
    @SequenceGenerator(name = "presenze_seq_gen", sequenceName = "PRESENZE_SEQ", allocationSize = 1)
    @Column(name = "ID_PRESENZE", nullable = false)
    private Integer id;

    @Column(name = "DATA")
    private Number data;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "USERNAME")
    private User username;

    @Size(max = 100)
    @Column(name = "DESCRIZIONE", length = 100)
    private String descrizione;

    @Column(name = "INIZIO_MATTINA")
    private Number inizioMattina;

    @Column(name = "FINE_MATTINA")
    private Number fineMattina;

    @Column(name = "INIZIO_POMERIGGIO")
    private Number inizioPomeriggio;

    @Column(name = "FINE_POMERIGGIO")
    private Number finePomeriggio;

    @Column(name = "RIMBORSO_SPESE")
    private Long rimborsoSpese;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Number getData() {
        return data;
    }

    public void setData(Number data) {
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

    public Number getInizioMattina() {
        return inizioMattina;
    }

    public void setInizioMattina(Number inizioMattina) {
        this.inizioMattina = inizioMattina;
    }

    public Number getFineMattina() {
        return fineMattina;
    }

    public void setFineMattina(Number fineMattina) {
        this.fineMattina = fineMattina;
    }

    public Number getInizioPomeriggio() {
        return inizioPomeriggio;
    }

    public void setInizioPomeriggio(Number inizioPomeriggio) {
        this.inizioPomeriggio = inizioPomeriggio;
    }

    public Number getFinePomeriggio() {
        return finePomeriggio;
    }

    public void setFinePomeriggio(Number finePomeriggio) {
        this.finePomeriggio = finePomeriggio;
    }

    public Long getRimborsoSpese() {
        return rimborsoSpese;
    }

    public void setRimborsoSpese(Long rimborsoSpese) {
        this.rimborsoSpese = rimborsoSpese;
    }

}
