package com.example.newretrofit;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newretrofit.databinding.UserItemBinding;
import java.util.List;
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> userList;
    private final OnUserActionListener listener;

    public UserAdapter(List<User> userList, OnUserActionListener listener) {
        this.userList = userList;
        this.listener = listener;

    }

    public interface OnUserActionListener {
        void onUpdate(User user, int position);
        void onDelete(int position);
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.binding.tvName.setText(user.getName());
        holder.binding.tvUsername.setText(user.getUsername());
        holder.binding.tvEmail.setText(user.getEmail());

        holder.binding.btnUpdate.setOnClickListener(v -> {
            listener.onUpdate(user, position);
        });

        holder.binding.btnDelete.setOnClickListener(v -> {
            listener.onDelete(position);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        UserItemBinding binding;

        UserViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
