package personal.nekopalyer.ewhat;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;
import fragment.AddRightFragment;
import oteher.DbHelper;
import oteher.FoodInfo;
import oteher.ItemRemoveRecyclerView;
import oteher.OnItemClickListener;
import oteher.RemoveRecyclerItemAdapter;
import static oteher.Tools.intoMap;

/**
 * Before you read this code, please make sure you have read the README in this project.Thanks!
 * <p>
 * Created by xuzj157 on 2016/11/9.
 * <p>
 *
 *     滑动删除界面
 *
 */
public class RemoveRecyclerActivity extends AppCompatActivity implements View.OnClickListener {
    private ItemRemoveRecyclerView recyclerView;
    private ArrayList<Map<String,String>> mListFood;
    private DrawerLayout drawer_layout;
//    private View topbar;
//    private Button btn_right;
//    private Button btn_back;
//    private AddRightFragment mAddRightFg = new AddRightFragment();
//    private FragmentManager fManager;
    private DbHelper dbHelper;
//    private Intent intent;
    private int kind;        //食物的种类
    private TextView mTopBarTv;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_recycler);
        initViews();
        dbHelper = new DbHelper(this, "food.db3", 1);
        mListFood = new ArrayList<>();
//        switch (kind) {
//            case 1:
//                mListFood = intoMap(1, dbHelper);
//                mTopBarTv.setText("早餐");
//                break;
//            case 2:
//                mListFood = intoMap(2, dbHelper);
//                mTopBarTv.setText("午餐");
//                break;
//            case 3:
//                mListFood = intoMap(3, dbHelper);
//                mTopBarTv.setText("晚餐");
//                break;
//        }
        intoListFood();
        final RemoveRecyclerItemAdapter adapter = new RemoveRecyclerItemAdapter(this, mListFood);     //设置一个调节器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //现在没什么用处的
            }
            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position,dbHelper);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                intoListFood();
                adapter.refreshData(mListFood);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void initViews() {
        recyclerView = (ItemRemoveRecyclerView) findViewById(R.id.id_item_remove_recyclerview);

        Intent intent = getIntent();                                 //从传进来的intent中取出kind
        kind = (Integer) intent.getSerializableExtra("kind");

        mTopBarTv = (TextView) findViewById(R.id.id_topbar_tv);
        FragmentManager fManager = getSupportFragmentManager();
        AddRightFragment mAddRightFg = (AddRightFragment) fManager.findFragmentById(R.id.fg_right_menu);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        View mTopBar = findViewById(R.id.topbar);
        Button btn_right = (Button) mTopBar.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        Button btn_back = (Button) mTopBar.findViewById(R.id.btn_back);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.remove_sl);
        //返回mainctivity
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoveRecyclerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //设置右面的侧滑菜单只能通过编程来打开
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                GravityCompat.END);
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
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
            }
            @Override
            public void onDrawerStateChanged(int i) {
            }
        });
        mAddRightFg.setDrawerLayout(drawer_layout);
    }
    public void onClick(View v) {
        drawer_layout.openDrawer(GravityCompat.END);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                GravityCompat.END);    //解除锁定
    }
    private void intoListFood(){            //插入食物
        switch (kind) {
            case 1:
                mListFood = intoMap(FoodInfo.BREAKFAST, dbHelper);      //将数据库中的数据取出插入list<Map>
                mTopBarTv.setText("早餐");
                break;
            case 2:
                mListFood = intoMap(FoodInfo.LUNCH, dbHelper);
                mTopBarTv.setText("午餐");
                break;
            case 3:
                mListFood = intoMap(FoodInfo.DINNER, dbHelper);
                mTopBarTv.setText("晚餐");
                break;
        }
    }
}