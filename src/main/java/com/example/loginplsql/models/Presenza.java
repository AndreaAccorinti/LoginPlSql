package com.example.loginplsql.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Entity
@Table(name = "attendance")
public class Presenza {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "DATE_ATTENDANCE")
    private Timestamp data;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_USER")
    private User username;

    @Size(max = 100)
    @Column(name = "DESCRIPTION_", length = 100)
    private String descrizione;

    @Column(name = "MORNING_I")
    private Timestamp inizioMattina;

    @Column(name = "MORNING_E")
    private Timestamp fineMattina;

    @Column(name = "AFTERNOON_I")
    private Timestamp inizioPomeriggio;

    @Column(name = "AFTERNOON_E")
    private Timestamp finePomeriggio;

    @Column(name = "REIMBURSEMENT")
    private Long rimborsoSpese;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getRimborsoSpese() {
        return rimborsoSpese;
    }

    public void setRimborsoSpese(Long rimborsoSpese) {
        this.rimborsoSpese = rimborsoSpese;
    }

    public Timestamp getInizioMattina() {
        return inizioMattina;
    }

    public void setInizioMattina(Timestamp inizioMattina) {
        this.inizioMattina = inizioMattina;
    }

    public Timestamp getFineMattina() {
        return fineMattina;
    }

    public void setFineMattina(Timestamp fineMattina) {
        this.fineMattina = fineMattina;
    }

    public Timestamp getInizioPomeriggio() {
        return inizioPomeriggio;
    }

    public void setInizioPomeriggio(Timestamp inizioPomeriggio) {
        this.inizioPomeriggio = inizioPomeriggio;
    }

    public Timestamp getFinePomeriggio() {
        return finePomeriggio;
    }

    public void setFinePomeriggio(Timestamp finePomeriggio) {
        this.finePomeriggio = finePomeriggio;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Double sumWork() {
        Long time = Math.abs(fineMattina.getTime() - inizioMattina.getTime()) + Math.abs(finePomeriggio.getTime() - inizioPomeriggio.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
        return  Double.parseDouble(sdf.format(time)) - 1;
    }

    public String dayNameIta() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ITALIAN);
        return sdf.format(this.data);
    }
}
