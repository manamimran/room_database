package com.example.room_database;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    //initialize variable
    private List<MainData> dataList;
    private Activity context;
    private RoomDB database;

    //create constructor

    public MainAdapter(Activity context, List<MainData> dataList){
        this.context =context;
        this.dataList =dataList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //initialize main data
        MainData mainData =dataList.get(position);

        //initialize database
        database =RoomDB.getInstance(context);

        //set text on textview
        holder.textView.setText(mainData.getText());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //initialize main data
                MainData maindata =dataList.get(holder.getAdapterPosition());

                //get Id
                int sID = mainData.getID();

                //get text
                String sTEXT = maindata.getText();

                //create dialog
                Dialog dialog =new Dialog(context);

                //set content view
                dialog.setContentView(R.layout.dialog_update);

                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;

                //initialize height
                int height =WindowManager.LayoutParams.WRAP_CONTENT;

                //set layout
                dialog.getWindow().setLayout(width,height);

                //show dialog
                dialog.show();

                //initialize and assign variable
                EditText edit = dialog.findViewById(R.id.edit2);
                Button update = dialog.findViewById(R.id.update);

                //set text on edit text
                edit.setText(sTEXT);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //dismiss dialog
                        dialog.dismiss();

                        //get update text from edit view
                        String uText =edit.getText().toString().trim();

                        //update text in database
                        database.mainDao().update(sID, uText);

                        //notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getALL());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //initialize main data
                MainData mainData =dataList.get(holder.getAdapterPosition());

                //delete text from database
                database.mainDao().delete(mainData);

                //notify when data is deleted
                int position =holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //initialize variable
        TextView textView;
        ImageView edit,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //assign variable
            textView= itemView.findViewById(R.id.textView);
            edit= itemView.findViewById(R.id.edit);
            delete= itemView.findViewById(R.id.delete);
        }
    }
}
