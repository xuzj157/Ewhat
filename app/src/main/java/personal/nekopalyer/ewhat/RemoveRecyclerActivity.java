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
import oteher.DbHelper;
import oteher.ItemRemoveRecyclerView;
import oteher.OnItemClickListener;
import oteher.RemoveRecyclerItemAdapter;

import static oteher.Tools.intoList;

public class RemoveRecyclerActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemRemoveRecyclerView recyclerView;
    private ArrayList<String> mListFood;
    private DrawerLayout drawer_layout;
    private View topbar;
    private Button btn_right;
    private Button btn_back;
    private AddRightFragment mAddRightFg = new AddRightFragment();
    private FragmentManager fManager;
    private DbHelper dbHelper;
    private Intent intent;
    private int kind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_recycler);



        initViews();

        dbHelper = new DbHelper(this, "food.db3", 1);
        mListFood = new ArrayList<>();
        switch (kind) {
            case 1:
                mListFood = intoList(1, dbHelper);
                break;
            case 2:
                mListFood = intoList(2, dbHelper);
                break;
            case 3:
                mListFood = intoList(3, dbHelper);
                break;
        }

        final RemoveRecyclerItemAdapter adapter = new RemoveRecyclerItemAdapter(this, mListFood);
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
        recyclerView = (ItemRemoveRecyclerView) findViewById(R.id.id_item_remove_recyclerview);
        fManager = getSupportFragmentManager();
        intent = getIntent();
        kind = (Integer) intent.getSerializableExtra("kind");
//        Bundle bundle = new Bundle();
//        bundle.putInt("kind",kind);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mAddRightFg.setArguments(bundle);
        mAddRightFg = (AddRightFragment) fManager.findFragmentById(R.id.fg_right_menu);

        topbar = findViewById(R.id.topbar);
        btn_right = (Button) topbar.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_back = (Button) topbar.findViewById(R.id.btn_back);





        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RemoveRecyclerActivity.this, MainActivity.class);
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

        mAddRightFg.setDrawerLayout(drawer_layout);
    }

    public void onClick(View v) {

        drawer_layout.openDrawer(Gravity.RIGHT);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.RIGHT);    //解除锁定



    }

}
