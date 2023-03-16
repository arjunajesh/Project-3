package project1;

import junit.framework.TestCase;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

class InternationalTest {

    @Test
    public void test_tuitionDue_internationalStudyAbroad() {
        International student = new International("John", "Doe", new Date("07/12/2003"), Major.CS, 25, true);
        assertEquals(student.tuitionDue(12), 5918.00);
    }

    @Test
    public void test_tuitionDue_internationalNotStudyAbroad() {
        International student = new International("John", "Doe", new Date("07/12/2003"), Major.CS, 25, false);
        assertEquals(student.tuitionDue(12), 35655.00);
    }
}