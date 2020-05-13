package ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.self.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Journal;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {
   private Context context;
   private List<Journal> journalList;

        public JournalRecyclerAdapter(Context context, List<Journal> journalList) {
            this.context = context;
            this.journalList = journalList;
        }

    @NonNull
    @Override
    public JournalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.journal_row,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerAdapter.ViewHolder holder, int position) {
        Journal journal=journalList.get(position);
        String imageurl;
        holder.Title.setText(journal.getTitle());
        holder.Thoughts.setText(journal.getThought());
        holder.name.setText(journal.getUsername());
        imageurl=journal.getImageUri();
//        String timeago= (String) DateUtils.getRelativeTimeSpanString(journal.getTimeadded().getSeconds()*1000);
    //    holder.Dateadded.setText(timeago);
        Picasso.get().load(imageurl).placeholder(R.drawable.image_two).fit().into(holder.imageView);
                //


    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Title,Thoughts,Dateadded,name;
        public ImageView imageView;
        public ImageButton sharebutton;
        String userid,username;
        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            Title=itemView.findViewById(R.id.journal_title_list);
            Thoughts=itemView.findViewById(R.id.journal_thought_list);
            Dateadded=itemView.findViewById(R.id.journal_timestamp_list);
            imageView=itemView.findViewById(R.id.journal_image_list);
            name=itemView.findViewById(R.id.journal_row_username);
            sharebutton=itemView.findViewById(R.id.journal_row_share_button);
            sharebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //context.startActivity();
                }
            });
        }
    }
}
