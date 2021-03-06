package personal.nekopalyer.ewhat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import fragment.BreakfastFragment;
import oteher.ComplexFragmentAdapter;
import fragment.DinnerFragment;
import fragment.LunchFragment;

public class MainActivity extends FragmentActivity {

    private RadioGroup mRadioGroup;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPager mPageVp;
    private RadioButton mTabBreakfastRb, mTabLunchRb, mTabDinnerRb;
    private ImageView mTabLineIv;
    private BreakfastFragment mBreakfastFragment;
    private LunchFragment mLunchFragment;
    private DinnerFragment mDinnerFragment;
    private int currentIndex;
    private int screenWidth;
    private static int color = Color.YELLOW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addFragmentList();
        initTabLineWidth();
    }

    private void init() {
        mBreakfastFragment = new BreakfastFragment();
        mLunchFragment = new LunchFragment();
        mDinnerFragment = new DinnerFragment();
        mRadioGroup = (RadioGroup) findViewById(R.id.id_tab_group_rg);
        mTabBreakfastRb = (RadioButton) findViewById(R.id.id_tab_breakfast_rb);
        mTabLunchRb = (RadioButton) findViewById(R.id.id_tab_lunch_rb);
        mTabDinnerRb = (RadioButton) findViewById(R.id.id_tab_dinner_rb);
        mTabLineIv = (ImageView) findViewById(R.id.id_tab_line_iv);
        mPageVp = (ViewPager) findViewById(R.id.id_page_vp);
    }

    private void addFragmentList() {
        mFragmentList.add(mBreakfastFragment);
        mFragmentList.add(mLunchFragment);
        mFragmentList.add(mDinnerFragment);
        ComplexFragmentAdapter mComplexFragmentAdapter = new ComplexFragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        mPageVp.setAdapter(mComplexFragmentAdapter);
//        设定第一个界面为初始界面
        mPageVp.setCurrentItem(0);
        mTabBreakfastRb.setTextColor(color);
        mTabBreakfastRb.setTextSize(21);
//        mTabBreakfastRb.setBackgroundColor(Color.GRAY);


        //设置手势的三种状态，对于分别的手势
        mPageVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                    Log.v("set", "first");
                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - positionOffset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                    Log.v("set", "second");
                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                    Log.v("set", "third");
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - positionOffset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                    Log.v("set", "forth");
                }
                mTabLineIv.setLayoutParams(lp);
            }

            //选择键
            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mTabBreakfastRb.setTextColor(color);
                        mTabBreakfastRb.setTextSize(21);
//                        mTabBreakfastRb.setBackgroundColor(Color.GRAY);
                        break;
                    case 1:
                        mTabLunchRb.setTextColor(color);
                        mTabLunchRb.setTextSize(21);
//                        mTabLunchRb.setBackgroundColor(Color.GRAY);
                        break;
                    case 2:
                        mTabDinnerRb.setTextColor(color);
                        mTabDinnerRb.setTextSize(21);
//                        mTabDinnerRb.setBackgroundColor(Color.GRAY);
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //点击事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.id_tab_breakfast_rb:
                        mPageVp.setCurrentItem(0);
                        break;

                    case R.id.id_tab_lunch_rb:
                        mPageVp.setCurrentItem(1);
                        break;

                    case R.id.id_tab_dinner_rb:
                        mPageVp.setCurrentItem(2);
                        break;
                }
            }
        });

    }

    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }

    private void resetTextView() {

        mTabBreakfastRb.setTextColor(Color.parseColor("#FEFEFD"));
        mTabLunchRb.setTextColor(Color.parseColor("#FEFEFD"));
        mTabDinnerRb.setTextColor(Color.parseColor("#FEFEFD"));

        mTabBreakfastRb.setTextSize(15);
        mTabLunchRb.setTextSize(15);
        mTabDinnerRb.setTextSize(15);

//        mTabBreakfastRb.setBackgroundResource(R.color.brown);
//        mTabLunchRb.setBackgroundResource(R.color.brown);
//        mTabDinnerRb.setBackgroundResource(R.color.brown);

    }

}