package com.nitikesh.abc.quizpp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitikesh.abc.quizpp.Common.Common;
import com.nitikesh.abc.quizpp.Model.Question;
import com.nitikesh.abc.quizpp.Model.QuestionScore;

public class Done extends AppCompatActivity {
    Button btnTryAgain;
    TextView textResultScore,getTextResultQuestion,question_score;
    ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Question_Score");

        textResultScore=(TextView)findViewById(R.id.textTotalScore);
        getTextResultQuestion=(TextView)findViewById(R.id.textTotalQuestion);
        progressBar=(ProgressBar)findViewById(R.id.doneProgressBar);
        btnTryAgain=(Button)findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Done.this,Home.class);
                startActivity(intent);
                finish();
            }
        });

        Bundle extra=getIntent().getExtras();
        if(extra!=null){
            int score=extra.getInt("SCORE");
            int totalQuestion=extra.getInt("TOTAL");
            int correctAnswer=extra.getInt("CORRECT");


            textResultScore.setText(String.format("SCORE : %d",score));
            getTextResultQuestion.setText(String.format("PASSED : %d/%d",correctAnswer,totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            databaseReference.child(String.format("%s_%s", Common.currentUser.getUserName(),
            Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getUserName(),
                            Common.categoryId),Common.currentUser.getUserName(),String.valueOf(score)));



        }
    }
}
