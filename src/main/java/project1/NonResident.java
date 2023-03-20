package project1;
/**
 * Class for NonResident Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class NonResident extends Student{

    private static final int MIN_CREDITS_FULLTIME = 12;
    private static final int ADDITIONAL_CREDITS = 16;
    private static final int TUITION_COST = 29737;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int PER_CREDIT_HOUR_COST = 966;
    private static final double PORTION = 0.8;

    /**
     * Constructor for NonResident Class
     * @param fname first name of student
     * @param lname last name
     * @param dob date of birth
     * @param major student's major
     * @param credits student's credits
     */
    public NonResident(String fname, String lname, Date dob, Major major, int credits) {
        super(fname, lname, dob, major, credits);
    }

    /**
     * Calculates student's tuition given credits enrolled
     * @param creditsEnrolled the value to be used to calculate tuition
     * @return returns the total tuition for student given the number of credits enrolled
     */
    @Override
    public double tuitionDue(int creditsEnrolled) {
        double tuition = 0.00;
        if(creditsEnrolled >= MIN_CREDITS_FULLTIME) { // full time
            if(creditsEnrolled > ADDITIONAL_CREDITS) {
                tuition = TUITION_COST + UNIVERSITY_FEE + (PER_CREDIT_HOUR_COST * (creditsEnrolled - ADDITIONAL_CREDITS));
            }
            else {
                tuition = TUITION_COST + UNIVERSITY_FEE;
            }
        }
        else { // part-time
            tuition = (PER_CREDIT_HOUR_COST * creditsEnrolled) + (PORTION * UNIVERSITY_FEE);
        }
        return tuition;
    }

    /**
     * @return returns false, for students of this class are not residents
     */
    @Override
    public boolean isResident() {
        return false;
    }
}
