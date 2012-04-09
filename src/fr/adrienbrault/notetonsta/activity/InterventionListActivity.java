package fr.adrienbrault.notetonsta.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import fr.adrienbrault.notetonsta.R;
import fr.adrienbrault.notetonsta.api.ApiClient;
import fr.adrienbrault.notetonsta.entity.Campus;
import fr.adrienbrault.notetonsta.entity.Intervention;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 16:55
 */

public class InterventionListActivity extends Activity {

    Integer campusId;

    ListView listView;
    InterventionListAdapter interventionListAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intervention_list);

        listView = (ListView) findViewById(R.id.intervention_list);

        campusId = getIntent().getExtras().getInt("campusId");

        setUpList();
        refreshList();
    }

    public void setUpList() {

        interventionListAdapter = new InterventionListAdapter(this, new ArrayList<Intervention>());
        listView.setAdapter(interventionListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long index) {
                Intervention intervention = interventionListAdapter.getItem(position);

                Intent intent = new Intent(InterventionListActivity.this, InterventionDetailActivity.class);
                intent.putExtra("interventionId", intervention.getId());

                startActivity(intent);
            }
        });
    }

    public void refreshList() {

        AsyncTask<Void, Void, List<Intervention>> refreshCampusesTask = new AsyncTask<Void, Void, List<Intervention>>() {

            @Override
            protected List<Intervention> doInBackground(Void... params) {
                return ApiClient.getInstance().getCampusInterventions(campusId);
            }

            @Override
            protected void onPostExecute(List<Intervention> interventions) {
                interventionListAdapter = new InterventionListAdapter(InterventionListActivity.this, interventions);
                listView.setAdapter(interventionListAdapter);
            }
        };
        refreshCampusesTask.execute();
    }

    private class InterventionListAdapter extends ArrayAdapter<Intervention> {

        List<Intervention> interventions;

        public InterventionListAdapter(Context context, List<Intervention> interventions) {
            super(context, R.layout.intervention_row, interventions);

            this.interventions = interventions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View rowView = inflater.inflate(R.layout.intervention_row, parent, false);

            TextView subjectTextView = (TextView) rowView.findViewById(R.id.subject);
            TextView dateTextView = (TextView) rowView.findViewById(R.id.date);
            TextView statusTextView = (TextView) rowView.findViewById(R.id.status);

            Intervention intervention = interventions.get(position);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            subjectTextView.setText(intervention.getSubject());
            dateTextView.setText(dateFormat.format(intervention.getDateBegin()) + " - " + dateFormat.format(intervention.getDateEnd()));
            statusTextView.setText(intervention.getStatusString());

            return rowView;
        }

    }

}
