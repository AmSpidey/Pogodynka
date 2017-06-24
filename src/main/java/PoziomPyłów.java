/**
 * Created by Reonora on 2017-06-09.
 */
public class PoziomPyłów {

    private String bazaMikroGramMetrSz25;
    private String bazaMikroGramMetrSz10;

    public PoziomPyłów (String mgm325, String mgm310) {
        this.bazaMikroGramMetrSz25=mgm325;
        this.bazaMikroGramMetrSz10=mgm310;
    }

    public String PyłyMikroGramMetrSz25ToString() {
        return bazaMikroGramMetrSz25;
    }

    public String PyłyMikroGramMetrSz10ToString() {
        return bazaMikroGramMetrSz10;
    }
}
