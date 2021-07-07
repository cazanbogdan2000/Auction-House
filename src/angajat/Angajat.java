package angajat;

/**
 * Clasa care detine toate caracteristicile unui angajat;
 * nume -- numele angajatului
 * experienta -- anii de experienta pe care acesta ii are in domeniu
 * notaConcurs -- nota obtinuta pe post, in urma unui concurs
 */
public abstract class Angajat {
    private String nume;
    private int experienta;
    private double notaConcurs;

    public Angajat(String nume, int experienta, double notaConcurs) {
        this.nume = nume;
        this.experienta = experienta;
        this.notaConcurs = notaConcurs;
    }
    /*
        Getteri si setteri sugestivi
 */

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getExperienta() {
        return experienta;
    }

    public void setExperienta(int experienta) {
        this.experienta = experienta;
    }

    public double getNotaConcurs() {
        return notaConcurs;
    }

    public void setNotaConcurs(double notaConcurs) {
        this.notaConcurs = notaConcurs;
    }

    public abstract double calculSalariu();
}
