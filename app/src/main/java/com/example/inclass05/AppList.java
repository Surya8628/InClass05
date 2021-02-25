/*
 *Assignment In class 05
 * FileName:AppList
 * Group 21
 * Harshitha Govind-801212772
 * Surya Teja Chintala-801212229
 * */
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
import android.widget.Toast;
import java.util.ArrayList;
import static com.example.inclass05.AppCategories.selectedCategory;
import static com.example.inclass05.AppCategories.tokenValue;


public class AppList extends Fragment {
    AppListRowItemAdapter rowItemAdapter;
    ListView appListView;
    ArrayList<DataServices.App> appListData;
    public AppList() {
        // Required empty public constructor
    }

    public static AppList newInstance() {
        AppList fragment = new AppList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        getActivity().setTitle(selectedCategory);


        DataServices.getAppsByCategory(tokenValue, selectedCategory, new DataServices.DataResponse<DataServices.App>() {
            @Override
            public void onSuccess(ArrayList<DataServices.App> data) {
                appListData=data;

            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity().getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        appListView=view.findViewById(R.id.appListView);
        if(appListData != null) {
            rowItemAdapter = new AppListRowItemAdapter(getActivity().getApplicationContext(), R.layout.app_list_row_item, appListData);
            appListView.setAdapter(rowItemAdapter);
        }

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataServices.App row=appListData.get(position);
                // Set title bar
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
            throw new RuntimeException(context.getResources().getString(R.string.app_check));
        }
    }
    //Creating App List interface
    AppList.appListListener aListener;
    public interface appListListener{
        void goToAppDetails(String token,DataServices.App selectedApp);
    }
}