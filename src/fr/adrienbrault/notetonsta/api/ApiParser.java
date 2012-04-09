package fr.adrienbrault.notetonsta.api;

import fr.adrienbrault.notetonsta.entity.Campus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 20:07
 */

public class ApiParser {

    public static List<Campus>  parseCampuses(String jsonString) {
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

}
