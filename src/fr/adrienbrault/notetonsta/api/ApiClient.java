package fr.adrienbrault.notetonsta.api;

import fr.adrienbrault.notetonsta.entity.Campus;
import fr.adrienbrault.notetonsta.entity.Evaluation;
import fr.adrienbrault.notetonsta.entity.Intervention;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

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
    static public final String EVALUATIONS_URL = "/evaluations";

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

    public boolean addEvaluation(Evaluation evaluation, Integer interventionId) {
        JSONObject jsonEvaluation = null;
        try {
            jsonEvaluation = new JSONObject();
            jsonEvaluation.put("interventionId", interventionId.toString());

            JSONObject jsonEvaluationObject = new JSONObject();
            jsonEvaluationObject.put("idBooster", evaluation.getIdBooster());
            jsonEvaluationObject.put("comment", evaluation.getComment());
            jsonEvaluationObject.put("slidesContentMark", evaluation.getSlidesContentMark());
            jsonEvaluationObject.put("slidesExamplesMark", evaluation.getSlidesExamplesMark());
            jsonEvaluationObject.put("slidesFormatMark", evaluation.getSlidesFormatMark());
            jsonEvaluationObject.put("speakerAnswersMark", evaluation.getSpeakerAnswersMark());
            jsonEvaluationObject.put("speakerKnowledgeMark", evaluation.getSpeakerKnowledgeMark());
            jsonEvaluationObject.put("speakerTeachingMark", evaluation.getSpeakerTeachingMark());

            jsonEvaluation.put("evaluation", jsonEvaluationObject);

            HttpResponse response;
            HttpPost post = new HttpPost(BASE_URL + EVALUATIONS_URL);

            StringEntity se = new StringEntity(jsonEvaluation.toString());
            post.setEntity(se);
            post.setHeader("Content-Type", "application/json");
            response = client.execute(post);

            if(response != null) {
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == 201) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
