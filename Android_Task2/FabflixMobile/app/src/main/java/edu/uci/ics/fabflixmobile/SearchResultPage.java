package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.*;
import com.google.gson.reflect.*;
import android.widget.*;
import android.graphics.Color;
import android.view.View;
import android.graphics.*;
import android.util.Log;

public class SearchResultPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {

            String result = bundle.getString("result");
            if (result != null) {

                Log.d("Response", result);
                AndroidClass listOfMoviesFromServer = new Gson().fromJson(result, new TypeToken<AndroidClass>() {
                }.getType());

                TextView titleQueryTextViewLayout = (TextView) findViewById(R.id.titleQueryTextView);
                titleQueryTextViewLayout.setText("Results For Query: \"" + listOfMoviesFromServer.getTitleQuery() + "\"");
                titleQueryTextViewLayout.setTextSize(30);
                titleQueryTextViewLayout.setTypeface(null, Typeface.BOLD);
                TextView movieResultPageLayout = (TextView) findViewById(R.id.movieSearchResultLinearLayout);

                if (listOfMoviesFromServer.getListOfMovies().size() != 0) {

                    String all = "-------------------------------------\n";
                    int num = listOfMoviesFromServer.getNumberOfMovies();
                    if (listOfMoviesFromServer.getNumberOfMovies() >= 5) {
                        num = 5;
                    }
                    for (int i = 0; i < num; i++) {

                        String title = "Title: ";
                        title += listOfMoviesFromServer.getListOfMovies().get(i).getTitle();
                        title += "\n";
                        String year = "Year: ";
                        year += listOfMoviesFromServer.getListOfMovies().get(i).getYear();
                        year += "\n";
                        String director = "Director: ";
                        director += listOfMoviesFromServer.getListOfMovies().get(i).getDirector();
                        director += "\n";
                        String genres = "Genres: ";
                        genres += listOfMoviesFromServer.getListOfMovies().get(i).getGenres();
                        genres += "\n";

                        String stars = "Stars: ";
                        stars += listOfMoviesFromServer.getListOfMovies().get(i).getStars();
                        stars += "\n";

                        all += title + year+ director + genres + stars  + "-------------------------------------\n";

                    }
                    movieResultPageLayout.setText(all);
                    movieResultPageLayout.setTextSize(20);

                    Button nextButton = (Button) findViewById(R.id.nextButton);
                    if (listOfMoviesFromServer.getNumberOfMovies()<5)
                        nextButton.setVisibility(View.INVISIBLE);
                    else
                        nextButton.setOnClickListener(new MovieNextButtonOnClickListener(listOfMoviesFromServer.getTitleQuery(),listOfMoviesFromServer.getCurrentPageNumber()));

                    Button previousButton = (Button) findViewById(R.id.previousButton);
                    if (listOfMoviesFromServer.getCurrentPageNumber()==0)
                        previousButton.setVisibility(View.INVISIBLE);
                    else
                        previousButton.setOnClickListener(new MoviePreviousButtonOnClickListener(listOfMoviesFromServer.getTitleQuery(),listOfMoviesFromServer.getCurrentPageNumber()));


                }
                else{
                    movieResultPageLayout.setText("No result");
                    movieResultPageLayout.setTextSize(40);
                    Button nextButton = (Button) findViewById(R.id.nextButton);
                    Button previousButton = (Button) findViewById(R.id.previousButton);
                    nextButton.setVisibility(View.INVISIBLE);
                    previousButton.setVisibility(View.INVISIBLE);
                }

            }

        }
    }

    public void searchPageOnClick(View view) {

        Intent resultPage = new Intent(SearchResultPage.this, SearchPage.class);
        startActivity(resultPage);

    }

}
