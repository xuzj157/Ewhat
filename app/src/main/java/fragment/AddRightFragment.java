package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import oteher.DbHelper;
import personal.nekopalyer.ewhat.R;

/**
 * Created by 智杰 on 1/29/2017.
 */

public class AddRightFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private EditText mAddEt;
    private final String INSERT_FOOD = "insert into food values (null,?,?)";
    private RadioButton mBreakfastRb;
    private RadioButton mLunchRb;
    private RadioButton mDinnerRb;
    private Context context;
    private DbHelper dbHelper;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_right, null);
        view.findViewById(R.id.id_add_bt).setOnClickListener(this);
        context = getContext();
        dbHelper = new DbHelper(context,"food.db3",1);
        mAddEt = (EditText) view.findViewById(R.id.id_add_et);
        mBreakfastRb = (RadioButton)view.findViewById(R.id.id_add_brakfast_rb);
        mLunchRb = (RadioButton)view.findViewById(R.id.id_add_lunch_rb);
        mDinnerRb = (RadioButton)view.findViewById(R.id.id_add_dinner_rb);
        return view;
    }

    public void onClick(View v) {



        if(mAddEt.getText() != null){
            String s = mAddEt.getText().toString();
            if(mBreakfastRb.isChecked()){
                insertFood(s,1);
                newToast(s+"已经加入菜单");
                mAddEt.setText(null);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }else if (mLunchRb.isChecked()){
                insertFood(s,2);
                newToast(s+"已经加入菜单");
                mAddEt.setText(null);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }else if(mDinnerRb.isChecked()){
                insertFood(s,3);
                newToast(s+"已经加入菜单");
                mAddEt.setText(null);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }else{
                mAddEt.setText("none");
            }
        }else{
            newToast("你不能什么都不告诉我！");
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

    private void newToast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void insertFood(String food, int kind){
        dbHelper.getReadableDatabase().execSQL(INSERT_FOOD,new Object[]{food,kind});
    }

}
