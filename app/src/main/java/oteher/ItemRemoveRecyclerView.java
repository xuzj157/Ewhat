package oteher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import personal.nekopalyer.ewhat.R;

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


}
