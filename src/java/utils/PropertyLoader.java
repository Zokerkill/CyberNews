/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ResourceBundle;

/**
 *
 * @author Melnikov
 */
public class PropertyLoader {
    private final static ResourceBundle resource = ResourceBundle.getBundle("properties.imageFolder");
    public static String getFolderPath(String key){
        return resource.getString(key);
    }
}
