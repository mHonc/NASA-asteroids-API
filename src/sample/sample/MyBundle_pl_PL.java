package sample;

import java.util.ListResourceBundle;

public class MyBundle_pl_PL extends ListResourceBundle {
    public MyBundle_pl_PL() {
    }

    protected Object[][] getContents() {
        return new Object[][]{{"greetings", "czołem C"}, {"inquire", "jak się masz C?"}, {"farewell", "do widzenia C"}};
    }
}