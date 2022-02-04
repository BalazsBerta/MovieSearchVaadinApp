package com.example.MovieSearch.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.json.JSONException;

import java.io.IOException;

@Route("main")
public class MainScreen extends VerticalLayout {
    private Label ellenorz;


    public  MainScreen() throws IOException, JSONException{
        CreateHeader();
    }

    public void CreateHeader(){
        HorizontalLayout header = new HorizontalLayout();

        H1 title = new H1("Vaadin Movie app");


        header.setWidth("100%");
        header.add(title);
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        add(header);

    }
    public void MainMovieScreen(){

    }
}
