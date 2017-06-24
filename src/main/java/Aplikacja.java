import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import rx.observables.JavaFxObservable;

/**
 * Created by Reonora on 2017-06-09.
 */
public class Aplikacja extends Application{

private Interfejs interfejs = new Interfejs();

    public void start(Stage primaryStage){

        interfejs.odśwież();

        int ilośćPól=0;
        primaryStage.setTitle("Pogodynka");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);

        Text tytuł = new Text("Warszawa");
        tytuł.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(tytuł, 0, ilośćPól++, 2, 1);

        final Text błąd = new Text();

        tytuł.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        interfejs.getErrorPołączenia().subscribe(x -> błąd.setText(x.toString()));

        grid.add(błąd, 0, ilośćPól++, 2, 1);

        Label Ciśnienie = new Label("Ciśnienie: "
                + interfejs.getAktualnaPogoda().getCiśnienie().ciśnienieWhPa() + "hPa");
        interfejs.getInformacjaPogodowa().subscribe(i -> Ciśnienie.setText("Ciśnienie: "
                + i.getCiśnienie().ciśnienieWhPa().toString() + "hPa"));
        grid.add(Ciśnienie, 0, ilośćPól++);

        Label Temperatura = new Label("Temperatura: "
                + interfejs.getAktualnaPogoda().getTemperatura().temperaturaWCelcjuszach() + "C");
        interfejs.getInformacjaPogodowa().subscribe(i -> Temperatura.setText("Temperatura: "
                + i.getTemperatura().temperaturaWCelcjuszach() + "C"));
        grid.add(Temperatura, 0, ilośćPól++);

        Label Wilgotność = new Label("Wilgotność: "
                + interfejs.getAktualnaPogoda().getWilgotność().toString() + "%");
        interfejs.getInformacjaPogodowa().subscribe(i -> Wilgotność.setText("Wilgotność: "
                + i.getWilgotność().toString()+ "%"));
        grid.add(Wilgotność, 0, ilośćPól++);

        Label Zachmurzenie = new Label("Zachmurzenie: "
                + interfejs.getAktualnaPogoda().getZachmurzenie().ZachmurzenieToString());
        interfejs.getInformacjaPogodowa().subscribe(i -> Zachmurzenie.setText("Zachmurzenie: "
                + i.getZachmurzenie().ZachmurzenieToString()));
        grid.add(Zachmurzenie, 0, ilośćPól++);

        Label SiłaWiatru = new Label("Siła wiatru: "
                + interfejs.getAktualnaPogoda().getWiatr().prędkośćKmH());
        interfejs.getInformacjaPogodowa().subscribe(i -> SiłaWiatru.setText("Siła wiatru: "
                + i.getWiatr().prędkośćKmH() + "m/s"));
        grid.add(SiłaWiatru, 0, ilośćPól++);

        Label KierunekWiatru = new Label("Kierunek wiatru: "
                + interfejs.getAktualnaPogoda().getWiatr().wStopniach());
        interfejs.getInformacjaPogodowa().subscribe(i -> KierunekWiatru.setText("Kierunek wiatru: "
                + i.getWiatr().wStopniach()));
        grid.add(KierunekWiatru, 0, ilośćPól++);

        Label PoziomPyłów = new Label("Poziom pyłów PM10: "
                + interfejs.getAktualnaPogoda().getPyły().PyłyMikroGramMetrSz10ToString());
        interfejs.getInformacjaPogodowa().subscribe(i -> PoziomPyłów.setText("Poziom pyłów PM10: "
                + i.getPyły().PyłyMikroGramMetrSz10ToString()));
        grid.add(PoziomPyłów, 0, ilośćPól++);

        Label PoziomPyłów25 = new Label("Poziom pyłów PM2.5: "
                + interfejs.getAktualnaPogoda().getPyły().PyłyMikroGramMetrSz25ToString());
        interfejs.getInformacjaPogodowa().subscribe(i -> PoziomPyłów25.setText("Poziom pyłów PM2.5: "
                + i.getPyły().PyłyMikroGramMetrSz25ToString()));
        grid.add(PoziomPyłów25, 0, ilośćPól++);

        Label Data = new Label("Data: "
                + interfejs.getAktualnaData());
        interfejs.getData().subscribe(i -> Data.setText("Data: "
                + i.toString()));
        grid.add(Data, 0, ilośćPól++);

        Button odśwież = new Button("Odśwież");
        JavaFxObservable.actionEventsOf(odśwież).subscribe(ae -> interfejs.odśwież());
        grid.add(odśwież, 0, ilośćPól + 1);

        Button Meteo = new Button("Meteo");
        JavaFxObservable.actionEventsOf(Meteo).subscribe(ae -> interfejs.zmieńŹródło(Źródło.METEO),
                ae -> interfejs.odśwież());
        grid.add(Meteo, 1, ilośćPól + 1);

        Button openWeather = new Button("OpenWeather");
        JavaFxObservable.actionEventsOf(openWeather).subscribe(ae -> interfejs.zmieńŹródło(Źródło.OPEN_WEATHER),
                ae -> interfejs.odśwież());
        grid.add(openWeather, 2, ilośćPól + 1);

        primaryStage.show();

        /**
         * Mierzy czas do nastepnego automatcznego odswiezania.
         */

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.minutes(5),
                ae -> interfejs.odśwież()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public static void main (String args[]) {
        launch(args);
    }


}
