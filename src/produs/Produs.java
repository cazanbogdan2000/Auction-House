package produs;

/**
 * Clasa produs; detine toate detaliile majore despre un produs aflat in casa de
 * lictitatii; aceste caracteristici sunt:
 * id -- numarul de identificare al produsului
 * nume -- numele produsului in fapt
 * pretVanzare -- pretul cu care a fost vandut produsul in urma licitatiei
 * pretMinim -- pretul minim pentru care acest produs poate fi vandut
 */
public abstract class Produs {
    private int id;
    private String nume;
    private double pretVanzare;
    private double pretMinim;

    public Produs() {
        pretVanzare = 0;
    }

    /*
        Getteri si setteri sugestivi
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPretVanzare() {
        return pretVanzare;
    }

    public void setPretVanzare(double pretVanzare) {
        this.pretVanzare = pretVanzare;
    }

    public double getPretMinim() {
        return pretMinim;
    }

    public void setPretMinim(double pretMinim) {
        this.pretMinim = pretMinim;
    }
}
