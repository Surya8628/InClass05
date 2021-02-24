package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.inclass05.AppCategories.selectedCategory;
import static com.example.inclass05.AppCategories.tokenValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AppListRowItemAdapter rowItemAdapter;
ListView appListView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<DataServices.App> appListData;
    public AppList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppList.
     */
    // TODO: Rename and change types and number of parameters
    public static AppList newInstance(String param1, String param2) {
        AppList fragment = new AppList();
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
                .setActionBarTitle(getResources().getString(R.string.app_list));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void setPassedData(String token,String selectedCategory) {
        tokenValue=token;
        AppCategories.selectedCategory= selectedCategory;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_app_list, container, false);

        DataServices.getAppsByCategory(tokenValue, selectedCategory, new DataServices.DataResponse<DataServices.App>() {
            @Override
            public void onSuccess(ArrayList<DataServices.App> data) {
                appListData=data;
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {

            }
        });
        appListView=view.findViewById(R.id.appListView);
        rowItemAdapter=new AppListRowItemAdapter(getActivity().getApplicationContext(),R.layout.app_list_row_item,appListData);
        appListView.setAdapter(rowItemAdapter);
        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataServices.App row=appListData.get(position);
            aListener.goToAppDetails(tokenValue,row);
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AppList.appListListener){
            aListener = (AppList.appListListener)context;
        }else{
            throw new RuntimeException(context.toString()+"app list check");
        }
    }
    //Creating App List interface
    AppList.appListListener aListener;
    public interface appListListener{
        void goToAppDetails(String token,DataServices.App selectedApp);
    }
}