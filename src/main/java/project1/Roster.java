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

    private static final int MIN_SCHOLARSHIP = 0;
    private static final int MAX_SCHOLARSHIP = 10000;


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

    public void awardScholarShip(Profile p, int scholarshipAmount, Enrollment enrollment) throws Exception{
        Student s = getStudent(p);
        //verify student is in roster
        if(s == null){
            throw new Exception(p + " is not in the roster");
        }
        //verify student is a resident
        if(!s.isResident()){
            throw new Exception(s.getProfile().toString() + " (" + getTypeString(s) + ")" + " is not eligible for the scholarship.");
        }
        //verify student is not part-time
        if(!enrollment.getEnrolledStudent(s.getProfile()).isFulltime()){
            throw new Exception(p + " part time student is not eligible for the scholarship.");
        }
        //verify scholarship amount is valid
        if (scholarshipAmount <= MIN_SCHOLARSHIP || scholarshipAmount > MAX_SCHOLARSHIP){
            throw new Exception(scholarshipAmount + ": invalid amount.");
        }

        Resident r = (Resident) s;
        r.setScholarship(scholarshipAmount);

    }

    private String getTypeString(Student s){
        if(s instanceof Resident){
            return "Resident";
        }
        else if(s instanceof International){
            return "International student" + (((International) s).isStudyAbroad() ? "study abroad":"");
        }
        else if(s instanceof TriState){
            return "Tri-state";
        }
        else if(s instanceof NonResident){
            return "Non-Resident";
        }
        else return "";
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
        System.arraycopy(roster, 0, newRoster, 0, roster.length);
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
    public String change(Profile profile, String major){
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
            return "Major code invalid: " + major;
        }

        if(find(profile) == -1){
            return profile.toString() + " is not in the roster.";
        }
        else{
            int pivot = find(profile);
            roster[pivot].setMajor(m);
        }
        return profile.toString() + " major changed to " + major;
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
   public String sortByStanding() {
       StringBuilder sb = new StringBuilder();
       if (size == 0) {
           return "Student roster is empty!";
       } else {
           sort();
           sb.append("\n** Student roster sorted by standing **");
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Freshman")) {
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Junior")) {
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Senior")) {
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for (int i = 0; i < size; i++) {
               if (roster[i].getStanding().equals("Sophomore")) {
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           sb.append("\n* end of roster *");
       }
       return sb.toString();
   }

    /**
     * Prints out the roster sorted by school, major
     */
   public String sortBySchoolMajor(){
       StringBuilder sb = new StringBuilder();
       if (size == 0) {
           return "Student roster is empty!";
       } else {
           sort();
           sb.append("\n** Student roster sorted by school, major **");
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("BAIT")){
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("CS")){
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("MATH")){
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("SC&I")){
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("ITI")){
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           for(int i = 0; i < size; i++){
               if(roster[i].getMajor().getMajorName().equals("EE")){
                   sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
               }
           }
           sb.append("\n* end of roster *");
       }
       return sb.toString();
   }

    /**
     * Prints the roster
     */
    public String printRoster(){
        if(size == 0){
            return "Student roster is empty!";
        }
        sort();
        StringBuilder sb = new StringBuilder();
        sb.append("\n** Student roster sorted by last name, first name, DOB **");
        for(int i = 0; i < size; i++){
            if(this.roster[i] != null) {
                sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
            }
        }
        sb.append("\n* end of roster *");
        return sb.toString();
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
    public String printEligibleGraduates(){ //print eligible graduates
        StringBuilder sb = new StringBuilder();
        sb.append("\n** list of students eligible for graduation **");
        for(int i = 0; i < size; i++){
            Student s = roster[i];
            if(s.getCreditCompleted() >= CREDITS_REQUIRED_GRADUATION){
                sb.append("\n" + roster[i] + getStudentInfo(roster[i]));
            }
        }
        return sb.toString();
    }

    /**
     * Prints the students in a specified school
     * @param school
     */
    public String printSchool(String school){
        StringBuilder sb = new StringBuilder();
        if(!("RBS".equalsIgnoreCase(school)) &&
           !("SAS".equalsIgnoreCase(school)) &&
           !("SC&I".equalsIgnoreCase(school)) &&
           !("SOE".equalsIgnoreCase(school))){
            sb.append("\nSchool doesn't exist: " + school);
        }
        if(this.roster[0] == null){
            sb.append("\nStudent roster is empty!");
        }
        sort();
        sb.append("\n* Students in " + school + " *");
        for(int i = 0; i < size; i++){
            if((this.roster[i].getSchool()).equalsIgnoreCase(school) && (this.roster[i] != null)){
                sb.append("\n" + this.roster[i] + getStudentInfo(this.roster[i]));
            }
        }
        sb.append("\n* end of list *");
        return sb.toString();
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
