package com.example.skillswap.utils;


import com.example.skillswap.models.Match;
import com.example.skillswap.models.User;

import org.apache.commons.text.similarity.LevenshteinDistance;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkillMatcher {

    private static final int threshold = 3;

    private static final LevenshteinDistance levenshtein = new LevenshteinDistance();

    // Method to calculate Levenshtein distance-based matching
    public static List<Match> findMatchingUsers(User currentUser, List<User> allUsers) {

        List<Match> matches = new ArrayList<>();

        for (User user : allUsers) {
            // Check if the current user wants to learn a skill that the other user can teach
            boolean userWants = false;
            int learnCount = 0;
            ArrayList<String> learnArr= new ArrayList<>();
            for (String skillToLearn : currentUser.getLearn()) {
                for (String skillToTeach : user.getTeach()) {
                    // Use Levenshtein distance to check similarity between skills
                    if (levenshtein.apply(skillToLearn.toLowerCase(), skillToTeach.toLowerCase()) <= threshold) {
                        userWants = true;
                        learnArr.add(skillToLearn);
                        learnCount +=1;
                        break;
                    }
                }

            }

            // Check if the current user can teach a skill that the other user wants to learn
            boolean userCanHelp = false;
            int teachCount = 0;
            ArrayList<String> teachArr= new ArrayList<>();
            for (String skillToTeach : currentUser.getTeach()) {
                for (String skillToLearn : user.getLearn()) {
                    if (levenshtein.apply(skillToTeach.toLowerCase(), skillToLearn.toLowerCase()) <= threshold) {
                        userCanHelp = true;
                        teachCount +=1;
                        teachArr.add(skillToTeach);
                        break;
                    }
                }

            }

            // Add to matches if both conditions are met
            if (userWants && userCanHelp) {
                matches.add(new Match(currentUser,user,learnCount,learnArr,teachCount,teachArr));


            }
        }

        Collections.sort(matches);

        return matches;
    }
}

