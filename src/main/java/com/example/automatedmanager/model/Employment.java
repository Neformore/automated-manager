package com.example.automatedmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employment")
@NoArgsConstructor
@Data
public class Employment implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "start_work")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startWork;

    @Column(name = "end_work")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endWork;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "organization_name")
    private String organizationName;

}
