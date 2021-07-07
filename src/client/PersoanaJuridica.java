package client;

/**
 * Clasa care mosteneste toate trasasturile unui client, si are in plus:
 * companie -- poate fi SA sau SRL
 * venitTotal -- venitul pe care dispune compania persoanei juridice
 */
public class PersoanaJuridica extends Client{
    private Companie companie;
    private double capitalSocial;

    public PersoanaJuridica(){
        super.setNrLicitatiiCastigate(0);
        super.setNrParticipari(0);
    }

    public PersoanaJuridica(int id, String nume, String adresa,
                            Companie companie, double capitalSocial) {
        super(id, nume, adresa);
        this.companie = companie;
        this.capitalSocial = capitalSocial;
    }

    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }
}
