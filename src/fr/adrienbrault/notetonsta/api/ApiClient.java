package fr.adrienbrault.notetonsta.api;

import android.util.Log;
import fr.adrienbrault.notetonsta.entity.Campus;
import fr.adrienbrault.notetonsta.entity.Intervention;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 19:59
 */

public class ApiClient {

    static public final String BASE_URL = "http://192.168.0.15:8080/NoteTonSTA/api";
    static public final String CAMPUSES_URL = "/campuses";
    static public final String CAMPUSES_INTERVENTIONS_URL = "/campuses/%d/interventions";
    static public final String INTERVENTION_URL = "/interventions/%d";

    private static ApiClient instance;

    public static ApiClient getInstance() {
        if (null == instance) {
            instance = new ApiClient();
        }

        return instance;
    }

    private DefaultHttpClient client;

    private ApiClient() {
        client = new DefaultHttpClient();
    }

    private String getString(HttpRequestBase request) {

        StringBuilder builder = new StringBuilder();

        try {
            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();

            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public List<Campus> getCampuses() {

        HttpGet httpGet = new HttpGet(BASE_URL + CAMPUSES_URL);

        String jsonString = getString(httpGet);

        return ApiParser.parseCampuses(jsonString);
    }

    public List<Intervention> getCampusInterventions(Integer campusId) {
        HttpGet httpGet = new HttpGet(BASE_URL + String.format(CAMPUSES_INTERVENTIONS_URL, campusId));

        String jsonString = getString(httpGet);

        return ApiParser.parseInterventions(jsonString);
    }

    public Intervention getIntervention(Integer interventionId) {
        HttpGet httpGet = new HttpGet(BASE_URL + String.format(INTERVENTION_URL, interventionId));

        String jsonString = getString(httpGet);

        return ApiParser.parseIntervention(jsonString);
    }

}
