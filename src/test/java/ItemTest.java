/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Peter Perri
 */
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testItemCount(){
        //test for requirement 1's sub requirement: the list SHALL have the capacity to store at least
        //100 unique items
        for (int i = 0; i < 150; i++){
            String desc = "item" + i;
            Item.getToDoList().add(new Item(desc, "", false));
        }
        assertTrue(Item.getToDoList().size() > 100);
    }

    @Test
    void testToString() {
        Item.getToDoList().clear();
        Item testItem = new Item("Go to the store", "2021-07-24", false);
        String actual = testItem.toString();
        String expected = """
                Description: Go to the store
                DueDate: 2021-07-24
                Completed: false""";
        assertEquals(expected, actual);
    }

    @Test
    void setDescription() {
        Item.getToDoList().clear();
        //tests for requirement number 2: an item SHALL have a description
        Item testItem = new Item("Go to the store", "2021-07-24", false);

        //test for requirement 2's sub-requirement: a description SHALL be between 1 and 256 characters in length
        testItem.setDescription("e");
        assertEquals("Go to the store", testItem.getDescription());
        testItem.setDescription("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        assertEquals("Go to the store", testItem.getDescription());

        //test for requirement 7: a user SHALL be able to edit the description of an item
        testItem.setDescription("Go to the doctor's office");
        assertEquals("Go to the doctor's office", testItem.getDescription());
    }

    @Test
    void setDueDate() {
        Item.getToDoList().clear();
        //tests for requirement number 3: an item SHALL have a due date
        //test for requirement number 3's sub-requirement: a due date SHALL be optional
        Item testItem = new Item("Go to the store", "", false);
        assertEquals("N/A", testItem.getDueDate());

        //test for requirement number 3's sub-requirements: if present, a due date SHALL be displayed to users
        //in the format: YYYY-MM-DD + if present, a due date SHALL be a valid date within the Gregorian Calendar
        testItem.setDueDate("efaefae");
        testItem.setDueDate("eeee-ee-ee");
        testItem.setDueDate("2021-13-24");
        testItem.setDueDate("20211-07-24");
        testItem.setDueDate("2021-45-01");
        testItem.setDueDate("2021-02-29");
        assertEquals("N/A", testItem.getDueDate());

        //test for requirement number 8: a user SHALL be able to edit the due date of an item within the list
        testItem.setDueDate("2024-02-29");
        assertEquals("2024-02-29", testItem.getDueDate());
    }

    @Test
    void setCompleted() {
        Item.getToDoList().clear();
        //test for requirement number 9: a user SHALL be able to mark
        // an item in the list as either complete or incomplete
        Item testItem = new Item("Go to the store", "2021-07-24", false);
        testItem.setCompleted(true);
        assertTrue(testItem.getCompleted());
        testItem.setCompleted(false);
        assertFalse(testItem.getCompleted());
    }

    @Test
    void getDescription() {
        Item.getToDoList().clear();
        Item testItem = new Item("desc", "", false);
        assertEquals("desc", testItem.getDescription());
    }

    @Test
    void getDueDate() {
        Item.getToDoList().clear();
        Item testItem = new Item("desc", "", false);
        assertEquals("N/A", testItem.getDueDate());
        Item testItem2 = new Item("desc", "eeeee", false);
        assertEquals("N/A", testItem2.getDueDate());
        Item testItem3 = new Item("desc", "2021-07-24", false);
        assertEquals("2021-07-24", testItem3.getDueDate());
    }

    @Test
    void getCompleted() {
        Item.getToDoList().clear();
        Item testItem = new Item("Go to the store", "2021-07-24", false);
        assertFalse(testItem.getCompleted());
        Item testItem2 = new Item("Go to the store", "2021-07-24", true);
        assertTrue(testItem2.getCompleted());
    }

    @Test
    void getToDoList() {
        Item.getToDoList().clear();
        //test for requirement 4: a user SHALL be able to add a new item to the list
        Item.getToDoList().add(new Item("Go to the store", "2021-07-24", false));
        Item.getToDoList().add(new Item("Go to the doctor's", "2021-07-25", true));
        Item.getToDoList().add(new Item("Go to the pet store", "2021-09-25", false));

        ArrayList<Item> testList = new ArrayList<>();
        testList.add(new Item("Go to the store", "2021-07-24", false));
        testList.add(new Item("Go to the doctor's", "2021-07-25", true));
        testList.add(new Item("Go to the pet store", "2021-09-25", false));

        int index = 0;
        for (Item i : Item.getToDoList()){
            assertEquals(i.toString(), testList.get(index).toString());
            index++;
        }

    }

    @Test
    void removeFromList() {
        Item.getToDoList().clear();
        //test for requirement 5: a user SHALL be able to remove an item from the list
        Item.getToDoList().add(new Item("Go to the store", "2021-07-24", false));
        Item.getToDoList().add(new Item("Go to the doctor's", "2021-07-25", true));
        Item.getToDoList().add(new Item("Go to the pet store", "2021-09-25", false));

        ArrayList<Item> testList = new ArrayList<>();
        testList.add(new Item("Go to the store", "2021-07-24", false));
        testList.add(new Item("Go to the doctor's", "2021-07-25", true));
        testList.add(new Item("Go to the pet store", "2021-09-25", false));

        Item.removeFromList(Item.getToDoList().get(1));
        testList.remove(1);

        int index = 0;
        for (Item i : Item.getToDoList()){
            assertEquals(i.toString(), testList.get(index).toString());
            index++;
        }

        //test for requirement 6: a user SHALL be able to clear the list of all items
        Item.getToDoList().clear();
        testList.clear();

        index = 0;
        for (Item i : Item.getToDoList()){
            assertEquals(i.toString(), testList.get(index).toString());
            index++;
        }

    }
}