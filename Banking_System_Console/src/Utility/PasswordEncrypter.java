package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {
    public  String hashPassword(String password) {
        try {
            // 1. Get instance of SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 2. Hash the password
            byte[] hashBytes = md.digest(password.getBytes());

            // 3. Convert byte array to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: " + e);
        }
    }


//    public static void main(String[] args) {
//        PasswordEncrypter pe= new PasswordEncrypter();
//        String pass1= pe.hashPassword("Kali@123568");
//        String pass2 = pe.hashPassword("Kali@123568");
//
//        System.out.println(pass1);
//        System.out.println(pass2);
//
//        System.out.println(pass1.equals(pass2));
//
//    }
}
