package com.example.todolistapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Models.Lists;
import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListViewHolder> implements Filterable {

    List<Lists> mlists;
    List<Lists> filterLists;
    OnItemCilckListener listener;
    Context context;

    public ListsAdapter(ArrayList<Lists> lists, OnItemCilckListener listener) {
        this.mlists = lists;
        this.listener = listener;
        this.filterLists= lists;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent, false));
    }

    public void setLists(List<Lists> lists) {
        mlists = lists;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

//        holder.list_name.setText(lists.get(position).getListname());
//        holder.list_num.setText(lists.get(position).getListnum());

        Lists currentlist = filterLists.get(position);
        holder.list_name.setText(currentlist.getListname());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filterLists.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView list_name;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);

            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterLists = mlists;
                } else {
                    List<Lists> filteredList = new ArrayList<>();
                    for (Lists row : mlists) {
                        if (row.getListname().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterLists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterLists = (ArrayList<Lists>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
