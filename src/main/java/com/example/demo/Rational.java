package com.example.demo;

import java.math.BigInteger;
import java.util.Objects;
 
public class Rational implements Comparable<Rational> {
    private final int numerator;
    private final int denominator;
 
    public static final Rational ZERO = new Rational(0);
    public static final Rational ONE = new Rational(1);
    public static final Rational HALF = new Rational(1, 2);
    public static final Rational TWO = new Rational(2);
 
    public Rational(int num) {
        this(num, 1);
    }
 
    public Rational(int m, int n) {
        if (n == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        } else if (m == Integer.MIN_VALUE && n == -1) {
            throw new ArithmeticException("Rational cannot be represented");
        }
 
        int sign = (m < 0) != (n < 0) ? -1 : 1;
        int gcd = gcd(Math.abs(m), Math.abs(n));
        this.numerator = sign * Math.abs(m) / gcd;
        this.denominator = Math.abs(n) / gcd;
    }
 
    private Rational(long num, long denom) {
        this((int) num, (int) denom);
    }
 
    public String toString() {
        return String.format("%d/%d", numerator, denominator);
    }
 
    public static Rational fromString(String str) {
        String[] parts = str.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format for Rational: " + str);
        }
 
        try {
            int num = Integer.parseInt(parts[0]);
            int denom = Integer.parseInt(parts[1]);
            return new Rational(num, denom);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numbers in Rational: " + str);
        }
    }
 
    public double asDouble() {
        return (double) numerator / denominator;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Rational rational = (Rational) obj;
        return numerator == rational.numerator && denominator == rational.denominator;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
 
    @Override
    public int compareTo(Rational other) {
        BigInteger thisNum = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(other.denominator));
        BigInteger otherNum = BigInteger.valueOf(other.numerator).multiply(BigInteger.valueOf(this.denominator));
        return thisNum.compareTo(otherNum);
    }
 
    public Rational add(Rational rational) {
        Objects.requireNonNull(rational);
        long num = (long) this.numerator * rational.denominator + (long) rational.numerator * this.denominator;
        long denom = (long) this.denominator * rational.denominator;
        return new Rational(num, denom);
    }
 
    public Rational inv() {
        if (numerator == 0) {
            throw new ArithmeticException("Cannot compute inverse of zero");
        }
        return new Rational(denominator, numerator);
    }
 
    public Rational mul(Rational other) {
        return new Rational(this.numerator * other.numerator, this.denominator * other.denominator);
    }
 
    public Rational neg() {
        if (numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("Cannot negate: result outside representable range");
        }
        return new Rational(-numerator, denominator);
    }
 
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }
}