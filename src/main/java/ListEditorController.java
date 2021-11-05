/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Peter Perri
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.BooleanStringConverter;
import java.io.*;


public class ListEditorController{

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
        taskView.setCellFactory(TextFieldTableCell.forTableColumn());
        taskView.setOnEditCommit(event -> {
            //if the description entered is validated,
            //then set the selected item's description to the value the user put in the tableview
            if(validator.validateDescription(event.getNewValue())){
                Item item = event.getRowValue();
                item.setDescription(event.getNewValue());
                infoLabel.setText("Task Description updated");
            }
            //else don't update the description and refresh the tableview
            else{
                infoLabel.setText("Invalid Task Description: Must be \nbetween 2 and 256 characters ");
                listView.refresh();
            }
        });
        dueDateView.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDateView.setOnEditCommit(event -> {
            //if the dueDate entered is validated,
            //then set the selected item's dueDate to the value the user put in the tableview
            if(validator.validateDueDate(event.getNewValue())){
                Item item = event.getRowValue();
                item.setDueDate(event.getNewValue());
                infoLabel.setText("Due Date successfully updated");
            }
            //else don't update the dueDate and refresh the tableview
            else{
                infoLabel.setText("Invalid Due Date: Must be in \nformat YYYY-MM-DD");
                listView.refresh();
            }
        });
        doneView.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        doneView.setOnEditCommit(event -> {
            //don't have to handle validation of booleans, since by virtue of being a boolean
            //it will default to false if the user enters anything other than true or false
            Item item = event.getRowValue();
            item.setCompleted(event.getNewValue());
            listView.refresh();
        });
    }


    @FXML
    void addItemButtonClicked(ActionEvent event) {
        //call the createItem method, which reads & validates the two textFields and checkboxes to create items
        createItem();
    }

    @FXML
    void clearListButtonClicked(ActionEvent event) {
        //clear the static ArrayList of Items that the Item class has
        Item.getToDoList().clear();
        //clear the tableView
        listView.getItems().clear();
        //reset the index to 0
        index = 0;
        //call the clear function, which clears the textFields and sets the "done" checkbox to unchecked
        clear();
    }

    @FXML
    void exportButtonClicked(ActionEvent event){
        //create a new fileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save your To Do List (.txt)");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //create a new file object based on where the user tells the fileChooser they want to save their file
        File selectedFile = fileChooser.showSaveDialog(listView.getScene().getWindow());
        //pass that filepath to the exportToDoList function, which handles writing the file
        exportToDoList(selectedFile);
    }

    public void exportToDoList(File selectedFile){
        if(this.infoLabel != null){
            infoLabel.setText("List saved");
        }
        try (PrintWriter writer = new PrintWriter(selectedFile)) {
            for(Item i : Item.getToDoList()){
                //for each item in the static arraylist todolist, write its description, dueDate, and completion
                //status to the file. separate each value with a comma, and put each item on its own line
                writer.println(i.getDescription() + "," + i.getDueDate() + "," + i.getCompleted());
            }
        } catch (FileNotFoundException e){
            //if there's an issue with writing to the file, update the infoLabel to tell the user there was an issue
            //we have to verify that it's not null so that this function (and the import function) can be tested.
            if(this.infoLabel != null){
                infoLabel.setText("File not found");
            }
        }
    }

    @FXML
    void importButtonClicked(ActionEvent event) throws FileNotFoundException {
        //create a new fileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import ToDoList File (.txt)");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //create a new file object and assign it to whatever file the user selected
        File selectedFile = fileChooser.showOpenDialog(listView.getScene().getWindow());
        //call the importToDoList function wile passing it the file the user selected.
        //this function will handle populating the static ArrayList of Items
        importToDoList(selectedFile);
        //for each item in the static ArrayList of Items, display the item.
        for(int i = 0; i < Item.getToDoList().size(); i++) {
            displayItem();
        }
    }

    public void importToDoList(File selectedFile) throws FileNotFoundException {
        //create a FileReader based on the selected file
        FileReader reader = new FileReader(selectedFile);
        //clear the toDoList to make sure it's empty before we import a list
        Item.getToDoList().clear();
        if(this.listView != null){
            listView.getItems().clear();
        }
        index = 0;

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            boolean bool;
            //while the file is not empty:
            while ((line = br.readLine()) != null) {
                //make an array of string values split by a comma
                String[] values = line.split(",");
                //if that array's length is not 3, tell the user they imported an incompatible file
                //since that means the file has been tampered with, or
                // it did not originate from this application
                if(values.length != 3){
                    if(this.infoLabel != null){
                        infoLabel.setText("Incompatible file");
                    }
                    break;
                }
                //otherwise, add a new item to the to do list based on the array of string values read from the file
                else{
                    bool = !values[2].equals("false");
                    Item.getToDoList().add(new Item(values[0], values[1], bool));
                }
            }
        } catch (IOException e) {
            //if the file can't be found for whatever reason, update the infoLabel to tell the user there was an issue
            if(this.infoLabel != null){
                infoLabel.setText("File not found");
            }
            e.printStackTrace();
        }
    }

    @FXML
    void removeItemButtonClicked(ActionEvent event) {
        //call the removeItemFromList function to remove an item from the toDoList
        removeItemFromList();
    }

    @FXML
    void showDoneChecked(ActionEvent event) {
        //make an observable list to hold the completed items
        ObservableList<Item> completedItems = FXCollections.observableArrayList();
        //if the checkbox is selected:
        if(showCompletedCheckbox.isSelected()){
            //set the other checkbox to not be selected (since we don't want them both to be selected)
            showIncompleteCheckbox.setSelected(false);
            //clear the listView of items
            listView.getItems().clear();
            //call a function to populate the listView with completed items
            populateCompleteOrIncompleteList(true, completedItems);
            //refresh the listView
            listView.refresh();
        }
        listView.setItems(completedItems);
        if(!showCompletedCheckbox.isSelected()){
            uncheck();
        }

    }

    @FXML
    void showNotDoneChecked(ActionEvent event) {
        //make an observable list to hold the completed items
        ObservableList<Item> incompleteItems = FXCollections.observableArrayList();
        //if the checkbox is selected:
        if(showIncompleteCheckbox.isSelected()){
            //set the other checkbox to not be selected (since we don't want them both to be selected)
            showCompletedCheckbox.setSelected(false);
            //clear the listView of items
            listView.getItems().clear();
            //call a function to populate the listView with incomplete items
            populateCompleteOrIncompleteList(false, incompleteItems);
            //refresh the listView
            listView.refresh();
        }
        listView.setItems(incompleteItems);
        if(!showIncompleteCheckbox.isSelected()){
            uncheck();
        }
    }

    public ObservableList<Item> populateCompleteOrIncompleteList(boolean completed, ObservableList<Item> items) {
        items.clear();
        //for each item in the toDoList, if that item's completion status is equal to
        //the passed in completion status, then add it to the observableList
        for (Item i : Item.getToDoList()) {
            if (completed == i.getCompleted()) {
                items.add(i);
            }
        }
        return items;
    }

    private void uncheck(){
        //this function is to be called when the checkboxes to show all completed items
        //or all the incomplete items are unchecked, fulfilling requirement 10. this requirement
        //cannot be tested since it only has to do with displaying GUI components

        //clear the list of all items
        listView.getItems().clear();
        //add all items in the toDoList to the tableView
        for(Item i : Item.getToDoList()){
            listView.getItems().add(Item.getToDoList().get(Item.getToDoList().indexOf(i)));
        }
        listView.refresh();
    }

    private void clear(){
        //clear all fields where the user can enter text
        descriptionField.clear();
        dueDateField.clear();
        doneCheckBox.setSelected(false);
    }

    private void displayItem(){
        //display the created item and add to the index
        taskView.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateView.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        doneView.setCellValueFactory(new PropertyValueFactory<>("completed"));
        listView.getItems().add(Item.getToDoList().get(index));
        index++;
    }


    public void createItem(){
        //call the validateDuplicates function, which then calls addAndDisplayItem, which then finally calls addItemToList
        validateDuplicates();
    }

    public void addItemToList(String description, String dueDate, boolean isChecked){
        //add a new item to the static toDoList using the passed in description, dueDate, and boolean
        Item.getToDoList().add(new Item(description, dueDate, isChecked));
    }

    private void addAndDisplayItem(String description, String dueDate, boolean isChecked){
        //if the dueDate is empty, add the item to the list and tell the user the item was added with no dueDate
        if(dueDate.equals("")){
            infoLabel.setText("Item added with no due date");
            addItemToList(description, dueDate, isChecked);
            displayItem();
        }
        //if the dueDate is invalid, add the item to the list without a dueDate and tell the user the item was added
        //without a due date.
        else if(!validator.validateDueDate(dueDate)){
            infoLabel.setText("Invalid Due Date: Must be in \nformat YYYY-MM-DD\nItem added with no due date");
            addItemToList(description, dueDate, isChecked);
            displayItem();
        }
        //if the dueDate is present and valid, add the item to the list properly
        else{
            infoLabel.setText("Item successfully added to list");
            addItemToList(description, dueDate, isChecked);
            displayItem();
        }
        clear();
    }

    private void validateDuplicates(){
        boolean duplicate = false;
        //if the descriptionField is valid:
        if(validator.validateDescription(descriptionField.getText())){
            //if the toDoList isn't empty, loop through it and check to make sure the user isn't
            //adding an item with a duplicate description. if they are, don't add the item to the
            //to do list, and warn them that the item is already in the list.
            if(!(Item.getToDoList().isEmpty())){
                for(Item item : Item.getToDoList()) {
                    if (item.getDescription().equals(descriptionField.getText())) {
                        infoLabel.setText("Item already in list");
                        duplicate = true;
                    }
                }
                //if it's not a duplicate, add the item to the list
                if(!duplicate){
                    addAndDisplayItem(descriptionField.getText(), dueDateField.getText(), doneCheckBox.isSelected());
                }
            }
            //if the list is empty, then just add the item to the list without checking if it's a duplicate
            //since the list is empty
            else {
                addAndDisplayItem(descriptionField.getText(), dueDateField.getText(), doneCheckBox.isSelected());
            }
        }
        //if the description field is invalid, then don't add the item to the list and warn the user that it's invalid
        else{
            infoLabel.setText("Description invalid\nMust be between 1 and 256 \ncharacters");
        }
    }

    public void removeItemFromList(){
        Item selectedItem = listView.getSelectionModel().getSelectedItem();
        //if the user has selected an item:
        if(selectedItem != null){
            //call the static removeFromList function in the Item class to remove it from the ArrayList
            Item.removeFromList(selectedItem);
            //subtract from the index, which effectively counts how many items are in the list
            index--;
            //delete it from the TableView once it's been removed from the underlying data structure
            listView.getItems().remove(selectedItem);
        }
        else{
            //if the user hasn't selected an item then do nothing and tell them that they didn't select anything
            infoLabel.setText("No task selected");
        }

    }
}
