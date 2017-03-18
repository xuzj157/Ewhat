package oteher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Map;
import personal.nekopalyer.ewhat.R;
import static oteher.Tools.delFood;


public class RemoveRecyclerItemAdapter extends RecyclerView.Adapter {

//    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Map<String,String>> mList;

    public RemoveRecyclerItemAdapter(Context context, ArrayList<Map<String,String>> list) {
//        mContext = context;
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
        Map<String,String> map;
        map = mList.get(position);
        removeRecyclerViewHolder.contentTv.setText(map.get("food_name"));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position,DbHelper dbHelper) {            //根据行号删除数据
        Map<String,String> map;
        map = mList.get(position);
        String s = map.get("id");

        delFood(s,dbHelper);

        mList.remove(position);
        notifyDataSetChanged();
    }

    public void refreshData( ArrayList<Map<String,String>> mList){
        this.mList = mList;
    }

}
