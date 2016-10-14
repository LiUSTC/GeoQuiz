package com.liyue.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        if (savedInstanceState != null){
            if (savedInstanceState.getBoolean(EXTRA_ANSWER_IS_SHOWN,false)){
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                isAnswerShown = true;
                setAnswerShownResult(isAnswerShown);
            }
        }

        Button mShowAnswerButton = (Button)findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                isAnswerShown = true;
                setAnswerShownResult(isAnswerShown);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.liyue.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.liyue.android.geoquiz.answer_shown";
    private static final String EXTRA_ANSWER_IS_SHOWN =
            "com.liyue.android.geoquiz.answer_is_shown";
    private boolean mAnswerIsTrue;
    private boolean isAnswerShown;
    private TextView mAnswerTextView;
}
