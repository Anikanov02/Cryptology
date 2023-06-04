package org.example;

import java.math.BigInteger;
import java.security.SecureRandom;

public class DiffieHellman {
    private BigInteger p; // Prime number
    private BigInteger g; // Generator
    private BigInteger privateKey;
    private BigInteger publicKey;

    public DiffieHellman() {
        generateKeys();
    }

    public void generateKeys() {
        // Choose a large prime number p
        p = BigInteger.probablePrime(512, new SecureRandom());

        // Choose a generator g
        g = new BigInteger("2"); // Commonly used value for g

        // Generate a private key
        privateKey = new BigInteger(p.bitLength() - 2, new SecureRandom());

        // Generate the corresponding public key
        publicKey = g.modPow(privateKey, p);
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getSharedSecret(BigInteger receivedPublicKey) {
        return receivedPublicKey.modPow(privateKey, p);
    }

    public static void main(String[] args) {
        DiffieHellman alice = new DiffieHellman();
        DiffieHellman bob = new DiffieHellman();

        // Alice generates her public key
        BigInteger alicePublicKey = alice.getPublicKey();

        // Bob generates his public key
        BigInteger bobPublicKey = bob.getPublicKey();

        // Alice and Bob exchange public keys

        // Alice calculates the shared secret using Bob's public key
        BigInteger aliceSharedSecret = alice.getSharedSecret(bobPublicKey);

        // Bob calculates the shared secret using Alice's public key
        BigInteger bobSharedSecret = bob.getSharedSecret(alicePublicKey);

        // The shared secrets should be the same

        // Print Alice's shared secret
        System.out.println("Alice's shared secret: " + aliceSharedSecret);

        // Print Bob's shared secret
        System.out.println("Bob's shared secret: " + bobSharedSecret);
    }
}
