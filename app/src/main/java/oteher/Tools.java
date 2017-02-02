package oteher;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by 智杰 on 2016/11/10.
 */

public class Tools {

    public static int ram(int max, int min) {
        Random random = new Random();
        int s;
        s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static List<String> Array2list(String[] s) {
        List<String> sl = Arrays.asList(s);
        return sl;
    }

    public static ArrayList<String> intoList(int k, DbHelper dbHelper) {

        ArrayList<String> listFood = new ArrayList<>();
        String SELECT_FOOD = "select food_name from food where kind = ? ";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(SELECT_FOOD, new String[]{Integer.toString(k)});
        while(cursor.moveToNext()){
            listFood.add(cursor.getString(0));
        }
        return listFood;

    }

}
