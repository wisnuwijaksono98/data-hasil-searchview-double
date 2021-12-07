package com.example.submission2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission2.databinding.ListRowUserBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements Filterable {

    private  ArrayList<Model> modelArrayList;
    private  ArrayList<Model> modelArrayFull;

    public ListAdapter(ArrayList<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
        modelArrayFull = new ArrayList<>(modelArrayList);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ListRowUserBinding binding = ListRowUserBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Model model = modelArrayList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(model.getAvatar())
                .circleCrop()
                .into(holder.binding.imgAvatar);
        holder.binding.tvName.setText(model.getName());
        holder.binding.tvUsername.setText(model.getUsername());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.GET_DATA, (Parcelable) model);

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        private final ListRowUserBinding binding;

        public ListViewHolder(ListRowUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

//    filter

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelArrayFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Model item : modelArrayFull) {
                    if (item.getUsername().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelArrayList.clear();
            modelArrayFull.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
