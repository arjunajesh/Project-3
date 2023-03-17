package project1;
/**
 * Class for Date Object
 * @author Arjun Ajesh, Nathan Roh
 */
import java.util.Calendar;
public class Date implements Comparable<Date>{
    private final int year;
    private final int month;
    private final int day;
    private final long MILLISECONDS_PER_YEAR = 365L * 24 * 60 * 60 * 1000;

    public static void main(String[] args){
        final String PASSED = "PASSED";
        final String FAILED = "FAILED";

        Date d = new Date("2/29/2003"); // testing leap year
        System.out.println("Testing isValid: " + d);
        System.out.println(!d.isValid() ? PASSED:FAILED);

        d = new Date("4/31/2003"); // invalid day on month
        System.out.println("Testing isValid: " + d);
        System.out.println(!d.isValid() ? PASSED:FAILED);

        d = new Date("13/31/2003"); // invalid month
        System.out.println("Testing isValid: " + d);
        System.out.println(!d.isValid() ? PASSED:FAILED);

        d = new Date("-1/31/2003"); // invalid month (negative)
        System.out.println("Testing isValid: " + d);
        System.out.println(!d.isValid() ? PASSED:FAILED);

        d = new Date("3/32/2003"); // invalid day of month
        System.out.println("Testing isValid: " + d);
        System.out.println(!d.isValid() ? PASSED:FAILED);

        d = new Date("3/31/2003"); // valid date
        System.out.println("Testing isValid: " + d);
        System.out.println(d.isValid() ? PASSED:FAILED);

        d = new Date("2/29/2004"); // valid leap year date
        System.out.println("Testing isValid: " + d);
        System.out.println(d.isValid() ? PASSED:FAILED);
    }

    /**
     * Empty Constructor for Date Class, creates Date object for today's date
     */
    public Date (){
        this.month = Calendar.getInstance().MONTH;
        this.year = Calendar.getInstance().YEAR;
        this.day = Calendar.getInstance().DAY_OF_MONTH;
    }

    /**
     * Constructor for Date
     * @param date
     */
    public Date(String date){
        String[] d = date.split("/");
        this.month = Integer.parseInt(d[0]);
        this.day = Integer.parseInt(d[1]);
        this.year = Integer.parseInt(d[2]);
    }

    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Makes sure date is a valid date
     * @return true if valid date, false otherwise
     */
    public boolean isValid(){
        try{
            Calendar c = Calendar.getInstance();
            c.setLenient(false);
            c.set(year, month - 1, day);
            c.get(Calendar.DATE);

            return isOver16(c);
        }catch(IllegalArgumentException e){
            System.out.println("DOB invalid: " + this + " not a valid calendar date!");
            return false;
        }
    }

    /**
     * Checks if student is atleast 16 years old
     * @param dob date of birth of student
     * @return true if student is 16 or over, false otherwise
     */
    private boolean isOver16(Calendar dob){

        Calendar today = Calendar.getInstance();
        long diffMillis = today.getTimeInMillis() - dob.getTimeInMillis();
        long numYears = diffMillis / MILLISECONDS_PER_YEAR;
        if(numYears >= 16){
            return true;
        }
        else{
            System.out.println("DOB invalid: " + this + " younger than 16 years old.");
            return false;
        }
         // checks that the student is 16 or older, also makes sure the date is not in the future or today's date as per requirements of project
    }

    /**
     * Compares two dates
     * @param o the object to be compared.
     * @return returns 0 if dates are equal, integer greater than 0 if date is greater, less than 0 if date is less
     */
    @Override
    public int compareTo(Date o) {
        long diff = getMilliSeconds() - o.getMilliSeconds();
        if (diff > 0){
            return 1;
        }
        else if (diff < 0){
            return -1;
        }
        else{
            return 0;
        }

    }

    /**
     * Converts date into milliseconds since 1970
     * @return (long) number of milliseconds
     */
    private long getMilliSeconds(){
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, 0, 0, 0);
        return c.getTimeInMillis();
    }

    /**
     * Compares object to year, month, and date
     * @param o
     * @return true if date matches, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Date other)){
            return false;
        }
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }

    /**
     * @return string of date in format MM/DD/YYYY
     */
    @Override
    public String toString(){
        return month + "/" + day + "/" + year;
    }
}
