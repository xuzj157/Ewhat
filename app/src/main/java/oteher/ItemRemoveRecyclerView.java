package oteher;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import personal.nekopalyer.ewhat.R;
import personal.nekopalyer.ewhat.RemoveRecyclerActivity;

/**
 *
 * Created by 智杰 on 1/23/2017.
 */

public class ItemRemoveRecyclerView extends RecyclerView{

    private Context mContext;
    private int mLastX,mLastY;
    private int mPosition;
    private LinearLayout mItemLayout;
    private TextView mDel;
    private int mMaxLength;
    private boolean isDragging;
    private boolean isItemMoving;
    private boolean isStartScroll;
    private int mDelBtnState;
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;
    private OnItemClickListener mListener;


    public ItemRemoveRecyclerView(Context context) {
        this(context, null);
    }

    public ItemRemoveRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemRemoveRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        mScroller = new Scroller(context, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        mVelocityTracker.addMovement(e);
        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDelBtnState == 0) {
                    View view = findChildViewUnder(x, y);
                    if (view == null) {
                        return false;
                    }

                    RemoveRecyclerViewHolder viewHolder = (RemoveRecyclerViewHolder) getChildViewHolder(view);
                    mItemLayout = viewHolder.layout;
                    mPosition = viewHolder.getAdapterPosition();
                    mDel = (TextView) mItemLayout.findViewById(R.id.id_item_del_tv);
                    mMaxLength = mDel.getWidth();

                    mDel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onDeleteClick(mPosition);
                            newToast(mPosition);
                            mItemLayout.scrollTo(0, 0);
                            mDelBtnState = 0;
                        }
                    });
                } else if (mDelBtnState == 3) {
                    //200为持续时间
                    mScroller.startScroll(mItemLayout.getScrollX(), 0, -mMaxLength, 0, 200);
                    invalidate();
                    mDelBtnState = 0;
                    return false;
                } else return false;
                break;

            case MotionEvent.ACTION_HOVER_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;

                int scrollX = mItemLayout.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {
                        mItemLayout.scrollTo(0, 0);
                        return true;
                    } else if (scrollX + dx >= mMaxLength) {
                        mItemLayout.scrollBy(mMaxLength, 0);
                        return true;
                    }
                    mItemLayout.scrollBy(dx, 0);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(mItemLayout, mPosition);
                }
                isItemMoving = false;

                mVelocityTracker.computeCurrentVelocity(1000);//计算手指滑动的速度
                float xVelocity = mVelocityTracker.getXVelocity();//水平方向速度（向左为负）
                float yVelocity = mVelocityTracker.getYVelocity();//垂直方向速度

                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();

                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity <= -100) {//左滑速度大于100，则删除按钮显示
                        deltaX = mMaxLength - upScrollX;
                        mDelBtnState = 2;
                    } else if (xVelocity > 100) {//右滑速度大于100，则删除按钮隐藏
                        deltaX = -upScrollX;
                        mDelBtnState = 1;
                    }
                } else {
                    if (upScrollX >= mMaxLength / 2) {//item的左滑动距离大于删除按钮宽度的一半，则则显示删除按钮
                        deltaX = mMaxLength - upScrollX;
                        mDelBtnState = 2;
                    } else if (upScrollX < mMaxLength / 2) {//否则隐藏
                        deltaX = -upScrollX;
                        mDelBtnState = 1;
                    }
                }

                //item自动滑动到指定位置
                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();

                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mItemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mDelBtnState == 1) {
                mDelBtnState = 0;
            }

            if (mDelBtnState == 2) {
                mDelBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public void newToast(int num) {

        Toast.makeText(mContext, "**" + num + "**", Toast.LENGTH_SHORT).show();

    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    /**
     * Created by 智杰 on 2016/11/9.
     *
     */

    public static class DinnerFragment extends Fragment {
        Context context;
        private int screenWidth;
        private int screenHeight;
        String[] arrFood = {"盖浇饭", "砂锅", "大排档", "米线", "满汉全席",
                "西餐", "麻辣烫", "自助餐", "炒面", "快餐", "水果", "西北风",
                "馄饨", "火锅", "烧烤", "泡面", "水饺", "日本料理", "涮羊肉",
                "味千拉面", "面包", "扬州炒饭", "自助餐", "菜饭骨头汤",
                "茶餐厅", "海底捞", "西贝莜面村", "披萨", "麦当劳", "KFC",
                "汉堡王", "卡乐星", "兰州拉面", "沙县小吃", "烤鱼", "烤肉",
                "海鲜", "铁板烧", "韩国料理", "粥", "快餐", "萨莉亚", "桂林米粉",
                "东南亚菜", "甜点", "农家菜", "川菜", "粤菜", "湘菜", "本帮菜",
                "全家便当", "要不不吃了。。", "真的不吃了", "真真真的不吃了"};
        private FrameLayout mMainLayoutFl;
        private TextView mShowTv;
        private ObjectAnimator Alpha;
        private FrameLayout.LayoutParams animatorTextParams;
        private Handler handler = new Handler();
        Runnable runSetText = new Runnable() {
            @Override
            public void run() {
                mShowTv.setText(randomFood());
                handler.postDelayed(runSetText, 80);
            }
        };

        Runnable runSetAnimationFirst = new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(context);
                mMainLayoutFl.addView(textView);
                textView.setText(randomFood());
                animatorTextParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                animatorTextParams.leftMargin = (Tools.ram(screenWidth, 0));
                animatorTextParams.topMargin = (Tools.ram(screenHeight, 0));
                textView.setTextSize(Tools.ram(33, 20));
                textView.setLayoutParams(animatorTextParams);
                int time = Tools.ram(3000, 2000);
                textView.invalidate();
                Alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 0.6f, 0f);
    //            scale = ObjectAnimator.ofFloat(textView,"scale",1,3,1,3);
                Alpha.setDuration(time - 1).start();
    //            scale.setDuration(time - 1).start();
    //            animatorSet = new AnimatorSet();
    //            animatorSet.setDuration(time-1);
    //            animatorSet.play(scale).with(Alpha);
    //            animatorSet.start();
                handler.postDelayed(this, Tools.ram(300, 200));
            }
        };

        Runnable runSetAnimationSecond = new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(context);
                mMainLayoutFl.addView(textView);
                textView.setText(randomFood());
                animatorTextParams = (FrameLayout.LayoutParams) textView.getLayoutParams();
                animatorTextParams.leftMargin = (Tools.ram(screenWidth, 0));
                animatorTextParams.topMargin = (Tools.ram(screenHeight, 0));
                textView.setTextSize(Tools.ram(33, 20));
                textView.setLayoutParams(animatorTextParams);
                int time = Tools.ram(4000, 2000);
                textView.invalidate();
                Alpha = ObjectAnimator.ofFloat(textView, "alpha", 0f, 0.6f, 0f);
                Alpha.setDuration(time - 1).start();
                handler.postDelayed(this, Tools.ram(200, 100));
            }
        };

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            context = getContext();
            mMainLayoutFl = (FrameLayout) view.findViewById(R.id.id_main_layout_fl);
            mShowTv = (TextView) view.findViewById(R.id.id_show_tv);
            ToggleButton mCheckTb = (ToggleButton) view.findViewById(R.id.id_click_tb);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            screenHeight = dm.heightPixels - 7;
            screenWidth = dm.widthPixels - 50;

            mCheckTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        handler.post(runSetText);
                        handler.post(runSetAnimationFirst);
                        handler.post(runSetAnimationSecond);
                    } else {
                        handler.removeCallbacks(runSetText);
                        handler.removeCallbacks(runSetAnimationFirst);
                        handler.removeCallbacks(runSetAnimationSecond);
                    }
                }
            });

            imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,RemoveRecyclerActivity.class);
                    startActivity(intent);
    //                Toast.makeText(context, "此处为食物菜单设置功能！即将推出！\n敬请期待>_<", Toast.LENGTH_LONG).show();
                }
            });

            return view;

        }

        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
        }

        private String randomFood() {
            return arrFood[Tools.ram(arrFood.length, 0)];
        }
    }
}
