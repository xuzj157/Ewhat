package oteher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import personal.nekopalyer.ewhat.R;

/**
 * Created by 智杰 on 1/23/2017.
 */

public class RemoveRecyclerItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<String> mList;

    public RemoveRecyclerItemAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemoveRecyclerViewHolder(mInflater.inflate(R.layout.recyclerview_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RemoveRecyclerViewHolder removeRecyclerViewHolder = (RemoveRecyclerViewHolder) holder;
        removeRecyclerViewHolder.contentTv.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

}
