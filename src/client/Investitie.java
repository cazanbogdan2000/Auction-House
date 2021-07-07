package client;

/**
 * Clasa ajutatoare, cate una pentru fiecare client, in care se retin detalii
 * legate de o anumita licitatie; campuri de interes:
 * idLicitatie -- idul licitatiei
 * sumaInvestita -- suma curenta investita pentru aceasta licitatie
 * sumaMaxima -- suma maxima pe care o poate da clientul pentru aceasta licitatie
 */
public class Investitie {
    private int idLicitatie;
    private double sumaInvestita;
    private final double sumaMaxima;

    public Investitie(int idLicitatie, double sumaMaxima) {
        this.idLicitatie = idLicitatie;
        this.sumaMaxima = sumaMaxima;
    }

    public int getIdLicitatie() {
        return idLicitatie;
    }

    public void setIdLicitatie(int idLicitatie) {
        this.idLicitatie = idLicitatie;
    }

    public double getSumaInvestita() {
        return sumaInvestita;
    }

    /**
     * Metoda care geneareaza un pret de start pentru client; euristica aplicata
     * difera de la persoana fizica la o persoana juridica
     * @param client clientul pentru care se doreste a se calcula pretuld de
     *               start
     * @param pretMinim pretul minim pe produsul licitatiei curente
     * @return returneaza pointer catre adresa Investitei nou obtinute
     */
    public Investitie setSumaInvestita(Client client, double pretMinim) {
        if(client instanceof PersoanaFizica) {
            sumaInvestita = 0.9 * pretMinim;
            ((PersoanaFizica) client).setVenitTotal(
                    ((PersoanaFizica) client).getVenitTotal() - sumaInvestita
            );
        }
        else if(client instanceof PersoanaJuridica){
            sumaInvestita = pretMinim;
            ((PersoanaJuridica) client).setCapitalSocial(
                    ((PersoanaJuridica) client).getCapitalSocial() - sumaInvestita
            );
        }
        return this;
    }

    public void crestereSumaInvestita(double crestere) {
        this.sumaInvestita += crestere;
    }

    public double getSumaMaxima() {
        return sumaMaxima;
    }
}
