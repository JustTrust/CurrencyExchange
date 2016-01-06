package org.belichenko.a.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements MyConstants {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    private TextView txReg;
    private TextView txLogin;
    private EditText editName;
    private EditText editPass;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static LoginFragment newInstance() {
        LoginFragment fr = new LoginFragment();
        Log.d("InstanceState", "have new instance=" + fr.toString());
        return fr;
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
        txReg = (TextView) fragmentView.findViewById(R.id.textRegister);
        // if landscape orientation then Reg button doesn't need
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RelativeLayout l = (RelativeLayout) txReg.getParent();
            l.removeView(txReg);
        } else {
            // on Registre listener
            txReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View fragmentView) {
                    onRegistreClicked();
                }
            });
        }
        // on Login listener
        txLogin = (TextView) fragmentView.findViewById(R.id.textLoginLink);
        txLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClicked();
            }
        });
        editName = (EditText) fragmentView.findViewById(R.id.editLoginName);
        editPass = (EditText) fragmentView.findViewById(R.id.editLoginPass);

        if (savedInstanceState != null) {
            Log.d("InstanceState", "fragment name=" + this.toString());
            Log.d("InstanceState", "nameConst=" + EDIT_LOGIN_NAME);
            Log.d("InstanceState", "passConst=" + EDIT_LOGIN_PASS);

            editName.setText(savedInstanceState.getString(EDIT_LOGIN_NAME));
            editPass.setText(savedInstanceState.getString(EDIT_LOGIN_PASS));
        }
        return fragmentView;
    }

    private void onLoginClicked() {
        StorageOfUser storageOfUser = StorageOfUser.getInstance();

        String name = editName.getText().toString();
        String pass = editPass.getText().toString();

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
            editName.setText("");
            editPass.setText("");
            // go to main activity
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            editPass.setText("");
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

        String name = editName.getText().toString();
        String pass = editPass.getText().toString();

        outState.putString(EDIT_LOGIN_NAME, name);
        outState.putString(EDIT_LOGIN_PASS, pass);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            editName.setText(savedInstanceState.getString(EDIT_LOGIN_NAME));
            editPass.setText(savedInstanceState.getString(EDIT_LOGIN_PASS));
        }
    }
}