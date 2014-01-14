package ru.ifmo.washer;

/**
 * Created by asus on 14.01.14.
 */

public class Info {
    final static String[] tags = new String[]{"ID", "NAME", "BOXES"};
    final static int ID = 0;
    final static int NAME = 1;
    final static int BOXES = 2;
    String[] param = new String[tags.length];

    Info makeCopy() {
        Info a = new Info();
        for (int i = 0; i < param.length; i++) {
            a.param[i] = this.param[i];
        }
        return a;
    }

    void clear() {
        param = new String[tags.length];
    }
}
