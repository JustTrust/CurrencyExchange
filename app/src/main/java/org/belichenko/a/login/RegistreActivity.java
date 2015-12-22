package org.belichenko.a.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.belichenko.a.constant.MyConstants;
import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.currencyexchange.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistreActivity extends AppCompatActivity implements MyConstants {

    private EditText editName;
    private EditText editPass;
    private EditText editRepeatPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        editName = (EditText) findViewById(R.id.editName);
        editPass = (EditText) findViewById(R.id.edit_pass);
        editRepeatPass = (EditText) findViewById(R.id.edit_rewrite_pass);

        Intent intent = getIntent();
        String messageName = intent.getStringExtra(EXTRA_MESSAGE);
        if (messageName != null) {
            editName.setText(messageName);
        }
    }

    public void saveNewUser(View view) {
        String name = editName.getText().toString();
        String pass = editPass.getText().toString();
        String repeatPass = editRepeatPass.getText().toString();

        if (name.isEmpty() | (!isStringValidate("^[a-zA-Z0-9_-]{5,20}$", name))) {
            Toast.makeText(RegistreActivity.this, getString(R.string.notValidName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(repeatPass)) {
            Toast.makeText(RegistreActivity.this, getString(R.string.wrongRepeatPass), Toast.LENGTH_SHORT).show();
            return;
        }
        if ((pass.isEmpty()) | (!isStringValidate("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", pass))) {
            Toast.makeText(RegistreActivity.this, getString(R.string.notValidPass), Toast.LENGTH_SHORT).show();
            return;
        }

        // set new user
        StorageOfUser storageOfUser = StorageOfUser.getInstance();
        int returnMessage = storageOfUser.setUser(name, pass);
        Toast.makeText(RegistreActivity.this, getString(returnMessage), Toast.LENGTH_SHORT).show();

        if (returnMessage == R.string.succsessfull_regisration) {

            // start main acivity if sucsesful
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }

    private boolean isStringValidate(String patternWord, String word) {
        Pattern pattern = Pattern.compile(patternWord);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }
}
