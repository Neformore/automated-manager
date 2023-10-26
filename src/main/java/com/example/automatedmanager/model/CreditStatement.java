package com.example.automatedmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_statement")
@NoArgsConstructor
@Data
public class CreditStatement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public CreditStatement(boolean status, Client client) {
        this.status = status;
        this.client = client;
    }
}
