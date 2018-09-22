package edu.gatech.cs2340.app_4t5.models;

import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;

public class User {
    private static final int hash_size = 256;    // hash size in bits
    private String username;
    private byte[] salt;
    private byte[] password_hash;

    public User(String username, String password) {
        this.username = username;
        this.salt = generate_salt();
        this.password_hash = generate_pass_hash(password);
    }

    public boolean is_correct_password(String password) {
        return Arrays.equals(generate_pass_hash(password), password_hash);
    }

    public void set_password(String password) {
        this.salt = generate_salt();
        this.password_hash = generate_pass_hash(password);
    }

    public String getUsername() {
        return username;
    }

    private byte[] generate_salt() {
        SecureRandom rand = new SecureRandom();
        rand.setSeed(System.nanoTime());
        byte[] ret = new byte[hash_size/8];
        rand.nextBytes(ret);
        return ret;
    }

    private byte[] generate_pass_hash(String password) {
        int iterations = 1000;                  // TODO: how many iterations?
        char[] chars = password.toCharArray();
        byte[] ret;

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, hash_size);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            ret = skf.generateSecret(spec).getEncoded();
            return ret;
        } catch (Exception e) {     // TODO: Fix error handling
            System.out.println(e);
        }
        return null;
    }
}
