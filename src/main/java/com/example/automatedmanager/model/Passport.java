package com.example.automatedmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "passport")
@NoArgsConstructor
@Data
public class Passport implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "series")
    private int passportSeries;

    @Column(name = "number")
    private int passportNumber;
}
