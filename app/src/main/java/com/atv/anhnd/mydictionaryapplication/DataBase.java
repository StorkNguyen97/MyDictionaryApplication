package com.atv.anhnd.mydictionaryapplication;

public class DataBase {
    public static String[] getData(int id) {
        if (id == R.id.action_en_vn) {
            return getEnVn();
        } else if (id == R.id.action_vn_en) {
            return getVnEn();
        }
        return new String[0];
    }

    private static String[] getVnEn() {
        String[] source = new String[]{
                "Linh",
                "is",
                "very",
                "beautiful!",
                "It's",
                "REAL!"
        };
        return source;
    }

    private static String[] getEnVn() {
        String[] source = new String[]{
                "Linh",
                "Xinh",
                "Dep",
                "Nhat",
                "Tren",
                "Doi",
                "That",
                "Su",
                "Luon"
        };
        return source;
    }
}
