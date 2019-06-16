package com.example.android.flash_card;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardTestActivity extends AppCompatActivity {
    public static final String CARD_DIFFICULTY = "com.example.android.flash_card.CARD_DIFFICULTY";
    public static final String CARD_WORDNUM = "com.example.android.flash_card.CARD_WORDNUM";
    public static final String CARD_CATEGORY = "com.example.android.flash_card.CARD_CATEGORY";
    public static final int VALUE_NOT_SET = -1;
    private List<CardContent> mRandCard;
    private int mPosition = 0;
    private boolean mConfirm;
    private TextView mDisplay;
    private TextView mChecked;
    private EditText mAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readDisplayStateValue();

        mDisplay = (TextView) findViewById(R.id.test_display);
        mAnswer = (EditText) findViewById(R.id.test_answer);
        mChecked = (TextView) findViewById(R.id.test_checked);
        mDisplay.setText(mRandCard.get(mPosition).getMeaning());
        mChecked.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_study, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.study_next) {
            moveNext();
        }

        return super.onOptionsItemSelected(item);
    }

    private void moveNext() {
        if(mPosition == mRandCard.size()-1 && !mConfirm) {
            super.onBackPressed();
        }
        else if(mConfirm) {
            if(mAnswer.getText().toString().toLowerCase().equals(mRandCard.get(mPosition).getWord().toLowerCase())) {
                mChecked.setText("Correct!");
            }
            else {
                mChecked.setText("Wrong! - " + mRandCard.get(mPosition).getWord());
            }
        }
        else {
            mAnswer.setText("");
            mChecked.setText("");
            mPosition++;
            mDisplay.setText(mRandCard.get(mPosition).getMeaning());
        }
        mConfirm = !mConfirm;
    }

    private void readDisplayStateValue() {
        Intent intent = getIntent();
        int difficult = intent.getIntExtra(CARD_DIFFICULTY, VALUE_NOT_SET);
        int wordnumIndex = intent.getIntExtra(CARD_WORDNUM, VALUE_NOT_SET);;
        ArrayList<String> category = intent.getStringArrayListExtra(CARD_CATEGORY);

        List<CardContent> card = DataManager.getInstance().getCards();
        mRandCard = card;
//        mRandCard = DataManager.getRandomCards(cards, difficult, category, Integer.parseInt(getResources().getStringArray(R.array.num_sel)[wordnumIndex]));
        mConfirm = true;
    }

}
