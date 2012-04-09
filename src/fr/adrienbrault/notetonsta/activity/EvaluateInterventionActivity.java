package fr.adrienbrault.notetonsta.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import fr.adrienbrault.notetonsta.R;
import fr.adrienbrault.notetonsta.api.ApiClient;
import fr.adrienbrault.notetonsta.entity.Evaluation;

/**
 * Author: Adrien Brault
 * Date: 09/04/12
 * Time: 16:55
 */

public class EvaluateInterventionActivity extends Activity {

    Integer interventionId;

    Button submitButton;

    EditText idBoosterEditText;
    RatingBar speakerKnowledgeRatingBar;
    RatingBar speakerAbilitiesRatingBar;
    RatingBar speakerAnswersQualityRatingBar;
    RatingBar slidesContentRichnessRatingBar;
    RatingBar slidesFormatLayoutRatingBar;
    RatingBar slidesExamplesRatingBar;
    EditText commentEditText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.evaluate_intervention);

        submitButton = (Button) findViewById(R.id.submit);
        idBoosterEditText = (EditText) findViewById(R.id.id_booster);
        speakerKnowledgeRatingBar = (RatingBar) findViewById(R.id.speaker_knowledge_rating);
        speakerAbilitiesRatingBar = (RatingBar) findViewById(R.id.speaker_abilities_rating);
        speakerAnswersQualityRatingBar = (RatingBar) findViewById(R.id.speaker_answers_quality_rating);
        slidesContentRichnessRatingBar = (RatingBar) findViewById(R.id.slides_content_richness_rating);
        slidesFormatLayoutRatingBar = (RatingBar) findViewById(R.id.slides_format_layout_rating);
        slidesExamplesRatingBar = (RatingBar) findViewById(R.id.slides_examples_rating);
        commentEditText = (EditText) findViewById(R.id.comment);

        interventionId = getIntent().getExtras().getInt("interventionId");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitClick();
            }
        });
    }

    public void onSubmitClick() {
        Evaluation evaluation = new Evaluation();
        evaluation.setComment(commentEditText.getText().toString());
        evaluation.setIdBooster(Integer.parseInt(idBoosterEditText.getText().toString()));
        evaluation.setSlidesContentMark(slidesContentRichnessRatingBar.getRating());
        evaluation.setSlidesExamplesMark(slidesExamplesRatingBar.getRating());
        evaluation.setSlidesFormatMark(slidesFormatLayoutRatingBar.getRating());
        evaluation.setSpeakerAnswersMark(speakerAnswersQualityRatingBar.getRating());
        evaluation.setSpeakerKnowledgeMark(speakerKnowledgeRatingBar.getRating());
        evaluation.setSpeakerTeachingMark(speakerAbilitiesRatingBar.getRating());

        AsyncTask<Evaluation, Void, Boolean> addEvaluationTask = new AsyncTask<Evaluation, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Evaluation... params) {
                return ApiClient.getInstance().addEvaluation(params[0], interventionId);
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Error, check that you set an id booster.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        addEvaluationTask.execute(evaluation);
    }

}
