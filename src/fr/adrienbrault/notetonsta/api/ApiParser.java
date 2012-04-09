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
                campus.setId(jsonCampus.getInt("id"));
                campus.setName(jsonCampus.getString("name"));

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
                JSONObject jsonInterventionDetail = jsonIntervention.getJSONObject("interventionDetail");

                Intervention intervention = new Intervention();
                intervention.setId(jsonInterventionDetail.getInt("id"));
                intervention.setSubject(jsonInterventionDetail.getString("subject"));
                intervention.setDescription(jsonInterventionDetail.getString("description"));
                intervention.setDateBegin(parseDateString(jsonInterventionDetail.getString("dateBegin")));
                intervention.setDateEnd(parseDateString(jsonInterventionDetail.getString("dateEnd")));

                interventions.add(intervention);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return interventions;
    }

    public static Date parseDateString(String dateString) {
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
