package org.belichenko.a.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistreFragment#returnInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistreFragment extends Fragment implements MyConstants {

    private static RegistreFragment ourInstance = new RegistreFragment();

    @Bind(R.id.editName)
    EditText editName;
    @Bind(R.id.edit_pass)
    EditText edit_pass;
    @Bind(R.id.edit_rewrite_pass)
    EditText edit_rewrite_pass;
    @Bind(R.id.text_save)
    TextView text_save;

    public RegistreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static RegistreFragment returnInstance() {
        return ourInstance;
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
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @OnClick(R.id.text_save)
    protected void onSaveClicked(View fragmentView) {

        String name = editName.getText().toString();
        String pass = edit_pass.getText().toString();
        String repeatPass = edit_rewrite_pass.getText().toString();

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String name = editName.getText().toString();
        String pass = edit_pass.getText().toString();

        outState.putString(EDIT_LOGIN_NAME, name);
        outState.putString(EDIT_LOGIN_PASS, pass);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            editName.setText(savedInstanceState.getString(EDIT_LOGIN_NAME));
            edit_pass.setText(savedInstanceState.getString(EDIT_LOGIN_PASS));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}