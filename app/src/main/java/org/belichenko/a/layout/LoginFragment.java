package org.belichenko.a.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
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

    private int mOrientation;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance(int param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(ORIENTATION, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOrientation = getArguments().getInt(ORIENTATION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        TextView txReg = (TextView) fragmentView.findViewById(R.id.textRegister);
        // if landscape orientation Reg button don't need
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
//            RelativeLayout l = (RelativeLayout) txReg.getParent();
//            l.removeView(txReg);
        }
        // on Registre listener
        txReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View fragmentView) {
                onRegistreClicked(fragmentView);
            }
        });
        // on Login listener
        TextView txLogin = (TextView) fragmentView.findViewById(R.id.textLoginLink);
        txLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClicked(view);
            }
        });

        return fragmentView;
    }

    private void onLoginClicked(View view) {

        EditText editName = (EditText) getView().findViewById(R.id.editLoginName);
        EditText editPass = (EditText) getView().findViewById(R.id.editLoginPass);

        StorageOfUser storageOfUser = StorageOfUser.getInstance();

        String name = editName.getText().toString();
        String pass = editPass.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this.getActivity(), getString(R.string.notValidName), Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.isEmpty()) {
            Toast.makeText(this.getActivity(), getString(R.string.notValidPass), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this.getActivity(), getString(R.string.wrongLogin), Toast.LENGTH_SHORT).show();
            editPass.setText("");
        }
    }

    private void onRegistreClicked(View view) {
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String st);
    }
}
