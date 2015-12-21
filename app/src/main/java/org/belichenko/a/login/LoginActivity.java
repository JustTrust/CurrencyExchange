package org.belichenko.a.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.belichenko.a.constant.MyConstants;
import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.currencyexchange.R;

public class LoginActivity extends AppCompatActivity implements MyConstants {
    private EditText editName;
    private EditText editPass;
    private StorageOfUser storageOfUser = StorageOfUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check in preferences a user is login
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = mPrefs.getString(USER_IS_LOGIN, null);
        if (user != null) {
            // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        editName = (EditText) findViewById(R.id.editLoginName);
        editPass = (EditText) findViewById(R.id.editLoginPass);

        // restore list of users
        String users = mPrefs.getString(STORAGE_OF_USERS, null);
        storageOfUser.fillUsers(users);
    }

    public void onRegistreLinkClick(View view) {
        Intent intent = new Intent(this, RegistreActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        String message = editName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
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
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putString(USER_IS_LOGIN, name);
            edit.apply();
            // clear fields
            editName.setText("");
            editPass.setText("");
            // go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, getString(R.string.wrongLogin), Toast.LENGTH_SHORT).show();
            editPass.setText("");
        }
    }

}
