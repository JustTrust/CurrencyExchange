package org.belichenko.a.layout;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
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
 * Activities that contain this fragment must implement the
 * {@link RegistreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistreFragment extends Fragment implements MyConstants{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegistreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistreFragment.
     */
    public static RegistreFragment newInstance(int param1, String param2) {
        RegistreFragment fragment = new RegistreFragment();
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
            mParam1 = getArguments().getInt(ORIENTATION);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_registre, container, false);
        // on Save listener
        TextView txSave = (TextView) fragmentView.findViewById(R.id.text_save);
        txSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View fragmentView) {
                onSaveClicked(fragmentView);
            }
        });
        return fragmentView;
    }

    private void onSaveClicked(View fragmentView) {
        EditText editName = (EditText) getView().findViewById(R.id.editName);
        EditText editPass = (EditText) getView().findViewById(R.id.edit_pass);
        EditText editRepeatPass = (EditText) getView().findViewById(R.id.edit_rewrite_pass);

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        if (activity instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) activity;
//        } else {
//            throw new RuntimeException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}