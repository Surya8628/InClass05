/*
 *Assignment In class 05
 * FileName:AppDetails
 * Group 21
 * Harshitha Govind-801212772
 * Surya Teja Chintala-801212229
 * */
package com.example.inclass05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.inclass05.AppCategories.appRowDetails;


public class AppDetails extends Fragment {

    TextView applistAppname,appListArtistName,appListReleaseDate;
    ListView genreList;
    ArrayAdapter<String> adapter;

    public AppDetails() {
        // Required empty public constructor
    }

    public static AppDetails newInstance() {
        AppDetails fragment = new AppDetails();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setPassedData(String token,DataServices.App appDetails) {
        AppCategories.tokenValue=token;
        appRowDetails= appDetails;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_app_details, container, false);
        // Set title bar
      getActivity().setTitle(getResources().getString(R.string.app_details));

        applistAppname=view.findViewById(R.id.applistAppname);
        appListArtistName= view.findViewById(R.id.appListArtistName);
        appListReleaseDate=view.findViewById(R.id.appListReleaseDate);
        genreList=view.findViewById(R.id.genreList);
        applistAppname.setText(appRowDetails.name);
        appListArtistName.setText(appRowDetails.artistName);
        appListReleaseDate.setText(appRowDetails.releaseDate);
        adapter=new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,android.R.id.text1,appRowDetails.genres);
        genreList.setAdapter(adapter);
        return view;
    }
}