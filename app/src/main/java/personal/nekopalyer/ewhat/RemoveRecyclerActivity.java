package personal.nekopalyer.ewhat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import oteher.ItemRemoveRecyclerView;
import oteher.OnItemClickListener;
import oteher.RemoveRecyclerItemAdapter;

public class RemoveRecyclerActivity extends AppCompatActivity {

    private ItemRemoveRecyclerView recyclerView;
    private ArrayList<String> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_recycler);

        recyclerView = (ItemRemoveRecyclerView) findViewById(R.id.id_item_remove_recyclerview);

        mList = new ArrayList<>();
        for(int i = 0;i < 50;i++){
            mList.add(i+"");
        }

        final RemoveRecyclerItemAdapter adapter = new RemoveRecyclerItemAdapter(this,mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);
            }
        });


    }
}
