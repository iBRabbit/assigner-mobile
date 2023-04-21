package com.google.assigner_mobile.helpers.seeders;

import android.content.Context;
import android.util.Log;

import com.google.assigner_mobile.helpers.AssignmentHelper;
import com.google.assigner_mobile.helpers.DatabaseHelper;
import com.google.assigner_mobile.helpers.UserHelper;
import com.google.assigner_mobile.models.Assignment;
import com.google.assigner_mobile.models.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Vector;



public class DatabaseSeeder {

    AssignmentHelper asgDB;
    UserHelper userDB;

    DatabaseHelper dbh;

    Random rand = new Random();

    /**
     * Fungsi untuk generate dummy data assignment
     */
    public Vector<Assignment> generateAssignmentDummyVector() {

        Vector <Assignment> dummyDataVector = new Vector<>();

        dummyDataVector.add(new Assignment(1, 1, 1, "Assignment 1", "This is assignment 1", LocalDate.now(), LocalDate.of(2023, 4, 25)));
        dummyDataVector.add(new Assignment(2, 1, 1, "Assignment 2", "This is assignment 2", LocalDate.now(), LocalDate.of(2023, 5, 27)));
        dummyDataVector.add(new Assignment(3, 1, 1, "Assignment 3", "This is assignment 3", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(4, 1, 1, "Assignment 4", "This is assignment 4", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(5, 1, 1, "Assignment 5", "This is assignment 5", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(6, 1, 1, "Assignment 6", "This is assignment 6", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(7, 1, 1, "Assignment 7", "This is assignment 7", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(8, 1, 1, "Assignment 8", "This is assignment 8", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(9, 1, 1, "Assignment 9", "This is assignment 9", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(10, 1, 1, "Assignment 10", "This is assignment 10", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));

        return dummyDataVector;
    }

    /**
     * Fungsi untuk generate dummy data assignment ke database
     * @param context Context
     * @param seedSize Jumlah data yang ingin di-generate
     */
    public void seedAssignments(Context context, int seedSize)  {
        asgDB = new AssignmentHelper(context);
        asgDB.open();

        dbh = new DatabaseHelper(context);
        dbh.execQuery("DELETE FROM assignments");

        userDB = new UserHelper(context);
        userDB.open();

        Vector <User> userVector = userDB.getAllData();
        int     userIdLowerBound = userVector.get(0).getId(),
                userIdUpperBound = userVector.get(userVector.size() - 1).getId() ;

        userDB.close();

        int i = 0;
        for(i = 0; i < seedSize; i++)
            asgDB.insert(
                    rand.nextInt(userIdUpperBound - userIdLowerBound) + userIdLowerBound,
                    1,
                    "Assignment " + i,
                    "This is assignment " + i,
                    LocalDate.now(),
                    LocalDate.of(2023, LocalDate.now().getMonthValue(), rand.nextInt(30 - LocalDate.now().getDayOfMonth()) + LocalDate.now().getDayOfMonth())
            );

        Log.d("DatabaseSeeder", String.format("Assignment Seeder : Successfully seeded %d assignments", i));

        asgDB.close();
    }

    /**
     * Fungsi untuk generate dummy data user ke database
     * @param context Context
     * @param seedSize Jumlah data yang ingin di-generate
     */
    public void seedUsers(Context context, int seedSize) {

        userDB = new UserHelper(context);
        userDB.open();


        dbh = new DatabaseHelper(context);
        dbh.execQuery("DELETE FROM users");

        userDB.insert(
                "admin",
                "admin",
                "admin@gmail.com",
                "082919237129"
        );

        int i = 0;
        for(i = 0; i < seedSize - 1; i++)
            userDB.insert(
                   "user" + i,
                   "user" + i,
                   "user" + i + "@gmail.com",
                   "0829192371" + i
           );


       userDB.close();

       Log.d("DatabaseSeeder", String.format("User Seeder : Successfully seeded %d users", i));
    }

    /**
     * Fungsi untuk generate dummy data ke database
     * @param context Context
     */
    public void seed(Context context) throws SQLException {
        seedUsers(context, 3);
        seedAssignments(context, 20);
    }
}