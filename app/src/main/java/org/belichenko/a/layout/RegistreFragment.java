package org.belichenko.a.layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.currencyexchange.R;
import org.belichenko.a.login.StorageOfUser;
import org.belichenko.a.utils.MyConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistreFragment extends Fragment implements MyConstants {

    private TextView txSave;
    private EditText editName;
    private EditText editPass;
    private EditText editRepeatPass;

    public RegistreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static RegistreFragment newInstance() {
        return new RegistreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_registre, container, false);
        // on Save listener
        txSave = (TextView) fragmentView.findViewById(R.id.text_save);
        txSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View fragmentView) {
                onSaveClicked(fragmentView);
            }
        });

        editName = (EditText) fragmentView.findViewById(R.id.editName);
        editPass = (EditText) fragmentView.findViewById(R.id.edit_pass);
        editRepeatPass = (EditText) fragmentView.findViewById(R.id.edit_rewrite_pass);

        return fragmentView;
    }

    private void onSaveClicked(View fragmentView) {

        String name = editName.getText().toString();
        String pass = editPass.getText().toString();
        String repeatPass = editRepeatPass.getText().toString();

        if (name.isEmpty() | (!isStringValidate("^[a-zA-Z0-9_-]{5,20}$", name))) {
            Toast.makeText(this.getActivity(), getString(R.string.notValidName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pass.equals(repeatPass)) {
            Toast.makeText(this.getActivity(), getString(R.string.wrongRepeatPass), Toast.LENGTH_SHORT).show();
            return;
        }
        if ((pass.isEmpty()) | (!isStringValidate("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", pass))) {
            Toast.makeText(this.getActivity(), getString(R.string.notValidPass), Toast.LENGTH_SHORT).show();
            return;
        }

        // set new user
        StorageOfUser storageOfUser = StorageOfUser.getInstance();
        int returnMessage = storageOfUser.setUser(name, pass);
        Toast.makeText(this.getActivity(), getString(returnMessage), Toast.LENGTH_SHORT).show();

        if (returnMessage == R.string.succsessfull_regisration) {

            // start main acivity if sucsesful
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            this.getActivity().finish();
        }
    }

    // check validation passwords and username
    private boolean isStringValidate(String patternWord, String word) {
        Pattern pattern = Pattern.compile(patternWord);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }
}