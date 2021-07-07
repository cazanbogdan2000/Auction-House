package produs;

/**
 * Un tip de produs; Specificatii suplimentare:
 * material -- numele materialului din care este constituit
 * tip -- tipul mobilierului
 */
public class Mobila extends Produs{
    private String tip;
    private String material;

    public Mobila() {

    }

    public Mobila(String tip, String material) {
        this.tip = tip;
        this.material = material;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
