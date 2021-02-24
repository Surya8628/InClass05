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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView applistAppname,appListArtistName,appListReleaseDate;
    ListView genreList;
    ArrayAdapter<String> adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AppDetails newInstance(String param1, String param2) {
        AppDetails fragment = new AppDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title bar
        ((MainActivity) getActivity())
                .setActionBarTitle(getResources().getString(R.string.app_details));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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