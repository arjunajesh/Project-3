package project1;

/**
 * Class for TriState Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class TriState extends NonResident {
    private final String state;
    private static final int MIN_CREDITS_FULLTIME = 12;
    private static final int ADDITIONAL_CREDITS = 16;
    private static final int TUITION_COST = 29737;
    private static final int UNIVERSITY_FEE = 3268;
    private static final int PER_CREDIT_HOUR_COST = 966;
    private static final int NY_DISCOUNT = 4000;
    private static final int CT_DISCOUNT = 5000;
    private static final double PORTION = 0.8;

    /**
     * Constructor for TriState Class
     * @param fname student's first name
     * @param lname last name
     * @param dob date of birth
     * @param major student's major
     * @param credits student's credits
     * @param state which state student is from
     */
    public TriState(String fname, String lname, Date dob, Major major, int credits, String state) {
        super(fname, lname, dob, major, credits);
        this.state = state;
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
        else { // part time
            tuition = (PER_CREDIT_HOUR_COST * creditsEnrolled) + (PORTION * UNIVERSITY_FEE);
        }

        if(state.toLowerCase().equalsIgnoreCase("NY") && creditsEnrolled >= MIN_CREDITS_FULLTIME) {
            tuition = tuition - NY_DISCOUNT;
        }
        if(state.toLowerCase().equalsIgnoreCase("CT") && creditsEnrolled >= MIN_CREDITS_FULLTIME) {
            tuition = tuition - CT_DISCOUNT;
        }

        return tuition;
    }

    /**
     * @return returns state from which student is from (in capital letters)
     */
    public String getState(){
        return state.toUpperCase();
    }
}
