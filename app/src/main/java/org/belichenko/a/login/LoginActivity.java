package org.belichenko.a.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.belichenko.a.utils.MyConstants;
import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.currencyexchange.R;

public class LoginActivity extends AppCompatActivity implements MyConstants {

    private EditText editName;
    private EditText editPass;
    private StorageOfUser storageOfUser = StorageOfUser.getInstance();
    private static SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPrefs = getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);

        // check in preferences a user is login
        String user = mPrefs.getString(USER_IS_LOGIN, null);
        if (user != null) {
            // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        editName = (EditText) findViewById(R.id.editLoginName);
        editPass = (EditText) findViewById(R.id.editLoginPass);
    }

    public void onRegistreLinkClick(View view) {
        Intent intent = new Intent(this, RegistreActivity.class);
        String name = editName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);
    }

    public void onLoginLinkClick(View view) {
        String name = editName.getText().toString();
        String pass = editPass.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.notValidName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.notValidPass), Toast.LENGTH_SHORT).show();
            return;
        }
        if (storageOfUser.findUser(name, pass)) {
            // set up user name to Preferences
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putString(USER_IS_LOGIN, name);
            edit.apply();
            // clear fields
            editName.setText("");
            editPass.setText("");
            // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.wrongLogin), Toast.LENGTH_SHORT).show();
            editPass.setText("");
        }
    }

}
