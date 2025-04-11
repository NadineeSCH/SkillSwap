package com.example.skillswap;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skillswap.models.Match;
import com.example.skillswap.models.User;
import com.example.skillswap.ui.MatchAdaptor;
import com.example.skillswap.utils.SkillMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchPage extends AppCompatActivity {
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_match_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        //set back to home button function
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for demo purposes, we will not immediately navigate back
                //instead, pop-up a simulated dialog for an incoming request
                //after clicking either buttons on the dialog,go back to home
                //finish();
                showIncomingRequestDialog();
            }
        });

        ////dummy data in place of database call

        //dummy data start

        User currentUser = new User (
                "x",
                "Cassie",
                Arrays.asList("Paint", "Photography"),
                Arrays.asList("Public Speaking", "Dance","Cooking"));


        ArrayList<User> otherUsers = new ArrayList<>();
        otherUsers.add(new User(
                "u1",
                "Alice",
                Arrays.asList("Python", "Java"),
                Arrays.asList("Guitar", "Cooking")
        ));

        otherUsers.add(new User(
                "u2",
                "Bob",
                Arrays.asList("Guitar", "Painting"),
                Arrays.asList("Python", "Dancing")
        ));

        otherUsers.add(new User(
                "u3",
                "Charlie",
                Arrays.asList("Public Speaking", "Cooking"),
                Arrays.asList("Java", "Photography"),R.drawable.sample_u3
        ));

        otherUsers.add(new User(
                "u4",
                "Diana",
                Arrays.asList("Photography", "Dancing"),
                Arrays.asList("Painting", "Public Speaking")
        ));

        otherUsers.add(new User(
                "u5",
                "Ethan",
                Arrays.asList("Chess", "JavaScript"),
                Arrays.asList("Python", "Public Speaking")

        ));

        //dummy data end

        List<Match> matchedUsers = SkillMatcher.findMatchingUsers(currentUser,otherUsers);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MatchAdaptor matchAdaptor = new MatchAdaptor(MatchPage.this,matchedUsers);
        recyclerView.setAdapter(matchAdaptor);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;

            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                //swipe to delete functionality
                int layoutPosition=viewHolder.getLayoutPosition();
                matchedUsers.remove(layoutPosition);
                matchAdaptor.notifyItemRemoved(layoutPosition);

            }
        };
        ItemTouchHelper itemTouchHelper
                = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }



    //showDialog function to display request details
    public void showDialog(Match match, MatchAdaptor adapter, int position){
        Dialog dialog = new Dialog(MatchPage.this);
        dialog.setContentView(R.layout.activity_set_match);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        LayoutInflater inflater = LayoutInflater.from(this);

        // fetch the values from the match object, then set the contents of the widgets

        TextView userName= dialog.findViewById(R.id.matchedUserName);
        ImageView profilePicture = dialog.findViewById(R.id.profilePicture);
        Button sendRequestButton = dialog.findViewById(R.id.sendSkillswapRequest);


        profilePicture.setImageResource(match.matchedUser.getImageId());
        userName.setText(match.matchedUser.getName());
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.setPending();
                adapter.notifyItemChanged(position);
                Toast.makeText(MatchPage.this,"Request sent. They'll see it on their dashboard.",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        LinearLayout containerLearn = dialog.findViewById(R.id.selectContainerLearn);

        List<String> learnList = match.learnArr;

        for (String skill : learnList) {
            View skillView = inflater.inflate(R.layout.skill_selection, containerLearn, false);
            CheckBox checkBox = skillView.findViewById(R.id.selectSkill);
            checkBox.setText(skill);
            containerLearn.addView(skillView);
        }

        LinearLayout containerTeach = dialog.findViewById(R.id.selectContainerTeach);

        List<String> teachList = match.teachArr;

        for (String skill : teachList) {
            View skillView = inflater.inflate(R.layout.skill_selection, containerTeach, false);
            CheckBox checkBox = skillView.findViewById(R.id.selectSkill);
            checkBox.setText(skill);
            containerTeach.addView(skillView);
        }

        dialog.show();

    }

    //FOR DEMO PURPOSES ONLY (incoming dialogue simulation)
    private void showIncomingRequestDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.incoming_request_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView userImage = dialog.findViewById(R.id.incomingUserImage);
        TextView message = dialog.findViewById(R.id.requestMessage);
        Button acceptBtn = dialog.findViewById(R.id.acceptRequest);
        Button declineBtn = dialog.findViewById(R.id.declineRequest);
        TextView theyLearn = dialog.findViewById(R.id.theyLearn);
        TextView theyTeach = dialog.findViewById(R.id.theyTeach);

        userImage.setImageResource(R.drawable.sample_profile_request);
        message.setText("Alice is interested in your skill!");
        theyLearn.setText("They want to learn: Photography");
        theyTeach.setText("They can teach you: Dancing");

        acceptBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Accepted!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            finish(); //simulation purposes only
        });

        declineBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Declined!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            finish(); //simulation purposes only
        });

        dialog.show();
    }

}