package org.belichenko.a.login;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.belichenko.a.currencyexchange.R;

import layout.LoginFragment;
import layout.RegistreFragment;

public class LogRegActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reg);

        LoginFragment loginFrag = new LoginFragment();
        RegistreFragment regFrag = new RegistreFragment();

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            getFragmentManager().beginTransaction().add(R.id.first_fragment, loginFrag, "").commit();
            getFragmentManager().beginTransaction().add(R.id.second_fragment, regFrag, "").commit();
        }else{ // portrait
            getFragmentManager().beginTransaction().add(R.id.first_fragment, loginFrag, "").commit();
        }
    }


}
