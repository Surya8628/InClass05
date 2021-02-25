package com.example.inclass05;

/*
 *Assignment In class 05
 * FileName:LoginFrgament
 * Group 21
 * Harshitha Govind-801212772
 * Surya Teja Chintala-801212229
 * */


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    TextView editTextLoginEmailAddress,editTextLoginPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String TAG="Login Fragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        // Set title bar
         getActivity().setTitle(getResources().getString(R.string.login));

        view.findViewById(R.id.updateButton).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //User Account login
                        editTextLoginEmailAddress = view.findViewById(R.id.editTextLoginEmailAddress);
                        editTextLoginPassword = view.findViewById(R.id.editTextLoginPassword);
                        String email = editTextLoginEmailAddress.getText().toString();
                        String password = editTextLoginPassword.getText().toString();
                        //Valiation of User Account
                        DataServices.login(email,
                                password,
                                new DataServices.AuthResponse() {
                                    @Override
                                    public void onSuccess(String token) {
                                        if (token != null) {
                                            DataServices.getAccount(token, new DataServices.AccountResponse() {
                                                @Override
                                                public void onSuccess(DataServices.Account account) {
                                                    mListener.sendUser(token,account);
                                                }
                                                @Override
                                                public void onFailure(DataServices.RequestException exception) {
                                                    Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, getResources().getString(R.string.login_success));
                                        }
                                    }

                                    @Override
                                    public void onFailure(DataServices.RequestException exception) {
                                        Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
        // Open Register Fragment
        view.findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.newuser();
            }
        });
        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof loginListener){
            mListener = (loginListener)context;
        }else{
            throw new RuntimeException(context.toString()+"login check");
        }
    }
    //Creating login interface
    loginListener mListener;
    public interface loginListener{
        void sendUser(String token,DataServices.Account userAccount);
        void newuser();
    }
}