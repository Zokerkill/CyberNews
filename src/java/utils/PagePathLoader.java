/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ResourceBundle;

/**
 *
 * @author jvm
 */
public class PagePathLoader {
    private final static ResourceBundle pagePath = ResourceBundle.getBundle("properties.pagePath");
    public static String getPagePath(String key){
        return pagePath.getString(key);
    }
}
