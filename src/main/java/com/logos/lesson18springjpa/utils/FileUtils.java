package com.logos.lesson18springjpa.utils;

import java.io.File;

public class FileUtils {
    public static String getImagesfolder(){
       return System.getProperty("user.home") + File.separator + "images" + File.separator;
    }
}
