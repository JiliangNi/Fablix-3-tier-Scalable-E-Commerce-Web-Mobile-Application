package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
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

import android.app.AlarmManager;
import java.util.Calendar;
import android.app.PendingIntent;
import android.widget.TextView;

public class LoginPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String result = bundle.getString("result");
            if(result != null && result.trim().equals("false")){
                final TextView invalidText = (TextView) findViewById(R.id.invalidview);
                invalidText.setText("Invalid username or password");
                invalidText.setTextSize(20);
            }
        }
    }

    public void loginToFabflix(View view){

        //

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        EditText emaileditText = (EditText) findViewById(R.id.emailTextBox);
        EditText passwordeditText = (EditText) findViewById(R.id.passwordTextBox);
        String email = emaileditText.getText().toString();
        String pw = passwordeditText.getText().toString();
        String url = "http://52.53.176.245:8080/project22/AndroidLogin?username=" + email + "&" + "password=" + pw;
        //String url = "http://52.34.97.23:8080/project22/AndroidLogin?username=" + email + "&" + "password=" + pw;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if(response.contains("true")) {
                            Intent resultPage = new Intent(LoginPage.this, SearchPage.class);
                            startActivity(resultPage);
                        }
                        else{
                            Intent resultPage = new Intent(LoginPage.this,LoginPage.class);
                            resultPage.putExtra("result", response);
                            startActivity(resultPage);
                        }
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


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return ;
    }


}
