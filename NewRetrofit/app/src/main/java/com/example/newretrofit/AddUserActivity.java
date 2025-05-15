package com.example.newretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newretrofit.databinding.ActivityAddUserBinding;

public class AddUserActivity extends AppCompatActivity {

    private ActivityAddUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAddUser.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            String username = binding.etUsername.getText().toString();
            String email = binding.etEmail.getText().toString();

            if (name.isEmpty() || username.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(name, username, email);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newUser", newUser);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
