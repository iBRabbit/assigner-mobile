package com.google.assigner_mobile.helpers.seeders;

import com.google.assigner_mobile.functions.DateFunction;
import com.google.assigner_mobile.models.Assignment;

import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;

public class DatabaseSeeder {

    public DatabaseSeeder() {

    }

    // Debug factory //
    public Vector<Assignment> generateAssignmentDummyVector() {

        Vector <Assignment> dummyDataVector = new Vector<>();

        dummyDataVector.add(new Assignment(1, 1, "Assignment 1", "This is assignment 1", LocalDate.now(), LocalDate.of(2023, 4, 25)));
        dummyDataVector.add(new Assignment(2, 1, "Assignment 2", "This is assignment 2", LocalDate.now(), LocalDate.of(2023, 5, 27)));
        dummyDataVector.add(new Assignment(3, 1, "Assignment 3", "This is assignment 3", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(4, 1, "Assignment 4", "This is assignment 4", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(5, 1, "Assignment 5", "This is assignment 5", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(6, 1, "Assignment 6", "This is assignment 6", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(7, 1, "Assignment 7", "This is assignment 7", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(8, 1, "Assignment 8", "This is assignment 8", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(9, 1, "Assignment 9", "This is assignment 9", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));
        dummyDataVector.add(new Assignment(10, 1, "Assignment 10", "This is assignment 10", LocalDate.of(2023,4,10), LocalDate.of(2023, 4, 20)));

        return dummyDataVector;
    }


}
