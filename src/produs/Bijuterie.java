package produs;

/**
 * Un tip de produs; Specificatii suplimentare:
 * material -- numele materialului din care este constituit
 * piatraPretioasa -- boolean care are valoare true daca bijuteria are piatra
 * pretioasa, si false altfel
 */
public class Bijuterie extends Produs{
    private String material;
    private boolean piatraPretioasa;

    public Bijuterie() {

    }

    public Bijuterie(String material, boolean piatraPretioasa) {
        this.material = material;
        this.piatraPretioasa = piatraPretioasa;
    }

    /*
        Getteri si setteri sugestivi
     */

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isPiatraPretioasa() {
        return piatraPretioasa;
    }

    public void setPiatraPretioasa(boolean piatraPretioasa) {
        this.piatraPretioasa = piatraPretioasa;
    }
}
