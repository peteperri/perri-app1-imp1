/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Peter Perri
 */

//all functions in this class are implicitly tested in ItemTest.java
public class Validator {

    //validate an item's due date fits the format and is a valid date
    public boolean validateDueDate(String dueDate) {
        return validateDueDateAlphabetic(dueDate);
    }

    //validate an item's description to verify that descriptions are between 1 and 256 chars
    public boolean validateDescription(String description){
        return description.length() > 1 && description.length() < 256;
    }

    //part 1 of validating a due date
    private boolean validateDueDateAlphabetic(String dueDate){
        //String validation method to verify that dueDates are formatted as YYYY-MM-DD
        char[] charArray = dueDate.toCharArray();

        //if it's not 10 chars long, it's invalid
        if(dueDate.length() != 10){
            //dueDate is not 10 chars.
            return false;
        }

        //if there's anything other than a number (or a dash at the right indexes) it's invalid
        for(int i = 0; i < 10; i++){
            if(Character.isAlphabetic(charArray[i])){
                //dueDate contains letters
                return false;
            }
            if((i == 4 || i == 7) && charArray[i] != '-'){
                //dueDate doesn't have a dash as its character at index 4 or 7"
                return false;
            }
            if(!(Character.isDigit(charArray[i]))) {
                if (i == 4 || i == 7) {
                    continue;
                }
                //dueDate has something other than a digit at the wrong index
                return false;
            }
        }
        return validateDueDateNumeric(dueDate);
    }

    //part 2 of validating a due date, once it's confirmed that integers can be parsed from it
    private boolean validateDueDateNumeric(String dueDate) {
        //if the month is greater than 12, or the day is greater than 31, then the date is invalid.
        int month = Integer.parseInt(dueDate.substring(5, 7));
        int day = Integer.parseInt(dueDate.substring(8));
        int year = Integer.parseInt(dueDate.substring(0, 4));
        if(month > 12 || day > 31){
            //dueDate's month is greater than 12 or its day is greater than 31.
            return false;
        }
        if((month == 4 || month == 5 || month == 6 || month == 9 || month == 11) && day > 30){
            //dueDate has an invalid day
            return false;
        }
        //if we return false, dueDate has a leap year error. if true, dueDate is valid.
        return (year % 4 != 0 || month != 2 || day <= 29) && (year % 4 == 0 || month != 2 || day <= 28);
    }
}
