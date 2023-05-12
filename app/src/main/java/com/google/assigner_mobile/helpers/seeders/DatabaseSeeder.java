package com.google.assigner_mobile.helpers.seeders;

import android.content.Context;
import android.util.Log;

import com.google.assigner_mobile.functions.GlobalFunction;
import com.google.assigner_mobile.helpers.AssignmentHelper;
import com.google.assigner_mobile.helpers.DatabaseHelper;
import com.google.assigner_mobile.helpers.GroupHelper;
import com.google.assigner_mobile.helpers.GroupMembersHelper;
import com.google.assigner_mobile.helpers.UserHelper;
import com.google.assigner_mobile.models.Assignment;
import com.google.assigner_mobile.models.Group;
import com.google.assigner_mobile.models.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Vector;



public class DatabaseSeeder {

    AssignmentHelper asgDB;
    UserHelper userDB;
    GroupHelper groupDB;
    GroupMembersHelper groupMembersDB;

    DatabaseHelper dbh;

    GlobalFunction rand = new GlobalFunction();


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

    public void seedGroupMembers(Context context, int seedSize) {
        groupMembersDB = new GroupMembersHelper(context);

        groupMembersDB.open();
        userDB.open();
        groupDB.open();

        Vector <User> userVector = userDB.getAllData();
        Vector <Group> groupVector = groupDB.getAllData();
        int userIdLowerBound = userVector.get(0).getId(),
                userIdUpperBound = userVector.get(userVector.size() - 1).getId(),
                groupIdLowerBound = groupVector.get(0).getId(),
                groupIdUpperBound = groupVector.get(groupVector.size() - 1).getId();

        userDB.close();
        groupDB.close();

        int i = 0;
        for(i = 0; i < seedSize; i++)
            groupMembersDB.insert(
                    rand.randIntWithRange(groupIdLowerBound, groupIdUpperBound),
                    rand.randIntWithRange(userIdLowerBound, userIdUpperBound)
            );

        Log.d("DatabaseSeeder", String.format("Group Members Seeder : Successfully seeded %d group members", i));

    }

    public void seedGroups(Context context, int seedSize) {
        groupDB = new GroupHelper(context);

        groupDB.open();

        userDB.open();
        Vector <User> userVector = userDB.getAllData();
        int    userIdLowerBound = userVector.get(0).getId(),
                userIdUpperBound = userVector.get(userVector.size() - 1).getId();
        userDB.close();

        int i = 0;
        for(i = 0; i < seedSize; i++)
            groupDB.insert(
                    "Group " + i,
                    "This is group " + i,
                    rand.randIntWithRange(userIdLowerBound, userIdUpperBound)
            );

        Log.d("DatabaseSeeder", String.format("Group Seeder : Successfully seeded %d groups", i));

        groupDB.close();
    }

    /**
     * Fungsi untuk generate dummy data assignment ke database
     * @param context Context
     * @param seedSize Jumlah data yang ingin di-generate
     */
    public void seedAssignments(Context context, int seedSize)  {
        asgDB = new AssignmentHelper(context);
        asgDB.open();

        userDB = new UserHelper(context);
        userDB.open();

        Vector <User> userVector = userDB.getAllData();
        int     userIdLowerBound = userVector.get(0).getId(),
                userIdUpperBound = userVector.get(userVector.size() - 1).getId() ;

        userDB.close();

        groupDB = new GroupHelper(context);
        groupDB.open();

        Vector <Group> groupVector = groupDB.getAllData(); // problem : Bugged, tapi gapapa karena hanya untuk debug
        int     groupIdLowerBound = groupVector.get(0).getId(),
                groupIdUpperBound = groupVector.get(groupVector.size() - 1).getId() ;

        groupDB.close();
        int i = 0;
        for(i = 0; i < seedSize; i++)
            asgDB.insert(
                    rand.randIntWithRange(userIdLowerBound, userIdUpperBound),
                    rand.randIntWithRange(groupIdLowerBound, groupIdUpperBound),
                    "Assignment " + i,
                    "This is assignment " + i,
                    LocalDate.now(),
                    LocalDate.of(2023, LocalDate.now().getMonthValue(), rand.randIntWithRange(LocalDate.now().getDayOfMonth() - 3,28))
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


    public void seedFresh(Context context) throws SQLException{
        DatabaseHelper dbh = new DatabaseHelper(context);
        dbh.execQuery("DELETE FROM users");
        dbh.execQuery("DELETE FROM assignments");
        dbh.execQuery("DELETE FROM group_members");
        dbh.execQuery("DELETE FROM groups");

        seed(context);
    }

    /**
     * Fungsi untuk generate dummy data ke database
     * @param context Context
     */
    public void seed(Context context) throws SQLException {
        seedUsers(context, 3);
        seedGroups(context, 10);
        seedGroupMembers(context, 20);
        seedAssignments(context, 20);
    }
}
