package org.belichenko.a.login;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.belichenko.a.currencyexchange.R;
import org.belichenko.a.utils.Utils;

import layout.LoginFragment;
import layout.RegistreFragment;

public class LogRegActivity extends AppCompatActivity {

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

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.frame_fragment);
        LoginFragment loginFrag = new LoginFragment();
        RegistreFragment regFrag = new RegistreFragment();

        // add two frame for two fragments
        FrameLayout frameLeft = new FrameLayout(this);
        FrameLayout frameRight = new FrameLayout(this);
        setViewId(frameLeft);
        setViewId(frameRight);

        LinearLayout.LayoutParams lparams = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // add two frames and two fragments
            myLayout.addView(frameLeft, lparams);
            myLayout.addView(frameRight, lparams);

            getFragmentManager().beginTransaction().add(frameLeft.getId(), loginFrag, "loginFrag").commit();
            getFragmentManager().beginTransaction().add(frameRight.getId(), regFrag, "regFrag").commit();

        } else { // portrait

            myLayout.addView(frameLeft, lparams);
            getFragmentManager().beginTransaction().add(frameLeft.getId(), loginFrag, "loginFrag").commit();
        }
    }
}


