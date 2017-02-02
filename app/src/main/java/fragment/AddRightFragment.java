package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import personal.nekopalyer.ewhat.R;

/**
 * Created by 智杰 on 1/29/2017.
 */

public class AddRightFragment extends Fragment implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private EditText mAddEt;
    private final String ADD_FOOD = "insert food values (null,?,?)";
//    private FragmentManager mFragmentManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_right, null);
        view.findViewById(R.id.id_add_bt).setOnClickListener(this);
        mAddEt = (EditText) view.findViewById(R.id.id_add_et);
//        mFragmentManager = getActivity().getSupportFragmentManager();
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_add_bt:
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
//                int s = getArguments().getInt("kind");
//                mAddEt.setText(s);
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.mDrawerLayout = drawer_layout;
    }

}
