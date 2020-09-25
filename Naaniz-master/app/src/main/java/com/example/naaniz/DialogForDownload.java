package com.example.naaniz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DialogForDownload extends AppCompatDialogFragment {
    private Button bt1,bt2;
    private SwitchCompat foodSwitch;
    private Spinner sortSpinner,sub_spinner;
    String food_choice;
    String price_bar;
    String sub_location;
    private StringBuilder savedPDF=new StringBuilder();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.downloaddialog,null);
        builder.setView(view)
                .setTitle("")
                .setCancelable(false);
        bt1=(Button)view.findViewById(R.id.bt1_dialog);
        bt2=(Button)view.findViewById(R.id.bt2_dialog);
        final AlertDialog alertDialog =  builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        foodSwitch = view.findViewById(R.id.food_choice_search);
        sortSpinner = view.findViewById(R.id.price_spinner);
        sub_spinner = view.findViewById(R.id.sub_location_spinner);
        foodSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodSwitch.setText(R.string.non_veg);
                } else {
                    foodSwitch.setText(R.string.veg);
                }
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodSwitch.isChecked())
                {
                    food_choice="Non Veg";
                }
                else
                {
                    food_choice="Veg";
                }


             price_bar=sortSpinner.getSelectedItem().toString();
                sub_location =sub_spinner.getSelectedItem().toString();
                Toast.makeText(getActivity(),price_bar,Toast.LENGTH_SHORT);
                Log.d("data_list","price : "+price_bar);

             if(price_bar.equals("10–50"))
             {
                 DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
                 DatabaseReference ref2 = parentRef.child("hyderabad");
                 DatabaseReference ref = ref2.child(sub_location);
                 ref.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         Log.d("data_list", "settings");
                         Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                         Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                         for (DataSnapshot data : dataSnapshot.getChildren()) {
                             Log.d("sub", data.getKey());
                             String name = data.getKey();
                             savedPDF.append(name+"  (RESTAURANT)"+"\n\n");
                             Log.d("res", data.getKey());
                             for(DataSnapshot d : data.getChildren())
                             {



                                 if(d.getKey().equals("rating") || d.getKey().equals("price") || d.getKey().equals("category"))
                                 {
                                     Log.d("data_list", "settings");
                                 }
                                 else
                                 {
                                     Boolean a=false,b=false;
                                     String cg=null;
                                     int p=0;
                                     Log.d("dishes", d.getKey());
                                     for(DataSnapshot f : d.getChildren())
                                     {
                                         if(f.getKey().equals("price"))
                                         {
                                             Log.d("prices", f.getValue().toString());
                                             p = Integer.parseInt(f.getValue().toString());
                                             if(p>=10 && p<=50)
                                             {
                                                 a=true;
                                             }
                                         }
                                         if(f.getKey().equals("taste"))
                                         {
                                             Log.d("taste", f.getValue().toString());
                                             cg  = f.getValue().toString();
                                             if(cg.equals(food_choice))
                                             {
                                                 b=true;
                                             }
                                         }
                                     }
                                     if(a&&b)
                                     {
                                         int count = 20;

                                         String pp = d.getKey();
                                         int pl = pp.length();
                                         if(pl>19)
                                         {
                                             count=count-(pl-19);
                                         }
                                         else
                                         {
                                             count=count+(19-pl);
                                         }
                                         String spaces = String.format("%"+count+"s", "");
                                         savedPDF.append(d.getKey()+spaces+ "Rs"+ p+"       "+cg + "\n");
                                     }
                                 }

                             }
                             savedPDF.append("\n\n");

                         }
                         rest1();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }
             else if(price_bar.equals("51–100"))
             {
                 DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
                 DatabaseReference ref2 = parentRef.child("hyderabad");
                 DatabaseReference ref = ref2.child(sub_location);
                 ref.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         Log.d("data_list", "settings");
                         Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                         Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                         for (DataSnapshot data : dataSnapshot.getChildren()) {
                             Log.d("sub", data.getKey());
                             String name = data.getKey();
                             savedPDF.append(name+"  (RESTAURANT)"+"\n\n");
                             Log.d("res", data.getKey());
                             for(DataSnapshot d : data.getChildren())
                             {



                                 if(d.getKey().equals("rating") || d.getKey().equals("price") || d.getKey().equals("category"))
                                 {
                                     Log.d("data_list", "settings");
                                 }
                                 else
                                 {
                                     Boolean a=false,b=false;
                                     String cg=null;
                                     int p=0;
                                     Log.d("dishes", d.getKey());
                                     for(DataSnapshot f : d.getChildren())
                                     {
                                         if(f.getKey().equals("price"))
                                         {
                                             Log.d("prices", f.getValue().toString());
                                             p = Integer.parseInt(f.getValue().toString());
                                             if(p>=51 && p<=100)
                                             {
                                                 a=true;
                                             }
                                         }
                                         if(f.getKey().equals("taste"))
                                         {
                                             Log.d("taste", f.getValue().toString());
                                             cg  = f.getValue().toString();
                                             if(cg.equals(food_choice))
                                             {
                                                 b=true;
                                             }
                                         }
                                     }
                                     if(a&&b)
                                     {
                                         int count = 20;
                                         String spaces = String.format("%"+count+"s", "");
                                         savedPDF.append(d.getKey()+spaces+ "Rs"+ p+"       "+cg + "\n");
                                     }
                                 }

                             }
                             savedPDF.append("\n\n");

                         }



                         rest1();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }
             else if(price_bar.equals("101–200"))
             {
                 DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
                 DatabaseReference ref2 = parentRef.child("hyderabad");
                 DatabaseReference ref = ref2.child(sub_location);
                 ref.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         Log.d("data_list", "settings");
                         Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                         Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                         for (DataSnapshot data : dataSnapshot.getChildren()) {
                             Log.d("sub", data.getKey());
                             String name = data.getKey();
                             savedPDF.append(name+"  (RESTAURANT)"+"\n\n");
                             Log.d("res", data.getKey());
                             for(DataSnapshot d : data.getChildren())
                             {



                                 if(d.getKey().equals("rating") || d.getKey().equals("price") || d.getKey().equals("category"))
                                 {
                                     Log.d("data_list", "settings");
                                 }
                                 else
                                 {
                                     Boolean a=false,b=false;
                                     String cg=null;
                                     int p=0;
                                     Log.d("dishes", d.getKey());
                                     for(DataSnapshot f : d.getChildren())
                                     {
                                         if(f.getKey().equals("price"))
                                         {
                                             Log.d("prices", f.getValue().toString());
                                             p = Integer.parseInt(f.getValue().toString());
                                             if(p>=101 && p<=200)
                                             {
                                                 a=true;
                                             }
                                         }
                                         if(f.getKey().equals("taste"))
                                         {
                                             Log.d("taste", f.getValue().toString());
                                             cg  = f.getValue().toString();
                                             if(cg.equals(food_choice))
                                             {
                                                 b=true;
                                             }
                                         }
                                     }
                                     if(a&&b)
                                     {
                                         int count = 20;


                                         String spaces = String.format("%"+count+"s", "");
                                         savedPDF.append(d.getKey()+spaces+ "Rs"+ p+"       "+cg + "\n");
                                     }
                                 }

                             }
                             savedPDF.append("\n\n");

                         }
                         rest1();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }
             else if(price_bar.equals("201–300"))
             {
                 DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
                 DatabaseReference ref2 = parentRef.child("hyderabad");
                 DatabaseReference ref = ref2.child(sub_location);
                 ref.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         Log.d("data_list", "settings");
                         Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                         Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                         for (DataSnapshot data : dataSnapshot.getChildren()) {
                             Log.d("sub", data.getKey());
                             String name = data.getKey();
                             savedPDF.append(name+"  (RESTAURANT)"+"\n\n");
                             Log.d("res", data.getKey());
                             for(DataSnapshot d : data.getChildren())
                             {



                                 if(d.getKey().equals("rating") || d.getKey().equals("price") || d.getKey().equals("category"))
                                 {
                                     Log.d("data_list", "settings");
                                 }
                                 else
                                 {
                                     Boolean a=false,b=false;
                                     String cg=null;
                                     int p=0;
                                     Log.d("dishes", d.getKey());
                                     for(DataSnapshot f : d.getChildren())
                                     {
                                         if(f.getKey().equals("price"))
                                         {
                                             Log.d("prices", f.getValue().toString());
                                             p = Integer.parseInt(f.getValue().toString());
                                             if(p>=201 && p<=300)
                                             {
                                                 a=true;
                                             }
                                         }
                                         if(f.getKey().equals("taste"))
                                         {
                                             Log.d("taste", f.getValue().toString());
                                             cg  = f.getValue().toString();
                                             if(cg.equals(food_choice))
                                             {
                                                 b=true;
                                             }
                                         }
                                     }
                                     if(a&&b)
                                     {
                                         int count = 20;
                                         String spaces = String.format("%"+count+"s", "");
                                         savedPDF.append(d.getKey()+spaces+ "Rs"+ p+"       "+cg + "\n");
                                     }
                                 }

                             }
                             savedPDF.append("\n\n");

                         }
                         rest1();
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }

                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }
    public void rest1()
    {
        savedPDF.append("Restaurants which have dishes in price range of Rs 10-50\n\n");
        DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
        DatabaseReference ref = parentRef.child("hyderabad");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("data_list", "settings");
                Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot d : data.getChildren())
                    {

                        String name = d.getKey();
                        for(DataSnapshot e : d.getChildren())
                        {
                            if(e.getKey().equals("rating") || e.getKey().equals("price") || e.getKey().equals("category"))
                            {
                                Log.d("data_list", "settings");
                            }
                            else
                            {
                                Boolean a=false,b=false;
                                String cg=null;
                                int p=0;
                                for(DataSnapshot f : e.getChildren())
                                {
                                    if(f.getKey().equals("price"))
                                    {
                                        p = Integer.parseInt(f.getValue().toString());
                                        if(p>=10 && p<=50)
                                        {
                                            a=true;
                                        }
                                    }
                                    if(f.getKey().equals("taste"))
                                    {
                                        cg = f.getValue().toString();
                                        if(cg.equals(food_choice))
                                        {
                                            b=true;
                                        }
                                    }
                                }
                                if(a&&b)
                                {
                                    savedPDF.append(d.getKey() + "\n");
                                    break;
                                }
                            }
                        }

                    }


                }
                savedPDF.append("\n\n");
               rest2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void rest2()
    {
        savedPDF.append("Restaurants which have dishes in price range of Rs 51-100\n\n");
        DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
        DatabaseReference ref = parentRef.child("hyderabad");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("data_list", "settings");
                Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot d : data.getChildren())
                    {

                        String name = d.getKey();
                        for(DataSnapshot e : d.getChildren())
                        {
                            if(e.getKey().equals("rating") || e.getKey().equals("price") || e.getKey().equals("category"))
                            {
                                Log.d("data_list", "settings");
                            }
                            else
                            {
                                Boolean a=false,b=false;
                                String cg=null;
                                int p=0;
                                for(DataSnapshot f : e.getChildren())
                                {
                                    if(f.getKey().equals("price"))
                                    {
                                        p = Integer.parseInt(f.getValue().toString());
                                        if(p>=51 && p<=100)
                                        {
                                            a=true;
                                        }
                                    }
                                    if(f.getKey().equals("taste"))
                                    {
                                        cg = f.getValue().toString();
                                        if(cg.equals(food_choice))
                                        {
                                            b=true;
                                        }
                                    }
                                }
                                if(a&&b)
                                {
                                    savedPDF.append(d.getKey() + "\n");
                                    break;
                                }
                            }
                        }

                    }


                }
                savedPDF.append("\n\n");
              rest3();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void rest3()
    {
        savedPDF.append("Restaurants which have dishes in price range of Rs 101-200\n\n");
        DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
        DatabaseReference ref = parentRef.child("hyderabad");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("data_list", "settings");
                Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot d : data.getChildren())
                    {

                        String name = d.getKey();
                        for(DataSnapshot e : d.getChildren())
                        {
                            if(e.getKey().equals("rating") || e.getKey().equals("price") || e.getKey().equals("category"))
                            {
                                Log.d("data_list", "settings");
                            }
                            else
                            {
                                Boolean a=false,b=false;
                                String cg=null;
                                int p=0;
                                for(DataSnapshot f : e.getChildren())
                                {
                                    if(f.getKey().equals("price"))
                                    {
                                        p = Integer.parseInt(f.getValue().toString());
                                        if(p>=101 && p<=200)
                                        {
                                            a=true;
                                        }
                                    }
                                    if(f.getKey().equals("taste"))
                                    {
                                        cg = f.getValue().toString();
                                        if(cg.equals(food_choice))
                                        {
                                            b=true;
                                        }
                                    }
                                }
                                if(a&&b)
                                {
                                    savedPDF.append(d.getKey() + "\n");
                                    break;
                                }
                            }
                        }

                    }


                }
                savedPDF.append("\n\n");
               rest4();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void rest4()
    {
        savedPDF.append("Restaurants which have dishes in price range of Rs 201-300\n\n");
        DatabaseReference parentRef = FirebaseDatabase.getInstance().getReference("restaurants");
        DatabaseReference ref = parentRef.child("hyderabad");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("data_list", "settings");
                Log.d("data_list", "dataSnapshot key : "+dataSnapshot.getKey());
                Log.d("data_list", "dataSnapshot value : "+dataSnapshot.getValue().toString());

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for(DataSnapshot d : data.getChildren())
                    {

                        String name = d.getKey();
                        for(DataSnapshot e : d.getChildren())
                        {
                            if(e.getKey().equals("rating") || e.getKey().equals("price") || e.getKey().equals("category"))
                            {
                                Log.d("data_list", "settings");
                            }
                            else
                            {
                                Boolean a=false,b=false;
                                String cg=null;
                                int p=0;
                                for(DataSnapshot f : e.getChildren())
                                {
                                    if(f.getKey().equals("price"))
                                    {
                                        p = Integer.parseInt(f.getValue().toString());
                                        if(p>=201 && p<=300)
                                        {
                                            a=true;
                                        }
                                    }
                                    if(f.getKey().equals("taste"))
                                    {
                                        cg = f.getValue().toString();
                                        if(cg.equals(food_choice))
                                        {
                                            b=true;
                                        }
                                    }
                                }
                                if(a&&b)
                                {
                                    savedPDF.append(d.getKey() + "\n");
                                    break;
                                }
                            }
                        }

                    }


                }
                savedPDF.append("\n\n");
                savePdf();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void savePdf()
    {
        String po = savedPDF.toString();

        Document document = new Document();
        String file = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String filePath = Environment.getExternalStorageDirectory()+"/"+file+".pdf";

        try {
            PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph(po));
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
