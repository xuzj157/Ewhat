package oteher;

import android.view.View;

/**
 * Created by 智杰 on 2017/1/19.
 */

public interface OnItemClickListener {

    /**
     * item点击回调
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     * 删除按钮回调
     *
     * @param position
     */
    void onDeleteClick(int position);

}
