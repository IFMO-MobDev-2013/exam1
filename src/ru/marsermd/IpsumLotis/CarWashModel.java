package ru.marsermd.IpsumLotis;

/**
 * Created with IntelliJ IDEA.
 * User: misch_000
 * Date: 14.01.14
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class CarWashModel {
    private static CarWashModel ourInstance = new CarWashModel();

    public static CarWashModel getInstance() {
        return ourInstance;
    }

    private CarWashModel() {
    }

    private static int boxCount = 0;
    public static int getBoxCount() {
        return boxCount;
    }
    public static void setBoxCount(int count) {
        boxCount = count;
    }

    private static String name = "";
    public static String getName() {
        return name;
    }
    public static void setName(String washName) {
        name = washName;
    }

    public static String getFormatedTime(int time) {
        String ans = "" + (time / 2);
        if (ans.length() == 1)
            ans = 0 + ans;
        ans += " : " + (time % 2 == 0 ? "00" : "30");
        return ans;
    }
    public static int deformatTime(String time) {
        String[] s = time.split(" : ");
        return Integer.parseInt(s[0]) * 2 + Integer.parseInt(s[1]) / 30;
    }

    public static boolean isWorkingTime(int time) {
        return time >= 16 && time < 44;
    }
}
