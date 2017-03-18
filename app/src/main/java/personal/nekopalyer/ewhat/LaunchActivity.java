package personal.nekopalyer.ewhat;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Before you read this code, please make sure you have read the README in this project.Thanks!
 * <p>
 * Created by xuzj157 on 2016/11/9.
 * <p>
 *     初始界面
 *
 */
public class LaunchActivity extends AppCompatActivity {
    //持续时间比较稳定，通常用于放置广告
    Button button ;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        button = (Button)findViewById(R.id.id_launch_into_bt);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        textView = (TextView)findViewById(R.id.id_launch_tv);
        textView.setText("\t\t美食家把钱包放进肚子里，而吝啬鬼把肚子放在钱包里。\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tby  古希腊 荷马");
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    LaunchActivity.this.finish();
            }
        };
        handler.postDelayed(runnable, 3000);
//        handler.post(runnable);

        button = (Button)findViewById(R.id.id_launch_into_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            }
        });
    }
}