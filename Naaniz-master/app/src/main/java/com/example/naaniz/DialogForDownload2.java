package com.example.naaniz;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogForDownload2 extends AppCompatDialogFragment {
    private Button bt1,bt2;
    DatabaseReference ref;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.sharemenu,null);
        builder.setView(view)
                .setTitle("")
                .setCancelable(false);
        bt1=(Button)view.findViewById(R.id.bt11_dialog);
        bt2=(Button)view.findViewById(R.id.bt22_dialog);
        final AlertDialog alertDialog =  builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }
}
