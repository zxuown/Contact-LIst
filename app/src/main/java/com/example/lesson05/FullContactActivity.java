package com.example.lesson05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lesson05.databinding.ActivityFullContactBinding;

import java.util.Objects;

public class FullContactActivity extends AppCompatActivity {
    private ActivityFullContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFullContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");

        assert contact != null;
        binding.ivAvatar.setImageResource(contact.getAvatar());
        binding.etFirstName.setText(contact.getFirstName());
        binding.etLastName.setText(contact.getLastName());
        binding.etPhone.setText(contact.getPhone());
        binding.etEmail.setText(contact.getEmail());

        findViewById(R.id.btnApply).setOnClickListener(v -> {
            contact.setFirstName(binding.etFirstName.getText().toString());
            contact.setLastName(binding.etLastName.getText().toString());
            contact.setPhone(binding.etPhone.getText().toString());
            contact.setEmail(binding.etEmail.getText().toString());

            ContactApi.updateContact(contact);

            finish();
        });
    }
}