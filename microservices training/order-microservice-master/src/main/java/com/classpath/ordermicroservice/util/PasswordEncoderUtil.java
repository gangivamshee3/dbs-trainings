package com.classpath.ordermicroservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtil {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "welcome";

        String encodedPassword1 = encoder.encode(rawPassword);
        String encodedPassword2 = encoder.encode(rawPassword);
        String encodedPassword3 = encoder.encode(rawPassword);
        String encodedPassword4 = encoder.encode(rawPassword);

        System.out.println("Encoded password - 1 : " +encodedPassword1);
        System.out.println("Encoded password - 2 : " +encodedPassword2);
        System.out.println("Encoded password - 3 : " +encodedPassword3);
        System.out.println("Encoded password - 4 : " +encodedPassword4);

        System.out.println("Passwords do match ? "+ encoder.matches(rawPassword, encodedPassword1));
        System.out.println("Passwords do match ? "+ encoder.matches(rawPassword, encodedPassword2));
        System.out.println("Passwords do match ? "+ encoder.matches(rawPassword, encodedPassword3));
        System.out.println("Passwords do match ? "+ encoder.matches(rawPassword, encodedPassword4));
    }
}