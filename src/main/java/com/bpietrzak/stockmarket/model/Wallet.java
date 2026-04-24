package com.bpietrzak.stockmarket.model;

import jakarta.persistence.*; // JPA (ORM)
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.UUID) // PK set to UUID
    private UUID id;

    // constructor
    public Wallet() {}

    // getter id
    public UUID getId() {
        return id;
    }
}
