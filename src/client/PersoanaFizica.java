package client;

/**
 * Clasa care mosteneste toate trasasturile unui client, si are in plus:
 * dataNastere -- ceva irelevant (momentan) pentru functionalitatea programului
 * venitTotal -- venitul total de care dispune un client
 */
public class PersoanaFizica extends Client {
    private String dataNastere;
    private double venitTotal;

    public PersoanaFizica(){
        super.setNrLicitatiiCastigate(0);
        super.setNrParticipari(0);
    }

    public PersoanaFizica(int id, String nume, String adresa,
                          String dataNastere, double venitTotal) {
        super(id, nume, adresa);
        this.dataNastere = dataNastere;
        this.venitTotal = venitTotal;
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    public double getVenitTotal() {
        return venitTotal;
    }

    public void setVenitTotal(double venitTotal) {
        this.venitTotal = venitTotal;
    }
}
