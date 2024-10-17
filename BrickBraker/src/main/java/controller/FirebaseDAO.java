package controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Καθορίζει την επικοινωνία με το Firabase μέσω REST για την ενημέρωση των high scores 
 */
public class FirebaseDAO {
    //private String baseURL = "https://test-4db2a-default-rtdb.europe-west1.firebasedatabase.app/brickbraker/";
    private String baseURL = "https://brickbreaker2023-default-rtdb.europe-west1.firebasedatabase.app/brickbraker/";
    
    
    /**
     * Ενημερώνει τη Firebase ΒΔ με τον σκορ του παίκτη
     * Αν ο παίκτης υπάρχει ήδη, τότε αντικαθιστά τον παλιό σκορ με το νέο
     * @param name  , το όνομα του παίκτη
     * @param score , το σκορ του παίκτη
     */
    public void writeScore(String name, int score) {
        try {
            String url = baseURL+ name + ".json";
            HttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(url);
            StringEntity requestEntity = new StringEntity(String.valueOf(score), ContentType.APPLICATION_JSON);
            httpPut.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("Score updated successfully.");
            } else {
                System.out.println("Failed to update score. Error: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException ex) {
            Logger.getLogger(FirebaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Διαβάζει από την Firebase ΒΔ το σκορ ενός παίκτη
     * @param name  , το όνομα του παίκτη
     * @return  , το σκορ του παίκτη, 0 αν δεν υπάρχει το όνομα 
     */
    public int getScore(String name)  {
        try {
            String url = baseURL + name + ".json";
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                if (responseBody != null && !responseBody.isEmpty()) {
                    return Integer.parseInt(responseBody);
                }
            }
            return 0;
        } catch (IOException ex) {
            Logger.getLogger(FirebaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Διαβάζει από την Firebase ΒΔ τα σκορ όλων των παικτών
     * @return  , μια φθίνουσα ταξινομημένη λίστα με τα σκορ των παικτών
     */
    public ArrayList<HighScore> getTopScores()  {
        try {
            String url = baseURL + ".json";
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            ArrayList<HighScore> topScores = new ArrayList<>();
            
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                
                // Χειρισμός JSON  με Gson
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(responseBody);
                
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    for (String key : jsonObject.keySet()) {
                        int score = jsonObject.get(key).getAsInt();
                        HighScore scoreEntry = new HighScore(key, score);
                        topScores.add(scoreEntry);
                    }
                }
            }
            
            // Για ταξινόμηση
            Comparator<HighScore> customComparator = new Comparator<HighScore>() {
                public int compare(HighScore e1, HighScore e2) {
                    return (e2.getScore() - e1.getScore());
                }
            };
            
            topScores.sort(customComparator);
            
            return topScores;
        } catch (IOException ex) {
            Logger.getLogger(FirebaseDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }




}
