package com.bpietrzak.stockmarket.model;

import jakarta.persistence.*;

@Entity
@Table(name="bank_stocks")
public class BankStock {
    // id - Long PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement 1, 2, 3...
    private Long id;

    // name - unique (there is only one name)
    @Column(unique = true, nullable = false)
    private String name;

    // quantity - integer
    @Column(nullable = false)
    private int quantity;

    // CONSTRUCTORS
    public BankStock() {}

    public BankStock(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    // GETTERS
    public Long getId() {
        return id;
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
