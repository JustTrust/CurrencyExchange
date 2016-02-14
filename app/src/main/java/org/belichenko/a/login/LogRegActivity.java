package org.belichenko.a.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.currencyexchange.R;
import org.belichenko.a.layout.LoginFragment;
import org.belichenko.a.layout.RegistreFragment;
import org.belichenko.a.utils.MyConstants;


public class LogRegActivity extends AppCompatActivity implements MyConstants,
        LoginFragment.OnFragmentInteractionListener {

    private LoginFragment loginFrag = LoginFragment.returnInstance();
    private RegistreFragment regFrag = RegistreFragment.returnInstance();
    private boolean mDualPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_reg);
        SharedPreferences mPrefs = this.getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);
        String user = mPrefs.getString(USER_IS_LOGIN, null);
        if (user != null) {
            // start main activity if we have name
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        FrameLayout container_right = (FrameLayout) findViewById(R.id.container_right);
        mDualPane = container_right != null && container_right.getVisibility() == View.VISIBLE;

        buildViewByOrientation();
    }

    private void buildViewByOrientation() {

        FragmentTransaction frManager = getSupportFragmentManager().beginTransaction();
        if (mDualPane) {
            frManager.replace(R.id.container_left, loginFrag, "Login");
            frManager.replace(R.id.container_right, regFrag, "Reg");
        } else {
            frManager.replace(R.id.container_left, loginFrag, "Login");
            frManager.addToBackStack("Login");
        }
        frManager.commit();
    }

    @Override
    public void onFragmentInteraction(String st) {
        // replace Login fragment by Register

        if (!mDualPane) {
            FragmentTransaction frManager = getSupportFragmentManager().beginTransaction();
            frManager.replace(R.id.container_left, regFrag, "Reg");
            frManager.addToBackStack("Details");
            frManager.commit();
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
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.d("backstack", "popping backstack");
            fm.popBackStack();
        } else {
            Log.d("backstack", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}