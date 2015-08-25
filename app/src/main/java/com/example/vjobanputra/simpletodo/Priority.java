package com.example.vjobanputra.simpletodo;

/**
 * Created by vjobanputra on 8/22/15.
 */
public class Priority {

    static String[] options = {"High",
                               "Medium",
                               "Low"};


    public static int getPosition(String s) {
        for (int i = 0; i <= options.length; i++) {
            if (options[i].equals(s)) {
                return i;
            }
        }
        return 0;
    }
}
