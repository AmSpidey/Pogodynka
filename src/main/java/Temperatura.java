/**
 * Created by Reonora on 2017-06-09.
 */
public class Temperatura {

    private String celcjuszBaza;

    public Temperatura(String temperaturaWCelcjuszach) {
        this.celcjuszBaza = temperaturaWCelcjuszach;
    }

    public String temperaturaWCelcjuszach() {
        return celcjuszBaza;
    }

    /**
     * Tworzenie temperatury zaleznie od jednostki w ktorej dostalismy.
     * Mozna uzupelnic o nowe jednostki.
     * @param temperatura
     * @param jednostka
     */

    public Temperatura(String temperatura, JednostkaTemperatury jednostka) {

        if (jednostka.equals(JednostkaTemperatury.KELWIN)) {
            double kelwiny = Double.parseDouble(temperatura);
            double celcjusze = kelwiny - 273.15;
            String temp = Double.toString(celcjusze);
            this.celcjuszBaza = temp;
        } else {
            this.celcjuszBaza = temperatura;
        }

    }

}
