/*
 *Assignment In class 05
 * FileName:MainActivity
 * Group 21
 * Harshitha Govind-801212772
 * Surya Teja Chintala-801212229
 * */
package com.example.inclass05;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoginFragment.loginListener,
        AppCategories.appCategoriesListener,
        AppList.appListListener,
        RegisterFragment.RegisterListener {
    ArrayAdapter<String> adapter;
    ArrayList<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment(), "loginfragment")
                .commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void sendUser(String token, DataServices.Account userAccount) {
        AppCategories appCategories = new AppCategories(token, userAccount);

        appCategories.setPassedData(token, userAccount);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, appCategories, "appCategories")
                .commit();
    }


    @Override
    public void newuser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterFragment(), "registerfragment")
                .commit();

    }

    @Override
    public void goBackToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), "loginfragment")
                .commit();
    }

    @Override
    public void goToAppList(String token, String SelectedCategory) {
        AppList appList = new AppList();

        appList.setPassedData(token, SelectedCategory);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, appList, "appList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToAppDetails(String token, DataServices.App selectedApp) {
        AppDetails appDetailsList = new AppDetails();

        appDetailsList.setPassedData(token, selectedApp);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, appDetailsList, "appDetailsList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void registerUser(String token, DataServices.Account userAccount) {
        AppCategories appCategories = new AppCategories(token, userAccount);

        appCategories.setPassedData(token, userAccount);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, appCategories, "appCategories")
                .commit();


    }
}
