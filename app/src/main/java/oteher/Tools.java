package oteher;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import personal.nekopalyer.ewhat.R;
/**
 * Before you read this code, please make sure you have read the README in this project.Thanks!
 * <p>
 * Created by xuzj157 on 2016/11/9.
 * <p>
 * 工具类：
 *           返回随机数字
 *           将数据库中的数据取出插入list<String>
 *           将数据库中的数据取出插入list<map>
 *           删除一个数据
 *           显示一个提示框
 */
public class Tools {
    /**
     *
     * @param max   最大值
     * @param min   最小值
     * @return  一个随机数    min < x < max
     */
    public static int ram(int max, int min) {
        Random random = new Random();
        int s;
        s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

//    public static List<String> Array2list(String[] s) {
//        List<String> sl = Arrays.asList(s);
//        return sl;
//    }

    /**
     *
     * @param k 食物种类
     * @param dbHelper 传入一个可用数据库
     * @return 根据食物种类和数据库 取出所有该食物种类的食物名称 并返回一个list<String >
     */
    public static ArrayList<String> intoList(int k, DbHelper dbHelper) {

        ArrayList<String> listFood = new ArrayList<>();
        String SELECT_FOOD = "select food_name from food where kind = ? ";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(SELECT_FOOD, new String[]{Integer.toString(k)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                listFood.add(cursor.getString(0));
            }
        } else {
            listFood.add("您未添加食品或店铺名");
            listFood.add("请进入添加界面添加");
            return listFood;
        }
        cursor.close();
        return listFood;

    }

    public static ArrayList<Map<String, String>> intoMap(int k, DbHelper dbHelper) {

        ArrayList<Map<String, String>> result = new ArrayList<>();
        String SELECT_FOOD = "select * from food where kind = ? ";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(SELECT_FOOD, new String[]{Integer.toString(k)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Map<String, String> mapFood = new HashMap<>();

                mapFood.put("id", cursor.getString(0));
                mapFood.put("food_name", cursor.getString(1));

                result.add(mapFood);
            }
        } else return null;
        cursor.close();
        return result;
    }

    //删除食物
    public static boolean delFood(String id, DbHelper dbHelper) {
        String DELETE_FOOD = "delete from food where id = " + id;
        dbHelper.getReadableDatabase().execSQL(DELETE_FOOD);
        return true;
    }

    //显示一个新的提示框
    public static void newToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

}
