package com.example.MovieSearch.view;

import com.example.MovieSearch.service.MovieService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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

import java.util.ArrayList;
import java.util.List;


@Route("")
@CssImport("./style.css")
@CssImport(value = "./text-field.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    @Autowired
    MovieService movieService;

    private Label title;
    private Label year;
    private Label runtime;
    private Label plot;
    private TextField movieTitle;
    private Button multipleSearch;
    private Image image;
    private Icon iconForw;
    private Icon iconBackw;
    private int count;
    private Label meta;
    private Label imdb;
    private Label imdbvotes;
    private Label posterCount;


    Notification notification = new Notification("Center notification", 3000, Notification.Position.MIDDLE);


    public MainView() throws JSONException, IOException {
        createHeader();
        searchForm();
        movieDataView();


        multipleSearch.addClickListener(ClickEvent -> {
            if (!movieTitle.getValue().equals("")) {
                try {
                    iconForw.setVisible(true);
                    iconBackw.setVisible(true);
                    mainUpdate();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                iconForw.setVisible(false);
                iconBackw.setVisible(false);
                notification.show("please enter  valid MovieTitle");
            }


        });
        count = 1;
        iconForw.addClickListener(ClickEvent -> {

            try {
                update();
                count = count + 1;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                notification.show("A lista végére értél!");
                count = 9;
            }
        });
        iconBackw.addClickListener(ClickEvent -> {
            //PostersSteping(count);
            try {
                update();
                count = count - 1;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                notification.show(" A lista végére értél");
                count = 0;
            }
        });
    }


    public void createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        H1 mainTitle = new H1("Vaadin Movie app");
        header.addClassName("header-style");

        header.setSizeFull();
        header.add(mainTitle);
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        add(header);

    }

    public void searchForm() {
        VerticalLayout mainview = new VerticalLayout();


        movieTitle = new TextField("please enter a movie title");
        multipleSearch = new Button("Search");
        multipleSearch.addClickShortcut(Key.ENTER);

        movieTitle.setWidth("30%");
        multipleSearch.setThemeName("primary");


        mainview.setJustifyContentMode(JustifyContentMode.CENTER);
        mainview.setAlignItems(Alignment.CENTER);
        mainview.add(movieTitle, multipleSearch);
        add(mainview);

    }

    public void movieDataView() {
        HorizontalLayout mainBody = new HorizontalLayout();
        VerticalLayout movieView = new VerticalLayout();

        VerticalLayout moviePosterView = new VerticalLayout();
        HorizontalLayout iconLayout = new HorizontalLayout();
        HorizontalLayout ratingView = new HorizontalLayout();

        addClassName("moviedata-style");

        //Poszter és az ikonok megjelenenítése

        image = new Image();
        iconForw = new Icon(VaadinIcon.CHEVRON_CIRCLE_RIGHT);
        iconForw.setVisible(false);
        iconBackw = new Icon(VaadinIcon.CHEVRON_CIRCLE_LEFT);
        iconBackw.setVisible(false);
        posterCount = new Label();
        Image posterImages = new Image();

        mainBody.setSizeFull();
        moviePosterView.setSizeFull();
        posterImages.setSizeFull();
        posterImages.setMaxHeight("505");
        posterImages.setMaxWidth("500");
        //Film adatok megjelenitése

        title = new Label();
        year = new Label();
        Label rated = new Label();
        runtime = new Label();
        meta = new Label();
        imdb = new Label();
        imdbvotes = new Label();
        plot = new Label();

        moviePosterView.setMaxWidth("50");
        moviePosterView.setHeight("50");
        movieView.setMaxWidth("50");
        movieView.setMaxHeight("50");
        iconLayout.setWidth("25");

        moviePosterView.setJustifyContentMode(JustifyContentMode.CENTER);
        moviePosterView.setAlignItems(Alignment.CENTER);
        moviePosterView.setPadding(true);
        moviePosterView.setMargin(true);


        movieView.setJustifyContentMode(JustifyContentMode.CENTER);
        movieView.setAlignItems(Alignment.CENTER);


        mainBody.setJustifyContentMode(JustifyContentMode.CENTER);
        mainBody.setAlignItems(Alignment.CENTER);

        ratingView.add(meta, imdb, imdbvotes);
        movieView.add(title, year, rated, runtime, plot, ratingView);
        iconLayout.add(iconBackw, iconForw, posterCount);
        moviePosterView.add(image, posterImages, iconLayout);


        mainBody.add(moviePosterView, movieView);
        add(mainBody);
    }

   /* public void PosterView() {
        VerticalLayout postview = new VerticalLayout();
        HorizontalLayout countview = new HorizontalLayout();
        HorizontalLayout icons = new HorizontalLayout();

        postview.addClassName("poster-style");
        image = new Image();
        iconForw = new Icon(VaadinIcon.CHEVRON_CIRCLE_RIGHT);
        iconForw.setVisible(false);
        iconBackw = new Icon(VaadinIcon.CHEVRON_CIRCLE_LEFT);
        iconBackw.setVisible(false);
        posterCount = new Label();
        posterImages = new Image();

        icons.add(iconBackw, iconForw);

        countview.setJustifyContentMode(JustifyContentMode.CENTER);
        countview.setAlignItems(Alignment.CENTER);
        countview.add(posterCount);

        postview.setJustifyContentMode(JustifyContentMode.CENTER);
        postview.setAlignItems(Alignment.CENTER);
        postview.add(image, icons, countview);
        add(postview);
    }
*/

    public void update() throws JSONException, IOException {
        //-- Plakát váltás közben, a listában lévő cím alapján, lekéri a filmek adatait.
        String mTitle = movieNameList.get(count);
        try {
            movieService.setTitle(mTitle);

            JSONObject mainObject = movieService.getMovieData();

            String movieCaption = mainObject.getString("Title");
            title.setText(movieCaption);

            int releaseYear = mainObject.getInt("Year");
            year.setText("movie realase date: " + releaseYear);

            String movieRunTime = mainObject.getString("Runtime");
            runtime.setText(movieRunTime);

            String moviePlot = mainObject.getString("Plot");
            plot.setText(moviePlot);

            posterCount.setText(count + 1 + "/10");
            String posterke = mainObject.getString("Poster");
            image.setSrc(posterke);

            String metascore = mainObject.getString("Metascore");
            meta.setText("Metascore: " + metascore + " /100");
            String imdbRating = mainObject.getString("imdbRating");
            imdb.setText("IMDB rating: " + imdbRating + " /10");
            String imdbVotes = mainObject.getString("imdbVotes");
            imdbvotes.setText("Imdb Vote count: " + imdbVotes);
        } catch (Exception e) {
            notification.show("Nem találtunk több filmet ilyen címmel");
        }
    }

    private List<String> posterList = new ArrayList<>();
    private List<String> movieNameList = new ArrayList<>();

    public void mainUpdate() throws JSONException, IOException {

        String mTitle = movieTitle.getValue();
        try {
    //--- az első film megjelenítése a MovieDataViewnél
            movieService.setTitle(mTitle);
            JSONObject mainObject = movieService.getMovieData();

            String movieCaption = mainObject.getString("Title");
            title.setText(movieCaption);

            int releaseYear = mainObject.getInt("Year");
            year.setText("movie realase date: " + releaseYear);

            String movieRunTime = mainObject.getString("Runtime");
            runtime.setText("Duration: " + movieRunTime);

            String moviePlot = mainObject.getString("Plot");
            plot.setText("Plot: " + moviePlot);

            String poster = mainObject.getString("Poster");
            image.setSrc(poster);

            String metascore = mainObject.getString("Metascore");
            meta.setText("Metascore: " + metascore + " /100");
            String imdbRating = mainObject.getString("imdbRating");
            imdb.setText("IMDB rating: " + imdbRating + " /10");
            String imdbVotes = mainObject.getString("imdbVotes");
            imdbvotes.setText("Imdb Vote count: " + imdbVotes);


//-------------------------------------------------------------------------------------------------
        //leszedett 10 film és posztereik listába pakolása

            JSONObject multipleMainObject = movieService.getMultipleMovieData();

            JSONArray outMovie = new JSONArray();
            JSONArray multipleMovies = multipleMainObject.getJSONArray("Search");

            for (int i = 0; i < multipleMovies.length(); i++) {

                outMovie.put(multipleMovies.getJSONObject(i).getString("Title"));
            }
            JSONArray outPosters = new JSONArray();
            for (int i = 0; i < multipleMovies.length(); i++) {
                outPosters.put(multipleMovies.getJSONObject(i).getString("Poster"));

            }
            posterList = new ArrayList<>();
            for (int i = 0; i < outPosters.length(); i++) {
                posterList.add(outPosters.getString(i));
            }

            movieNameList = new ArrayList<>();
            for (int i = 0; i < outMovie.length(); i++) {
                movieNameList.add(outMovie.getString(i));
            }
            count = 1;
            posterCount.setText(count + " /10");

        } catch (Exception e) {
            iconForw.setVisible(false);
            iconBackw.setVisible(false);
            notification.show("The movie you requested,dont exits in this Database, try a new move title");
        }

    }
    /* public void PostersSteping(int count) {

        String source = "";
        String mTitle  = "";

        source = posterList.get(count);
        movieTitle.setText(source)
        mTitle = movieNameList.get(count);
        movieTitle.setText(mTitle);
}*/
}

