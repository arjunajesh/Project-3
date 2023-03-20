package com.example.project3;
/**
 * Class for International Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class International extends NonResident{
    private final boolean isStudyAbroad;
    private static final int MIN_CREDITS_FULLTIME = 12;
    private static final int ADDITIONAL_CREDITS = 16;
    private static final int TUITION_COST = 29737;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int HEALTH_INSURANCE_FEE = 2650;
    private static final int PER_CREDIT_HOUR_COST = 966;
    private static final int STUDYABROAD_MAX_CREDITS = 12;
    private static final int STUDYABROAD_MIN_CREDITS = 3;
    private static final int INTERNATIONAL_MAX_CREDITS = 24;
    private static final int INTERNATIONAL_MIN_CREDITS = 12;
    /**
     * Constructor for International Class
     * @param fname first name
     * @param lname last name
     * @param dob date of birth
     * @param major student's major
     * @param credits incoming credits
     * @param isStudyAbroad whether student is studying abroad
     */
    public International(String fname, String lname, Date dob, Major major, int credits, boolean isStudyAbroad) {
        super(fname, lname, dob, major, credits);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Determines if student's credits are acceptable
     * @param creditEnrolled the value to be assessed
     * @return returns true if credit requirements are met for international students either studying abroad or not
     * and returns false if credit requirements are not met
     */
    @Override
    public boolean isValid(int creditEnrolled){
        if(isStudyAbroad){
            return creditEnrolled <= STUDYABROAD_MAX_CREDITS && creditEnrolled >= STUDYABROAD_MIN_CREDITS;
        }
        else{
            return creditEnrolled >= INTERNATIONAL_MIN_CREDITS && creditEnrolled <= INTERNATIONAL_MAX_CREDITS;
        }
    }

    /**
     * Compares two dates
     * @param creditsEnrolled the value to be used to calculate tuition
     * @return returns the total tuition for student given the number of credits enrolled
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        double tuition = 0.00;
        if(creditsEnrolled >= MIN_CREDITS_FULLTIME) {
            if(isStudyAbroad) {
                tuition = UNIVERSITY_FEE + HEALTH_INSURANCE_FEE;
            }
            else { // NOT study abroad
                if(creditsEnrolled > ADDITIONAL_CREDITS) {
                    tuition = TUITION_COST + UNIVERSITY_FEE + HEALTH_INSURANCE_FEE + (PER_CREDIT_HOUR_COST * (creditsEnrolled - ADDITIONAL_CREDITS));
                }
                else {
                    tuition = TUITION_COST + UNIVERSITY_FEE + HEALTH_INSURANCE_FEE;
                }
            }
        }
        else { // <12 credits entails that the student is studying abroad
            tuition = UNIVERSITY_FEE + HEALTH_INSURANCE_FEE;
        }

        return tuition;
    }

    /**
     * Yields whether student is studying abroad or not
     * @return returns true if student is indeed studying abroad and false otherwise
     */
    public boolean isStudyAbroad(){
        return isStudyAbroad;
    }

}
