package angajat;

import CasaLicitatii.CasaLicitatii;
import CasaLicitatii.Licitatie;
import client.Client;
import client.Investitie;
import client.PersoanaFizica;
import client.PersoanaJuridica;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import produs.Produs;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Clasa care reprezinta abstractizarea unui broker; contine in plus niste
 * campuri suplimentare, cum ar fi:
 * mesajeNecitite -- o lista de mesaje pe care broker-ul inca nu le-a parcurs;
 *                   mesajele pot fi trimise de casa de licitatii brokerului
 * listaClienti -- lista de clienti pe care ii are brokerul respectiv
 */
public class Broker extends Angajat {

    private ArrayList<String> mesajeNecitite = new ArrayList<>();
    private ArrayList<Client> listaClienti = new ArrayList<>();

    public Broker(String nume, int experienta, double notaConcurs) {
        super(nume, experienta, notaConcurs);
    }

    /**
     * Metoda ce calculeaza salariul unui broker
     * @return salariul ce i se cuvine
     */
    @Override
    public double calculSalariu() {
        return (super.getExperienta() + 0.5 * super.getNotaConcurs()) * 1250;
    }


    public void adaugaClient(Client client) {
        this.listaClienti.add(client);
    }

    public ArrayList<String> getMesajeNecitite() {
        return mesajeNecitite;
    }

    public void setMesajeNecitite(ArrayList<String> mesajeNecitite) {
        this.mesajeNecitite = mesajeNecitite;
    }

    public ArrayList<Client> getListaClienti() {
        return listaClienti;
    }

    public void setListaClienti(ArrayList<Client> listaClienti) {
        this.listaClienti = listaClienti;
    }

    /**
     * Metoda care calculeaza comisionul pe care brokerul il ia clientului
     * castigator
     * @param client clientul castigator, caruia i se va lua comisionul
     * @param pret pretul cu care a castigat clientul licitatia
     */
    public void calculComision(Client client, double pret) {
        if(client instanceof PersoanaFizica) {
            if(client.getNrParticipari() < 5) {
                ((PersoanaFizica) client).setVenitTotal(
                        ((PersoanaFizica) client).getVenitTotal()
                        - pret * 0.2
                );
            }
            else {
                ((PersoanaFizica) client).setVenitTotal(
                        ((PersoanaFizica) client).getVenitTotal()
                                - pret * 0.15
                );
            }
        }
        else {
            if(client.getNrParticipari() < 25) {
                ((PersoanaJuridica) client).setCapitalSocial(
                        ((PersoanaJuridica) client).getCapitalSocial()
                                - pret * 0.25
                );
            }
            else {
                ((PersoanaJuridica) client).setCapitalSocial(
                        ((PersoanaJuridica) client).getCapitalSocial()
                                - pret * 0.1
                );
            }
        }
    }

    /**
     * Metoda care afiseaza si elimina ultimul mesaj necitit
     */
    private void deschideUltimulMesajNecitit() {
        System.out.println(mesajeNecitite
                .remove(mesajeNecitite.size() - 1)
        );
    }

    /**
     * Metoda care selecteaza pretul castigator al acestei licitatii
     * @param licitatie licitatia pentru care cautam pretul
     * @return licitarea castigatoare pentru respectivul produs
     */
    public double licitatieCastigatoare(Licitatie licitatie) {
        double licitatieMaxima = 0;
        //
        for(Client client : this.getListaClienti()) {
            for(Investitie investitie : client.getListaInvestitii()) {
                if(investitie.getIdLicitatie() == licitatie.getId()) {
                    this.cresteSumaLicitata(client, investitie);
                    break;
                }
            }
        }

        for(Client client : this.getListaClienti()) {
            for(Investitie investitie : client.getListaInvestitii()) {
                if(investitie.getIdLicitatie() == licitatie.getId()) {
                    if(licitatieMaxima < investitie.getSumaInvestita()) {
                        licitatieMaxima = investitie.getSumaInvestita();
                    }
                    break;
                }
            }
        }
        return licitatieMaxima;
    }
    
    /**
     * Functie care are rolul de a comunica cu clientii unui anumit broker,
     * care sunt activi la licitatia data ca parametru; in cazul in care acestia
     * au licitat pentru produsul respectiv, atunci brokerul le va cere o noua
     * suma pentru pretul curent; in cazul in care pretul oferit la acest pas
     * este mai mare decat suma maxima pe care o poate oferi participantul,
     * acesta se va retrage
     * @param client clientul cu care comunica brokerul pe moment
     * @param investitie investitia facuta de client pe acel produs
     */
    public void cresteSumaLicitata(Client client, Investitie investitie) {
        //cazul in care avem o persoana fizica, generam o oferta dupa o anumita
        // formula, prestabilita
        if(client instanceof PersoanaFizica) {
            double crestere = Math.random() * 1000;
            ((PersoanaFizica) client).setVenitTotal(
                    ((PersoanaFizica) client).getVenitTotal() - crestere
            );
            investitie.crestereSumaInvestita(crestere);
            if(investitie.getSumaInvestita() > investitie.getSumaMaxima()) {
                ((PersoanaFizica) client).setVenitTotal(
                        ((PersoanaFizica) client).getVenitTotal() +
                                investitie.getSumaInvestita()
                );
                System.out.println("clientul cu id " + client.getId() +
                        " si numele " + client.getNume() + " a depasit" +
                        " suma maxima; se retrage\n");
                client.getListaInvestitii().remove(investitie);
                return;
            }
        }
        // cazul in care avem persoana juridica, generam o oferta cu alta
        // formula
        else if(client instanceof PersoanaJuridica) {
            double crestere = Math.random() * 900;
            ((PersoanaJuridica) client).setCapitalSocial(
                    ((PersoanaJuridica) client).getCapitalSocial() - crestere
            );
            investitie.crestereSumaInvestita(crestere);
            if(investitie.getSumaInvestita() > investitie.getSumaMaxima()) {
                ((PersoanaJuridica) client).setCapitalSocial(
                        ((PersoanaJuridica) client).getCapitalSocial() +
                                investitie.getSumaInvestita()
                );
                System.out.println("clientul cu id " + client.getId() +
                        " si numele " + client.getNume() + " a depasit" +
                        " suma maxima; se retrage\n");
                client.getListaInvestitii().remove(investitie);
                return;
            }
        }
        System.out.println("clientul cu id " + client.getId() +
                " si numele " + client.getNume() + " a licitat suma de" +
                " " + investitie.getSumaInvestita());
    }

    /**
     * Metoda care sterge produsul castigat din lista de produse a casei de
     * licitatie
     * @param casaLicitatii casas de licitatii de unde va fi sters produsul
     * @param licitatie din moment ce licitatia s-a incheiat, e logic ca nici
     *                  aceasta nu va mai exista in cadrul casei de licitatii
     */
    @Test
    @DisplayName("Casa licitatii negasita; licitatie lipsa")
    public synchronized void stergereProdus(CasaLicitatii casaLicitatii,
                                            Licitatie licitatie) {
        int idProdusCastigator = licitatie.getIdProdus();
        for(Produs produs : casaLicitatii.getProduse()) {
            if(produs.getId() == idProdusCastigator) {
                casaLicitatii.getProduse().remove(produs);
                break;
            }
        }
        Assertions.assertAll(() -> Assertions.assertNotEquals(casaLicitatii, null),
                () -> Assertions.assertNotEquals(licitatie, null));
        casaLicitatii.getLicitatii().remove(licitatie);

        this.returneazaBani(licitatie);
    }

    /**
     * Metoda care returneaza banii clientilor care au intrat la aceasta
     * licitatie, insa nu au avut castigat-o
     * @param licitatie licitatia de pe urma careia trebuie returnati banii
     */
    @Test
    @DisplayName("Client neexistent pentru a scoate licitatia")
    public void returneazaBani(Licitatie licitatie) {
        for(Client client : this.getListaClienti()) {
            for(Investitie investitie : client.getListaInvestitii()) {
                if(investitie.getIdLicitatie() == licitatie.getId()) {
                    // banii se returneaza in functie de tipul fiecarui client,
                    // fie ca este o persoana fizica, fie ca este una juridica
                    if(client instanceof PersoanaFizica) {
                        ((PersoanaFizica) client).setVenitTotal(
                                ((PersoanaFizica) client).getVenitTotal() +
                                        investitie.getSumaInvestita()
                        );
                    }
                    else if(client instanceof PersoanaJuridica) {
                        ((PersoanaJuridica) client).setCapitalSocial(
                                ((PersoanaJuridica) client).getCapitalSocial()
                                + investitie.getSumaInvestita()
                        );
                    }
                    Assertions.assertNotEquals(client, null);
                    client.getListaInvestitii().remove(investitie);
                    break;
                }
            }
        }
    }
}
