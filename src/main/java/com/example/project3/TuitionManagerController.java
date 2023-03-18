package com.example.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project1.*;

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

        //construct major
        Major m = getMajor();
        String opText = "";
        if (residentRadio.isSelected()) {
            opText = roster.addStudent(new Resident(firstName.getText(), lastName.getText(),
                    d, m, Integer.parseInt(creditsCompleted.getText())));
        } else { //non-resident chosen
            if (tristateRadio.isSelected()) {
                String state = nyRadio.isSelected() ? "NY" : "CT";
                opText = roster.addStudent(new TriState(firstName.getText(), lastName.getText(), d, m, Integer.parseInt(creditsCompleted.getText()), state));
            } else { //international chosen
                boolean isStudyAbroad = studyAbroadCheckBox.isSelected();
                opText = roster.addStudent(new International(firstName.getText(), lastName.getText(), d, m, Integer.parseInt(creditsCompleted.getText()), isStudyAbroad));
            }
        }
        output.appendText("\n"  + opText);


    }
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
            output.appendText("Scholarship Amount" + ex.getMessage());
        }
        try{
            roster.awardScholarShip(new Profile(sFirstName.getText(), sLastName.getText(), d),
                    Integer.parseInt(scholarshipAmount.getText()), enrollment);
        }
        catch(Exception ex){
            output.appendText(ex.getMessage());
        }

    }
    public void printRosterButton(ActionEvent e){
        output.appendText(roster.printRoster());
    }
    public void printRosterStandingButton(ActionEvent e){
        output.appendText(roster.sortByStanding());
    }
    public void printRosterSchoolMajorButton(ActionEvent e){
        output.appendText(roster.sortBySchoolMajor());
    }
    public void printEligibleGradsButton(ActionEvent e){
        output.appendText(roster.printEligibleGraduates());
    }
    public void printRBSButton(ActionEvent e){
        output.appendText(roster.printSchool("RBS"));
    }
    public void printSASButton(ActionEvent e){
        output.appendText(roster.printSchool("SAS"));
    }
    public void printSCIButton(ActionEvent e){
        output.appendText(roster.printSchool("SC&I"));
    }
    public void printSOEButton(ActionEvent e){
        output.appendText(roster.printSchool("SOE"));
    }
    public void printCurrentEnrollmentButton(ActionEvent e){
        output.appendText(enrollment.printEnrollment());
    }
    public void printTuitionDueButton(ActionEvent e){
        output.appendText(enrollment.printTuition(roster));
    }
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

        int year = dob.getValue().getYear();
        int month = dob.getValue().getMonth().getValue();
        int day = dob.getValue().getDayOfMonth();
        Date date = new Date(year, month, day);

        return date;
    }
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
    public void enableNonResidentSettings(ActionEvent e){
        //enable non resident settings
        tristateRadio.setDisable(false);
        internationalRadio.setDisable(false);
        nyRadio.setDisable(false);
        ctRadio.setDisable(false);

        //set default selections
        nyRadio.setSelected(true);
        tristateRadio.setSelected(true);
    }
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
    }
    public void internationalSettings(ActionEvent e){
        //disable tri-state settings
        nyRadio.setDisable(true);
        ctRadio.setDisable(true);

        //enable international settings
        studyAbroadCheckBox.setDisable(false);

        //clear tri-state selections
        nyRadio.setSelected(false);
        ctRadio.setSelected(false);
    }
}