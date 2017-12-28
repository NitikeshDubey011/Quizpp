package com.nitikesh.abc.quizpp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitikesh.abc.quizpp.Common.Common;
import com.squareup.picasso.Picasso;

public class Playing extends AppCompatActivity implements View.OnClickListener{

    final static  long INTERVAL=1000;
    final static long TIMEOUT=7000;
    int progressValue=0;
    CountDownTimer countDownTimer;
    int index=0,thisQuestion,totalQuestion,correctAnswer,score=0;


    ProgressBar progressBar;
    ImageView question_image;
    TextView textScore,textQuestionNum,question_text;
    Button btnA,btnB,btnC,btnD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        question_image=(ImageView)findViewById(R.id.question_image);

        textScore=(TextView)findViewById(R.id.textScore);
        textQuestionNum=(TextView)findViewById(R.id.textTotalQuestion);
        question_text=(TextView)findViewById(R.id.question_text);

        btnA=(Button)findViewById(R.id.buttonAnswerA);
        btnB=(Button)findViewById(R.id.buttonAnswerB);
        btnC=(Button)findViewById(R.id.buttonAnswerC);
        btnD=(Button)findViewById(R.id.buttonAnswerD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        countDownTimer.cancel();
        if(index<totalQuestion){
            Button clickedButton=(Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer())){
                score+=10;
                correctAnswer++;
                showQuestion(++index);
            }
            else
            {
                Intent intent=new Intent(this,Done.class);
                Bundle bundle=new Bundle();
                bundle.putInt("SCORE",score);
                bundle.putInt("TOTAL",totalQuestion);
                bundle.putInt("CORRECT",correctAnswer);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            textScore.setText(String.format("%d",score));
        }

    }

    private void showQuestion(int index) {
        if(index<totalQuestion){
            thisQuestion++;
            textQuestionNum.setText(String.format("%d/%d",thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;

            if(Common.questionList.get(index).getIsImageQuestion().equals("true")){
                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            }
            else{
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);

                btnA.setText(Common.questionList.get(index).getAnswerA());
                btnB.setText(Common.questionList.get(index).getAnswerB());
                btnC.setText(Common.questionList.get(index).getAnswerC());
                btnD.setText(Common.questionList.get(index).getAnswerD());

                countDownTimer.start();
            }
        }
        else{
            Intent intent=new Intent(this,Done.class);
            Bundle bundle=new Bundle();
            bundle.putInt("SCORE",score);
            bundle.putInt("TOTAL",totalQuestion);
            bundle.putInt("CORRECT",correctAnswer);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        totalQuestion=Common.questionList.size();
        countDownTimer=new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;

            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                showQuestion(++index);

            }
        };
        showQuestion(index);
    }
}
