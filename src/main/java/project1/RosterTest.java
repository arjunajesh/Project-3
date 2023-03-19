package project1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RosterTest {

    @Test
    void addStudent() {

        International student = new International("John", "Doe", new Date("07/12/2003"), Major.CS, 25, true);
        Assertions.assertTrue(new Roster().addStudent(student));

        student = new International("John", "Doe", new Date("07/12/2003"), Major.CS, 25, true);
        Assertions.assertTrue(new Roster().addStudent(student));

        TriState newstudent = new TriState("John", "Doe", new Date("07/12/2003"), Major.CS, -25, "PA");
        Assertions.assertTrue(new Roster().addStudent(newstudent));

        newstudent = new TriState("John", "Doe", new Date("07/12/2003"), Major.CS, 25, "NY");
        Assertions.assertTrue(new Roster().addStudent(newstudent));


    }

    @Test
    void remove() {
        Profile profile = new Profile("John", "Doe", new Date("07/12/2003"));
        Assertions.assertTrue(new Roster().remove(profile));
        //idrk what im doing here so ignore this
    }
}
