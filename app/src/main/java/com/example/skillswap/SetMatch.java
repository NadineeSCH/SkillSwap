package com.example.skillswap;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class SetMatch extends DialogFragment {
    /*List<String> skillList = List.of("testskill1", "testskill2");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_set_match, container, false);
        LinearLayout selectContainer = view.findViewById(R.id.selectContainer);
        Log.d("DEBUG","inflated");

        for (String skill : skillList) {
            View skillView = inflater.inflate(R.layout.skill_selection, selectContainer, false);
            CheckBox checkBox = skillView.findViewById(R.id.selectSkill);
            checkBox.setText(skill);
            selectContainer.addView(skillView);
        }

        return view;
    }*/
}
