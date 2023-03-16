package project1;

import static org.junit.Assert.*;

public class DateTest {

    @org.junit.Test
    public void test_isValid_daysInFeb_nonLeap() {
        Date d = new Date("2/29/2003");
        assertFalse(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_daysInApril() {
        Date d = new Date("4/31/2003");
        assertFalse(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_invalidDaysInApril() {
        Date d = new Date("4/31/2003");
        assertFalse(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_invalidMonth() {
        Date d = new Date("13/31/2003");
        assertFalse(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_negativeMonth() {
        Date d = new Date("-1/31/2003");
        assertFalse(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_invalidDaysInMarch() {
        Date d = new Date("3/32/2003");
        assertFalse(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_validDaysInMarch() {
        Date d = new Date("3/31/2003");
        assertTrue(d.isValid());
    }

    @org.junit.Test
    public void test_isValid_validLeapYear() {
        Date d = new Date("2/29/2004");
        assertTrue(d.isValid());
    }



}