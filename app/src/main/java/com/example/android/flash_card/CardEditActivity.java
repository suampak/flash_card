package com.example.android.flash_card;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CardEditActivity extends AppCompatActivity {
    public static final String CARD_POSITION = "com.example.android.flash_card.CARD_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private CardContent mCard;
    private int mPosition;
    private final List<String> mCategoryList = new ArrayList<String>();
    private boolean mIsNewCard;
    private EditText mWord;
    private EditText mMeaning;
    private EditText mCategory;
    private ChipGroup mChipGroup;
    private Spinner mDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWord = (EditText) findViewById(R.id.card_edit_word);
        mMeaning = (EditText) findViewById(R.id.card_edit_meaning);
        mCategory = (EditText) findViewById(R.id.card_edit_category);
        mChipGroup = (ChipGroup) findViewById(R.id.chips_category);
        mDifficulty = (Spinner) findViewById(R.id.card_edit_difficult);

        mCategory.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    addChip(mChipGroup, mCategoryList, mCategory.getText().toString());
                    mCategory.setText("");
                }
                return true;
            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.word_difficult, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDifficulty.setAdapter(spinnerAdapter);

        readDisplayStateValue();
        displayCardContent(mWord, mMeaning, mChipGroup, mDifficulty, mCategoryList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIsNewCard) {
            DataManager.getInstance().removeCard(mPosition);
        }
    }

    private void readDisplayStateValue() {
        Intent intent = getIntent();
        mPosition = intent.getIntExtra(CARD_POSITION, POSITION_NOT_SET);
        mIsNewCard = mPosition == POSITION_NOT_SET;
        DataManager dm = DataManager.getInstance();
        if(mIsNewCard) {
            mPosition = dm.createNewCard();
        }
        mCard = dm.getCards().get(mPosition);
    }

    private void displayCardContent(EditText word, EditText meaning, ChipGroup category, Spinner difficulty, final List<String> categoryList) {
        if(mIsNewCard) {
            return;
        }

        category.removeAllViews();
        word.setText(mCard.getWord());
        meaning.setText(mCard.getMeaning());
        categoryList.removeAll(categoryList);
        for(String i: mCard.getCategory()) {
            addChip(category, categoryList, i);
        }
        difficulty.setSelection(mCard.getDifficulty());
    }

    private void addChip(final ChipGroup chipGrp, final List<String> categoryList, final String text) {
        Chip chip = new Chip(CardEditActivity.this);
        chip.setText(text);
        chip.setCloseIcon(getDrawable(R.drawable.ic_cancel_black_24dp));
        chip.setCloseIconEnabled(true);

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryList.remove(text);
                chipGrp.removeView(v);
            }
        });

        categoryList.add(text);
        chipGrp.addView(chip, 0);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem next = menu.findItem(R.id.menu_next);
        MenuItem prev = menu.findItem(R.id.menu_prev);
        int lastNoteIndex = DataManager.getInstance().getCards().size()-1;
        next.setEnabled(mPosition < lastNoteIndex);
        prev.setEnabled(mPosition > 0);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_prev) {
            moveCard(-1);
        } else if(id == R.id.menu_next) {
            moveCard(1);
        } else if(id == R.id.menu_save) {
            saveCard();
        } else if(id == R.id.menu_delete) {
            deleteCard();
        }

        return super.onOptionsItemSelected(item);
    }

    private void moveCard(int diff) {
        saveCard();
        mPosition += diff;
        mCard = DataManager.getInstance().getCards().get(mPosition);
        displayCardContent(mWord, mMeaning, mChipGroup, mDifficulty, mCategoryList);
        invalidateOptionsMenu();
    }

    private void saveCard() {
        mIsNewCard = false;
        mCard.setWord(mWord.getText().toString());
        mCard.setMeaning(mMeaning.getText().toString());
        mCard.setCategory(mCategoryList);
        mCard.setDifficulty(mDifficulty.getSelectedItemPosition());
    }

    private void deleteCard() {
        DataManager dm = DataManager.getInstance();
        dm.removeCard(mPosition);
        if(mPosition == dm.getCards().size()) {
            mPosition--;
        }
        mCard = dm.getCards().get(mPosition);
        displayCardContent(mWord, mMeaning, mChipGroup, mDifficulty, mCategoryList);
    }
}
