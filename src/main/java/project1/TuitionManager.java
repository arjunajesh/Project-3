package project1; /**
 * Class for RosterManager Object
 * @author Arjun Ajesh, Nathan Roh
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TuitionManager {
    static Roster roster;
    static Enrollment enrollment;
    private static final int MIN_SCHOLARSHIP = 0;
    private static final int MAX_SCHOLARSHIP = 10000;

    /**
     * Constructor for TuitionManager Class
     */
    public TuitionManager(){
        this.roster = new Roster();
        this.enrollment = new Enrollment();
    }

    /**
     * Runs the tuition manager
     */
    public static void run(){
        System.out.println("Tuition Manager running...");
        boolean running = true;
        Scanner input = new Scanner(System.in);
        while(running){
            String[] command = input.nextLine().split(" +"); // splits by single space, need to change it to so that it splits by white space
            if(command[0].equals("Q")){
                System.out.println("Tuition Manager terminated.");
                running = false;
            }
            else{
                handleCommand(command);
            }
        }
        input.close();
    }

    /**
     * Takes the command and processes which command is being called
     * @param command String array, where first value will be the command, and the rest will be the parameters for the command
     */
    private static void handleCommand(String[] command){
        switch(command[0]){
            case "AR", "AN", "AT", "AI":
                if(command.length==1) System.out.println("Missing data in line command.");
                else //roster.add(command);
                break;
            case "E": addEnrollment(command);
                break;
            case "R": roster.remove(new Profile(command[1], command[2], new Date(command[3])));
                break;
            case "P": roster.sortByProfile();
                break;
            case "PS": roster.sortByStanding();
                break;
            case "PC": roster.sortBySchoolMajor();
                break;
            case "L": roster.printSchool(command[1]);
                break;
            case "C": roster.change(new Profile(command[1], command[2], new Date(command[3])), command[4]);
                break;
            case "D": enrollment.remove(new EnrollStudent(new Profile(command[1], command[2], new Date(command[3])), 0));
                break;
            case "PE": enrollment.printEnrollment();
                break;
            case "S": awardScholarship(command);
                break;
            case "PT": enrollment.printTuition(roster);
                break;
            case "LS": loadFile(command);
                break;
            case "SE": enrollment.endSemester(roster);
                break;
            case "":
                break;
            default: System.out.println(command[0] + " is an invalid command!");
        }
    }

    /**
     *
     * @param command
     * @return
     */
    private static boolean awardScholarship(String[] command){
        try{
            Profile p = new Profile(command[1], command[2], new Date(command[3]));
            Student s = roster.getStudent(p);
            //first check if student is not in the roster
            if(s == null){
                System.out.println(command[1] + " " + command[2] + " " + new Date(command[3]) + " is not in the roster.");
                return false;
            }
            //verify that student is a resident
            if(!s.isResident()){
                System.out.println(s.getProfile().toString() + " (" + getTypeString(s) + ")" + " is not eligible for the scholarship.");
                return false;
            }
            //verify student is not part-time
            if(!enrollment.getEnrolledStudent(s.getProfile()).isFulltime()){
                System.out.println(s.getProfile() + " part time student is not eligible for the scholarship.");
                return false;
            }
            //verify scholarship amount is valid
            int scholarshipAmount = Integer.parseInt(command[4]);
            if (scholarshipAmount <= MIN_SCHOLARSHIP || scholarshipAmount > MAX_SCHOLARSHIP){
                System.out.println(scholarshipAmount + ": invalid amount.");
                return false;
            }
            //award scholarship
            Resident r = (Resident) s;
            r.setScholarship(scholarshipAmount);
            System.out.println(s.getProfile() + ": scholarship amount updated.");
            return true;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Missing data in line command.");
            return false;
        }
        catch(NumberFormatException e){
            System.out.println("Amount is not an integer.");
            return false;
        }
    }
    private static String getTypeString(Student s){
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
    private static boolean addEnrollment(String[] command){
        try{
            Profile p = new Profile(command[1], command[2], new Date(command[3]));
            Student s = roster.getStudent(p);
            //first check if student is not in the roster
            if(s == null){
                System.out.println("Cannot enroll: " + command[1] + " " + command[2] + " " + new Date(command[3]) + " is not in the roster.");
                return false;
            }

            //check if number of credits is valid
            int numCredits = Integer.parseInt(command[4]);
            if(!s.isValid(numCredits)){
                System.out.println("(" + getTypeString(s) + ") " + numCredits + ": invalid credit hours.");
                return false;
            }

            //add to enrollment
            enrollment.add(new EnrollStudent(s.getProfile(), numCredits));
            System.out.println(s.getProfile() + " enrolled " + numCredits + " credits");
            return true;

        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Missing data in line command.");
            return false;
        }
        catch(NumberFormatException e){
            System.out.println("Credits enrolled is not an integer.");
            return false;
        }
        catch(Exception e){
            System.out.println("Error occurred "  + e.getMessage());
            return false;
        }
    }


    private static void loadFile(String[] command) {
        File file = new File(command[1]);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while(scan.hasNextLine()) {
            String str = scan.nextLine();
            String[] token = str.split(",");
            Major m = roster.validateBasicCredentials(token[3], token[4], token[5]);
            switch(token[0]){
                case "R": roster.addStudent(new Resident(token[1], token[2], new Date(token[3]), m, Integer.parseInt(token[5])));
                break;
                case "N": roster.addStudent(new NonResident(token[1], token[2], new Date(token[3]), m, Integer.parseInt(token[5])));
                break;
                case "T": roster.addStudent(new TriState(token[1], token[2], new Date(token[3]), m, Integer.parseInt(token[5]), token[6]));
                break;
                case "I":
                    boolean isStudyAbroad = false;
                    if(token[6].equalsIgnoreCase("true")) {
                        isStudyAbroad = true;
                    }
                    roster.addStudent(new International(token[1], token[2], new Date(token[3]), m, Integer.parseInt(token[5]), isStudyAbroad));
            }
        }
        System.out.println("Students loaded to the roster.");


    }


}


