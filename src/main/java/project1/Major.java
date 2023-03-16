package project1;

/**
 * Class for Major Object
 * @author Arjun Ajesh, Nathan Roh
 */
public enum Major {
    BAIT("BAIT", "33:136", "RBS"),
    CS("CS", "01:198", "SAS"),
    MATH("MATH", "01:640", "SAS"),
    ITI("ITI", "04:547", "SC&I"),
    EE("EE", "14:332", "SOE");

    private String majorName;
    private String majorCode;
    private String school;

    /**
     * Constructor for Major Class
     * @param name name of specified major
     * @param code code of specified major
     * @param school school of specified major
     */
    Major(String name, String code, String school) {
        this.majorName = name;
        this.majorCode = code;
        this.school = school;

    }

    /**
     * @return school of specified student
     */
    public String getSchool(){
        return this.school;
    }

    /**
     * @return string consisting of major code, major name, and school
     */
    @Override
    public String toString(){
        return "(" + majorCode + " " + majorName + " " + school + ")";
    }

    /**
     * @return major name of specified student
     */
    public String getMajorName(){
        return majorName;
    } //comm
}