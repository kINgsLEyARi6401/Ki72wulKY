// 代码生成时间: 2025-09-29 00:02:52
package com.example.crypto

/**
 * CryptoWallet class represents a cryptocurrency wallet
 * Each wallet has a unique identifier and a balance
 * The wallet supports deposit and withdrawal transactions
 */
class CryptoWallet {

    /**
     * Unique identifier for the wallet
     */
    String id

    /**
     * Balance of the wallet
     */
    BigDecimal balance = 0

    /**
     * Constructor to create a new wallet with a unique identifier
     * @param id The unique identifier for the wallet
     */
    CryptoWallet(String id) {
        this.id = id
    }

    /**
     * Deposits an amount to the wallet
     * @param amount The amount to deposit
     */
    void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative")
        }
        balance += amount
    }

    /**
     * Withdraws an amount from the wallet
     * @param amount The amount to withdraw
     */
    void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative")
        }
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal")
        }
        balance -= amount
    }

    /**
     * Checks if the wallet has sufficient funds for a withdrawal
     * @param amount The amount to check
     * @return true if the wallet has sufficient funds, false otherwise
     */
    boolean hasSufficientFunds(BigDecimal amount) {
        return amount.compareTo(balance) <= 0
    }

    /**
     * Custom exception for insufficient funds
     */
    static class InsufficientFundsException extends RuntimeException {
        InsufficientFundsException(String message) {
            super(message)
        }
    }
}
