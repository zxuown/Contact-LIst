package com.example.lesson05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView lvContacts;
    private List<Contact> contacts;
    private ContactAdapter adapter;
    private Button btnAdd;
    public boolean areNamesSwapped = false;

    public SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContacts = findViewById(R.id.lvContacts);
        btnAdd = findViewById(R.id.btnAdd);

        ContactApi.init();
        contacts = ContactApi.getContacts();

        adapter = new ContactAdapter(
                this,
                R.layout.contact_item,
                contacts
        );
        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            Contact contact = contacts.get(position);
            Intent intent = new Intent(this, FullContactActivity.class);
            intent.putExtra("contact", contact);
            startActivity(intent);
        });
        lvContacts.setOnItemLongClickListener((parent, view, position, id) -> {
            contacts.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "removed", Toast.LENGTH_SHORT).show();
            return true;
        });

        btnAdd.setOnClickListener(v -> {
            ContactDialog contactDialog = new ContactDialog();
            contactDialog.show(getSupportFragmentManager(), "contact");
        });

        sharedPref = getSharedPreferences("com.example.lesson05", MODE_PRIVATE);
        areNamesSwapped = sharedPref.getBoolean("areNamesSwapped", false);
        if (areNamesSwapped){
            swapNames();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setttings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        SharedPreferences.Editor editor = sharedPref.edit();
        if (id == R.id.firstSecond) {
            if (areNamesSwapped) {
                swapNames();
                areNamesSwapped = false;
                editor.putBoolean("areNamesSwapped", false);
                editor.apply();
                Toast.makeText(this, "First name and last name swapped", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.secondFirst) {
            if (!areNamesSwapped) {
                swapNames();
                areNamesSwapped = true;
                editor.putBoolean("areNamesSwapped", true);
                editor.apply();
                Toast.makeText(this, "First name and last name restored", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void swapNames() {
        for (Contact contact : contacts) {
            String temp = contact.getFirstName();
            contact.setFirstName(contact.getLastName());
            contact.setLastName(temp);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}