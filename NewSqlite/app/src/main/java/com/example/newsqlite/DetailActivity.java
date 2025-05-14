package com.example.newsqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper db;
    List<Person> personList;
    PersonAdapter adapter;

    EditText edtName, edtEmail;
    Button btnUpdate;

    int selectedPersonId = -1; // To track which person is being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnUpdate = findViewById(R.id.btnUpdate);

        db = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnUpdate.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();

            if (selectedPersonId != -1 && !name.isEmpty() && !email.isEmpty()) {
                db.updatePerson(new Person(selectedPersonId, name, email));
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                selectedPersonId = -1;
                edtName.setText("");
                edtEmail.setText("");
                btnUpdate.setVisibility(View.GONE);
                loadData();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();
    }

    private void loadData() {
        personList = db.getAllPersons();
        adapter = new PersonAdapter(
                this,
                personList,
                db,
                this::loadData,
                this::startEditForm // <- this is the fix
        );
        recyclerView.setAdapter(adapter);
    }


    // Start edit mode in the form
    private void startEditForm(Person person) {
        edtName.setText(person.getName());
        edtEmail.setText(person.getEmail());
        selectedPersonId = person.getId();
        btnUpdate.setVisibility(View.VISIBLE);
    }
}
