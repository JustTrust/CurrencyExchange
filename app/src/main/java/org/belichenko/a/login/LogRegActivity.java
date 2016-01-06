package org.belichenko.a.login;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.layout.LoginFragment;
import org.belichenko.a.layout.RegistreFragment;
import org.belichenko.a.utils.MyConstants;
import org.belichenko.a.utils.Utils;


public class LogRegActivity extends AppCompatActivity implements MyConstants,
        LoginFragment.OnFragmentInteractionListener {

    private int frameId;
    private LoginFragment loginFrag = LoginFragment.returnInstance();
    private RegistreFragment regFrag = RegistreFragment.returnInstance();

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
        Log.d("Orientation", "Create " + this.getLocalClassName());
        SharedPreferences mPrefs = this.getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);
        String user = mPrefs.getString(USER_IS_LOGIN, null);
        if (user != null) {
            // start main activity if we have name
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        // create LinearLayout
        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f);

        // set linLayout root view
        setViewId(linLayout);
        frameId = linLayout.getId();
        setContentView(linLayout, linLayoutParam);

        buildViewByOrientation(linLayout);
    }

    private void buildViewByOrientation(LinearLayout myLayout) {

        LinearLayout.LayoutParams lparams = new LinearLayout
                .LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("AddFragments", "orientation = LANDSCAPE");
            // add two frames and two fragments
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
            Log.d("AddFragments", "orientation = PORTRAIT");
            // portrait, add just one fragment
            FragmentTransaction frManager = getFragmentManager().beginTransaction();
            frManager.add(myLayout.getId(), loginFrag, "loginFrag");
            // TODO: 04.01.2016 Back stack doesn't work
            frManager.addToBackStack(null);
            frManager.commit();
        }
    }

    @Override
    public void onFragmentInteraction(String st) {
        // replace Login fragment by Register
        int orientation = getResources().getConfiguration().orientation;
        if (frameId > 0) {
            LinearLayout myLayout = (LinearLayout) findViewById(frameId);
            Fragment loginFrag = getFragmentManager().findFragmentByTag("loginFrag");
            if (loginFrag.isAdded()) {
                FragmentTransaction frManager = getFragmentManager().beginTransaction();
                frManager.replace(myLayout.getId(), regFrag, "regFrag");
                frManager.addToBackStack(null);
                frManager.commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}