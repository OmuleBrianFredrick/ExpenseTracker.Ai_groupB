/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author USER
 */
// File: src/Transaction.java

import java.time.LocalDate;

/**
 * Represents a single financial transaction.
 */
public class Transaction {
    private LocalDate date;
    private double amount;
    private String description;

    /**
     * Constructs a Transaction with the specified date, amount, and description.
     *
     * @param date        the date of the transaction
     * @param amount      the amount of the transaction
     * @param description the description of the transaction
     */
    public Transaction(LocalDate date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    // Getter and Setter methods

    /**
     * Returns the date of the transaction.
     *
     * @return the transaction date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the transaction.
     *
     * @param date the transaction date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the amount of the transaction.
     *
     * @return the transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the transaction.
     *
     * @param amount the transaction amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the description of the transaction.
     *
     * @return the transaction description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the transaction.
     *
     * @param description the transaction description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a string representation of the transaction.
     *
     * @return a string containing the date, amount, and description
     */
    @Override
    public String toString() {
        return "Transaction{" +
               "date=" + date +
               ", amount=" + amount +
               ", description='" + description + '\'' +
               '}';
    }
}
