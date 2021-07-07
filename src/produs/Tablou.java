package produs;

/**
 * Un tip de produs; Specificatii suplimentare:
 * numePictor -- numele pictorului care a realizat aceasta pictura
 * culoare -- culoarea tabloului (ACRILIC, TEMPERA sau ULEI)
 */
public class Tablou extends Produs{
    private String numePictor;
    private Culoare culoare;

    public Tablou() {

    }

    public Tablou(String numePicior, Culoare culori) {
        this.numePictor = numePicior;
        this.culoare = culori;
    }

    public String getNumePictor() {
        return numePictor;
    }

    public void setNumePictor(String numePicior) {
        this.numePictor = numePicior;
    }

    public Culoare getCuloare() {
        return culoare;
    }

    public void setCulori(Culoare culoare) {
        this.culoare = culoare;
    }
}
