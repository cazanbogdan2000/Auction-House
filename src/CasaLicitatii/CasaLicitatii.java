package CasaLicitatii;

import angajat.Administrator;
import angajat.Angajat;
import angajat.Broker;
import client.Client;
import client.Investitie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import executie.Consola;
import produs.Produs;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clasa ce contine toate datele despre o casa de licitatii; campurile de interes
 * din aceasta clasa sunt:
 * nume -- numele casei de licitatie
 * angajati -- lista tuturor angajatilor din casa de licitatii; aceasta cuprinde
 * atat brokerii, cat si administratorul (se considera un administrator unic,
 * desi implementarea sustine si folosirea mai multor administratori)
 * produse -- lista produselor care se pot initia licitatii si care pot fi
 * castigate
 * clienti -- o lista de clienti care se afla de asemenea inscrisi in casa de
 * licitatii
 * licitatii -- lista de licitatii active; aceasta se creeaza pe parcurs, pe
 * masura ce clientii liciteaza anumite produse
 * ObservatorCasaLicitatii -- design pattern care surprinde anumite evenimente
 * din casa de licitatii actuala (de exemplu, inceputul unei licitatii) si anunta
 * clientii si brokerii de acel eveniment
 */
public class CasaLicitatii {
    private String nume;
    private ArrayList<Angajat> angajati;
    private ArrayList<Produs> produse;
    private ArrayList<Client> clienti;
    private ArrayList<Licitatie> licitatii;

    public ObservatorCasaLicitatii observatorCasaLicitatii
            = new ObservatorCasaLicitatii(this);

    public CasaLicitatii() {
        produse = new ArrayList<>();
        clienti = new ArrayList<>();
        licitatii = new ArrayList<>();
        angajati = new ArrayList<>();
    }

    public void ataseazaObservator(ObservatorCasaLicitatii observatorCasaLicitatii) {
        this.observatorCasaLicitatii = observatorCasaLicitatii;
    }

    public void notificaObservator() {
        observatorCasaLicitatii.actualizeaza();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public ArrayList<Produs> getProduse() {
        return produse;
    }

    public void setProduse(ArrayList<Produs> produse) {
        this.produse = produse;
    }

    public ArrayList<Client> getClienti() {
        return clienti;
    }

    public void setClienti(ArrayList<Client> clienti) {
        this.clienti = clienti;
    }

    public ArrayList<Licitatie> getLicitatii() {
        return licitatii;
    }

    public void setLicitatii(ArrayList<Licitatie> licitatii) {
        this.licitatii = licitatii;
    }

    public ArrayList<Angajat> getAngajati() {
        return angajati;
    }

    public void setAngajati(ArrayList<Angajat> angajati) {
        this.angajati = angajati;
    }

    /**
     * Metoda care porneste o casa de licitatii, si ruleaza comenzile primite,
     * atat pe cele pentru clienti, cat si cele pentru
     * @param numarInput numarul testului care se doreste sa fie rulat
     */
    public void startZiLucratoare(String numarInput) {
        try {
            Consola.citesteComenzi(this, numarInput);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda care asociaza unui client un broker
     * @param client clientul caruia i se va asocia un broker
     */
    public void asociazaBroker(Client client) {
        Angajat broker;
        if(client.getBroker() == null) {
            do {
                broker = this.getAngajati().get(new Random().nextInt(
                        this.getAngajati().size()
                ));
            } while (!(broker instanceof Broker));
            client.setBroker((Broker) broker);
            ((Broker) broker).adaugaClient(client);
        }
    }

    /**
     * Metoda care trimite un mesaj unui broker
     * @param broker brokerul caruia i se va trimite mesajul
     * @param mesaj mesajul ce se va trimite brokerului
     */
    @Test
    @DisplayName("Mesaj de trimis inexistent")
    public static void trimiteMesaj(Broker broker, String mesaj) {
        Assertions.assertFalse(mesaj == null || mesaj.equals(""));
        broker.getMesajeNecitite().add(mesaj);
    }

    /**
     * Metoda care alege clientul castigator, adica clientul care a licitat
     * cel mai mult pentru un anumit produs
     * @param sumaMaxima suma maxima care se afla in dreptul unei licitatii
     * @param licitatie licitatia pentru care se verifica pretul maxim
     * @return clientul castigator al acestei licitatii
     */
    public Client clientCastigator(double sumaMaxima, Licitatie licitatie) {
        double maxim = 0;
        Client clientCastigator = null;
        for(Angajat broker : this.getAngajati()) {
            if (broker instanceof Administrator) {
                continue;
            }
            for(Client client : ((Broker)broker).getListaClienti()) {
                for(Investitie investitie : client.getListaInvestitii()) {
                    if(investitie.getIdLicitatie() == licitatie.getId()) {
                        if(investitie.getSumaInvestita() > maxim) {
                            maxim = investitie.getSumaInvestita();
                            clientCastigator = client;
                        }
                    }
                }
            }
        }
        return clientCastigator;
    }

    /**
     * Metoda care porneste o licitatie in momentul in care se atinge numarul
     * de participanti pentru respectiva licitatie; dupa incheierea acestei
     * licitatii, se revine totul la normal, restul clientilor isi primesc
     * inapoi banii, iar produsul si licitatia sunt eliminate din cadrul casei
     * de licitatie
     * @param licitatie licitatia care s-a initiat
     */
    public void startLicitatie(Licitatie licitatie) {
        double sumaMaxima = 0;
        for(Angajat angajat : this.getAngajati()) {
            if(angajat instanceof Broker) {
                CasaLicitatii.trimiteMesaj((Broker) angajat,
                        "A inceput licitatia pentru produsul cu id-ul " +
                        licitatie.getIdProdus());
            }
        }
        // Se notifica clientii si angajatii de inceperea licitatiei
        this.notificaObservator();
        System.out.println(licitatie.getIdProdus() + "\n");
        double sumaMaximaPasCurent;
        for(int i = 0; i < licitatie.getNrPasiMaxim(); i++) {
            for(Angajat broker : this.getAngajati()) {
                if(broker instanceof Administrator) {
                    continue;
                }
                sumaMaximaPasCurent =
                        ((Broker) broker).licitatieCastigatoare(licitatie);
                if(sumaMaxima < sumaMaximaPasCurent) {
                    sumaMaxima = sumaMaximaPasCurent;
                }
            }
            System.out.println();
        }
        // Se selecteaza castigatorul acestei licitatii, urmand sa fie anuntat
        // si brokerul sa ii aplice comisionul cerut
        Client castigator = this.clientCastigator(sumaMaxima, licitatie);
        if(castigator == null) {
            System.out.println("Nu exista castigator la aceasta licitatie\n");
        }
        else {
            System.out.println("Castigatorul licitatiei este clientul cu " +
                    "id-ul " + castigator.getId() + " si numele " +
                    castigator.getNume());
            castigator.setNrLicitatiiCastigate(
                    castigator.getNrLicitatiiCastigate() + 1
            );
            for(Investitie investitie : castigator.getListaInvestitii()) {
                if(investitie.getIdLicitatie() == licitatie.getId()) {
                    castigator.getListaInvestitii().remove(investitie);
                    break;
                }
            }
            Broker brokerCastigator = castigator.getBroker();
            brokerCastigator.calculComision(castigator, sumaMaxima);
            brokerCastigator.stergereProdus(this, licitatie);
        }
        // Brokerii returneaza banii celorlalti participanti, care au investit
        // in licitatie, insa nu au castigat-o
        for(Angajat broker : this.getAngajati()) {
            if(broker instanceof Broker) {
                ((Broker) broker).returneazaBani(licitatie);
            }
        }
    }
}
