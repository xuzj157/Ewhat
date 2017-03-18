package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import oteher.DbHelper;
import oteher.FoodInfo;
import personal.nekopalyer.ewhat.R;

import static oteher.Tools.newToast;

/**
 * Before you read this code, please make sure you have read the README in this project.Thanks!
 * <p>
 * Created by xuzj157 on 2016/11/9.
 * <p>
 * This class is for the add food into the database.
 * 这个类是将食物数据添加进入数据库中的
 * <p>
 * 本界面类将嵌入ItemRemoveRecycleView中
 */


public class AddRightFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private EditText mAddEt;
    private RadioButton mBreakfastRb;
    private RadioButton mLunchRb;
    private RadioButton mDinnerRb;
    private Context context;
    private DbHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_right, null);
        view.findViewById(R.id.id_add_bt).setOnClickListener(this);
        context = getContext();                                       //获取该界面的context
        dbHelper = new DbHelper(context, "food.db3", 1);              //连接数据库
        mAddEt = (EditText) view.findViewById(R.id.id_add_et);
        mBreakfastRb = (RadioButton) view.findViewById(R.id.id_add_brakfast_rb);
        mLunchRb = (RadioButton) view.findViewById(R.id.id_add_lunch_rb);
        mDinnerRb = (RadioButton) view.findViewById(R.id.id_add_dinner_rb);
        return view;
    }

    public void onClick(View v) {
        //检验是否为空，trim()去除两边空格
        int i = mAddEt.getText().toString().trim().length();

        if (i != 0) {
            String s = mAddEt.getText().toString().trim();
            if (mBreakfastRb.isChecked()) {
                insertFood(new FoodInfo(s, FoodInfo.BREAKFAST));             //向数据库插入食物，并且食物类型为
                newToast(context, s + "已经加入菜单");                        //Tools类中会解释该函数
                mAddEt.setText(null);                                       //清空输入栏
                mDrawerLayout.closeDrawer(GravityCompat.END);           //关闭在屏幕右侧的Drawer   To close the drawer on right.
            } else if (mLunchRb.isChecked()) {
                insertFood(new FoodInfo(s, FoodInfo.LUNCH));
                newToast(context, s + "已经加入菜单");
                mAddEt.setText(null);
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else if (mDinnerRb.isChecked()) {
                insertFood(new FoodInfo(s, FoodInfo.DINNER));
                newToast(context, s + "已经加入菜单");
                mAddEt.setText(null);
                mDrawerLayout.closeDrawer(GravityCompat.END);
            } else {
                newToast(context, "您没有选择就餐类型");
                mDrawerLayout.closeDrawer(GravityCompat.END);
            }
        } else {
            newToast(context, "你不能什么都不告诉我！");
        }

//        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.mDrawerLayout = drawer_layout;
    }

//    private void newToast(String s){
//        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//    }

    /**
     * @param food FoodInfo类型 输入该类型的数据进行插入
     */
    private void insertFood(FoodInfo food) {
        final String INSERT_FOOD = "insert into food values (null,?,?)";
        dbHelper.getReadableDatabase().execSQL(INSERT_FOOD, new Object[]{food.getFoodName(), food.getFoodKind()});
    }

}
