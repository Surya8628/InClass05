package com.example.inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppListRowItemAdapter extends ArrayAdapter<DataServices.App> {
    public AppListRowItemAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.app_list_row_item,parent,false);
        }
        DataServices.App appRowItem=getItem(position);
        TextView appNameView=convertView.findViewById(R.id.appNameView);
        TextView artistNameView=convertView.findViewById(R.id.artistNameView);
        TextView releaseDateView=convertView.findViewById(R.id.releaseDateView);
        appNameView.setText(appRowItem.name);
        artistNameView.setText(appRowItem.artistName);
        releaseDateView.setText(appRowItem.releaseDate);
        return convertView;
    }
}
