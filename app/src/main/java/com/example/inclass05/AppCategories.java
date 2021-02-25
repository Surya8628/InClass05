/*
 *Assignment In class 05
 * FileName:AppCategories
 * Group 21
 * Harshitha Govind-801212772
 * Surya Teja Chintala-801212229
 * */

package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;


public class AppCategories extends Fragment {
    private static final String ARG_APP_CATEGORIES = "appCategories";
    private static final String ARG_TOKEN = "token";
    public static String selectedCategory;
    public static String tokenValue;
    public static DataServices.App appRowDetails;
    private static DataServices.Account userProfile;

    private Serializable mParam1;
    private String token;
    TextView welcomeText;
    ListView appCategories;
    ArrayAdapter<String> adapter;
    ArrayList<String> categories=new ArrayList<>();

    public AppCategories(String token, DataServices.Account userAccount) {
    }

    public static AppCategories newInstance(String token,DataServices.Account userAccount) {
        AppCategories fragment = new AppCategories(token,userAccount);
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        args.putSerializable(ARG_APP_CATEGORIES,(Serializable) userAccount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        if (getArguments() != null) {
            token = getArguments().getString(ARG_TOKEN);
            mParam1 = (DataServices.Account) getArguments().getSerializable(ARG_APP_CATEGORIES);
        }
    }
    public void setPassedData(String token,DataServices.Account profile) {
        AppCategories.tokenValue=token;
        AppCategories.userProfile= profile;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_app_categories, container, false);
        // Set title bar
        getActivity().setTitle(getResources().getString(R.string.app_categories));

        welcomeText=view.findViewById(R.id.welcomeText);
        appCategories=view.findViewById(R.id.appCategoriesList);
        welcomeText.setText(getResources().getString(R.string.welcome)+userProfile.getName());
        DataServices.getAppCategories(tokenValue, new DataServices.DataResponse<String>() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                categories=data;

            }
            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        if(categories != null) {
            adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, categories);
            appCategories.setAdapter(adapter);
        }
        view.findViewById(R.id.logOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aListener.goBackToLogin();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,categories);
        appCategories.setAdapter(adapter);
        appCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aListener.goToAppList(tokenValue,categories.get(position));
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof appCategoriesListener){
            aListener = (appCategoriesListener)context;
        }else{
            throw new RuntimeException(context.getResources().getString(R.string.app_cat));
        }
    }
    //Creating App Categories interface
    AppCategories.appCategoriesListener aListener;
    public interface appCategoriesListener{
        void goBackToLogin();
        void goToAppList(String token,String SelectedCategory);
    }
}
