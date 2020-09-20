package com.example.coffeemachine;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List <CoffeeModel> mylist;
    Context context;
    DataBaseHelperDAO db;

    public MyAdapter(Context ct, List <CoffeeModel> list) {
        context = ct;
        mylist = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mybrand.setText(mylist.get(position).getCoffeeBrand());
        holder.mybrew.setText(mylist.get(position).getCoffeeBrew());
        holder.mydate.setText(mylist.get(position).getDate());
        holder.myMilk.setText(mylist.get(position).getCoffeeMilk());
        holder.myNote.setText(mylist.get(position).getNotes());
     //   int pos = mylist.get(position).getId();
     //   holder.myId.setText(pos+".");
        final CoffeeModel md = mylist.get(position);

        holder.mybrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(md);
            }
        });

    }

    private void removeItem(CoffeeModel md) {
        int currentPosition = mylist.indexOf(md);
        mylist.remove(currentPosition);
        notifyItemRemoved(currentPosition);
        db = new DataBaseHelperDAO(context);
      //  db.DeleteOne(md.getId());

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mybrand;
        TextView mybrew;
        TextView mydate;
        TextView myMilk;
        TextView myNote;
        TextView myId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mybrand = itemView.findViewById(R.id.brandtxtview);
            mybrew = itemView.findViewById(R.id.brewtxtview);
            mydate = itemView.findViewById(R.id.datetxtview);
            myMilk = itemView.findViewById(R.id.milktxtview);
            myNote = itemView.findViewById(R.id.notetxtview);
            myId   = itemView.findViewById(R.id.IDtxtview);
        }
    }
}
