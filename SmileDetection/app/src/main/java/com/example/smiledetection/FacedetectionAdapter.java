package com.example.smiledetection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.List;

public class FacedetectionAdapter extends RecyclerView.Adapter<FacedetectionAdapter.ViewHolder> {

    private List<Facedetectionmodel>facedetectionmodelList;
    private Context context;

    public FacedetectionAdapter(List<Facedetectionmodel> facedetectionmodelList, Context context) {
        this.facedetectionmodelList = facedetectionmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FacedetectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_face_detection,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacedetectionAdapter.ViewHolder holder, int position) {
          Facedetectionmodel facedetectionmodel=facedetectionmodelList.get(position);
          holder.text1.setText(MessageFormat.format("face{0}", String.valueOf(facedetectionmodel.getId())));
          holder.text2.setText(MessageFormat.format(" face{0}", facedetectionmodel.getText()));

     }

    @Override
    public int getItemCount() {
        return facedetectionmodelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text1,text2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(R.id.item_facedetection_textview1);
            text2=itemView.findViewById(R.id.item_facedetection_textview2);
        }
    }
}
