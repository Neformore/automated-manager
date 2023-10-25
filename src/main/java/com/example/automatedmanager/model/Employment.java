package com.example.automatedmanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "employment")
@NoArgsConstructor
@Data
public class Employment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "start_work")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startWork;

    @Column(name = "stop_work")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date stopWork;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "organization_name")
    private String organizationName;

    @OneToOne(mappedBy = "employmentId")
    private Client client;
}
