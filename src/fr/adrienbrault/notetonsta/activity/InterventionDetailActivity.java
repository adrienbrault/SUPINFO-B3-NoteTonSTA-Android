package fr.adrienbrault.notetonsta.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import fr.adrienbrault.notetonsta.R;
import fr.adrienbrault.notetonsta.api.ApiClient;
import fr.adrienbrault.notetonsta.entity.Intervention;

import java.text.SimpleDateFormat;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 16:55
 */

public class InterventionDetailActivity extends Activity {

    Integer interventionId;
    Intervention intervention;

    TextView subjectTextView;
    TextView campusTextView;
    TextView dateTextView;
    TextView descriptionTextView;
    TextView evaluationsCountTextView;
    TextView slidesMarkTextView;
    TextView speakerMarkTextView;
    TextView averageMarkTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intervention_detail);

        subjectTextView = (TextView) findViewById(R.id.subject);
        campusTextView = (TextView) findViewById(R.id.campus);
        dateTextView = (TextView) findViewById(R.id.date);
        descriptionTextView = (TextView) findViewById(R.id.description);
        evaluationsCountTextView = (TextView) findViewById(R.id.evaluation_count);
        slidesMarkTextView = (TextView) findViewById(R.id.slides_mark);
        speakerMarkTextView = (TextView) findViewById(R.id.speaker_mark);
        averageMarkTextView = (TextView) findViewById(R.id.mark);

        interventionId = getIntent().getExtras().getInt("interventionId");
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.intervention_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, EvaluateInterventionActivity.class);
        intent.putExtra("interventionId", intervention.getId());

        startActivity(intent);

        return true;
    }

    public void refreshData() {

        AsyncTask<Void, Void, Intervention> refreshCampusesTask = new AsyncTask<Void, Void, Intervention>() {

            @Override
            protected Intervention doInBackground(Void... params) {
                return ApiClient.getInstance().getIntervention(interventionId);
            }

            @Override
            protected void onPostExecute(Intervention intervention) {
                InterventionDetailActivity.this.intervention = intervention;
                refreshView();
            }
        };
        refreshCampusesTask.execute();
    }

    public void refreshView() {
        subjectTextView.setText(intervention.getSubject());
        campusTextView.setText(intervention.getCampus().getName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateString = dateFormat.format(intervention.getDateBegin()) + " to " + dateFormat.format(intervention.getDateEnd());
        dateTextView.setText(dateString);

        descriptionTextView.setText(intervention.getDescription());
        evaluationsCountTextView.setText(intervention.getEvaluationsCount().toString());

        String markFormat = "%.1f / 5.0";
        slidesMarkTextView.setText(String.format(markFormat, intervention.getSlidesAverageMark()));
        speakerMarkTextView.setText(String.format(markFormat, intervention.getSpeakerAverageMark()));
        averageMarkTextView.setText(String.format(markFormat, intervention.getAverageMark()));
    }

}
