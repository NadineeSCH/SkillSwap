package com.example.skillswap;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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
        MatchAdaptor matchAdaptor = new MatchAdaptor(matchedUsers, new MatchAdaptor.OnMatchClickListener() {
            @Override
            public void onMatchClick(Match match) {
                //Toast.makeText(MatchPage.this,"clicked",Toast.LENGTH_SHORT).show();
                showDialog(match);


            }
        });
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

    private void showDialog(Match match){
        Dialog dialog = new Dialog(MatchPage.this);
        dialog.setContentView(R.layout.activity_set_match);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);

        LayoutInflater inflater = LayoutInflater.from(this);

        // fetch the values from the match object, then set the contents of the widgets

        TextView userName= dialog.findViewById(R.id.matchedUserName);
        ImageView profilePicture = dialog.findViewById(R.id.profilePicture);
        profilePicture.setImageResource(match.matchedUser.getImageId());
        userName.setText(match.matchedUser.getName());

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
}