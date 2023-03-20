package com.example.project3;
/**
 * Class for Resident Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class Resident extends Student{

    private int scholarship;
    private static final int MIN_CREDITS_FULLTIME = 12;
    private static final int ADDITIONAL_CREDITS = 16;
    private static final int TUITION_COST = 12536;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int PER_CREDIT_HOUR_COST = 404;
    private static final double PORTION = 0.8;

    /**
     * Constructor for Resident Class
     * @param fname student's first name
     * @param lname last name
     * @param dob date of birth
     * @param major student's major
     * @param credits student's credits
     */
    public Resident(String fname, String lname, Date dob, Major major, int credits) {
        super(fname, lname, dob, major, credits);
    }

    /**
     * Calculates student's tuition given credits enrolled
     * @param creditsEnrolled the value to be used to calculate tuition
     * @return returns the total tuition for student given the number of credits enrolled
     */
    @Override
    public double tuitionDue(int creditsEnrolled) { // resident students are eligible for scholarships!
        double tuition = 0.00;
        if(creditsEnrolled >= MIN_CREDITS_FULLTIME) { // full time student
            if(creditsEnrolled > ADDITIONAL_CREDITS) { // additional pay for over 16 credits
                tuition = TUITION_COST + UNIVERSITY_FEE + (PER_CREDIT_HOUR_COST * (creditsEnrolled - ADDITIONAL_CREDITS)) - scholarship;
            }
            else {
                tuition = TUITION_COST + UNIVERSITY_FEE - scholarship;
            }
        }
        else { // part time student
            tuition = (PER_CREDIT_HOUR_COST * creditsEnrolled) + (PORTION * UNIVERSITY_FEE);
        }
        return tuition;
    }

    /**
     * @return returns true, for students of this class are indeed residents
     */
    @Override
    public boolean isResident() {
        return true;
    }

    /**
     * Sets total scholarship
     */
    public void setScholarship(int scholarship){
        this.scholarship = scholarship;
    }


}
