package com.example.killerapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDialogFragment;

import okhttp3.Response;

public class ResponseErrorDialog extends AppCompatDialogFragment {
    private int responseCode;
    private String responseMessage;

    public ResponseErrorDialog(Response response){
        this.responseCode = response.code();
        switch(responseCode){
            case 400:
                responseMessage = "Bad request! (The request was invalid.)";
                break;
            case 401:
                responseMessage = "Unauthorized! (The request did not include an authentication token or the authentication token was expired.)";
                break;
            case 403:
                responseMessage = "Forbidden! (The client did not have permission to access the requested resource.)";
                break;
            case 404:
                responseMessage = "Not Found (The requested resource was not found.)";
                break;
            case 500:
                responseMessage = "Internal Server Error (The request was not completed due to an internal error on the server side.)";
                break;
            case 503:
                responseMessage = "Service Unavailable (The server was unavailable.)";
                break;
            default:
                responseMessage = response.message();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Napaka!")
                .setMessage("Koda " + responseCode + ": " + responseMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
