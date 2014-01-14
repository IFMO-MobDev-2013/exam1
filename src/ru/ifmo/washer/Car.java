package ru.ifmo.washer;

/**
 * Created by asus on 13.01.14.
 */

/**
 * Created by asus on 27.12.13.
 */
public class Car {
    final static String[] tags = new String[]{"ID", "NAME", "COLOR", "NUMBER", "PHONE", "BOX", "TIME"};
    final static int ID = 0;
    final static int NAME = 1;
    final static int COLOR = 2;
    final static int NUMBER = 3;
    final static int PHONE = 4;
    final static int BOX = 5;
    final static int TIME = 6;
    String[] param = new String[tags.length];

    Car makeCopy() {
        Car a = new Car();
        for (int i = 0; i < param.length; i++) {
            a.param[i] = this.param[i];
        }
        return a;
    }

    void clear() {
        param = new String[tags.length];
    }

    public String getTime() {
        int t = Integer.parseInt(param[TIME]);
        return (t / 2 + 11) + ":" + (t % 2 == 0 ? "00" : "30");
    }

    public static String getTime(int a) {
        return (a / 2 + 11) + ":" + (a % 2 == 0 ? "00" : "30");
    }

    public static int fromTime(String a){
        return (Integer.parseInt(a.substring(0, 2)) - 11) * 2 + (a.charAt(3) == '0' ? 0 : 1);
    }
}

