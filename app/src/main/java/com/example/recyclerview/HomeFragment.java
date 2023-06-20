package com.example.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {



    public HomeFragment() {
        // Required empty public constructor
    }

    ProgressBar pgBarHome;
    TextView txtNetwork;
    RecyclerView recyclerViewHome;
    NewsRecyclerAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayoutHome;

    int page;
    ArrayList<NewsModel> newsArr = new ArrayList<>();
    String mUrl = "https://content.guardianapis.com/search?api-key=3ff07e32-dc60-45f2-97cc-" +
            "71070a348d7e&show-tags=contributor&show-fields=starRating,headline,thumbnail,sho" +
            "rt-url&show-refinements=all&order-by=newest";
    RequestQueue requestQueue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewHome =(RecyclerView) view.findViewById(R.id.recycler_view_home);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));
        pgBarHome = (ProgressBar) view.findViewById(R.id.pgBarHome);
        txtNetwork = (TextView) view.findViewById(R.id.txt_network_home);
        swipeRefreshLayoutHome = view.findViewById(R.id.homeView);
        txtNetwork.setVisibility(View.GONE);
        requestQueue = Volley.newRequestQueue(getContext());
//        page=1;
//        getNews();
        if(isNetwork(getContext())){
            txtNetwork.setVisibility(View.GONE);
            pgBarHome.setVisibility(View.VISIBLE);
        }else{
            pgBarHome.setVisibility(View.GONE);
            txtNetwork.setText("Connnect To Internet \n and Refresh the page");
            txtNetwork.setVisibility(View.VISIBLE);
        }

        recyclerViewHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean hasEnded = lastVisible + 1 >= totalItemCount;

                if (totalItemCount>0 && hasEnded){
                    //
                    loadNews(++page);
                }
            }
        });

        swipeRefreshLayoutHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutHome.setRefreshing(false);
                onResume();
            }
        });
        return view;
    }

    public void getNews(){
        String url = mUrl ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                //
                pgBarHome.setVisibility(View.GONE);
                try {

                    JSONObject respObject = response.getJSONObject("response");
                    int noOfNews = Integer.parseInt(respObject.getString("pageSize"));
                    JSONArray object1 = respObject.getJSONArray("results");
                    for(int i=0;i<noOfNews;i++){
                        JSONObject object2 = object1.getJSONObject(i);
                        String title = object2.getString("webTitle");
                        JSONObject object3 = object2.getJSONObject("fields");
                        String img = object3.getString("thumbnail");
                        String newsUrl = object2.getString("webUrl");

                        newsArr.add(new NewsModel(title,img,newsUrl));
                    }

                    adapter = new NewsRecyclerAdapter(getContext(),newsArr);
                    recyclerViewHome.setAdapter(adapter);
                    int color = ContextCompat.getColor(getContext(),R.color.newsbackground);
                    recyclerViewHome.setBackgroundColor(color);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onResume() {

        super.onResume();
        if(isNetwork(getContext())){
            txtNetwork.setVisibility(View.GONE);
            if(newsArr.size()==0) {
                pgBarHome.setVisibility(View.VISIBLE);
                page = 1;
                getNews();
            }

        }
        else{
            pgBarHome.setVisibility(View.GONE);
        }
    }

    public static final boolean isNetwork(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifi.isAvailable() && wifi.isConnected()) || (mobile.isAvailable() && mobile.isConnected())){
            return true;
        }
        else if (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    public void loadNews(int page){
        String url = mUrl + "&page=" + Integer.toString(page) ;

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject respObject = response.getJSONObject("response");
                            int noOfNews = Integer.parseInt(respObject.getString("pageSize"));
                            JSONArray object1 = respObject.getJSONArray("results");
                            int lastSeenPage = newsArr.size()-1;
                            for(int i=0;i<noOfNews;i++){
                                JSONObject object2 = object1.getJSONObject(i);
                                String title = object2.getString("webTitle");
                                JSONObject object3 = object2.getJSONObject("fields");
                                String img = object3.getString("thumbnail");
                                String newsUrl = object2.getString("webUrl");

                                newsArr.add(new NewsModel(title,img,newsUrl));
                                adapter.notifyItemInserted(newsArr.size()-1);
                            }
//                            recyclerViewHome.scrollToPosition(lastSeenPage -40);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                txtNetwork.setText("Error");
                pgBarHome.setVisibility(View.GONE);

            }
        });
        requestQueue.add(jsonObjectRequest2);

    }

}