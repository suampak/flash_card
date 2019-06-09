package com.example.android.flash_card;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class CardListsActivity extends AppCompatActivity {

    private CardRecyclerAdaptor mCardRecyclerAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_lists);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        final RecyclerView recyclerCards = (RecyclerView) findViewById(R.id.card_lists);
        final GridLayoutManager cardsLayoutManager = new GridLayoutManager(this, 2);
        recyclerCards.setLayoutManager(cardsLayoutManager);

        List<CardContent> cards = DataManager.getInstance().getCards();
        mCardRecyclerAdaptor = new CardRecyclerAdaptor(this, cards);
        recyclerCards.setAdapter(mCardRecyclerAdaptor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardRecyclerAdaptor.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add) {
            startActivity(new Intent(CardListsActivity.this, CardEditActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
