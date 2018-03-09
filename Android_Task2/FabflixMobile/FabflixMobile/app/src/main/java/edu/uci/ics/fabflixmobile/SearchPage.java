package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import android.widget.EditText;
import android.os.*;
import android.app.*;
import java.util.*;
import com.android.volley.*;

public class SearchPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    public void connectToFabflix(View view){
        final Map<String, String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(this);
        EditText editText = (EditText)findViewById(R.id.searchTextBox);
        String query = editText.getText().toString().trim();
        if (query.equals("")){

            final TextView invalidText = (TextView) findViewById(R.id.invalidview);
            invalidText.setText("Please enter key word!");
            invalidText.setTextSize(20);

            return;

        }


        String url ="http://52.53.176.245:8080/project22/AndroidMovieList?lim=5&pn=0&pre_title="+query;
        //String url = "http://52.34.97.23:8080/project22/AndroidMovieList?lim=5&pn=0&pre_title="+query;


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Intent resultPage = new Intent(SearchPage.this, SearchResultPage.class);
                        resultPage.putExtra("result", response);
                        startActivity(resultPage);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

        };

        //postRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(postRequest);


        return ;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
