package com.kidtask.store;

import com.kidtask.model.Task;
import com.kidtask.model.Wish;
import com.kidtask.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppStore {

    private static final List<Wish> wishes = new ArrayList<>();
    private static final List<Task> tasks = new ArrayList<>();

    private static User currentUser;

    public static void setCurrentUser(User user ) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }


    public static List<Wish> getWishes() {
        return Collections.unmodifiableList(wishes);
    }

    public static List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public static void addWish(Wish wish) {
        wishes.add(wish);
    }

    public static void addTask(Task task) {
        tasks.add(task);
    }

}
