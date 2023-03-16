package project1;

/**
 * Class for Enrollment Object
 * @author Arjun Ajesh, Nathan Roh
 */

import java.text.DecimalFormat;

public class Enrollment {
    private EnrollStudent[] enrollStudents;
    private int size;
    private static final int CAPACITY = 4;

    /**
     * Constructor for Enrollment Class
     */
    public Enrollment(){
        this.size = 0;
        this.enrollStudents = new EnrollStudent[CAPACITY];

    }

    /**
     * Adds a student to enrollment
     * Takes a student object and adds to end of enrollStudents array
     * @param enrollStudent the object to be added.
     */
    public void add(EnrollStudent enrollStudent){
        int index = find(enrollStudent.getProfile());
        if(index != -1){
            enrollStudents[index].setCreditsEnrolled(enrollStudent.getCreditsEnrolled());
        }
        else {

            enrollStudents[size] = enrollStudent;
            size++;
            if (size == enrollStudents.length) {
                grow();
            }
        }
    }

    /**
     * Resizes the enrollment array when necessary
     */
    public void grow(){
        EnrollStudent[] newEnrollment = new EnrollStudent[size + CAPACITY];
        for(int i = 0; i < enrollStudents.length; i++){
            newEnrollment[i] = enrollStudents[i];
        }
        enrollStudents = newEnrollment;
    }

    /**
     * Removes a student from the enrollment list
     * @param enrollStudent the object to be removed.
     */
    public void remove(EnrollStudent enrollStudent){
        if(find(enrollStudent.getProfile()) == -1) {
            System.out.println(enrollStudent.getProfile() + " is not enrolled.");
        }
        else {
            int pivot = find(enrollStudent.getProfile());
            for(int i = pivot; i < size; i++) {
                enrollStudents[i] = enrollStudents[i+1];
            }
            enrollStudents[size] = null;
            size--;
            System.out.println(enrollStudent.getProfile() + " dropped.");
        }
    }

    /**
     * Finds the index at which a profile exists
     * @param p the object to be searched for.
     * @return -1 if the profile is not found and i (index) if found
     */
    public int find(Profile p){
        for (int i = 0; i < size; i++){
            if (p.equals(enrollStudents[i].getProfile())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Prints the enrollment list
     */
    public void printEnrollment(){
        if(this.enrollStudents[0] == null) {
            System.out.println("Enrollment is empty!");
        }
        else {
            System.out.println("** Enrollment **");
            for (int i = 0; i < size; i++) {
                if (this.enrollStudents[i] != null) {
                    System.out.println(enrollStudents[i].toString());
                }
            }
            System.out.println("* end of enrollment *");
        }
    }

    /**
     * Prints students' profile information and tuition
     * @param roster the object to be used to get student profiles.
     */
    public void printTuition(Roster roster){
        if(size == 0) {
            System.out.println("Student roster is empty!");
        }
        else {
            System.out.println("** Tuition due **");
            for(int i = 0; i < size; i++) {
                EnrollStudent es = enrollStudents[i];
                Student s = roster.getStudent(es.getProfile());
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                decimalFormat.setGroupingUsed(true);
                decimalFormat.setGroupingSize(3);
                System.out.println(s.getProfile() + " " + getStudentInfo(s) + " enrolled " +
                        es.getCreditsEnrolled() + " credits: tuition due: $" + decimalFormat.format(s.tuitionDue(es.getCreditsEnrolled())));
            }
            System.out.println("* end of tuition due *");
        }
    }

    /**
     * Prints the list of eligible graduates
     * @param roster the object to be used to get student profiles.
     */
    public void endSemester(Roster roster){
        for(int i = 0; i < size; i++){ //iterate through enrollment
            EnrollStudent es = enrollStudents[i];
            Student s = roster.getStudent(es.getProfile());
            s.setCreditCompleted(s.getCreditCompleted() + es.getCreditsEnrolled());
        }
        System.out.println("Credit completed has been updated.");
        roster.printEligibleGraduates();
    }

    /**
     * Determines the type of student
     * @param s the object to be compared.
     * @return returns the name of the type of student given an instance of student
     */
    public String getStudentInfo(Student s){
        if(s instanceof Resident){
            return "(Resident)";
        }
        else if(s instanceof International){
            return "(International student" + (((International) s).isStudyAbroad() ? "study abroad)":")");
        }
        else if(s instanceof TriState){
            return "(Tri-state " + ((TriState) s).getState().toUpperCase() + ")";
        }
        else if(s instanceof NonResident){
            return "(Non-Resident)";
        }
        else return "";
    }

    /**
     * Searches for appropriate profile index and matching student in enrollStudents.
     * @param p the object to be compared.
     * @return returns null if the profile is not found and enrollStudents[i] if a profile is found
     */
    public EnrollStudent getEnrolledStudent(Profile p){
        int i = find(p);
        if(i == -1){
            return null;
        }
        else{
            return enrollStudents[i];
        }
    }
}
