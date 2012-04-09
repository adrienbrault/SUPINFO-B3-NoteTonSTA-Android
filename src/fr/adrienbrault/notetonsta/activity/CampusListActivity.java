package fr.adrienbrault.notetonsta.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.adrienbrault.notetonsta.R;
import fr.adrienbrault.notetonsta.api.ApiClient;
import fr.adrienbrault.notetonsta.entity.Campus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 16:54
 */

public class CampusListActivity extends Activity {

    ListView listView;

    CampusListAdapter campusListAdapter;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.campus_list);

        listView = (ListView) findViewById(R.id.campus_list);

        setUpList();
        refreshList();
    }

    public void setUpList() {

        campusListAdapter = new CampusListAdapter(this, new ArrayList<Campus>());
        listView.setAdapter(campusListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long index) {
                Campus campus = campusListAdapter.getItem(position);

                Intent intent = new Intent(CampusListActivity.this, InterventionListActivity.class);
                intent.putExtra("campusId", campus.getId());

                startActivity(intent);
            }
        });
    }

    public void refreshList() {

        AsyncTask<Void, Void, List<Campus>> refreshCampusesTask = new AsyncTask<Void, Void, List<Campus>>() {

            @Override
            protected List<Campus> doInBackground(Void... params) {
                return ApiClient.getInstance().getCampuses();
            }

            @Override
            protected void onPostExecute(List<Campus> campuses) {
                campusListAdapter = new CampusListAdapter(CampusListActivity.this, campuses);
                listView.setAdapter(campusListAdapter);
            }
        };
        refreshCampusesTask.execute();
    }

    private class CampusListAdapter extends ArrayAdapter<Campus> {

        List<Campus> campuses;

        public CampusListAdapter(Context context, List<Campus> campuses) {
            super(context, R.layout.campus_row, campuses);

            this.campuses = campuses;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rowView = inflater.inflate(R.layout.campus_row, parent, false);

            TextView textView = (TextView) rowView.findViewById(R.id.campus_name);

            Campus campus = campuses.get(position);
            textView.setText(campus.getName());

            return rowView;
        }

    }

}
