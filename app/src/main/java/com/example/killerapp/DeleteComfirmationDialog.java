package com.example.killerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeleteComfirmationDialog extends AppCompatDialogFragment {
    private final OkHttpClient httpClient = new OkHttpClient();
    Integer clientId;
    String name;
    Context context;

    public DeleteComfirmationDialog(Integer clientId, String name, Context context){
        this.clientId = clientId;
        this.name = name;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("")
                .setMessage("Ali ste prepričani, da želite izbrisati to stranko?")
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel button
                    }})
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return builder.create();
    }

    public void delete() throws IOException {
        Request request = new Request.Builder()
                .url(getString(R.string.clients) + "/" + clientId.toString())
                .delete()
                .build();

        Response response = httpClient.newCall(request).execute();
        if (response.code() == 200 || response.code() == 201 || response.code() == 204){
            openDisplayClientActivity();
        }
    }

    public void openDisplayClientActivity() {
        Intent intent = new Intent(context, ClientActivity.class);
        startActivity(intent);
    }
}
