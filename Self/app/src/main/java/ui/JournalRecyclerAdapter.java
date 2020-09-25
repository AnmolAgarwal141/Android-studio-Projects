package ui;

import android.content.Context;
import android.graphics.Color;
import android.icu.util.ValueIterator;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.self.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.Journal;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> implements View.OnLongClickListener {
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
    public void onBindViewHolder(@NonNull final JournalRecyclerAdapter.ViewHolder holder, int position) {
        final Journal journal=journalList.get(position);
        String imageurl;
        holder.Title.setText(journal.getTitle());
        holder.Thoughts.setText(journal.getThought());
        holder.name.setText(journal.getUsername());
        imageurl=journal.getImageUri();
//        String timeago= (String) DateUtils.getRelativeTimeSpanString(journal.getTimeadded().getSeconds()*1000);
    //    holder.Dateadded.setText(timeago);
        Picasso.get().load(imageurl).placeholder(R.drawable.image_two).fit().into(holder.imageView);
        holder.circle.setVisibility(journal.showcheckbox ?View.VISIBLE:View.GONE);
        holder.circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                journal.setSelected(!journal.isSelected());
                if(journal.isSelected()==true){
                    holder.circle.setBackgroundColor(Color.rgb(255,117,0));
                }
                notifyItemChanged(holder.getAdapterPosition());
            }
        });


                //


    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    @Override
    public boolean onLongClick(View view) {

        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Title,Thoughts,Dateadded,name;
        public ImageView imageView;
        public ImageButton sharebutton;
        public Button circle;
        String userid,username;
        public ViewHolder(@NonNull final View itemView, Context ctx) {
            super(itemView);
            context=ctx;
            Title=itemView.findViewById(R.id.journal_title_list);
            Thoughts=itemView.findViewById(R.id.journal_thought_list);
            Dateadded=itemView.findViewById(R.id.journal_timestamp_list);
            imageView=itemView.findViewById(R.id.journal_image_list);
            name=itemView.findViewById(R.id.journal_row_username);
            sharebutton=itemView.findViewById(R.id.journal_row_share_button);
            circle=itemView.findViewById(R.id.selectbutton);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showallboxes();
                    return false;
                }
            });
            sharebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //context.startActivity();
                }
            });
        }
    }
    private void showallboxes(){
            for(Journal item:journalList){
                item.showcheckbox=true;
            }
            notifyDataSetChanged();
    }
    private void hideallboxes(){
        for(Journal item:journalList){
            item.showcheckbox=false;
        }
        notifyDataSetChanged();
    }
}
