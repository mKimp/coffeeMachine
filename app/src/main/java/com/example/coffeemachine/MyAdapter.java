package com.example.coffeemachine;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {
    private List <CoffeeModel> mylist;
    private List <CoffeeModel> myFullList;
    private Context context;
    private DataBaseHelperDAO db;

    public MyAdapter(Context ct, List <CoffeeModel> list) {
        context = ct;
        mylist = list;
        myFullList = new ArrayList<>(mylist);
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
        String img = mylist.get(position).getImage();
        if(img == null){
            holder.myImage.setImageURI(null);
        }
        else{
            Uri myUri = Uri.parse(img);
            holder.myImage.setImageURI(myUri);
        }

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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter(){
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CoffeeModel> filteredList = new ArrayList<>();

            if (constraint.length() == 0 || constraint == null){
                filteredList.addAll(myFullList);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CoffeeModel cf: myFullList){
                    if (cf.getCoffeeBrand().toLowerCase().trim().contains(filterPattern)){
                        filteredList.add(cf);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mylist.clear();
            mylist.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mybrand;
        TextView mybrew;
        TextView mydate;
        TextView myMilk;
        TextView myNote;
        TextView myId;
        ImageView myImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mybrand = itemView.findViewById(R.id.brandtxtview);
            mybrew = itemView.findViewById(R.id.brewtxtview);
            mydate = itemView.findViewById(R.id.datetxtview);
            myMilk = itemView.findViewById(R.id.milktxtview);
            myNote = itemView.findViewById(R.id.notetxtview);
            myId   = itemView.findViewById(R.id.IDtxtview);
            myImage = itemView.findViewById(R.id.imageView);
        }
    }
}
