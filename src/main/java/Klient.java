import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by Reonora on 2017-06-10.
 */
public class Klient {


    public static Pogoda połączZMeteo() throws Exception {
        Connection connect = Jsoup.connect("http://www.meteo.waw.pl/");
        Document document = connect.get();
        Elements temperatura = document.select("#PARAM_TA");
        Elements ciśnienie = document.select("#PARAM_PR");
        Elements wilgotność = document.select("#PARAM_RH");
        Elements stopnie = document.select("#PARAM_WD");
        Elements prędkość = document.select("#PARAM_WP10");

        Temperatura temp = new Temperatura(temperatura.text());
        Ciśnienie ciśn = new Ciśnienie(ciśnienie.text());
        Wilgotność wilg = new Wilgotność(wilgotność.text());
        Wiatr wia = new Wiatr(stopnie.text(), prędkość.text());
        PoziomPyłów pyły = połączZPowietrze();

        return new Pogoda(ciśn, pyły, wia, wilg, new Zachmurzenie("-"), temp);

    }

    public static Pogoda połączZOpenWeather() throws Exception {

        Connection połączenie = Jsoup.connect("http://api.openweathermap.org/data/2.5/weather?q=Warsaw&APPID=35665e8a7d12fd459c5fdd0abaa4c5e2");
        String dokument = połączenie.ignoreContentType(true).execute().body();

        org.json.simple.parser.JSONParser parses = new org.json.simple.parser.JSONParser();

        JSONObject obj = (JSONObject) parses.parse(dokument);
        JSONObject main = (JSONObject) obj.get("main");
        String temperatura = main.get("temp").toString();
        String ciśnienie = main.get("pressure").toString();
        String wilgotność = main.get("humidity").toString();

        JSONObject wind = (JSONObject) obj.get("wind");
        String prędkość = wind.get("speed").toString();
        String kierunek = wind.get("deg").toString();
        JSONObject clouds = (JSONObject) obj.get("clouds");
        String zachmurzenie = clouds.get("all").toString();

        Wiatr wiatr = new Wiatr(kierunek, prędkość);
        Temperatura temp = new Temperatura(temperatura, JednostkaTemperatury.KELWIN);
        Wilgotność wilg = new Wilgotność(wilgotność);
        Ciśnienie ciś = new Ciśnienie(ciśnienie);
        Zachmurzenie zach = new Zachmurzenie(zachmurzenie);


        PoziomPyłów pyły = połączZPowietrze();

        return new Pogoda(ciś, pyły, wiatr, wilg, zach, temp);

    }

    public static PoziomPyłów połączZPowietrze() throws IOException, ParseException, Exception {
        Connection połączenie = Jsoup.connect("http://powietrze.gios.gov.pl/pjp/current/getAQIDetailsList?param=AQI");
        String dokument = połączenie.ignoreContentType(true).execute().body();
        org.json.simple.parser.JSONParser parses = new org.json.simple.parser.JSONParser();
        Object obj = parses.parse(dokument);

        JSONArray array = (JSONArray) obj;
        JSONObject obiekt = (JSONObject) array.get(1);

        for (int i = 0; i <= array.size() - 1; i++) {
            obiekt = (JSONObject) array.get(i);
            if (obiekt.get("stationName").equals("Warszawa, ul. Marszałkowska 68")) {
                break;
            }
        }

        if (!obiekt.get("stationName").equals("Warszawa, ul. Marszałkowska 68")) {
            throw new Exception();
        }
        JSONObject obiekt2 = (JSONObject) obiekt.get("values");
        PoziomPyłów pyły = new PoziomPyłów(obiekt2.get("PM2.5").toString(),
                obiekt2.get("PM10").toString());

        return pyły;
    }
}
