package project1;
/**
 * Class for Roster Object
 * @author Arjun Ajesh, Nathan Roh
 */

public class Roster {
    private Student[] roster;
    private int size;
    public static final int CAPACITY = 4;
    private static final int CREDITS_REQUIRED_GRADUATION = 120;


    /**
     * Constructor for Roster Object
     */
    public Roster(){
        this.size = 0;
        this.roster = new Student[CAPACITY];
    }


    /**
     * Adds a student to the roster. If the roster is full, it will call grow()
     * @param student student that is to be added to roster
     * @return returns true if student was added, false if student is already in roster
     */
    public String addStudent(Student student){
        if(!student.getProfile().getDob().isValid()){
            return "DOB invalid: " + student.getProfile().getDob() + " younger than 16 years old";
        }

        if (size == roster.length){
            grow();
        }
        if(!contains(student)) {
            roster[size] = student;
            size++;

            return student.getProfile() + " added to the roster";
        }
        else{
            return student.getProfile() + " is already in the roster";
        }
    }

    /**
     * Handles All add student commands (international, resident, non-resident, tristate)
     * @param command
     * @return

    public boolean add(String[] command){
        try{
            Major m = validateBasicCredentials(command[3], command[4], command[5]);
            if (m == null){
                return false;
            }
            boolean worked = false;
            switch(command[0]){
                case "AR": worked = addStudent(new Resident(command[1], command[2], new Date(command[3]), m, Integer.parseInt(command[5])));
                    break;
                case "AN": worked = addStudent(new NonResident(command[1], command[2], new Date(command[3]), m, Integer.parseInt(command[5])));
                    break;
                case "AT":
                    if(command.length < 7){
                        System.out.println("Missing the state code.");
                        return false;
                    }
                    if(!command[6].equalsIgnoreCase("ny") && !command[6].equalsIgnoreCase("ct")){
                        System.out.println(command[6] + ": Invalid state code.");
                        return false;
                    }
                    worked = addStudent(new TriState(command[1], command[2], new Date(command[3]), m, Integer.parseInt(command[5]), command[6]));

                    break;
                case "AI":
                    boolean isStudyAbroad = command.length > 6 ? Boolean.parseBoolean(command[6]) : false;
                    worked = addStudent(new International(command[1], command[2], new Date(command[3]), m, Integer.parseInt(command[5]), isStudyAbroad));
            }
            if(worked){
                System.out.println(command[1] + " " + command[2] + " " + command[3] + " added to the roster.");
            }
            else{
                System.out.println(command[1] + " " + command[2] + " " + command[3] + " is already in the roster.");

            }
            return true;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Missing data in command line.");
            return false;
        }
        catch(Exception e){
            System.out.println("Error occurred "  + e.getMessage());
            return false;
        }
    }*/

    /**
     * Makes sure Date of birth, major, and credits all fit requirements
     * @param dob
     * @param major
     * @param credits
     * @return
     */
    public static Major validateBasicCredentials(String dob, String major, String credits){
        int c;
        try{ //Make sure credits is an integer
            c = Integer.parseInt(credits);
        }
        catch(NumberFormatException e){
            System.out.println("Credits completed invalid: not an integer!");
            return null;
        }
        if (c < 0) { //make sure credits is non-negative
            System.out.println("Credits completed invalid: cannot be negative!");
            return null;
        }
        Date d = new Date(dob);
        if (!d.isValid()) { //make sure date is a valid date
            return null;
        }
        Major m;
        switch(major.toLowerCase()){ // configure String major to appropriate major class
            case "bait": m = Major.BAIT;
                break;
            case "cs": m = Major.CS;
                break;
            case "math": m = Major.MATH;
                break;
            case "iti": m = Major.ITI;
                break;
            case "ee": m = Major.EE;
                break;
            default:
                System.out.println("Major code invalid: " + major);
                return null;
        }
        return m;
    }

    /**
     * Removes specified student from roster
     * @param profile student to be removed
     * @return returns true if student is removed, false if student is not in roster
     */
    public String remove(Profile profile){
        if(find(profile) == -1){ // student does not exist
            return profile.toString() + " is not in the roster.";
            //return false;
        }
        else{ // removing student
            int pivot = find(profile);
            for(int i = pivot; i < size; i++){
                roster[i] = roster[i+1];
            }
            roster[size] = null;
            size--;
            return profile.toString() + " removed from the roster.";
            //return true;
        }
    }

    /**
     * Increases the roster size by 4
     */
    private void grow(){
        Student[] newRoster = new Student[size + CAPACITY];
        for(int i = 0; i < roster.length; i++){
            newRoster[i] = roster[i];
        }
        roster = newRoster;
    }

    /**
     * Finds the specified student in the roster
     * @param profile
     * @return Integer index of student in index, -1 if student is not in roster
     */
    private int find(Profile profile){
        for (int i = 0; i < size; i++){
            if (profile.equals(roster[i].getProfile())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks if student is in roster
     * @param student
     * @return true if student is in roster, false if not
     */
    public boolean contains(Student student){
        for(int i = 0; i < size; i++) {
            if(student.equals(roster[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the major of the student
     * @param profile
     * @param major new major of the student
     * @return true is major was changed, false if invalid major or student does not exist in roster
     */
    public boolean change(Profile profile, String major){
        String majorL = major.toLowerCase();
        Major m;
        if (majorL.equals("bait")){
            m = Major.BAIT;
        }
        else if(majorL.equals("cs")){
            m = Major.CS;
        }
        else if(majorL.equals("math")){
            m = Major.MATH;
        }
        else if(majorL.equals("iti")){
            m = Major.ITI;
        }
        else if(majorL.equals("ee")){
            m = Major.EE;
        }
        else{
            System.out.println("Major code invalid: " + major);
            return false;
        }

        if(find(profile) == -1){
            System.out.println(profile.toString() + " is not in the roster.");
            return false;
        }
        else{
            int pivot = find(profile);
            roster[pivot].setMajor(m);
            System.out.println(profile.toString() + " major changed to " + major);
        }
        return true;
    }

    /**
     * Sorts the roster by last name, first name, and date of birth
     */
    public void sort(){
        if (size == 0){
            System.out.println("Student roster is empty!");
        }
        else {
            for (int i = 0; i < size - 1; i++) {
                for (int k = 0; k < size - i - 1; k++) {
                    if (roster[k].compareTo(roster[k + 1]) > 0) {
                        Student temp = roster[k];
                        roster[k] = roster[k + 1];
                        roster[k + 1] = temp;
                    }
                }
            }
        }
   }

    /**
     * Prints out the roster sorted by last name, first name, date of birth
     */
   public void sortByProfile(){
        if(size == 0){
            System.out.println("Student roster is empty!");
        }
        else{
            System.out.println("** Student roster sorted by last name, first name, DOB **");
            sort();
            printRoster();
            System.out.println("* end of roster *");
        }
   }

    /**
     * Prints out the roster sorted by standing
     */
   public void sortByStanding() {
       if (size == 0) {
           System.out.println("Student roster is empty!");
       } else {
           sort();
           System.out.println("** Student roster sorted by standing **");
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Freshman")) {
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Junior")) {
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Senior")) {
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Sophomore")) {
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           System.out.println("* end of roster *");
       }
   }

    /**
     * Prints out the roster sorted by school, major
     */
   public void sortBySchoolMajor(){
       if (size == 0) {
           System.out.println("Student roster is empty!");
       } else {
           sort();
           System.out.println("** Student roster sorted by school, major **");
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("BAIT")){
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("CS")){
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("MATH")){
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("SC&I")){
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("ITI")){
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("EE")){
                   System.out.println(roster[i] + getStudentInfo(roster[i]));
               }
           }
           System.out.println("* end of roster *");
       }
   }

    /**
     * Prints the roster
     */
    public void printRoster(){
        for(int i = 0; i < size; i++){
            if(this.roster[i] != null) {
                System.out.println(roster[i] + getStudentInfo(roster[i]));
            }
        }
    }
    public static String getStudentInfo(Student s){
        if(s instanceof Resident){
            return "(resident)";
        }
        else if(s instanceof International){
            return "(non-resident)(international" + (((International) s).isStudyAbroad() ? ":study abroad)":")");
        }
        else if(s instanceof TriState){
            return "(non-resident)(tri-state:" + ((TriState) s).getState() + ")";
        }
        else if(s instanceof NonResident){
            return "(non-resident)";
        }
        else return "";
    }
    public void printEligibleGraduates(){ //print eligible graduates
        System.out.println("** list of students eligible for graduation **");
        for(int i = 0; i < size; i++){
            Student s = roster[i];
            if(s.getCreditCompleted() >= CREDITS_REQUIRED_GRADUATION)
            System.out.println(roster[i] + getStudentInfo(roster[i]));
        }
    }

    /**
     * Prints the students in a specified school
     * @param school
     */
    public void printSchool(String school){
        if(!("RBS".equalsIgnoreCase(school)) &&
           !("SAS".equalsIgnoreCase(school)) &&
           !("SC&I".equalsIgnoreCase(school)) &&
           !("SOE".equalsIgnoreCase(school))){
            System.out.println("School doesn't exist: " + school);
        }
        if(this.roster[0] == null){
            System.out.println("Student roster is empty!");
        }
        sort();
        System.out.println("* Students in " + school + " *");
        for(int i = 0; i < size; i++){
            if((this.roster[i].getSchool()).equalsIgnoreCase(school) && (this.roster[i] != null)){
                System.out.println(this.roster[i] + getStudentInfo(this.roster[i]));
            }
        }
        System.out.println("* end of list *");
    }

    /**
     * Finds instance of profile
     * @param p profile to be found
     * @return returns null if profile is not found and Student at roster[i] otherwise
     */
    public Student getStudent(Profile p){
        int i = find(p);
        if(i == -1){
            return null;
        }
        else{
            return roster[i];
        }
    }
}
