package com.example.skillswap.ui;

import com.example.skillswap.MatchPage;
import com.example.skillswap.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillswap.models.Match;
import com.example.skillswap.models.User;


import java.util.List;

public class MatchAdaptor extends RecyclerView.Adapter<MatchAdaptor.MatchViewHolder> {




    private List<Match> matchList;

    private Context context;

    public MatchAdaptor(Context context, List<Match> matchList) {
        this.matchList = matchList;

        this.context = context;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_card, viewGroup, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);

        holder.name.setText(match.matchedUser.getName());
        holder.profileImage.setImageResource(match.matchedUser.getImageId());
        holder.profileImage.setBackgroundResource(R.drawable.profile_circle_background);

        String learn_string = "Skillset: ";
        for (String learn : match.learnArr){ //things they can offer the current user
            learn_string = learn_string + learn + ", "; //format it accordingly
        }

        String teach_string = "Skillwish: ";
        for (String teach : match.teachArr){ //things current user can offer them
            teach_string = teach_string + teach + ", "; //format it accordingly
        }
        holder.teachText.setText(teach_string.substring(0,teach_string.length()-2));
        holder.learnText.setText(learn_string.substring(0,learn_string.length()-2));


        //holder.matchButton.setOnClickListener(v -> listener.onMatchClick(match));
        holder.matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MatchPage) context).showDialog(match,MatchAdaptor.this,position);
            }
        });

        //show orange color if pending
        if (match.isPending()){
            holder.statusContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView name, teachText, learnText;
        Button matchButton;
        ImageView profileImage;

        LinearLayout statusContainer;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.match_user_name);
            teachText = itemView.findViewById(R.id.match_teach);
            learnText = itemView.findViewById(R.id.match_learn);
            matchButton = itemView.findViewById(R.id.btn_request_session);
            profileImage = itemView.findViewById(R.id.profile_image);
            statusContainer = itemView.findViewById(R.id.statusContainer);
        }
    }
}
