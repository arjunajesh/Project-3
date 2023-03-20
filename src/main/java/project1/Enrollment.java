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
     * @return returns appropriate string (error message or message indicating that student was successfully added
     */
    public String add(EnrollStudent enrollStudent, Roster roster){
        Student s = roster.getStudent(enrollStudent.getProfile());
        if(s == null){
            return "Cannot enroll: " + enrollStudent.getProfile() + " is not in the roster";
        }
        else if(!s.isValid(enrollStudent.getCreditsEnrolled())){
            return "Invalid Credit hours"; /* This error message does not align with project 2 specifications */
        }

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
        return s.getProfile() + " enrolled " + enrollStudent.getCreditsEnrolled() + " credits";
    }

    /**
     * Resizes the enrollment array when necessary
     */
    public void grow(){
        EnrollStudent[] newEnrollment = new EnrollStudent[size + CAPACITY];
        System.arraycopy(enrollStudents, 0, newEnrollment, 0, enrollStudents.length);
        enrollStudents = newEnrollment;
    }

    /**
     * Removes a student from the enrollment list
     * @param enrollStudent the object to be removed.
     * @return returns string indicating student is either not in roster or student was dropped from roster
     */
    public String remove(EnrollStudent enrollStudent){
        if(find(enrollStudent.getProfile()) == -1) {
            return enrollStudent.getProfile() + " is not enrolled.";
        }
        else {
            int pivot = find(enrollStudent.getProfile());
            for(int i = pivot; i < size; i++) {
                enrollStudents[i] = enrollStudents[i+1];
            }
            enrollStudents[size] = null;
            size--;
            return enrollStudent.getProfile() + " dropped.";
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
     * @return returns string that indicates desired list is empty or a list of students in enrollment
     */
    public String printEnrollment(){
        StringBuilder sb = new StringBuilder();
        if(this.enrollStudents[0] == null) {
            sb.append("\nEnrollment is empty!");
        }
        else {
            sb.append("\n** Enrollment **");
            for (int i = 0; i < size; i++) {
                if (this.enrollStudents[i] != null) {
                    sb.append("\n" + enrollStudents[i].toString());
                }
            }
            sb.append("\n* end of enrollment *");
        }
        return sb.toString();
    }

    /**
     * Prints students' profile information and tuition
     * @param roster the object to be used to get student profiles.
     * @return returns string that indicates roster is empty or a list of students and their tuitions due
     */
    public String printTuition(Roster roster){
        StringBuilder sb = new StringBuilder();
        if(size == 0) {
            sb.append("\nStudent roster is empty!");
        }
        else {
            sb.append("\n** Tuition due **");
            for(int i = 0; i < size; i++) {
                EnrollStudent es = enrollStudents[i];
                Student s = roster.getStudent(es.getProfile());
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                decimalFormat.setGroupingUsed(true);
                decimalFormat.setGroupingSize(3);
                sb.append("\n" + s.getProfile() + " " + getStudentInfo(s) + " enrolled " +
                        es.getCreditsEnrolled() + " credits: tuition due: $" + decimalFormat.format(s.tuitionDue(es.getCreditsEnrolled())));
            }
            sb.append("\n* end of tuition due *");
        }
        return sb.toString();
    }

    /**
     * Prints the list of eligible graduates
     * @param roster the object to be used to get student profiles.
     */
    public void endSemester(Roster roster) throws Exception{
        if(size == 0){
            throw new Exception("There are no students enrolled");
        }
        for(int i = 0; i < size; i++){ //iterate through enrollment
            EnrollStudent es = enrollStudents[i];
            Student s = roster.getStudent(es.getProfile());
            s.setCreditCompleted(s.getCreditCompleted() + es.getCreditsEnrolled());
        }
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
