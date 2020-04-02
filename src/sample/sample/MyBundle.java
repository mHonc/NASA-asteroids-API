package sample;

import java.util.ListResourceBundle;

public class MyBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"greetings", "hello C"},
                {"inquire", "how are you C?"},
                {"farewell", "goodby C"}};
    }
}