package com.example.lesson05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private int resource;
    private List<Contact> contacts;
    private LayoutInflater inflater;
    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> contacts) {
        super(context, resource, contacts);
        this.resource = resource;
        this.contacts = contacts;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = inflater.inflate(resource, parent, false);

        ImageView ivAvatar = item.findViewById(R.id.ivAvatar);
        TextView tvFirst = item.findViewById(R.id.tvFirst);
        TextView tvSecond = item.findViewById(R.id.tvSecond);

        Contact contact = contacts.get(position);

        ivAvatar.setImageResource(contact.getAvatar());
        tvFirst.setText(contact.getFirstName());
        tvSecond.setText(contact.getLastName());

        return item;
    }
}
