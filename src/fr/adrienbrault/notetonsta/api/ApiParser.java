package fr.adrienbrault.notetonsta.api;

import fr.adrienbrault.notetonsta.entity.Campus;
import fr.adrienbrault.notetonsta.entity.Intervention;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 20:07
 */

public class ApiParser {

    public static List<Campus> parseCampuses(String jsonString) {
        List<Campus> campuses = new ArrayList<Campus>();

        try {
            JSONObject jsonRoot = new JSONObject(jsonString);

            JSONArray jsonCampuses = jsonRoot.getJSONArray("campus");

            for (int i = 0; i < jsonCampuses.length(); i++) {
                JSONObject jsonCampus = jsonCampuses.getJSONObject(i);

                Campus campus = new Campus();
                fillCampus(campus, jsonCampus);

                campuses.add(campus);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return campuses;
    }

    public static List<Intervention> parseInterventions(String jsonString) {
        List<Intervention> interventions = new ArrayList<Intervention>();

        try {
            JSONObject jsonRoot = new JSONObject(jsonString);

            JSONArray jsonInterventions = jsonRoot.getJSONArray("intervention");

            for (int i = 0; i < jsonInterventions.length(); i++) {
                JSONObject jsonIntervention = jsonInterventions.getJSONObject(i);

                Intervention intervention = new Intervention();

                fillIntervention(intervention, jsonIntervention);

                interventions.add(intervention);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return interventions;
    }

    public static Intervention parseIntervention(String jsonString) {
        Intervention intervention = new Intervention();

        try {
            JSONObject jsonIntervention = new JSONObject(jsonString);

            fillIntervention(intervention, jsonIntervention);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return intervention;
    }

    private static void fillIntervention(Intervention intervention, JSONObject jsonIntervention) throws JSONException {
        intervention.setAverageMark(jsonIntervention.getDouble("averageMark"));
        intervention.setSlidesAverageMark(jsonIntervention.getDouble("slidesAverageMark"));
        intervention.setSpeakerAverageMark(jsonIntervention.getDouble("speakerAverageMark"));
        intervention.setEvaluationsCount(jsonIntervention.getInt("evaluationCount"));

        JSONObject jsonInterventionDetail = jsonIntervention.getJSONObject("interventionDetail");

        intervention.setId(jsonInterventionDetail.getInt("id"));
        intervention.setSubject(jsonInterventionDetail.getString("subject"));
        intervention.setDescription(jsonInterventionDetail.getString("description"));
        intervention.setDateBegin(parseDateString(jsonInterventionDetail.getString("dateBegin")));
        intervention.setDateEnd(parseDateString(jsonInterventionDetail.getString("dateEnd")));

        intervention.setCampus(new Campus());
        fillCampus(intervention.getCampus(), jsonInterventionDetail.getJSONObject("campus"));
    }

    private static void fillCampus(Campus campus, JSONObject jsonCampus) throws JSONException {
        campus.setId(jsonCampus.getInt("id"));
        campus.setName(jsonCampus.getString("name"));
    }

    private static Date parseDateString(String dateString) {
        final String pattern = "yyyy-MM-dd'T'hh:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date date = null;

        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
