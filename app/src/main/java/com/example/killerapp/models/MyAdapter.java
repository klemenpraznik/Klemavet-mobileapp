package com.example.killerapp.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.DisplayClient;
import com.example.killerapp.ItemClickListener;
import com.example.killerapp.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context c;
    ArrayList<Client> clients;

    public MyAdapter(Context c, ArrayList<Client> models) {
        this.c = c;
        this.clients = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mName.setText(clients.get(position).name);
        holder.mMail.setText(clients.get(position).email);
        holder.mPhone.setText(clients.get(position).phoneNumber);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                String gTitle = clients.get(position).name;
                String gMail = clients.get(position).email;
                String gPhone = clients.get(position).phoneNumber;

                Intent intent = new Intent(c, DisplayClient.class);
                intent.putExtra("iTitle", gTitle);
                intent.putExtra("iMail", gMail);
                intent.putExtra("iPhone", gPhone);
                c.startActivity(intent);
            }
        });

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int position) {
//                if (clients.get(position).name.equals())
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }
}
