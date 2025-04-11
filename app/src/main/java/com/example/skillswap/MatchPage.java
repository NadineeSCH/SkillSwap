package com.example.skillswap;

import android.os.Bundle;

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
                Arrays.asList("Java", "Photography")
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
}