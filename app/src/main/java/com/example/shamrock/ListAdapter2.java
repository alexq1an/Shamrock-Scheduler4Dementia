package com.example.shamrock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This is the adapter for our task list
 */
public class ListAdapter2 extends ArrayAdapter<pTask> {

    //initialize the list
    public ListAdapter2(@NonNull Context context,@NonNull ArrayList<pTask> objects) {
        super(context, R.layout.task_list_item, objects);
    }

    //get the current item id
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the position of the task and store the task elements
        pTask pTask = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item,parent,false);

        }

        // all attributes for our item and subitem design, and set the source
        TextView description = convertView.findViewById(R.id.task_description);

        description.setText(pTask.getDescription());
        TextView title = convertView.findViewById(R.id.task_title);
        title.setText(pTask.getTitle());
        TextView time = convertView.findViewById(R.id.task_time);
        time.setText(pTask.getTime().toString());
        
        return convertView;
    }
}
