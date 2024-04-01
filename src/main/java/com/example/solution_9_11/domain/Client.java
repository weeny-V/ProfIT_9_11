package com.example.solution_9_11.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @JsonIgnoreProperties("clients")
    @ManyToOne
    private Tour tour;

    public Client(String name, String surname, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", birthDate=" + birthDate +
                "}";
    }
}
