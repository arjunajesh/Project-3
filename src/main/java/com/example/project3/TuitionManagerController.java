package com.example.project3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project1.*;

public class TuitionManagerController {
    private Roster roster = new Roster();
    private Enrollment enrollment = new Enrollment();
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

    public void addStudentButton(ActionEvent e){
        int ccKey = creditsIsValid(creditsCompleted.getText());

        if(validateProfileFields(firstName, lastName, dob)) {
            if (creditsCompleted.getText().isBlank()) {
                output.setText("Please enter credits completed");
            } else if (ccKey != 2) {
                if (ccKey == 0) {
                    output.setText("Credits completed must be a number");
                } else {
                    output.setText("Credits completed cannot be negative");
                }
            } else {

                int year = dob.getValue().getYear();
                int month = dob.getValue().getMonth().getValue();
                int day = dob.getValue().getDayOfMonth();
                Date d = new Date(year, month, day);

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
                output.setText(opText);
            }
        }
    }
    public void removeStudentButton(ActionEvent e){
        int year = dob.getValue().getYear();
        int month = dob.getValue().getMonth().getValue();
        int day = dob.getValue().getDayOfMonth();
        Date d = new Date(year, month, day);
        String opText = "";
        opText = roster.remove(new Profile(firstName.getText(), lastName.getText(), d));
        output.setText(opText);
    }

    public void changeMajorButton(ActionEvent e){
        int year = dob.getValue().getYear();
        int month = dob.getValue().getMonth().getValue();
        int day = dob.getValue().getDayOfMonth();
        Date d = new Date(year, month, day);
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
        }
        output.setText(opText);
    }
    public void enrollStudentButton(ActionEvent e){
        int ccKey = creditsIsValid(creditsEnrolled.getText());
        if(validateProfileFields(eFirstName, eLastName, eDOB)){
            if (creditsCompleted.getText().isBlank()) {
                output.setText("Please enter credits completed");
            } else if (ccKey != 2) {
                if (ccKey == 0) {
                    output.setText("Credits completed must be a number");
                } else {
                    output.setText("Credits completed cannot be negative");
                }
            }
            else {
                int year = dob.getValue().getYear();
                int month = dob.getValue().getMonth().getValue();
                int day = dob.getValue().getDayOfMonth();
                Date d = new Date(year, month, day);

                enrollment.add(new EnrollStudent(new Profile(eFirstName.getText(), eLastName.getText(), d)
                        , Integer.parseInt(creditsEnrolled.getText())), roster);
            }
        }
    }
    public void removeStudent(ActionEvent e){

    }
    private boolean validateProfileFields(TextField fname, TextField lname, DatePicker d ){
        if(fname.getText().isBlank()){
            output.setText("Please enter first name of student");
            return false;
        }
        else if(lname.getText().isBlank()){
            output.setText("Please enter last name of student");
            return false;
        }
        else if(d.getValue() == null){
            output.setText("Please enter date of birth");
            return false;
        }
        return true;
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
    private int creditsIsValid(String credits){
        try{
            if(Integer.parseInt(credits) < 0){
                return 1; // negative
            }
            else{
                return 2; // valid
            }

        }
        catch(NumberFormatException e){
            return 0; // not an integer
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