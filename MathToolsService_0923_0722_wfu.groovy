// 代码生成时间: 2025-09-23 07:22:38
package services

import grails.transaction.Transactional

/**
 * Service providing a set of mathematical operations.
 */
@Transactional
class MathToolsService {

    /**
     * Adds two numbers.
     *
     * @param a First number
     * @param b Second number
     * @return The sum of a and b
     */
    Double add(Double a, Double b) {
        return a + b
    }

    /**
     * Subtracts two numbers.
     *
     * @param a First number
     * @param b Second number
     * @return The difference between a and b
     */
    Double subtract(Double a, Double b) {
        return a - b
    }

    /**
     * Multiplies two numbers.
     *
     * @param a First number
     * @param b Second number
     * @return The product of a and b
     */
    Double multiply(Double a, Double b) {
        return a * b
    }

    /**
     * Divides two numbers.
     *
     * @param a First number
     * @param b Second number
     * @return The quotient of a and b
     * @throws ArithmeticException if b is zero
     */
    Double divide(Double a, Double b) {
        if (b == 0) {
            throw new ArithmeticException('Cannot divide by zero')
        }
        return a / b
    }

    /**
     * Calculates the power of a number.
     *
     * @param base The base number
     * @param exponent The exponent
     * @return The result of base raised to the power of exponent
     */
    Double power(Double base, Double exponent) {
        return Math.pow(base, exponent)
    }

    /**
     * Calculates the square root of a number.
     *
     * @param number The number to find the square root of
     * @return The square root of the number
     * @throws IllegalArgumentException if number is negative
     */
    Double squareRoot(Double number) {
        if (number < 0) {
            throw new IllegalArgumentException('Cannot calculate square root of a negative number')
        }
        return Math.sqrt(number)
    }

    /**
     * Calculates the factorial of a non-negative integer.
     *
     * @param n The non-negative integer
     * @return The factorial of n
     * @throws IllegalArgumentException if n is negative
     */
    BigInteger factorial(Integer n) {
        if (n < 0) {
            throw new IllegalArgumentException('Cannot calculate factorial of a negative number')
        }
        return (0..n).collect { BigInteger it }.sum()
    }
}
