package com.example.MovieSearch.view;

import com.example.MovieSearch.service.MovieService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


@Route("")
@CssImport("./style.css")
public class MainView extends VerticalLayout {

    @Autowired
    MovieService movieService;

    private Label title;
    private Label year;
    private Label rated;
    private Label runtime;
    private Label plot;
    private TextField movieTitle;
    private Button movieTitleB;
    private Button multipleSearch;
    private Image image;
    private Grid grid;
    private Label proba;

    Notification notification = new Notification("Center notification", 3000, Notification.Position.MIDDLE);


    public MainView() throws JSONException, IOException {

        CreateHeader();
        SearchForm();
        PosterView();
        MovieDataView();
        PlotView();
        MultipleMovie();

        addClassName("style");

        movieTitleB.addClickListener(ClickEvent -> {
            if (!movieTitle.getValue().equals("")) {
                try {
                    Update();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                notification.show("please enter  valid MovieTitle");
            }
        });

        multipleSearch.addClickListener(ClickEvent -> {
            if (!movieTitle.getValue().equals("")) {
                try {
                    Update2();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                notification.show("please enter  valid MovieTitle");
            }

            /* lint to an another page ->*/ /*UI.getCurrent().navigate("main");*/
        });
    }


    public void CreateHeader() {
        HorizontalLayout header = new HorizontalLayout();
        H1 title = new H1("Vaadin Movie app");
        title.addClassName("header-style");


        header.setWidth("100%");
        header.add(title);
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        add(header);

    }

    public void SearchForm() {
        VerticalLayout mainview = new VerticalLayout();


        movieTitle = new TextField("please enter a movie title");
        movieTitleB = new Button("Search");
        multipleSearch = new Button("Back");
        movieTitle.setWidth("60%");

        movieTitle.addClassName("vaadin-textfield");
        //mainview.getThemeList().set("dark", true);
        //movieTitle.getStyle().set("background-color","#ffffff" );
        mainview.setJustifyContentMode(JustifyContentMode.CENTER);
        mainview.setAlignItems(Alignment.CENTER);
        mainview.add(movieTitle, movieTitleB, multipleSearch);
        add(mainview);

    }

    public void MovieDataView() {
        VerticalLayout movieView = new VerticalLayout();
        title = new Label();
        year = new Label();
        rated = new Label();
        runtime = new Label();

        addClassName("moviedata-style");
        movieView.setJustifyContentMode(JustifyContentMode.CENTER);
        movieView.setAlignItems(Alignment.CENTER);
        movieView.add(title, year, rated, runtime);
        add(movieView);
    }

    public void PlotView() {
        HorizontalLayout plotview = new HorizontalLayout();
        plot = new Label();

        //plotview.getThemeList().set("dark", true);
        plotview.setJustifyContentMode(JustifyContentMode.CENTER);
        plotview.setAlignItems(Alignment.CENTER);
        plotview.add(plot);
        add(plotview);
    }

    public void PosterView() {
        HorizontalLayout postview = new HorizontalLayout();
        postview.addClassName("poster-style");
        image = new Image();


        postview.setJustifyContentMode(JustifyContentMode.CENTER);
        postview.setAlignItems(Alignment.CENTER);
        postview.add(image);
        add(postview);
    }

    public void MultipleMovie() {
        VerticalLayout multipleMovie = new VerticalLayout();
        grid = new Grid();
        proba = new Label();

        multipleMovie.add(proba);
        add(multipleMovie);
    }

    public void Update() throws JSONException, IOException {
        //-----  //PAGINATION soon(tm)//----
        String Mtitle = movieTitle.getValue();

        try {
            movieService.setTitle(Mtitle);

            JSONObject mainObject = movieService.getMovieData();

            String movieTitle = mainObject.getString("Title");
            title.setText(movieTitle);

            int releaseYear = mainObject.getInt("Year");
            year.setText(String.valueOf("movie realase date: " + releaseYear));

            String movieRunTime = mainObject.getString("Runtime");
            runtime.setText(movieRunTime);

            String moviePlot = mainObject.getString("Plot");
            plot.setText(moviePlot);

            String posterke = mainObject.getString("Poster");
            image.setSrc(posterke);

            JSONArray rat = new JSONArray();
            String ratings = null;
            JSONArray ratingsArray = movieService.returnRatings();
            for (int i = 0; i < ratingsArray.length(); i++) {
                JSONObject ratingsObject = ratingsArray.getJSONObject(i);
                //ratings = ratingsObject.getString("Metascore");
                rat.put(ratingsObject);

            }
            //rated.setText(String.valueOf(rat));

            for (int i = 0; i < rat.length(); i++) {
                rated.setText(String.valueOf(rat.getJSONObject(i)));
            }

        } catch (Exception e) {
            notification.show("Enter a real movie title");
        }


    }

    public void Update2() throws JSONException, IOException {
        String Mtitle = movieTitle.getValue();

        try {

            movieService.setTitle(Mtitle);
            JSONArray multipleObject = movieService.returnMultipleMovie();
            proba.setText(String.valueOf(multipleObject));

           /* JSONArray probaArray  = new JSONArray();
            JSONArray moviesArray = new JSONArray();
            moviesArray = movieService.returnMultipleMovie();
            proba.setText(String.valueOf(multipleObject));
            for(int i = 0; i < multipleObject.length(); i++){
                JSONObject movieObject = multipleObject.getJSONObject(i);
                probaArray.put(movieObject);
            }
            for( int i = 0; i < probaArray.length(); i++){
                proba.setText(String.valueOf(probaArray.getJSONObject(i)));
            }*/


        } catch (Exception e) {
            notification.show("Enter a REAL movie title");
        }
    }

}

