package com.mcoefoss.modernTimes;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RviewAdapter extends RecyclerView.Adapter<RviewAdapter.DataObjectHolder> {

    private Context context;
    private ArrayList<Post> ListCoche;


    public RviewAdapter(Context context, ArrayList<Post> listCoche) {
        this.context = context;
        ListCoche = listCoche;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards,parent,false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, int position) {
        Bitmap img = ListCoche.get(position).getImage();
        if(img != null) holder.img.setImageBitmap(img);
        holder.head1.setText(ListCoche.get(position).getHeading());
        holder.head2.setText(ListCoche.get(position).getText());
        holder.date.setText(ListCoche.get(position).getDate());
        holder.club.setText(ListCoche.get(position).getClub());

        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.down.setVisibility(View.GONE);
                holder.up.setVisibility(View.VISIBLE);
                holder.head2.setMaxLines(Integer.MAX_VALUE);
            }
        });

        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.down.setVisibility(View.VISIBLE);
                holder.up.setVisibility(View.GONE);
                holder.head2.setMaxLines(2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListCoche.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView head1,head2,date,club;
        ImageButton down,up;


        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.homeCardsImage);
            this.head1 = itemView.findViewById(R.id.homeCardsHeading1);
            this.head2 = itemView.findViewById(R.id.homeCardsHeading2);
            this.date = itemView.findViewById(R.id.homeCardsDate);
            this.club = itemView.findViewById(R.id.homeCardsClub);
            this.down = itemView.findViewById(R.id.down);
            this.up = itemView.findViewById(R.id.up);
        }
    }
}
