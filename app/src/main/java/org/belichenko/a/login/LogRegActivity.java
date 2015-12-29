package org.belichenko.a.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.belichenko.a.currencyexchange.R;
import org.belichenko.a.layout.LoginFragment;
import org.belichenko.a.layout.RegistreFragment;
import org.belichenko.a.utils.Utils;



public class LogRegActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    // set unique id for fragments
    protected void setViewId(View view) {
        if (view != null || view.getId() < 1) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setId(Utils.generateViewId());
            } else {
                view.setId(View.generateViewId());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        buildViewByOrientation();
    }

    private void buildViewByOrientation() {

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.frame_fragment);
        LinearLayout.LayoutParams lparams = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // add two frames and two fragments
            LoginFragment loginFrag = LoginFragment.newInstance(orientation, "");
            RegistreFragment regFrag = RegistreFragment.newInstance(orientation, "");

            FrameLayout frameLeft = new FrameLayout(this);
            setViewId(frameLeft);
            FrameLayout frameRight = new FrameLayout(this);
            setViewId(frameRight);

            myLayout.addView(frameLeft, lparams);
            myLayout.addView(frameRight, lparams);

            FragmentTransaction frManager = getFragmentManager().beginTransaction();
            frManager.add(frameLeft.getId(), loginFrag, "loginFrag");
            frManager.add(frameRight.getId(), regFrag, "regFrag");
            frManager.commit();
        } else {
            LoginFragment loginFrag = LoginFragment.newInstance(orientation, "");
            // portrait, add just one fragment
            FragmentTransaction frManager = getFragmentManager().beginTransaction();
            frManager.add(myLayout.getId(), loginFrag, "loginFrag");
            frManager.addToBackStack(null);
            frManager.commit();
        }
    }

    @Override
    public void onFragmentInteraction(String st) {
        int orientation = getResources().getConfiguration().orientation;
        RegistreFragment regFrag = RegistreFragment.newInstance(orientation, "");
        LinearLayout myLayout = (LinearLayout) findViewById(R.id.frame_fragment);
        Fragment loginFrag = getFragmentManager().findFragmentByTag("loginFrag");
        if (loginFrag.isAdded()){
            FragmentTransaction frManager = getFragmentManager().beginTransaction();
            frManager.replace(myLayout.getId(), regFrag, "regFrag");
            frManager.addToBackStack(null);
            frManager.commit();
        }

    }
}


