package com.example.killerapp.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.killerapp.Documents.DisplayDocument;
import com.example.killerapp.ItemClickListener;
import com.example.killerapp.R;

import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentHolder>{
    Context c;
    ArrayList<Document> documents;

    public DocumentAdapter(Context c, ArrayList<Document> models) {
        this.c = c;
        this.documents = models;
    }

    @NonNull
    @Override
    public DocumentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.document_row, null);

        return new DocumentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentHolder holder, int position) {
        holder.dName.setText(documents.get(position).getClient().name);
        holder.dMail.setText(documents.get(position).getClient().email);
        holder.dPhone.setText(documents.get(position).getClient().phoneNumber);
        holder.dDocId.setText("Št. rač.: " + documents.get(position).getDocumentId().toString());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent = new Intent(c, DisplayDocument.class);
                intent.putExtra("iDocumentId", documents.get(position).getDocumentId());
                intent.putExtra("iClientId", documents.get(position).getClientId());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }
}
