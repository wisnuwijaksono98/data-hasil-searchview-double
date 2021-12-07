package com.example.submission2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.submission2.databinding.FragmentFollowingBinding;
import com.example.submission2.databinding.ListRowFollowerBinding;
import com.example.submission2.databinding.ListRowFollowingBinding;

import java.util.ArrayList;

public class ListAdapterFollowing extends RecyclerView.Adapter<ListAdapterFollowing.ListViewHolder> {

    private final ArrayList<ModelOther> arrayList;

    public ListAdapterFollowing(ArrayList<ModelOther> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ListRowFollowingBinding binding = ListRowFollowingBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ModelOther modelOther = arrayList.get(position);
        Glide.with(holder.itemView)
                .load(modelOther.getAvatarUrl())
                .circleCrop()
                .into(holder.binding.imgAvatar);
        holder.binding.tvName.setText(modelOther.getName());
        holder.binding.tvType.setText(modelOther.getType());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ListRowFollowingBinding binding;

        public ListViewHolder(@NonNull ListRowFollowingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
