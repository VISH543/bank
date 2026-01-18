package com.consumer.dto;

public class AccountDTO {
    private Long id;
    private String holderName;
    private double balance;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
