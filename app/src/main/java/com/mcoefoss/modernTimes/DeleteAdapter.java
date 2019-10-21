package com.mcoefoss.modernTimes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

import static com.parse.Parse.getApplicationContext;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Post> ListPost;

    public DeleteAdapter(Context context, ArrayList<Post> listPost) {
        this.context = context;
        ListPost = listPost;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {
        holder.head1.setText(ListPost.get(position).getHeading());
        holder.head2.setText(ListPost.get(position).getText());
        holder.date.setText(ListPost.get(position).getDate());
        holder.club.setText(ListPost.get(position).getClub());

        final Post Post_obj = ListPost.get(position);
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context,"Your post will be deleted as soon as possible.",Toast.LENGTH_SHORT).show();
                removeCard(Post_obj);
                return true;
            }
        });
    }

    private void removeCard(Post Post_obj) {
        ParseQuery<ParseObject> query = new ParseQuery<>("Posts");
        Log.d("object id:",Post_obj.Obj_id);
        query.whereEqualTo("objectId",Post_obj.Obj_id);
        try {
            ParseObject toDelete = query.getFirst();
            toDelete.delete();
            Toast.makeText(getApplicationContext(),"Your Post will be deleted shortly",Toast.LENGTH_SHORT).show();
            int currentPosition = ListPost.indexOf(Post_obj);
            ListPost.remove(currentPosition);
            notifyItemRemoved(currentPosition);
        }catch(ParseException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return ListPost.size();
}
    public class DataObjectHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView head1,head2,date,club;
        private CardView card;

        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.homeCardsImage);
            this.head1 = itemView.findViewById(R.id.homeCardsHeading1);
            this.head2 = itemView.findViewById(R.id.homeCardsHeading2);
            this.date = itemView.findViewById(R.id.homeCardsDate);
            this.club = itemView.findViewById(R.id.homeCardsClub);
            this.card = itemView.findViewById(R.id.cardView);
        }
    }
}

