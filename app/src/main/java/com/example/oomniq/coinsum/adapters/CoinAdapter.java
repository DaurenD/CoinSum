package com.example.oomniq.coinsum.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oomniq.coinsum.R;
import com.example.oomniq.coinsum.models.Coin;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by OomniQ on 10.06.2017.
 */

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinHolder> {
    private List<Coin> dataSet;
    private List<Coin> allDataSet;
    private Context context;

    public CoinAdapter(Context context,List<Coin> dataSet){
        this.allDataSet = dataSet;
        this.dataSet = new ArrayList<>();
        for (Coin c : allDataSet) {
            this.dataSet.add(c);
        }
        this.context = context;
        Collections.sort(this.dataSet, new Comparator<Coin>() {
            @Override
            public int compare(Coin o1, Coin o2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return Integer.compare(o1.getSortOrder(), o2.getSortOrder());
                }
                return o1.getSortOrder() > o2.getSortOrder() ? 1 : -1;
            }
        });

        Collections.sort(dataSet, new Comparator<Coin>() {
            @Override
            public int compare(Coin o1, Coin o2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    return Integer.compare(o1.getSortOrder(), o2.getSortOrder());
                }
                return o1.getSortOrder() > o2.getSortOrder() ? 1 : -1;
            }
        });


    }

    @Override
    public CoinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.coin_search_item, null);
        return new CoinHolder(view);
    }

    @Override
    public void onBindViewHolder(CoinHolder holder, int position) {
        Coin coin = dataSet.get(position);
        holder.name.setText(coin.getCoinName());
        holder.ticker.setText(coin.getTicker());
        Picasso.with(context).load(Uri.parse(coin.getImageUrl())).error(R.drawable.no_cover).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void filter(String text) {
        dataSet = new ArrayList<>();
        if(text.isEmpty()){
            dataSet = allDataSet;
        } else{
            text = text.toLowerCase();
            for(Coin coin: allDataSet){
                if(coin.getCoinName().toLowerCase().contains(text) || coin.getTicker().toLowerCase().contains(text)){
                    dataSet.add(coin);
                }
            }
        }
        notifyDataSetChanged();
    }


    class CoinHolder extends RecyclerView.ViewHolder {
        private ImageView logo;
        private TextView name;
        private TextView ticker;

        public CoinHolder(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.coinLogo);
            name = (TextView) itemView.findViewById(R.id.coinName);
            ticker = (TextView) itemView.findViewById(R.id.coinTicker);
        }
    }
}
