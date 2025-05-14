package com.example.newsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    Context context;
    List<Person> personList;
    DatabaseHelper db;
    OnDataChangedListener listener;
    OnEditClickListener editClickListener;

    public interface OnEditClickListener {
        void onEdit(Person person);
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    public PersonAdapter(Context context, List<Person> personList, DatabaseHelper db,
                         OnDataChangedListener listener, OnEditClickListener editClickListener) {
        this.context = context;
        this.personList = personList;
        this.db = db;
        this.listener = listener;
        this.editClickListener = editClickListener;
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person p = personList.get(position);
        holder.name.setText(p.getName());
        holder.email.setText(p.getEmail());

        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEdit(p);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deletePerson(p.getId());
            personList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, personList.size());
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            listener.onDataChanged();
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView name, email;
        Button btnDelete, btnEdit;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            email = itemView.findViewById(R.id.txtEmail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
