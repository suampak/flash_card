package com.example.android.flash_card;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardStudyActivity extends AppCompatActivity {
    public static final String CARD_DIFFICULTY = "com.example.android.flash_card.CARD_DIFFICULTY";
    public static final String CARD_WORDNUM = "com.example.android.flash_card.CARD_WORDNUM";
    public static final String CARD_CATEGORY = "com.example.android.flash_card.CARD_CATEGORY";
    public static final int VALUE_NOT_SET = -1;
    private List<CardContent> mRandCard;
    private boolean mFront;
    private int mPosition = 0;
    private TextView mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_study);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readDisplayStateValue();

        mDisplay = (TextView) findViewById(R.id.study_display);
        mDisplay.setText(mRandCard.get(mPosition).getWord());

        CardView card = (CardView) findViewById(R.id.study_card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFront) {
                    mDisplay.setText(mRandCard.get(mPosition).getMeaning());
                }
                else {
                    mDisplay.setText(mRandCard.get(mPosition).getWord());
                }
                mFront = !mFront;
            }
        });
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
            if(mPosition == mRandCard.size()-1) {
                super.onBackPressed();
            }
            else {
                mPosition++;
                mDisplay.setText(mRandCard.get(mPosition).getWord());
                mFront = true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void readDisplayStateValue() {
        Intent intent = getIntent();
        int difficult = intent.getIntExtra(CARD_DIFFICULTY, VALUE_NOT_SET);
        int wordnumIndex = intent.getIntExtra(CARD_WORDNUM, VALUE_NOT_SET);;
        ArrayList<String> category = intent.getStringArrayListExtra(CARD_CATEGORY);

        List<CardContent> card = DataManager.getInstance().getCards();
        mRandCard = card;
//        mRandCard = DataManager.getRandomCards(cards, difficult, category, Integer.parseInt(getResources().getStringArray(R.array.num_sel)[wordnumIndex]));

        mFront = true;
    }

}
