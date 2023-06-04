package org.example;

import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    public RSA(int keySize) {
        generateKeys(keySize);
    }

    public static void main(String[] args) {
        // Create an instance of RSA with a key size of 1024 bits
        RSA rsa = new RSA(1024);

        // Define a plaintext message as a BigInteger
        final BigInteger plaintext = new BigInteger("123456789");

        // Encrypt the plaintext message
        final BigInteger ciphertext = rsa.encrypt(plaintext);

        // Decrypt the ciphertext message
        final BigInteger decryptedText = rsa.decrypt(ciphertext);

        // Print the original plaintext, ciphertext, and decrypted text
        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public void generateKeys(int keySize) {
        final Random rnd = new Random();

        // Generate two prime numbers p and q of size keySize/2
        final BigInteger p = BigInteger.probablePrime(keySize / 2, rnd);
        final BigInteger q = BigInteger.probablePrime(keySize / 2, rnd);

        // Calculate the Euler's totient function φ(n)
        final BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Calculate the modulus n = p * q
        modulus = p.multiply(q);

        // Choose a random public key e such that 1 < e < φ(n) and gcd(e, φ(n)) = 1
        publicKey = BigInteger.probablePrime(keySize, rnd);

        while (phi.gcd(publicKey).compareTo(BigInteger.ONE) > 0 && publicKey.compareTo(phi) < 0) {
            publicKey = publicKey.add(BigInteger.ONE);
        }

        // Calculate the private key d as the modular multiplicative inverse of e modulo φ(n)
        privateKey = publicKey.modInverse(phi);
    }

    public BigInteger encrypt(BigInteger message) {
        // Encrypt the message using the public key (ciphertext = message^e mod n)
        return message.modPow(publicKey, modulus);
    }

    public BigInteger decrypt(BigInteger encryptedMessage) {
        // Decrypt the encrypted message using the private key (plaintext = encryptedMessage^d mod n)
        return encryptedMessage.modPow(privateKey, modulus);
    }
}
