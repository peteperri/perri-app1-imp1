/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Peter Perri
 */

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String description;
    private String dueDate;
    private boolean completed;
    private final Validator validator = new Validator();

    //static list to hold all instances of Items
    private static final List<Item> toDoList = new ArrayList<>();

    //item constructor
    public Item(String description, String dueDate, boolean completed){
            this.description = description;
            this.completed = completed;
            if(validator.validateDueDate(dueDate)){
                //dueDate is only initialized to a date if it is valid, as checked by the validator
                this.dueDate = dueDate;
            }
            //if the dueDate is empty, or invalid, then the dueDate is entered as "N/A"
            else if(dueDate.equals("") || !validator.validateDueDate(dueDate)){
                this.dueDate = "N/A";
            }
    }

    public String toString() {
        //str will be a String that contains all the information about an item
        //only for debugging and test purposes, shouldn't be used in final app
        return "Description: " + description + "\nDueDate: " + dueDate + "\nCompleted: " + completed;
    }

    public void setDescription(String description){
        //set the description, but only after checking if it is valid. otherwise, do nothing
        if(validator.validateDescription((description))){
            this.description = description;
        }
    }
    public void setDueDate(String dueDate){
        //set the dueDate, but only after checking if it is valid. otherwise, do nothing
        if(validator.validateDueDate(dueDate)){
            this.dueDate = dueDate;
        }
    }
    public void setCompleted(boolean completed){
        //used to update completion status
        this.completed = completed;
    }

    public String getDescription(){
        //used to get the description
        return description;
    }

    public String getDueDate(){
        return dueDate;
    }

    public boolean getCompleted(){
        return completed;
    }

    public static List<Item> getToDoList(){
        return toDoList;
    }

    //test
    public static void removeFromList(Item item){
        //remove the passed item from the toDoList
        toDoList.remove(item);
    }


}