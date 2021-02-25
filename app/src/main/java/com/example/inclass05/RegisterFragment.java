/*
 *Assignment In class 05
 * FileName:RegisterFragment
 * Group 21
 * Harshitha Govind-801212772
 * Surya Teja Chintala-801212229
 * */
package com.example.inclass05;
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

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    TextView editTextRegisterName, editTextRegisterEmail,editTextRegisterPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        //Set title bar
        getActivity().setTitle(getResources().getString(R.string.Register));

        view.findViewById(R.id.SubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextRegisterName= view.findViewById(R.id.editTextRegisterName);
                editTextRegisterEmail = view.findViewById(R.id.editTextRegisterEmail);
                editTextRegisterPassword = view.findViewById(R.id.editTextRegisterPassword);
                String name = editTextRegisterName.getText().toString();
                String email = editTextRegisterEmail.getText().toString();
                String password = editTextRegisterPassword.getText().toString();
                //Updating userAccount and Checking for Duplicate users
                DataServices.register(name, email, password, new DataServices.AuthResponse() {
                    @Override
                    public void onSuccess(String token) {
                        if (token != null) {
                            DataServices.getAccount(token, new DataServices.AccountResponse() {
                                @Override
                                public void onSuccess(DataServices.Account account) {
                                   RListener.registerUser(token,account);
                                    Log.d("TAG", "sendUser: login success");
                                }
                                @Override
                                public void onFailure(DataServices.RequestException exception) {
                                    Log.d("TAG", exception.getMessage());
                                    Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                            Log.d("TAG", getResources().getString(R.string.login_success));
                        }
                    }
                    @Override
                    public void onFailure(DataServices.RequestException exception) {
                        Log.d("TAG", exception.getMessage());
                        Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });
        //Open Login Frgament
        view.findViewById(R.id.Cancelbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RListener.goBackToLogin();
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof RegisterFragment.RegisterListener){
            RListener = (RegisterFragment.RegisterListener)context;
        }else{
            throw new RuntimeException(context.toString()+"Register check");
        }
    }
    //Creating Register Interface
    RegisterFragment.RegisterListener RListener;
    public interface RegisterListener{
        void goBackToLogin();
        void registerUser(String token,DataServices.Account userAccount);
    }
}