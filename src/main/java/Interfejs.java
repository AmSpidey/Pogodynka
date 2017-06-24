
import rx.subjects.PublishSubject;

/**
 * Created by Reonora on 2017-06-09.
 */
public class Interfejs implements InterfejsAplikacji {

    private Źródło źródło;

    private Pogoda aktualnaPogoda;
    private String aktualnaData;
    private PublishSubject<String> data = PublishSubject.create();
    private PublishSubject<String> errorPołączenia = PublishSubject.create();
    private PublishSubject<Pogoda> informacjaPogodowa = PublishSubject.create();

    Interfejs() {
        Ciśnienie ciś = new Ciśnienie("");
        PoziomPyłów pył = new PoziomPyłów("", "");
        Wilgotność wilg = new Wilgotność("");
        Temperatura temp = new Temperatura("");
        Zachmurzenie zach = new Zachmurzenie("");
        Wiatr wiatr = new Wiatr("", "");

        this.źródło=Źródło.METEO;

        this.aktualnaPogoda = new Pogoda(ciś, pył, wiatr, wilg, zach, temp);
        this.informacjaPogodowa.onNext(aktualnaPogoda);
        this.aktualnaData=Data.obecnaData();
        this.data.onNext(aktualnaData);
    }

    public Pogoda getAktualnaPogoda() {
        return aktualnaPogoda;
    }

    /**
     * Odświeżenie pogody zależnie od połączenia.
     */

    public void odśwież() {
        try {
            if (źródło.equals(Źródło.METEO)) {
                aktualnaPogoda = Klient.połączZMeteo();
                informacjaPogodowa.onNext(aktualnaPogoda);
                aktualnaData=Data.obecnaData();
                data.onNext(aktualnaData);
                errorPołączenia.onNext("");
            } else if (źródło.equals(Źródło.OPEN_WEATHER)) {
                aktualnaPogoda = Klient.połączZOpenWeather();
                informacjaPogodowa.onNext(aktualnaPogoda);
                aktualnaData=Data.obecnaData();
                data.onNext(aktualnaData);
                errorPołączenia.onNext("");
            }
        } catch (Exception e) {
           /* e.printStackTrace();*/
            errorPołączenia.onNext("Błąd połączenia. Spróbuj alternatywnego źródła.");
        }
    }

    /**
     * Zmiana źródła poboru danych.
     * @param noweŹródło
     */

    public void zmieńŹródło(Źródło noweŹródło) {
        źródło = noweŹródło;
        odśwież();
    }

    public PublishSubject<String> getErrorPołączenia() {
        return errorPołączenia;
    }

    public PublishSubject<Pogoda> getInformacjaPogodowa() {
        return informacjaPogodowa;
    }
    public PublishSubject<String> getData() {
        return data;
    }

    public String getAktualnaData() {
        return aktualnaData;
    }
}
