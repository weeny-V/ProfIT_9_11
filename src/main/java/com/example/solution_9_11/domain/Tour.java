package com.example.solution_9_11.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnoreProperties("tour")
    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Client> clients = new ArrayList<>();

    public Tour(String name) {
        this.name = name;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name=" + name +
                "}";
    }
}
