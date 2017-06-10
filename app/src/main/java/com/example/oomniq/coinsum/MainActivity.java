package com.example.oomniq.coinsum;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oomniq.coinsum.adapters.CoinAdapter;
import com.example.oomniq.coinsum.models.Coin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RequestQueue requestQueue;
    private List<Coin> coins = new ArrayList<>();
    private RecyclerView coinRecyclerView;
    private CoinAdapter coinAdapter;
    private Context context;
    private ProgressBar progessBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        coinRecyclerView = (RecyclerView) findViewById(R.id.coinList);
        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);


        StringRequest grabCoinsRequest = new StringRequest(Request.Method.GET,
                "https://www.cryptocompare.com/api/data/coinlist/",
                new CoinListLoader(), new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("My", error.toString());
            }
        });
        requestQueue.add(grabCoinsRequest);

        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        coinAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        coinAdapter.filter(newText);
        return true;
    }

    private class CoinListLoader implements Response.Listener<String> {

        @Override
        public void onResponse(String response) {
            Log.d("My", response);
            try {
                JSONObject jsonResponse = new JSONObject(response);
                String baseUrl = jsonResponse.getString("BaseImageUrl");
                Coin.setBaseImageUrl(baseUrl);
                JSONObject data = jsonResponse.getJSONObject("Data");
                Iterator<String> iterator = data.keys();
                while (iterator.hasNext()) {
                    JSONObject jsonCoin = data.getJSONObject(iterator.next());
                    Log.d("My", "onResponse: " + jsonCoin.toString());
                    Coin coin = new Coin();
                    coin.setId(jsonCoin.getLong("Id"));
                    coin.setTicker(jsonCoin.getString("Name"));
                    coin.setCoinName(jsonCoin.getString("CoinName"));
                    coin.setFullName(jsonCoin.getString("FullName"));
                    if (jsonCoin.has("ImageUrl")) {
                        coin.setImageUrl(jsonCoin.getString("ImageUrl"));
                    } else {
                        coin.setImageUrl("");
                    }
                    coin.setSortOrder(jsonCoin.getInt("SortOrder"));
                    coins.add(coin);
                }
                coinAdapter = new CoinAdapter(context,coins);
                coinRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                coinRecyclerView.setHasFixedSize(true);
                coinRecyclerView.setAdapter(coinAdapter);
                coinRecyclerView.setVisibility(View.VISIBLE);
                progessBar.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
