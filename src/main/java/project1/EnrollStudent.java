package project1;
/**
 * Class for EnrollStudent Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;
    private static final int FULL_TIME_CREDITS = 12;

    private boolean fullTime;

    /**
     * Constructor for EnrollStudent Class
     * @param profile profile of student
     * @param creditsEnrolled number of credits enrolled for a student
     */
    public EnrollStudent(Profile profile, int creditsEnrolled){
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
        this.fullTime = creditsEnrolled >= FULL_TIME_CREDITS ? true : false;
    }

    /**
     * Determines whether object is an instance of EnrollStudent
     * @param o the object to be compared.
     * @return returns false if object is not an instance of EnrollStudent and profile if so
     */
    public boolean equals(Object o){
        if (!(o instanceof EnrollStudent)){
            return false;
        }
        EnrollStudent other = (EnrollStudent) o;
        return this.profile.equals(other.profile);
    }

    /**
     * Sets creditsEnrolled and determines whether student is full or part time
     * @param creditsEnrolled number of credits enrolled
     */
    public void setCreditsEnrolled(int creditsEnrolled){
        this.creditsEnrolled = creditsEnrolled;
        this.fullTime = this.creditsEnrolled >= FULL_TIME_CREDITS ? true : false;
    }

    /**
     * @return returns number of credits enrolled
     */
    public int getCreditsEnrolled(){
        return creditsEnrolled;
    }

    /**
     * @return returns profile of student
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * @return returns profile of student in proper string format
     */
    public String toString() {
        return getProfile().toString() + ": credits enrolled: " + Integer.toString(getCreditsEnrolled());
    }

    /**
     * @return returns true or false depending on whether student is full time or not
     */
    public boolean isFulltime(){
        return fullTime;
    }

}
