package oteher;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 智杰 on 1/31/2017.
 */
class MyDbHelper extends SQLiteOpenHelper {

    private final String CREATE_TABLE_SQL = "create table food(id integer primary key autoincrement ,food_name, kind integer)";

    private final String INSERT_FOOD_SQL_BREAKFAST = "insert into food values " +
            "(null,'皮蛋瘦肉粥',1),(null,'油条',1),(null,'豆浆',1),(null,'大饼',1)," +
            "(null,'酱香饼',1),(null,'山东煎饼',1),(null,'小米粥',1),(null,'燕麦片',1)," +
            "(null,'菜包子',1),(null,'肉包子',1),(null,'馒头',1),(null,'花卷',1)," +
            "(null,'肉夹馍',1),(null,'小笼包',1),(null,'生煎',1),(null,'锅贴',1)," +
            "(null,'烧卖',1),(null,'黑米粥',1),(null,'烧饼',1),(null,'小馄饨',1)," +
            "(null,'粢饭团',1),(null,'水果',1),(null,'粢饭糕',1),(null,'手抓饼',1)," +
            "(null,'汉堡',1),(null,'豆腐花',1),(null,'糕点',1),(null,'糯米团',1)," +
            "(null,'面包',1),(null,'三明治',1),(null,'饭团',1),(null,'蛋饼',1)," +
            "(null,'还是要吃早饭的！',1)";

    private final String INSERT_FOOD_SQL_LUNCH = "insert into food values " +
            "(null,'盖浇饭',2),(null,'砂锅',2),(null,'麻辣烫',2),(null,'千里香馄饨',2)," +
            "(null,'炒面',2),(null,'汤面',2),(null,'排骨年糕',2),(null,'味千拉面',2)," +
            "(null,'肯德基',2),(null,'麦当劳',2),(null,'麻辣香锅',2),(null,'牛肉拉面',2)," +
            "(null,'牛肉泡馍',2),(null,'黄焖鸡米饭',2),(null,'过桥米线',2),(null,'桂林米粉',2)," +
            "(null,'骨头菜饭',2),(null,'炸酱面',2),(null,'石锅拌饭',2),(null,'避风塘',2)," +
            "(null,'吉祥馄饨',2),(null,'浏阳蒸菜',2),(null,'萨利亚',2),(null,'煲仔饭',2)," +
            "(null,'炸鸡',2),(null,'老鸭粉丝汤',2),(null,'日式牛肉盖饭',2),(null,'酸辣粉',2)," +
            "(null,'寿司',2),(null,'全家便当',2),(null,'喜士多',2),(null,'炒饭',2)," +
            "(null,'热干面',2),(null,'我好胖。。',2),(null,'我真的好胖。。',2)," +
            "(null,'我真的还吃吗',2),(null,'心情不好',2）";

    private final String getINSERT_FOOD_SQL_DINNER = "insert into food values " +
            "(null,'盖浇饭',3),(null,'砂锅',3),(null,'大排档',3),(null,'米线',3)," +
            "(null,'满汉全席',3),(null,'西餐',3),(null,'麻辣烫',3),(null,'自助餐',3)," +
            "(null,'炒面',3),(null,'快餐',3),(null,'水果',3),(null,'西北风',3),(null,'馄饨',3)," +
            "(null,'火锅',3),(null,'烧烤',3),(null,'泡面',3),(null,'水饺',3),(null,'日本料理',3)," +
            "(null,'涮羊肉',3),(null,'味千拉面',3),(null,'面包',3),(null,'扬州炒饭',3)," +
            "(null,'自助餐',3),(null,'菜饭骨头汤',3),(null,'茶餐厅',3),(null,'海底捞',3)," +
            "(null,'西贝莜面村',3),(null,'披萨',3),(null,'麦当劳',3),(null,'KFC',3)" +
            ",(null,'汉堡王',3),(null,'卡乐星',3),(null,'兰州拉面',3),(null,'沙县小吃',3)," +
            "(null,'烤鱼',3),(null,'烤肉',3),(null,'海鲜',3),(null,'铁板烧',3),(null,'韩国料理',3)," +
            "(null,'粥',3),(null,'快餐',3),(null,'萨莉亚',3),(null,'桂林米粉',3),(null,'东南亚菜',3)," +
            "(null,'甜点',3),(null,'农家菜',3),(null,'川菜',3),(null,'粤菜',3),(null,'湘菜',3)," +
            "(null,'本帮菜',3),(null,'全家便当',3),(null,'要不不吃了。。',3),(null,'真的不吃了',3)," +
            "(null,'真真真的不吃了',3)";

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(INSERT_FOOD_SQL_BREAKFAST);
        db.execSQL(INSERT_FOOD_SQL_LUNCH);
        db.execSQL(getINSERT_FOOD_SQL_DINNER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
