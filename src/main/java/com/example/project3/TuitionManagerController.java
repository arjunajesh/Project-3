package com.example.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Class for TuitionManagerController Object
 * @author Arjun Ajesh, Nathan Roh
 */
public class TuitionManagerController {
    private final Roster roster = new Roster();
    private final Enrollment enrollment = new Enrollment();
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private DatePicker dob;
    @FXML
    private TextArea output;
    @FXML
    private TextField creditsCompleted;
    @FXML
    private RadioButton tristateRadio;
    @FXML
    private RadioButton internationalRadio;
    @FXML
    private RadioButton nyRadio;
    @FXML
    private RadioButton ctRadio;
    @FXML
    private CheckBox studyAbroadCheckBox;
    @FXML
    private RadioButton residentRadio;
    @FXML
    private RadioButton baitRadio;
    @FXML
    private RadioButton csRadio;
    @FXML
    private RadioButton eeRadio;
    @FXML
    private RadioButton itiRadio;
    @FXML
    private RadioButton mathRadio;
    @FXML
    private RadioButton nonResidentRadio;
    @FXML
    private TextField creditsEnrolled;
    @FXML
    private TextField eFirstName;
    @FXML
    private TextField eLastName;
    @FXML
    private DatePicker eDOB;
    @FXML
    private TextField sFirstName;
    @FXML
    private TextField sLastName;
    @FXML
    private DatePicker sDOB;
    @FXML
    private TextField scholarshipAmount;

    /**
     * Handles instances where add student button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void addStudentButton(ActionEvent e){
        Date d;
        try{
            d = validateProfileFields(firstName, lastName, dob);
        }
        catch(Exception ex){
           output.appendText("\n" + ex.getMessage());
           return;
        }
        try{
            validateIntegerField(creditsCompleted.getText());
        }
        catch(Exception ex){
            output.appendText("\nCredits Completed" + ex.getMessage());
            return;
        }

        // construct major
        Major m = getMajor();
        String opText = "";
        if (residentRadio.isSelected()) {
            opText = roster.addStudent(new Resident(firstName.getText(), lastName.getText(),
                    d, m, Integer.parseInt(creditsCompleted.getText())));
        } else { // non-resident chosen
            if (tristateRadio.isSelected()) {
                String state = nyRadio.isSelected() ? "NY" : "CT";
                opText = roster.addStudent(new TriState(firstName.getText(), lastName.getText(), d, m, Integer.parseInt(creditsCompleted.getText()), state));
            } else if(internationalRadio.isSelected()){ //international chosen
                boolean isStudyAbroad = studyAbroadCheckBox.isSelected();
                opText = roster.addStudent(new International(firstName.getText(), lastName.getText(), d, m, Integer.parseInt(creditsCompleted.getText()), isStudyAbroad));
            }
            else{ // non-resident
                opText = roster.addStudent(new NonResident(firstName.getText(), lastName.getText(), d, m, Integer.parseInt(creditsCompleted.getText())));
            }
        }
        output.appendText("\n"  + opText);


    }

    /**
     * Handles instances where remove student button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void removeStudentButton(ActionEvent e){
        Date d;
        try{
            d = validateProfileFields(firstName, lastName, dob);
        }
        catch(Exception ex){
            output.appendText("\n" + ex.getMessage());
            return;
        }

        String opText = "";
        opText = roster.remove(new Profile(firstName.getText(), lastName.getText(), d));
        output.appendText("\n" + opText);
    }

    /**
     * Handles instances where change major button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void changeMajorButton(ActionEvent e){
        Date d;
        try{
            d = validateProfileFields(firstName, lastName, dob);
        }
        catch(Exception ex){
            output.appendText("\n" + ex.getMessage());
            return;
        }
        String opText = "";
        if(baitRadio.isSelected()) {
            opText = roster.change(new Profile(firstName.getText(), lastName.getText(), d), "BAIT");
        }
        if(csRadio.isSelected()) {
            opText = roster.change(new Profile(firstName.getText(), lastName.getText(), d), "CS");
        }
        if(eeRadio.isSelected()) {
            opText = roster.change(new Profile(firstName.getText(), lastName.getText(), d), "EE");
        }
        if(itiRadio.isSelected()) {
            opText = roster.change(new Profile(firstName.getText(), lastName.getText(), d), "ITI");
        }
        if(mathRadio.isSelected()) {
            opText = roster.change(new Profile(firstName.getText(), lastName.getText(), d), "MATH");
        } /* Use getMajor() method to save above lines of code. roster.change() will have to be reconfigured to
             accept type Major*/
        output.appendText("\n" + opText);

    }

    /**
     * Handles instances where load from file button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void loadFromFileButton(ActionEvent e){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        try{
            output.appendText(roster.loadFile(selectedFile));
        }
        catch(Exception ex){
            output.appendText(ex.getMessage());
        }

    }

    /**
     * Handles instances where enroll student button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void enrollStudentButton(ActionEvent e){
        Date d;
        try{
            d = validateProfileFields(eFirstName, eLastName, eDOB);
        }
        catch(Exception ex){
            output.appendText("\n" + ex.getMessage());
            return;
        }
        try{
            validateIntegerField(creditsEnrolled.getText());
        }
        catch(Exception ex){
            output.appendText("\nCredits Enrolled" + ex.getMessage());
            return;
        }

        String opText = enrollment.add(new EnrollStudent(new Profile(eFirstName.getText(), eLastName.getText(), d)
                , Integer.parseInt(creditsEnrolled.getText())), roster);
        output.appendText("\n" + opText);

    }

    /**
     * Handles instances where drop student from enrollment button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void dropEnrollButton(ActionEvent e){
        Date d;
        try{
            d = validateProfileFields(eFirstName, eLastName, eDOB);
        }
        catch(Exception ex){
            output.appendText("\n" + ex.getMessage());
            return;
        }
        String opText = enrollment.remove(new EnrollStudent(new Profile(eFirstName.getText(), eLastName.getText()
                ,d), 0));
        output.appendText("\n" + opText);

    }

    /**
     * Handles instances where update scholarship button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void scholarshipButton(ActionEvent e){
        Date d;
        try{
            d = validateProfileFields(sFirstName, sLastName, sDOB);
        }
        catch(Exception ex){
            output.appendText("\n" + ex.getMessage());
            return;
        }
        try{
            validateIntegerField(scholarshipAmount.getText());
        }
        catch(Exception ex){
            output.appendText("\nScholarship Amount" + ex.getMessage());
            return;
        }
        try{
            output.appendText("\n"+roster.awardScholarShip(new Profile(sFirstName.getText(), sLastName.getText(), d),
                    Integer.parseInt(scholarshipAmount.getText()), enrollment));
        }
        catch(Exception ex){
            output.appendText("\n"+ex.getMessage());
        }

    }

    /**
     * Handles instances where print roster button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printRosterButton(ActionEvent e){
        output.appendText(roster.printRoster());
    }

    /**
     * Handles instances where print roster by standing button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printRosterStandingButton(ActionEvent e){
        output.appendText(roster.sortByStanding());
    }

    /**
     * Handles instances where print roster by school & major button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printRosterSchoolMajorButton(ActionEvent e){
        output.appendText(roster.sortBySchoolMajor());
    }

    /**
     * Handles instances where print eligible graduates button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printEligibleGradsButton(ActionEvent e){
        try {
            enrollment.endSemester(roster);
        }
        catch(Exception ex){
            output.appendText("\n" + ex.getMessage());
            return;
        }
        output.appendText("\nCredit completed has been updated.");
        output.appendText(roster.printEligibleGraduates());
    }

    /**
     * Handles instances where print all RBS students button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printRBSButton(ActionEvent e){
        output.appendText(roster.printSchool("RBS"));
    }

    /**
     * Handles instances where print all SAS students button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printSASButton(ActionEvent e){
        output.appendText(roster.printSchool("SAS"));
    }

    /**
     * Handles instances where print all SC&I students button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printSCIButton(ActionEvent e){
        output.appendText(roster.printSchool("SC&I"));
    }

    /**
     * Handles instances where print all SOE students button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printSOEButton(ActionEvent e){
        output.appendText(roster.printSchool("SOE"));
    }

    /**
     * Handles instances where print current enrollment button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printCurrentEnrollmentButton(ActionEvent e){
        output.appendText(enrollment.printEnrollment());
    }

    /**
     * Handles instances where print tuition due button is pressed
     * @param e allows access to properties of ActionEvent
     */
    public void printTuitionDueButton(ActionEvent e){
        output.appendText(enrollment.printTuition(roster));
    }

    /**
     * Verifies that profile fields (fname, lname, dob) are filled in and valid
     * @param fname inputted first name
     * @param lname inputted last name
     * @param DOB chosen date of birth
     * @return returns the properly formatted date of the datepicker input
     */
    private Date validateProfileFields(TextField fname, TextField lname, DatePicker DOB) throws Exception{
        if(fname.getText().isBlank()){
            throw new Exception("Please enter first name");
        }
        if(lname.getText().isBlank()){
            throw new Exception("Please enter last name");
        }
        if(DOB.getValue() == null){
            throw new Exception("Please enter date of birth");
        }

        int year = DOB.getValue().getYear();
        int month = DOB.getValue().getMonth().getValue();
        int day = DOB.getValue().getDayOfMonth();
        Date date = new Date(year, month, day);

        return date;
    }

    /**
     * Identifies which major button the user presses
     * @return returns the major respective to the button chosen
     */
    private Major getMajor(){
        if(baitRadio.isSelected()){
            return Major.BAIT;
        }
        else if(csRadio.isSelected()){
            return Major.CS;
        }
        else if(eeRadio.isSelected()){
            return Major.EE;
        }
        else if(itiRadio.isSelected()){
            return Major.ITI;
        }
        else{
            return Major.MATH;
        }
    }

    /**
     * Verifies that the inputted credits is an appropriate input
     * @param credits inputted string that represents the number of credits
     */
    private void validateIntegerField(String credits) throws Exception{
        if(credits.isBlank()){
            throw new Exception(" cannot be empty");
        }
        try{
            int c = Integer.parseInt(credits);
            if(c < 0){
                throw new Exception(" cannot be negative");
            }
        }
        catch(NumberFormatException e){
            throw new Exception(" must be a number");
        }
    }

    /**
     * Enables the appropriate follow-up options for user upon choosing NonResident student option
     * @param e allows access to properties of ActionEvent
     */
    public void enableNonResidentSettings(ActionEvent e){
        //enable non resident settings
        tristateRadio.setDisable(false);
        internationalRadio.setDisable(false);
        nyRadio.setDisable(true);
        ctRadio.setDisable(true);
        tristateRadio.setSelected(false);
        internationalRadio.setSelected(false);
        nyRadio.setSelected(false);
        ctRadio.setSelected(false);
        studyAbroadCheckBox.setSelected(false);
    }

    /**
     * Enables the appropriate follow-up options for user upon choosing Resident student option
     * @param e allows access to properties of ActionEvent
     */
    public void disableNonResidentSettings(ActionEvent e){
        //disable all non resident settings
        tristateRadio.setDisable(true);
        internationalRadio.setDisable(true);
        nyRadio.setDisable(true);
        ctRadio.setDisable(true);
        studyAbroadCheckBox.setDisable(true);

        //clear non-resident selections
        nyRadio.setSelected(false);
        ctRadio.setSelected(false);
        studyAbroadCheckBox.setSelected(false);
        tristateRadio.setSelected(false);
        internationalRadio.setSelected(false);
    }

    /**
     * Enables the appropriate follow-up options for user upon choosing Tristate student option
     * @param e allows access to properties of ActionEvent
     */
    public void tristateSettings(ActionEvent e){
        //enable tri-state settings
        nyRadio.setDisable(false);
        ctRadio.setDisable(false);

        //disable international settings
        studyAbroadCheckBox.setDisable(true);

        //clear international selections
        studyAbroadCheckBox.setSelected(false);

        //set default state selection
        nyRadio.setSelected(true);
        nonResidentRadio.setSelected(false);
    }

    /**
     * Enables the appropriate follow-up options for user upon choosing International student option
     * @param e allows access to properties of ActionEvent
     */
    public void internationalSettings(ActionEvent e){
        //disable tri-state settings
        nyRadio.setDisable(true);
        ctRadio.setDisable(true);

        //enable international settings
        studyAbroadCheckBox.setDisable(false);

        //clear tri-state selections
        nyRadio.setSelected(false);
        ctRadio.setSelected(false);
        nonResidentRadio.setSelected(false);
    }
}