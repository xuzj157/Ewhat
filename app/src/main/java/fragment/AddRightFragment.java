package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import personal.nekopalyer.ewhat.R;

/**
 * Created by 智杰 on 1/29/2017.
 */

public class AddRightFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_right, null);
        view.findViewById(R.id.btn_one).setOnClickListener(this);
//        view.findViewById(R.id.btn_two).setOnClickListener(this);
//        view.findViewById(R.id.btn_three).setOnClickListener(this);
        mFragmentManager = getActivity().getSupportFragmentManager();
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                break;
//            case R.id.btn_two:
//
//                break;
//            case R.id.btn_three:
//
//                break;
        }
    }


    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.mDrawerLayout = drawer_layout;
    }

}
