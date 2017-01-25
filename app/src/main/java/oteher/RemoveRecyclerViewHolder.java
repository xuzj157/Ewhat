package oteher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import personal.nekopalyer.ewhat.R;

/**
 * Created by 智杰 on 1/23/2017.
 */

public class RemoveRecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView contentTv;
    public TextView delTv;
    public LinearLayout layout;

    public RemoveRecyclerViewHolder(View itemView) {
        super(itemView);

        contentTv = (TextView)itemView.findViewById(R.id.id_item_content_tv);
        delTv = (TextView)itemView.findViewById(R.id.id_item_del_tv);
        layout = (LinearLayout)itemView.findViewById(R.id.id_item_layout);

    }
}
