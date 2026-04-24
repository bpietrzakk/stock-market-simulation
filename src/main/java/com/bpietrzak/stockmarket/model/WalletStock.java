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
    private Integer quantity;

    // CONSTRUCTORS
    public WalletStock() {}

    public WalletStock(Wallet wallet, String name, Integer quantity) {
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

    public Integer getQuantity() {
        return quantity;
    }

    // SETTERS
    public void setQuantity(Integer quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
    }

}
