/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Peter Perri
 */
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;


public class ListEditorController {

    @FXML private TextField descriptionField;
    @FXML private CheckBox doneCheckBox;
    @FXML private TextField dueDateField;
    @FXML private Label infoLabel;
    @FXML private CheckBox showCompletedCheckbox;
    @FXML private CheckBox showIncompleteCheckbox;
    @FXML private TableColumn<Item, Boolean> doneView;
    @FXML private TableColumn<Item, String> dueDateView;
    @FXML private TableColumn<Item, String> taskView;
    @FXML private TableView<Item> listView;
    private int index = 0;
    Validator validator = new Validator();

    @FXML
    void initialize(){
        //initialize fxml function handles setting up the TableView and all of its TableColumns
        //this means that editing the tableview is not testable, since editing is handled by javafx
           //if the description entered is validated,
           //then set the selected item's description to the value the user put in the tableview
           //else don't update the description and refresh the tableview
           //if the dueDate entered is validated,
           //then set the selected item's dueDate to the value the user put in the tableview
           //else don't update the dueDate and refresh the tableview
           //don't have to handle validation of booleans, since by virtue of being a boolean
           //it will default to false if the user enters anything other than true or false
    }


    @FXML
    void addItemButtonClicked(ActionEvent event) {
        //call the createItem method, which reads & validates the two textfields and checkboxes to create items
    }

    @FXML
    void clearListButtonClicked(ActionEvent event) {
        //clear the static ArrayList of Items that the Item class has
        //clear the tableView
        //reset the index to 0
        //call the clear function, which clears the textfields and sets the "done" checkbox to unchecked
    }

    @FXML
    void exportButtonClicked(ActionEvent event){
        //create a new filechooser object
        //create a new file object based on where the user tells the filechooser they want to save their file
        //pass that filepath to the exportToDoList function, which handles writing the file
    }

    public void exportToDoList(File selectedFile){
        //for each item in the static arraylist todolist, write its description, dueDate, and completion
        //status to the file. separate each value with a comma, and put each item on its own line
        //if there's an issue with writing to the file, update the infolabel to tell the user there was an issue
    }

    @FXML
    void importButtonClicked(ActionEvent event) throws FileNotFoundException {
        //create a new fileChooser object
        //create a new file object and assign it to whatever file the user selected
        //call the importToDoList function wile passing it the file the user selected.
        //this function will handle populating the static ArrayList of Items
        //for each item in the static ArrayList of Items, display the item.
    }

    public void importToDoList(File selectedFile) throws FileNotFoundException {
        //create a FileReader based on the selected file
        //clear the toDoList to make sure it's empty before we import a list
        //while the file is not empty:
        //make an array of string values split by a comma
        //if that array's length is not 3, tell the user they imported an incompatible file
        //since that means the file has been tampered with, or
        // it did not originate from this application
        //otherwise, add a new item to the to do list based on the array of string values read from the file
        //if the file can't be found for whatever reason, update the infolabel to tell the user there was an issue

    }

    @FXML
    void removeItemButtonClicked(ActionEvent event) {
        //call the removeItemFromList function to remove an item from the toDoList
    }

    @FXML
    void showDoneChecked(ActionEvent event) {
        //make an observable list to hold the completed items
        //if the checkbox is selected:
        //set the other checkbox to not be selected (since we don't want them both to be selected)
        //clear the listView of items
        //call a function to populate the listView with completed items
        //refresh the listView

    }

    @FXML
    void showNotDoneChecked(ActionEvent event) {
        //make an observable list to hold the completed items
        //if the checkbox is selected:
        //set the other checkbox to not be selected (since we don't want them both to be selected)
        //clear the listView of items
        //call a function to populate the listView with incomplete items
        //refresh the listView
    }

    public void populateCompleteOrIncompleteList(boolean completed, ObservableList<Item> items) {
        //for each item in the toDoList, if that item's completion status is equal to
        //the passed in completion status, then add it to the observableList
    }

    private void uncheck(){
        //this function is to be called when the checkboxes to show all completed items
        //or all the incomplete items are unchecked
        //clear the list of all items
        //add all items in the toDoList to the tableView
    }

    private void clear(){
        //clear all fields where the user can enter text
    }

    private void displayItem(){
        //display the created item and add to the index
    }


    public void createItem(){
        //call the validateDuplicates function, which then calls addAndDisplayItem, which then finally calls addItemToList
    }

    public void addItemToList(String description, String dueDate, boolean isChecked){
        //add a new item to the static toDoList using the passed in description, dueDate, and boolean
    }

    private void addAndDisplayItem(String description, String dueDate, boolean isChecked){
        //if the dueDate is empty, add the item to the list and tell the user the item was added with no dueDate
        //if the dueDate is invalid, add the item to the list without a dueDate and tell the user the item was added
        //without a due date.
        //if the dueDate is present and valid, add the item to the list properly
    }

    private void validateDuplicates() {
        //if the descriptionField is valid:
        //if the toDoList isn't empty, loop through it and check to make sure the user isn't
        //adding an item with a duplicate description. if they are, don't add the item to the
        //to do list, and warn them that the item is already in the list.
        //if it's not a duplicate, add the item to the list
        //if the list is empty, then just add the item to the list without checking if it's a duplicate
        //since the list is empty
        //if the description field is invalid, then don't add the item to the list and warn the user that it's invalid
    }


    public void removeItemFromList() {
        //if the user has selected an item:
        //call the static removeFromList function in the Item class to remove it from the ArrayList
        //subtract from the index, which effectively counts how many items are in the list
        //delete it from the TableView once it's been removed from the underlying data structure
        //if the user hasn't selected an item then do nothing and tell them that they didn't select anything

    }
}
