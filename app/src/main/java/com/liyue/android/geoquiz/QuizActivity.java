package com.liyue.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean[] mIsCheater;
    private static final String EXTRA_ANSWER_SHOWN =
            "com.liyue.android.geoquiz.answer_shown";

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    //更新问题描述
    private void updateQuestion(){
        mCurrentIndex = (mCurrentIndex < 0) ? (mCurrentIndex + 5) : mCurrentIndex;
        mCurrentIndex = mCurrentIndex % mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    //检查答案是否正确并显示Toast
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (mIsCheater[mCurrentIndex]){
            messageResId = R.string.judgement_toast;
        }else{
            if (userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mIsCheater = new boolean[mQuestionBank.length];
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBooleanArray(EXTRA_ANSWER_SHOWN);
        }
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex += 1;
                updateQuestion();
            }
        });
        updateQuestion();
        Button mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mCurrentIndex += 1;
                updateQuestion();
            }
        });
        Button mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mCurrentIndex += 1;
                updateQuestion();
            }
        });
        Button mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex -= 1;
                updateQuestion();
            }
        });
        Button mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex += 1;
                updateQuestion();
            }
        });
        Button mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(EXTRA_ANSWER_SHOWN, mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }
    }
}
