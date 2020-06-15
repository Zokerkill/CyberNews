/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melnikov
 */
public class Encription {

    public Encription() {
    }
    public String getEncriptionPass(String password){
        try {
            MessageDigest m;
            m = MessageDigest.getInstance("SHA-256");
            m.update(password.getBytes(),0,password.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encription.class.getName())
               .log(Level.SEVERE, "Не поддерживается алгоритм шифрования", ex);
            return null;
        }
        
    }
    
}
