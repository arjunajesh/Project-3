package com.example.project3;

/**
 * Class for Profile Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class Profile implements Comparable<Profile>{
    private final String lname;
    private final String fname;
    private final Date dob;

    /**
     * Constructor for Profile Class
     * @param fname first name of student
     * @param lname last name of student
     * @param dob date of birth of student
     */
    public Profile(String fname, String lname, Date dob){
        this.lname = lname;
        this.fname = fname;
        this.dob = dob;
    }
    /**
     * Compares profile starting from last name, first name, then DOB
     * @param o the object to be compared
     * @return integer greater than 0 if the instance being compared is greater, integer less than 0 if instance is less,
     * or 0 if the instance is equal
     */
    @Override
    public int compareTo(Profile o) {
        if(this.lname.compareToIgnoreCase(o.lname) != 0){
            return this.lname.compareToIgnoreCase(o.lname);
        }
        else{
            if(this.fname.compareToIgnoreCase(o.fname)!=0){
                return this.fname.compareToIgnoreCase(o.fname);
            }
            else{
                return dob.compareTo(o.getDob());
            }
        }
    }
    /**
     * @return student's date of birth
     */
    public Date getDob(){
        return dob;
    }
    /**
     * @param o object to be compared to
     * @return true if profiles are equal, false if not
     */
    @Override
    public boolean equals(Object o){

        if(!(o instanceof Profile other)){
            return false;
        }

        return this.lname.equalsIgnoreCase(other.lname) && this.fname.equalsIgnoreCase(other.fname) && this.dob.equals(other.dob);
    }
    /**
     * @return  student's first name, last name, and date of birth respectively
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }
}
