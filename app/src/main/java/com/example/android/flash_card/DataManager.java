package com.example.android.flash_card;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DataManager {
    private static DataManager mInstance = null;
    private List<CardContent> mCards = new ArrayList<>();

    public static DataManager getInstance() {
        if(mInstance == null) {
            mInstance = new DataManager();
            mInstance.createExampleCards();
        }
        return mInstance;
    }

    public List<CardContent> getCards() {
        return mCards;
    }

    public int createNewCard() {
        CardContent newCard = new CardContent();
        mCards.add(newCard);
        return mCards.size()-1;
    }

    public void removeCard(int position) {
        mCards.remove(position);
    }

    public List<String> getCategory() {
        Set<String> hashSet = new HashSet<>();
        List<String> categoryList = new ArrayList<>();

        for(CardContent card: mCards) {
            for(String category: card.getCategory()) {
                hashSet.add(category);
            }
        }

        for(String category: hashSet) {
            categoryList.add(category);
        }

        return categoryList;
    }

    public static List<CardContent> getFilteredCards(List<CardContent> cardLists, String wordPattern) {
        List<CardContent> filteredCardLists = new ArrayList<>();

        for(CardContent card: cardLists) {
            if(card.getWord().toLowerCase().contains(wordPattern)) {
                filteredCardLists.add(card);
            }
        }

        return filteredCardLists;
    }

    public static List<CardContent> getFilteredCards(List<CardContent> cardLists, int difficult, List<String> category) {
        List<CardContent> filteredCardLists = new ArrayList<>();

        for(CardContent card: cardLists) {
            if(card.getDifficulty() == difficult && category.contains(card.getCategory())) {
                filteredCardLists.add(card);
            }
        }

        return filteredCardLists;
    }

    public static List<CardContent> getRandomCards(List<CardContent> cardLists, int difficult, List<String> category, int num) {
        List<CardContent> filteredCard = getFilteredCards(cardLists, difficult, category);
        List<CardContent> randomCard = new ArrayList<>();
        List<Integer> selIndex = Utils.randomIntArray(filteredCard.size(), num);

        for(Integer index : selIndex) {
            randomCard.add(filteredCard.get(index));
        }

        return randomCard;
    }

    public void createExampleCards() {
        mCards.add(new CardContent("Proprietary", "relating to an owner or ownership", Arrays.asList("Business"), 2, 0));
        mCards.add(new CardContent("Amongst", "situated more or less centrally in relation to (several other things)", Arrays.asList("Basic","Adverb"), 0, 0));
        mCards.add(new CardContent("Forgery", "the action of forging a copy or imitation of a document, signature, banknote, or work of art", Arrays.asList("Crime"), 1, 0));
        mCards.add(new CardContent("Counterfeit", "made in exact imitation of something valuable with the intention to deceive or defraud", Arrays.asList("Crime"), 1, 0));
        mCards.add(new CardContent("Inmate", "a person confined to an institution such as a prison or hospital", Arrays.asList("Crime", "Prison", "Person"), 0, 0));
    }
}
