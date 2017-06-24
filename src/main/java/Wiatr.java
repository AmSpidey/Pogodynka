/**
 * Created by Reonora on 2017-06-09.
 */
public class Wiatr {

    private String stopnie;
    private String prędkośćms;

    public Wiatr(String stopnie, String prędkość) {
        this.stopnie=stopnie;
        this.prędkośćms =prędkość;
    }

    public String wStopniach() {
        return stopnie;
    }
    public String prędkośćKmH() {return prędkośćms;};

    public String wKierunku() {
        return "unfinishedWiatr";
    }

}
