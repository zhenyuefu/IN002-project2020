import java.io.IOException;

import menu.Menu;

public class App {

    public static void main(String[] args) {

        Menu menu = Menu.getInstance();
        try {
            menu.mainMenu();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                menu.saveData();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.out.println("Save data failed!");
            }
        }
    }
}