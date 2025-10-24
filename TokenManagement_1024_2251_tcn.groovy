// 代码生成时间: 2025-10-24 22:51:33
package com.grails.tokenmanagement

import grails.transaction.Transactional

// 治理代币系统
@Transactional
class TokenManagementService {

    // 治理代币的存储结构
    static class Token {
        Long id
        String symbol
        BigDecimal balance
        BigDecimal supply
    }

    // 存储治理代币的列表
    private List<Token> tokens = []

    // 添加新的治理代币
    Token addToken(String symbol, BigDecimal initialSupply) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException('Token symbol cannot be null or empty.')
        }
        if (initialSupply == null || initialSupply.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException('Initial supply must be greater than zero.')
        }

        Token newToken = new Token(symbol: symbol, balance: BigDecimal.ZERO, supply: initialSupply)
        tokens.add(newToken)
        return newToken
    }

    // 获取所有治理代币
    List<Token> getAllTokens() {
        return tokens
    }

    // 获取特定治理代币的详情
    Token getTokenDetails(String symbol) {
        tokens.find { it.symbol == symbol } ?: null
    }

    // 增加治理代币的余额
    Token increaseTokenBalance(String symbol, BigDecimal amount) {
        Token token = tokens.find { it.symbol == symbol }
        if (token == null) {
            throw new IllegalArgumentException('Token not found.')
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException('Amount must be greater than zero.')
        }

        token.balance = token.balance + amount
        token.supply = token.supply + amount
        return token
    }

    // 减少治理代币的余额
    Token decreaseTokenBalance(String symbol, BigDecimal amount) {
        Token token = tokens.find { it.symbol == symbol }
        if (token == null) {
            throw new IllegalArgumentException('Token not found.')
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException('Amount must be greater than zero.')
        }

        if (token.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException('Insufficient balance to decrease.')
        }

        token.balance = token.balance - amount
        return token
    }
}

// 控制器类，处理HTTP请求
class TokenManagementController {
    def tokenManagementService

    def addToken() {
        String symbol = params.symbol
        BigDecimal initialSupply = params.initialSupply.toBigDecimal()
        try {
            Token newToken = tokenManagementService.addToken(symbol, initialSupply)
            render(status: 200, text: "Token ${symbol} added with initial supply: ${initialSupply}", contentType: 'text/plain')
        } catch (IllegalArgumentException e) {
            render(status: 400, text: e.message, contentType: 'text/plain')
        }
    }

    def getTokenDetails() {
        String symbol = params.symbol
        Token token = tokenManagementService.getTokenDetails(symbol)
        if (token) {
            render(status: 200, text: "Token: ${token.symbol}, Balance: ${token.balance}, Total Supply: ${token.supply}", contentType: 'text/plain')
        } else {
            render(status: 404, text: "Token not found.", contentType: 'text/plain')
        }
    }

    def increaseBalance() {
        String symbol = params.symbol
        BigDecimal amount = params.amount.toBigDecimal()
        try {
            Token token = tokenManagementService.increaseTokenBalance(symbol, amount)
            render(status: 200, text: "Token ${symbol} balance increased by ${amount}", contentType: 'text/plain')
        } catch (IllegalArgumentException e) {
            render(status: 400, text: e.message, contentType: 'text/plain')
        }
    }

    def decreaseBalance() {
        String symbol = params.symbol
        BigDecimal amount = params.amount.toBigDecimal()
        try {
            Token token = tokenManagementService.decreaseTokenBalance(symbol, amount)
            render(status: 200, text: "Token ${symbol} balance decreased by ${amount}", contentType: 'text/plain')
        } catch (IllegalArgumentException e) {
            render(status: 400, text: e.message, contentType: 'text/plain')
        }
    }
}