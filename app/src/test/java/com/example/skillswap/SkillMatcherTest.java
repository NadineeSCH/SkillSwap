package com.example.skillswap;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


import com.example.skillswap.models.Match;
import com.example.skillswap.utils.SkillMatcher;

import com.example.skillswap.models.User;

//testing skill matcher function
public class SkillMatcherTest {

    @Test
    public void testFindMatchingUsers() {
        User currentUser = new User("u1", "Alex",
                List.of("Python", "Drawing"),
                List.of("Guitar","Jumping","piano"));

        List<User> allUsers = new ArrayList<>();
        allUsers.add(new User("u2", "Beth",
                List.of("Jumping","Guitar"),
                List.of("Python")));

        allUsers.add(new User("u3", "Charlie",
                List.of("Guitar"),
                List.of("Dance")));

        allUsers.add(new User("u4", "Dana",
                List.of("Guitar"),
                List.of("Draw"))); // match (Guitar ↔ Drawing)

        List<Match> matches = SkillMatcher.findMatchingUsers(currentUser, allUsers);

        System.out.println(matches.toString());
        System.out.println(""+matches.get(0).teachCount+matches.get(0).learnCount+matches.get(1).teachCount+matches.get(1).learnCount);

        // Only u2 and u4 should match
        assertEquals(2, matches.size());
        assertTrue(matches.stream().anyMatch(match -> match.matchedUser.getId().equals("u2")));
        assertTrue(matches.stream().anyMatch(match -> match.matchedUser.getId().equals("u4")));
        assertTrue(matches.stream().anyMatch(match -> match.learnArr.equals(List.of("Guitar"))));
        assertTrue(matches.stream().anyMatch(match -> match.matchedUser.getId().equals("u4")));

    }
}
