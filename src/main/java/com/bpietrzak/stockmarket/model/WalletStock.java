package com.bpietrzak.stockmarket.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_stocks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"wallet_id", "name"})
})
public class WalletStock {
    // id PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    // CONSTRUCTORS
    public WalletStock() {}

    public WalletStock(Wallet wallet, String name, int quantity) {
        this.wallet = wallet;
        this.name = name;
        this.quantity = quantity;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    // SETTERS
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
}
