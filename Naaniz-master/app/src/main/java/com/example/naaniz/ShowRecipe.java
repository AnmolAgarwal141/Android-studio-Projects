package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naaniz.views.IngredientView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowRecipe extends AppCompatActivity {

    private TextView name;
    private TextView time;
    private TextView instructions_description;
    private TextView serving;
    public LinearLayout listOfIngredients;
    private List<Double> costOfIngredients = new ArrayList<>();
    private IngredientView ingredientView;
    private String dish;
    private String phone;
    private String choice; // whether the parent was home fragment OR user-recipes
    private TextView recipeCost;
    private Double cost = 0.0;
    private Button likeButton, likedButton;
    private Button addToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__recipe);
        name = findViewById(R.id.recipe_name);
        time = findViewById(R.id.preparation_time);
        instructions_description = findViewById(R.id.firstly_in_);
        serving = findViewById(R.id.servings_no);
        listOfIngredients = findViewById(R.id.listOfIngredients);
        recipeCost = findViewById(R.id.cost_amount);
        dish = getIntent().getStringExtra("dish");
        choice = getIntent().getStringExtra("parentRef");
        phone = getIntent().getStringExtra("phone");
        addToMenu = findViewById(R.id.addToMenu);

        Log.d("IntentDetails","Dish : "+dish);
        getRecipe();

        //pdf download and share button
        findViewById(R.id.downloadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdftoDevice(0);
            }
        });
        findViewById(R.id.shareButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf();
            }
        });

        likeButton = findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                likedButton.setVisibility(View.VISIBLE);
                likedButton.setEnabled(true);

                likeButton.setVisibility(View.INVISIBLE);
                likeButton.setEnabled(false);
            }
        });

        likedButton = findViewById(R.id.likedButton);
        likedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                likedButton.setVisibility(View.INVISIBLE);
                likedButton.setEnabled(false);

                likeButton.setVisibility(View.VISIBLE);
                likeButton.setEnabled(true);
            }
        });

        addToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firebaseDatabase.getReference("users").child(phone).getRef();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        Log.d("addingCard:","inside onClick of addToMEnu : "+phone);
                        boolean isMenuPresentInUserDatabase = false;
                        DatabaseReference reference = database.getReference("users").child(phone).getRef();
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Log.d("addingCard:","dataSnapshot1 : "+dataSnapshot1.getValue().toString());
                            if(!dataSnapshot1.getKey().equals("menu")) {
                                reference.child(dataSnapshot1.getKey()).setValue(dataSnapshot1.getValue());
                            }else {
                                isMenuPresentInUserDatabase = true;
                                boolean dishAlreadyPresent = false;
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    dishAlreadyPresent |= dataSnapshot2.getKey().equals(dish);
                                    reference.child("menu").child(dataSnapshot2.getKey()).setValue(dataSnapshot2.getValue().toString());
                                }
                                Log.d("addingCard","dataSnapshot : "+dataSnapshot.getValue().toString());
                                if(dishAlreadyPresent)
                                    Toast.makeText(ShowRecipe.this, "dishAlreadyPresent",Toast.LENGTH_SHORT).show();
                                else
                                    reference.child("menu").child(dish).setValue("recipes");
                            }
                        }
                        if(!isMenuPresentInUserDatabase){
                            Log.d("addingCard:","menu not present in user ");
                            reference.child("menu").child(dish).setValue("recipes");
                        }
                        addToMenu.setClickable(false);
                        addToMenu.setBackgroundColor(Color.BLACK);
                        addToMenu.setTextColor(Color.WHITE);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }});
            }
        });
    }

    private void getRecipe()
    {
        DatabaseReference parentRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(getIntent().getStringExtra("parentRef")==null)
            return;
        else if(getIntent().getStringExtra("parentRef").equals("recipes"))
        {
            parentRef = database.getReference(choice);

            DatabaseReference ref = parentRef.child(dish).getRef();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("Intentdetails", dataSnapshot.toString());
                    name.setText(dataSnapshot.getKey().toString());
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        Log.d("Intentdetails","dish snapshot : "+d.getKey()+" "+d.getValue().toString());
                        switch (d.getKey())
                        {
                            case "instructions": {
                                instructions_description.setText(d.getValue().toString());
                                break;
                            }
                            case "ingredients": {
                                for(DataSnapshot dc : d.getChildren()) {
                                    String ing_name="default",ing_qtyUS,ing_category="None",ing_qtySI="None;none";
                                    for(DataSnapshot dcm : dc.getChildren())
                                    {
                                        if(dcm.getKey().equals("name"))
                                            ing_name = dcm.getValue().toString();
                                        else if(dcm.getKey().equals("qtySI")){
                                            ing_qtySI = dcm.getValue().toString();
                                        }
                                        else if(dcm.getKey().equals("qtyUS")) {
                                            ing_qtyUS=dcm.getValue().toString();
                                            Log.d("Intentdetails","Sending details : "+ing_name+ " : "+ing_qtyUS);
                                            getIngredientPrice(ing_name,ing_qtySI,ing_qtyUS,ing_category);
                                        }
                                        else if(dcm.getKey().equals("category")) {
                                            ing_category = dcm.getValue().toString();
                                        }
                                        Log.d("Intentdetails","ingredient : "+dcm.getKey()+" : "+dcm.getValue().toString());
                                    }
                                }
                                break;
                            }
                            case "readyInMinutes":{
                                String t = "Ready in "+d.getValue().toString()+" minutes";
                                time.setText(t);
                                break;
                            }
                            case "servings":{
                                String s = "Servings : "+d.getValue().toString();
                                serving.setText(s);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(getIntent().getStringExtra("parentRef").equals("user_recipes"))
        {
            parentRef = database.getReference(choice);
            DatabaseReference ref2 = parentRef.child(phone).getRef();
            DatabaseReference ref = ref2.child(dish).getRef();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("Intentdetails", dataSnapshot.toString());
                    name.setText(dataSnapshot.getKey().toString());
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        Log.d("Intentdetails","dish snapshot : "+d.getKey()+" "+d.getValue().toString());
                        switch (d.getKey())
                        {
                            case "instructions": {
                                instructions_description.setText(d.getValue().toString());
                                break;
                            }
                            case "ingredients": {
                                for(DataSnapshot dc : d.getChildren()) {
                                    String ing_name="default",ing_qty="534",ing_qtySI="25454";
                                    for(DataSnapshot dcm : dc.getChildren())
                                    {
                                        if(dcm.getKey().equals("name"))
                                            ing_name = dcm.getValue().toString();

                                        if(dcm.getKey().equals("qty")){
                                            ing_qty = dcm.getValue().toString();
                                            ing_qtySI=ing_qty;
                                        }
                                        Log.d("!$#$#$%$%$%^%^%$^$^", ing_name+" "+ing_qty+" "+ing_qtySI);

                                        Log.d("Intentdetails","ingredient : "+dcm.getKey()+" : "+dcm.getValue().toString());
                                    }
                                    addIngredients(ing_name,ing_qtySI,ing_qty,null,null);
                                }
                                break;
                            }
                            case "readyInMinutes":{
                                String t = "Ready in "+d.getValue().toString()+" minutes";
                                time.setText(t);
                                break;
                            }
                            case "servings":{
                                String s = "Servings : "+d.getValue().toString();
                                serving.setText(s);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void getIngredientPrice(String name,String quantitySI,String qty,String category)
    {
        List<String> vendorList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = database.getReference("ingredient_categories");
        DatabaseReference catRef = parentRef.child(category).getRef();
        Log.d("ingredient_view",category+" : "+name);
        catRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    Log.d("ingredient_view",name+" : "+d.getKey());
                    if (d.getKey().equalsIgnoreCase(name)) {
                        for(DataSnapshot dc : d.getChildren()) {
                            if(dc.getValue().toString().indexOf("def;")!=-1)
                                costOfIngredients.add(Double.parseDouble(qty.substring(0,qty.indexOf(";")))*Double.parseDouble(dc.getValue().toString().substring(4)));
                            else
                                vendorList.add(dc.getValue().toString().replace(';',' '));
                        }
                    }
                }
                addIngredients(name,quantitySI,qty,category,vendorList);
                cost+= costOfIngredients.get(costOfIngredients.size()-1);
                String serves = serving.getText().toString().substring(11);
                recipeCost.setText(Double.toString(cost/Double.parseDouble(serves)));
                Log.d("EXPECTED PRICE","cost of recipe : "+cost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    private void addIngredients(String title, String quantitySI, String quantityUS,String category,List<String> vendorList)
    {
        Log.d("Intentdetails", "Inside addIngredients");
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ingredientView = new IngredientView(this, title, quantityUS,category,vendorList);
        listOfIngredients.addView(ingredientView);

        // adding in list for pdf content
        ingredientsList.add(new String[] {title, quantityUS});
    }

    // download pdf

    public boolean verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1
            );
            return false;
        }
        return true;

    }

    String basePath = Environment.getExternalStorageDirectory() + "/Naaniz/";
    ArrayList<String[]> ingredientsList = new ArrayList<>(); // list of ingredients for pdf content
    private void downloadPdftoDevice(int flag){
        //check for storage permission
        if(!verifyStoragePermissions(this)) return;

        String filename = "Naaniz_" + name.getText().toString().replace(" ", "_")+".pdf";

        Document document = new Document();
        File baseFolder = new File(basePath);
        if(!baseFolder.exists()) {
            baseFolder.mkdirs();
        }

        Paragraph header = new Paragraph("Recipe", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.ORANGE));
        Paragraph title = new Paragraph(name.getText().toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 24, Font.BOLD, BaseColor.BLACK));
        Paragraph preperationTime = new Paragraph("Preperation Time : " + time.getText().toString() , FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.NORMAL, BaseColor.BLACK));
        Paragraph servings = new Paragraph("Servings : " + serving.getText().toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.NORMAL, BaseColor.BLACK));
        Paragraph costToPrepare = new Paragraph("Cost to Prepare : " + recipeCost.getText().toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.NORMAL, BaseColor.BLACK));
        Paragraph ingredients = new Paragraph("INGREDIENTS", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK));
        Paragraph procedure = new Paragraph("PROCEDURE", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK));

        header.setAlignment(Element.ALIGN_CENTER);
        title.setAlignment(Element.ALIGN_CENTER);
        preperationTime.setIndentationLeft(15);
        servings.setIndentationLeft(15);
        costToPrepare.setIndentationLeft(15);
        ingredients.setIndentationLeft(10);
        procedure.setIndentationLeft(10);

        try{
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(basePath+filename));
            writer.setPageEvent(new PdfPageEvent() {
                @Override
                public void onOpenDocument(PdfWriter writer, Document document) {

                }

                @Override
                public void onStartPage(PdfWriter writer, Document document) {

                }

                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_CENTER,
                            new Phrase("Naaniz - Recipe",
                                    new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.85f))),
                                    297.5f, 421, 45);
                }

                @Override
                public void onCloseDocument(PdfWriter writer, Document document) {

                }

                @Override
                public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {

                }

                @Override
                public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {

                }

                @Override
                public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title) {

                }

                @Override
                public void onChapterEnd(PdfWriter writer, Document document, float paragraphPosition) {

                }

                @Override
                public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth, Paragraph title) {

                }

                @Override
                public void onSectionEnd(PdfWriter writer, Document document, float paragraphPosition) {

                }

                @Override
                public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {

                }
            });

            document.open();
            document.addAuthor("Naaniz");

            // adding header and title
            document.add(header);
            document.add(title);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // adding image and other details
            PdfPCell imgCell = new PdfPCell();
            imgCell.setBackgroundColor(BaseColor.RED);
            imgCell.setBorder(Rectangle.NO_BORDER);
            PdfPCell detailsCell = new PdfPCell();
            detailsCell.setBorder(Rectangle.NO_BORDER);
            detailsCell.addElement(preperationTime);
            detailsCell.addElement(servings);
            detailsCell.addElement(costToPrepare);
            PdfPTable imgTable = new PdfPTable(2);
            imgTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            imgTable.setWidthPercentage(100);
            imgTable.setWidths(new int[]{1,2});
            imgTable.addCell(imgCell);
            imgTable.addCell(detailsCell);
            document.add(imgTable);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // adding ingredients
            document.add(ingredients);
            if(ingredientsList.size() > 0){
                PdfPTable ingredientsTable = new PdfPTable(2);
                ingredientsTable.setWidthPercentage(50);
                ingredientsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
                ingredientsTable.setHorizontalAlignment(Element.ALIGN_CENTER);
                for(String[] ingredientItem : ingredientsList){
                    PdfPCell itemTitleCell = new PdfPCell(new Phrase("\u2022  "+ingredientItem[0], FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.DARK_GRAY)));
                    itemTitleCell.setPadding(2);
                    itemTitleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    itemTitleCell.setBorder(Rectangle.NO_BORDER);
                    ingredientsTable.addCell(itemTitleCell);

                    PdfPCell itemQuantityCell = new PdfPCell(new Phrase(ingredientItem[1], FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.DARK_GRAY)));
                    itemQuantityCell.setPadding(2);
                    itemQuantityCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    itemQuantityCell.setBorder(Rectangle.NO_BORDER);
                    ingredientsTable.addCell(itemQuantityCell);
                }
                document.add(ingredientsTable);
            } else{
                document.add(new Paragraph("Ingredients data not available!", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, BaseColor.DARK_GRAY)));
            }
            document.add(Chunk.NEWLINE);

            // adding procedure
            document.add(procedure);
            String instructionsContent;
            if(instructions_description.getText().toString().isEmpty()) instructionsContent = "No Instructions Available!";
            else instructionsContent = instructions_description.getText().toString();
            Paragraph procedureContent = new Paragraph(instructionsContent, FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.DARK_GRAY));
            procedureContent.setIndentationLeft(10);
            procedureContent.setIndentationRight(10);
            document.add(procedureContent);

            document.close();

            if(flag == 0) Toast.makeText(this, "pdf saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // share pdf
    private void sharePdf(){
        Toast.makeText(this, "create chooser opens", Toast.LENGTH_SHORT).show();
        if(!verifyStoragePermissions(this)) return;
        downloadPdftoDevice(1);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String filename = "Naaniz_" + name.getText().toString().replace(" ", "_")+".pdf";
        Uri uri = Uri.fromFile(new File(basePath + filename));
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(intent, "Select app to share"));
    }
}
