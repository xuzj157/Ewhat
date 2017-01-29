package personal.nekopalyer.ewhat;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import fragment.AddRightFragment;
import oteher.ItemRemoveRecyclerView;
import oteher.OnItemClickListener;
import oteher.RemoveRecyclerItemAdapter;

public class RemoveRecyclerActivity extends AppCompatActivity implements View.OnClickListener{

    private ItemRemoveRecyclerView recyclerView;
    private ArrayList<String> mList;
    private DrawerLayout drawer_layout;
    private View topbar;
    private Button btn_right;
    private Button btn_back;
    private AddRightFragment fg_right_menu;
    private FragmentManager fManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_recycler);
        fManager = getSupportFragmentManager();
        fg_right_menu = (AddRightFragment) fManager.findFragmentById(R.id.fg_right_menu);
        initViews();


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


    private void initViews() {
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        topbar = findViewById(R.id.topbar);
        btn_right = (Button) topbar.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_back = (Button)topbar.findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RemoveRecyclerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);

        drawer_layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {

            }

            @Override
            public void onDrawerClosed(View view) {
                drawer_layout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        fg_right_menu.setDrawerLayout(drawer_layout);
    }

    public void onClick(View v) {
        drawer_layout.openDrawer(Gravity.RIGHT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.RIGHT);    //解除锁定
    }
}
