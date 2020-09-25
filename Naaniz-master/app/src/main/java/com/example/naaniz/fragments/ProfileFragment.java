package com.example.naaniz.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.naaniz.R;
import com.example.naaniz.SupportActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private TextView nameText;
    private TextView phoneText;
    private TextView cityText;
    private TextView foodText;
    private TextView addressText;
    private ImageView panImage;
    private ImageView aadharImage;
    private ImageView cancelImage;
    private ImageView electricityBill;
    private ImageView photo;
    private ImageView foodLicenece;
    private ImageView panCheck;
    private ImageView aadharCheck;
    private ImageView cancelCheck;
    private ImageView electricityBillStatus;
    private ImageView photoStatus;
    private ImageView foodLiceneceStatus;
    private String name;
    private ArrayList<Uri> list;
    private String phone;

    FloatingActionButton support;

    TextView panStatus, aadharStatus, chequeStatus, billStatus, yourPhotoStatus, flStatus;

    LinearLayout documentTypeFL, documentTypeNoFL;

    int documentType, status;

    private  StateProgressBar stateProgressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void statusProgress()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = firebaseDatabase.getReference("users");
        DatabaseReference ref = parentRef.child(phone).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String p="1",q="0";
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                     if(d.getKey()=="aadhar_uploaded")
                     {
                         p=d.getValue().toString();
                     }
                     if(d.getKey()=="pan_uploaded")
                     {
                         q=d.getValue().toString();
                     }
                }
                if(p.equals("1") && q.equals("1"))
                {
                    StateProgressBar.StateNumber number = StateProgressBar.StateNumber.THREE;
                    stateProgressBar.setCurrentStateNumber(number);
                }
                else
                {
                    StateProgressBar.StateNumber number = StateProgressBar.StateNumber.ONE;
                    stateProgressBar.setCurrentStateNumber(number);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        support = view.findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(), SupportActivity.class));
            }
        });

        nameText = view.findViewById(R.id.name_profile);
        phoneText = view.findViewById(R.id.phone_profile);
        cityText = view.findViewById(R.id.city_profile);
        addressText = view.findViewById(R.id.address_profile);
        foodText = view.findViewById(R.id.food_profile);

        panImage = view.findViewById(R.id.pan_image);
        aadharImage = view.findViewById(R.id.aadhar_image);
        cancelImage = view.findViewById(R.id.cancel_cheque_image);
        electricityBill = view.findViewById(R.id.electricity_bill_image);
        photo = view.findViewById(R.id.your_photo_image);
        foodLicenece = view.findViewById(R.id.fl_image);

        panCheck = view.findViewById(R.id.pan_image_status);
        aadharCheck = view.findViewById(R.id.aadhar_image_status);
        cancelCheck = view.findViewById(R.id.cancel_cheque_image_status);
        electricityBillStatus = view.findViewById(R.id.electricity_bill_image_status);
        photoStatus = view.findViewById(R.id.your_photo_image_status);
        foodLiceneceStatus = view.findViewById(R.id.fl_image_status);

        panStatus = view.findViewById(R.id.statusPan);
        aadharStatus = view.findViewById(R.id.statusAadhar);
        chequeStatus = view.findViewById(R.id.statusCheque);
        billStatus = view.findViewById(R.id.statusBill);
        yourPhotoStatus = view.findViewById(R.id.statusPhoto);
        flStatus = view.findViewById(R.id.statusfl);

        // Status = 0 - not uploaded
        //          1 - under verification
        //          2 - verified
        //          3 - rejected

        setStatus(0, panCheck, panStatus);
        setStatus(1, aadharCheck, aadharStatus);                     //Set status value.
        setStatus(2, cancelCheck, chequeStatus);
        setStatus(3, electricityBillStatus, billStatus);
        setStatus(1, photoStatus, yourPhotoStatus);
        setStatus(2, foodLiceneceStatus, flStatus);

        documentTypeFL = view.findViewById(R.id.documentTypeOne);
        documentTypeNoFL = view.findViewById(R.id.documentTypeZero);

        // 0 = person having no food Licence
        // 1 = person having food Licence

        documentType = 0;                                                 //Set documentType value

        if(documentType == 0){

            documentTypeNoFL.setVisibility(View.VISIBLE);
        }
        else {

            documentTypeFL.setVisibility(View.VISIBLE);
        }

        name = getArguments().getString("name");
        phone = getArguments().getString("phone");

        stateProgressBar = (StateProgressBar) view.findViewById(R.id.your_state_progress_bar_id_partner2);
        String[] descriptionData = {"Details", "Pan & Aadhar", "Submitted", "Verified"};
        stateProgressBar.setStateDescriptionData(descriptionData);
        statusProgress();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = firebaseDatabase.getReference("users");
//        Log.d("IntentDetails","Phone number in profile : "+phone);

        DatabaseReference ref = parentRef.child(phone).getRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name="Default";
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.d("Intentdetails",d.getKey()+" : "+d.getValue().toString());
                    switch(d.getKey()) {
                        case "name" : {
                            nameText.setText(d.getValue().toString()); break;
                        }
                        case "address" : {
                            addressText.setText(d.getValue().toString()); break;
                        }
                        case "city" : {
                            cityText.setText(d.getValue().toString()); break;
                        }
                        case "food_choice" : {
                            foodText.setText(d.getValue().toString()); break;
                        }
                        case "pan_uploaded" : {
                            if(Integer.parseInt(d.getValue().toString())==0)
                            {
                                panCheck.setVisibility(View.VISIBLE);
                            }
                            else {
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference refer = storage.getReference();
                                    Log.d("image","started download\n");
                                    refer.child(phone+"pic1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("image","downloaded");
                                            Picasso.get().load(uri).fit().into(panImage);
                                        }
                                    });
                            }
                            break;
                        }
                        case "aadhar_uploaded" : {
                            if(Integer.parseInt(d.getValue().toString())==0)
                            {
                                aadharCheck.setVisibility(View.VISIBLE);
                            }
                            else {
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference refer = storage.getReference();
                                    Log.d("image","started download\n");
                                    refer.child(phone+"pic2").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("image","downloaded");
                                            Picasso.get().load(uri).fit().into(aadharImage);
                                        }
                                    });
                            }
                            break;
                        }
                        case "cancel_uploaded" : {
                            if(Integer.parseInt(d.getValue().toString())==0)
                            {
                                cancelCheck.setVisibility(View.VISIBLE);
                            }
                            else {
                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                    StorageReference refer = storage.getReference();
                                    Log.d("image","started download\n");
                                    refer.child(phone+"pic3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("image","downloaded");
                                            Picasso.get().load(uri).fit().into(cancelImage);
                                        }
                                    });
                            }
                            break;
                        }
                    }
                }
                phoneText.setText(phone);
                Log.d("Intentdetails", phoneText.getText().toString());
                Log.d("Intentdetails", nameText.getText().toString());
                Log.d("Intentdetails", foodText.getText().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setStatus(int status, ImageView view, TextView text) {

        if(status == 0){

            view.setImageResource(R.drawable.warning_sign);
            text.setText("Not Uploaded!");
        }
        else if(status == 1){

            view.setImageResource(R.drawable.waiting_sign);
            text.setText("Under Review");
        }
        else if(status == 2){

            view.setImageResource(R.drawable.correct);
            text.setText("Verified");
        }
        else if(status == 3){

            view.setImageResource(R.drawable.error_sign);
            text.setText("Rejected!");
        }
    }
}
