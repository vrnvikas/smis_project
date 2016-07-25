package com.onthespot.vikaskumar.miniproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LoginFragment extends Fragment implements RequestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    AutoCompleteTextView emailEditText;
    EditText passwordeditText;
    Button logInButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @param baseContext
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2, Context baseContext) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        context = baseContext;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditText = (AutoCompleteTextView) view.findViewById(R.id.input_email);
        logInButton = (Button) view.findViewById(R.id.login_button);
        passwordeditText = (EditText) view.findViewById(R.id.input_password);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkForEmptyField()){
                    if (Utility.isNetworkAvailable(context)) {


                        RetroInterface retroInterface = Utility.createRetrofit();
                        Call<UserTokenModel> call = retroInterface.LoginUserUser(constructHeader());


                        call.enqueue(new Callback<UserTokenModel>() {
                            @Override
                            public void onResponse(Call<UserTokenModel> call, Response<UserTokenModel> response) {
                                //log.i(response.headers())
                                if(response.body() != null){
                                    Toast.makeText(context, "Loged In", Toast.LENGTH_SHORT).show();
                                    Utility.putTokenIn(response.body().getToken(),context);
                                    startActivityMain();
                                }else {
                                    Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<UserTokenModel> call, Throwable t) {


                            }
                        });

                    }else {
                        Toast.makeText(context, "InterNet Not Working", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

    }

    private void startActivityMain() {
        Intent i = new Intent(context, MainActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private String constructHeader() {
        String hashPassword = Utility.getHashString(passwordeditText.getText().toString(), "SHA-1");
        String header = emailEditText.getText().toString() + ":" + passwordeditText.getText().toString();
        return "Basic " + Base64.encodeToString(header.getBytes(), Base64.NO_WRAP);
    }

    private boolean checkForEmptyField() {

        int counter = 0;

        if(emailEditText.getText().toString().isEmpty()){
            emailEditText.setError("Enter Email");
            counter++;
        }
        if(passwordeditText.getText().toString().isEmpty()){
            passwordeditText.setError("Enter Password");
            counter++;
        }

        return !(counter == 2);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
