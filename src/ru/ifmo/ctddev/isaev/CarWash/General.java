package ru.ifmo.ctddev.isaev.CarWash;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Xottab
 * Date: 14.01.14
 */
public class General {
    public static final String PREFERENCES = "CarWashPrefs";
    public static final String NAME = "CarWashName";
    public static final String BOX_NUMBER = "CarWashBoxNumber";
    public static String REAL_NAME = null;
    public static int NUMBER_OF_BOXES = 0;
    public static HashMap<Integer, ArrayList<Integer>> freeTime = new HashMap<Integer, ArrayList<Integer>>();

    public static String formatTime(int time) {
        StringBuilder builder = new StringBuilder();
        int hours = time / 2 + 8;
        String mins = time % 2 == 1 ? "30" : "00";
        builder.append(hours < 10 ? "0" + hours : hours);
        builder.append(":").append(mins);
        return builder.toString();
    }
}
