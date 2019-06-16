package com.example.android.flash_card;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CardListsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private CardRecyclerAdaptor mCardRecyclerAdaptor;
    private RecyclerView mCardRecycler;
    private EditText mWordFiltered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mWordFiltered = (EditText) findViewById(R.id.filter_word);
        mCardRecycler = (RecyclerView) findViewById(R.id.card_lists);

        final List<CardContent> cards = DataManager.getInstance().getCards();

        final GridLayoutManager cardsLayoutManager = new GridLayoutManager(this, 2);
        mCardRecycler.setLayoutManager(cardsLayoutManager);
        mCardRecyclerAdaptor = new CardRecyclerAdaptor(this, cards);
        mCardRecycler.setAdapter(mCardRecyclerAdaptor);

        mWordFiltered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String word = s.toString().toLowerCase();
                List<CardContent> filteredCards = DataManager.getFilteredCards(cards, word);

                mCardRecyclerAdaptor = new CardRecyclerAdaptor(CardListsActivity.this, filteredCards);
                mCardRecycler.setAdapter(mCardRecyclerAdaptor);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wordlist) {

        } else if (id == R.id.nav_modesel) {
            Intent intent = new Intent(this, ModeSelectionActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
