package oteher;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if(cursor != null){
            while(cursor.moveToNext()){
                listFood.add(cursor.getString(0));
            }
        }else {
            listFood.add("您未添加食品或店铺名");
            listFood.add("请进入添加界面添加");
        }

        return listFood;

    }

    public static ArrayList<Map<String,String>> intoMap(int k,DbHelper dbHelper){

        ArrayList<Map<String,String>> result = new ArrayList<>();
        String SELECT_FOOD = "select * from food where kind = ? ";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(SELECT_FOOD, new String[]{Integer.toString(k)});
        if (cursor != null){
            while(cursor.moveToNext()){
                Map<String,String> mapFood = new HashMap<>();

                mapFood.put("id",cursor.getString(0));
                mapFood.put("food_name",cursor.getString(1));

                result.add(mapFood);
            }
        }else return null;
        return result;
    }

    public static boolean delFood(String id,DbHelper dbHelper){
        String DELETE_FOOD = "delete from food where id = " + id;
        dbHelper.getReadableDatabase().execSQL(DELETE_FOOD);
        return true;
    }

}
