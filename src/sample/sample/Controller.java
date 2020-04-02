package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;


public class Controller {

    private static final String apiKey = "wsbRsp1rPAllIKIS3IAXJQqV9YUwcPhiw7bxiGYW";
    @FXML
    private Label asteroidsCount;
    @FXML
    private Label languageLabel;
    @FXML
    private Label startDataLabel;
    @FXML
    private Label endDataLabel;
    @FXML
    private Button generateInfo;
    @FXML
    private DatePicker startDatePick;
    @FXML
    private DatePicker endDatePick;
    @FXML
    private ChoiceBox choiceBox;
    private String startDate;
    private String endDate;
    private String language = "EN";
    private String country = "en";
    private Locale l = new Locale(language, country);
    private ResourceBundle rb1 = ResourceBundle.getBundle("MyBundle", l);
    MessageFormat messageForm = new MessageFormat("");

    @FXML
    public void handleAction() {
        try {
            startDate = startDatePick.getValue().toString();
            endDate = endDatePick.getValue().toString();
        } catch (NullPointerException e) {
        }
        if(startDate == null || endDate == null)
            asteroidsCount.setText(rb1.getString("emptyDateError"));
        else {
            int count = getAsteroidsInfo();
            if(count < 0)
                asteroidsCount.setText(rb1.getString("dateError"));
            else
                asteroidsCount.setText(plurals(count));
        }
    }

    @FXML
    public void handleLanguageChange(){
        language = (String) choiceBox.getSelectionModel().getSelectedItem();
        country = language.toLowerCase();

        l = new Locale(language, country);
        rb1 = ResourceBundle.getBundle("MyBundle", l);
        languageLabel.setText(rb1.getString("language"));
        startDataLabel.setText(rb1.getString("startData"));
        endDataLabel.setText(rb1.getString("endData"));
        asteroidsCount.setText(rb1.getString("countInfo"));
        generateInfo.setText(rb1.getString("generate"));
    }

    private int getAsteroidsInfo() {
        try {
            URL url = new URL("https://api.nasa.gov/neo/rest/v1/feed?start_date=" + startDate +  "&end_date=" + endDate + "&api_key=" + apiKey);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                System.out.println(content);
            }

            JSONObject json = new JSONObject(content.toString());
            int elementCount = json.getInt("element_count");
            in.close();

            return elementCount;

        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String plurals(int count){
        messageForm.setLocale(l);

        double[] fileLimits = {0,1,2,3,4,5};
        String [] fileStrings = {
                rb1.getString("zeroAsteroids"),
                rb1.getString("oneAsteroid"),
                rb1.getString("twoAsteroids"),
                rb1.getString("threeAsteroids"),
                rb1.getString("fourAsteroids"),
                rb1.getString("multipleAsteroids"),
        };

        ChoiceFormat choiceForm = new ChoiceFormat(fileLimits, fileStrings);

        String pattern = rb1.getString("pattern");
        messageForm.applyPattern(pattern);
        Format[] formats = {choiceForm, null, NumberFormat.getInstance()};
        messageForm.setFormats(formats);

        Object[] messageArguments = {null, "XDisk", null};
        messageArguments[0] = new Integer(count);
        messageArguments[1] = new Integer(count);
        String result = messageForm.format(messageArguments);


        //test odmiany
        for (int numFiles = 0; numFiles < 10; numFiles++) {
            messageArguments[0] = new Integer(numFiles);
            messageArguments[1] = new Integer(numFiles);
            String bap = messageForm.format(messageArguments);
            System.out.println(bap);
        }

        return result;
    }
}
