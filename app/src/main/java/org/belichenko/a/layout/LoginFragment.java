package org.belichenko.a.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.belichenko.a.currencyexchange.MainActivity;
import org.belichenko.a.currencyexchange.R;
import org.belichenko.a.login.StorageOfUser;
import org.belichenko.a.utils.App;
import org.belichenko.a.utils.MyConstants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#returnInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements MyConstants {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static LoginFragment ourInstance = new LoginFragment();
    private OnFragmentInteractionListener mListener;

    //Bind the Views of the fragment_login.xml file
    @Bind(R.id.editLoginName)
    EditText editLoginName;
    @Bind(R.id.editLoginPass)
    EditText editLoginPass;
    @Bind(R.id.textLoginLink)
    TextView textLoginLink;
    @Bind(R.id.textRegister)
    TextView textRegister;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static LoginFragment returnInstance() {
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
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, fragmentView);

        // if landscape orientation then Reg button doesn't need
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RelativeLayout l = (RelativeLayout) textRegister.getParent();
            l.removeView(textRegister);
        } else {
            // on Registre listener
            textRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View fragmentView) {
                    onRegistreClicked();
                }
            });
        }

        if (savedInstanceState != null) {
            editLoginName.setText(savedInstanceState.getString(EDIT_LOGIN_NAME));
            editLoginPass.setText(savedInstanceState.getString(EDIT_LOGIN_PASS));
        }
        return fragmentView;
    }

    @OnClick(R.id.textLoginLink)
    protected void onLoginClicked() {
        StorageOfUser storageOfUser = StorageOfUser.getInstance();

        String name = editLoginName.getText().toString();
        String pass = editLoginPass.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this.getActivity().getBaseContext(), R.string.notValidName, Toast.LENGTH_LONG).show();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(this.getActivity().getApplicationContext(), R.string.notValidPass, Toast.LENGTH_LONG).show();
            return;
        }
        if (storageOfUser.findUser(name, pass)) {
            // set up user name to Preferences
            Context context = App.getAppContext();
            SharedPreferences mPrefs = context.getSharedPreferences(MAIN_PREFERENCE, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = mPrefs.edit();
            edit.putString(USER_IS_LOGIN, name);
            edit.apply();
            // clear fields
            editLoginName.setText("");
            editLoginPass.setText("");
            // go to main activity
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            editLoginPass.setText("");
            Toast.makeText(this.getActivity(), R.string.wrongLogin, Toast.LENGTH_LONG).show();
        }
    }

    private void onRegistreClicked() {
        mListener.onFragmentInteraction("");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String st);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (editLoginName != null) {
            outState.putString(EDIT_LOGIN_NAME, editLoginName.getText().toString());
        }
        if (editLoginPass != null) {
            outState.putString(EDIT_LOGIN_PASS, editLoginPass.getText().toString());
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            editLoginName.setText(savedInstanceState.getString(EDIT_LOGIN_NAME));
            editLoginPass.setText(savedInstanceState.getString(EDIT_LOGIN_PASS));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}