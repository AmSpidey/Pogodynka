/**
 * Created by Reonora on 2017-06-09.
 */
public class Pogoda {

    private Ciśnienie ciśnienie;
    private PoziomPyłów pyły;
    private Wiatr wiatr;
    private Wilgotność wilgoć;
    private Zachmurzenie chmury;
    private Temperatura temp;

    Pogoda (Ciśnienie ciśnienie, PoziomPyłów pyły, Wiatr wiatr, Wilgotność wilgotność, Zachmurzenie zachmurzenie, Temperatura temp) {
        this.chmury = zachmurzenie;
        this.wiatr = wiatr;
        this.pyły = pyły;
        this.wilgoć = wilgotność;
        this.temp = temp;
        this.ciśnienie = ciśnienie;
    }


    public Ciśnienie getCiśnienie() {
        return ciśnienie;
    }

    public PoziomPyłów getPyły() {
        return pyły;
    }

    public Wiatr getWiatr() {
        return wiatr;
    }

    public Zachmurzenie getZachmurzenie() {
        return chmury;
    }

    public Temperatura getTemperatura() {
        return temp;
    }

    public Wilgotność getWilgotność() { return wilgoć;}

}
