package com.example.android.flash_card;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import java.util.List;

public class CardRecyclerAdaptor extends RecyclerView.Adapter<CardRecyclerAdaptor.ViewHolder> {

    private final Context mContext;
    private final List<CardContent> mCards;
    private final LayoutInflater mLayoutInflater;

    public CardRecyclerAdaptor(Context context, List<CardContent> cards) {
        mContext = context;
        mCards = cards;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_card_lists, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardContent card = mCards.get(position);
        holder.mWord.setText(card.getWord());
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mWord;
        public int mCurrentPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            mWord = (TextView) itemView.findViewById(R.id.card_word);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CardEditActivity.class);
                    intent.putExtra(CardEditActivity.CARD_POSITION, mCurrentPosition);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
