/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Peter Perri
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ListEditorControllerTest {

    @Test
    void importExportTest() throws FileNotFoundException {
        //test for requirements 13 and 14: saving and importing toDoLists
        //specifying the file path and the loaded list replacing the current list in the GUI
        //cannot be tested via JUnit as it is gotten from the FileChooser;
        //functionality manually tested and verified to work

        ListEditorController controller = new ListEditorController();
        for (int i = 0; i < 150; i++){
            String desc = "item" + i;
            Item.getToDoList().add(new Item(desc, "", false));
        }
        File testOutput = new File("src/test/testFiles/testOutput.txt");
        controller.exportToDoList(testOutput);
        Item.getToDoList().clear();
        File testImport = new File("src/test/testFiles/testOutput.txt");
        controller.importToDoList(testImport);
        for (int i = 0; i < 150; i++){
            String desc = "item" + i;
            assertEquals(desc, Item.getToDoList().get(i).getDescription());
            assertEquals("N/A", Item.getToDoList().get(i).getDueDate());
            assertFalse(Item.getToDoList().get(i).getCompleted());
        }
        Item.getToDoList().clear();
    }

    @Test
    void populateCompleteOrIncompleteListTest(){
        //test for requirements 11 and 12: a user shall be able to display
        //only the incomplete/completed items in the list.
        //requirement 10 (showing ALL the items in the list)
        //cannot be tested via JUnit, but the requirement is accounted for by
        //default  and by using the uncheck method in ListEditorController.
        //this requirement has been tested manually since it cannot be tested via JUnit.
        ListEditorController controller = new ListEditorController();
        Item.getToDoList().clear();
        for (int i = 0; i < 4; i++) {
            String desc = "falseItem" + i;
            Item.getToDoList().add(new Item(desc, "", false));
        }
        for (int i = 0; i < 4; i++){
            String desc = "trueItem" + i;
            Item.getToDoList().add(new Item(desc, "", true));
        }

        ObservableList<Item> completedItems = FXCollections.observableArrayList();
        completedItems = controller.populateCompleteOrIncompleteList(true, completedItems);
        assertEquals(4, completedItems.size());

        ObservableList<Item> incompleteItems = FXCollections.observableArrayList();
        incompleteItems = controller.populateCompleteOrIncompleteList(false, incompleteItems);
        assertEquals(4, incompleteItems.size());
        Item.getToDoList().clear();
    }

    @Test
    void addItemToListTest(){
        ListEditorController controller = new ListEditorController();
        Item.getToDoList().clear();
        for (int i = 0; i < 150; i++){
            String desc = "item" + i;
            controller.addItemToList(desc, "", false);
        }
        assertEquals(150, Item.getToDoList().size());
        Item.getToDoList().clear();
    }

}