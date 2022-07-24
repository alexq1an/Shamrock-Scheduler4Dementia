package com.example.shamrock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter2 extends ArrayAdapter<Task> {

    public ListAdapter2(@NonNull Context context,@NonNull ArrayList<Task> objects) {
        super(context, R.layout.task_list_item, objects);
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Task task = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item,parent,false);

        }
        // all attributes for our item and subitem design, and set the source
//        ImageView imageView = convertView.findViewById(R.id.profile_pic);
        TextView description = convertView.findViewById(R.id.task_description);
//        TextView id = convertView.findViewById(R.id.list_patient_id);

//        imageView.setImageResource(patient.getImageId());
        description.setText(task.getDescription());
//        id.setText(patient.getList_patient_id());


        return convertView;
    }
}
