package com.example.android.flash_card;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ModeSelectionActivity extends AppCompatActivity {
    private final List<String> mCategoryList = new ArrayList<String>();
    private Spinner mDifficulty;
    private Spinner mCategory;
    private ChipGroup mChipGroup;
    private Spinner mMode;
    private Spinner mWordNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDifficulty = (Spinner) findViewById(R.id.mode_difficult);
        mCategory = (Spinner) findViewById(R.id.mode_category);
        mChipGroup = (ChipGroup) findViewById(R.id.mode_chips);
        mMode = (Spinner) findViewById(R.id.mode_sel);
        mWordNum = (Spinner) findViewById(R.id.mode_num);
        Button confirm = (Button) findViewById(R.id.mode_confirm);

        mCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addChip(mChipGroup, mCategoryList, mCategory.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> diffSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.word_difficult, android.R.layout.simple_spinner_item);
        diffSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDifficulty.setAdapter(diffSpinnerAdapter);

        ArrayAdapter<CharSequence> modeSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.mode_sel, android.R.layout.simple_spinner_item);
        modeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMode.setAdapter(modeSpinnerAdapter);

        ArrayAdapter<CharSequence> numSpinnerAdapter =
                ArrayAdapter.createFromResource(this, R.array.num_sel, android.R.layout.simple_spinner_item);
        numSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWordNum.setAdapter(numSpinnerAdapter);

        displayCategory(mCategory);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMode.getSelectedItemId() == 0) {
                    Intent intent = new Intent(ModeSelectionActivity.this, CardStudyActivity.class);
                    intent.putExtra(CardStudyActivity.CARD_DIFFICULTY, mDifficulty.getSelectedItemId());
                    intent.putExtra(CardStudyActivity.CARD_WORDNUM, mWordNum.getSelectedItemId());
                    intent.putExtra(CardStudyActivity.CARD_CATEGORY, new ArrayList<String>(mCategoryList));
                    ModeSelectionActivity.this.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ModeSelectionActivity.this, CardTestActivity.class);
                    intent.putExtra(CardTestActivity.CARD_DIFFICULTY, mDifficulty.getSelectedItemId());
                    intent.putExtra(CardTestActivity.CARD_WORDNUM, mWordNum.getSelectedItemId());
                    intent.putExtra(CardTestActivity.CARD_CATEGORY, new ArrayList<String>(mCategoryList));
                    ModeSelectionActivity.this.startActivity(intent);
                }
            }
        });
    }

    private void addChip(final ChipGroup chipGrp, final List<String> categoryList, final String text) {
        if(text == "Select Category" || categoryList.contains(text)) {
            return;
        }

        Chip chip = new Chip(ModeSelectionActivity.this);
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

    private void displayCategory(Spinner category) {
        List<String> categoryList = DataManager.getInstance().getCategory();
        categoryList.add(0, "Select Category");

        ArrayAdapter<String> categorySpinnerAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categorySpinnerAdapter);
    }
}
