package client;

import CasaLicitatii.CasaLicitatii;
import angajat.Broker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import produs.Bijuterie;
import produs.Mobila;
import produs.Produs;
import produs.Tablou;
import CasaLicitatii.Licitatie;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clasa care inmagazineaza toate datele despre un client; campurile de interes
 * major din cadrul acestei clase sunt:
 * id -- reprezinta id-ul unui anumit client
 * nume -- reprezinta numele clientului
 * adresa -- semnifica adresa clientului, desi nu are absolut nicio relevanta
 * in cadrul acestei teme, a, pastrat acest camp
 * nrParticipari -- numarul de licitatii la care clientul a participat
 * nrLicitatiiCastigate -- numarul de licitatii pe care clientul le-a castigat
 * listaInvestitii -- reprezinta o lista cu toate licitatiile activa la care
 * participa clientul
 * produseCastigate -- reprezinta produsele castigate in urma licitatiilor
 * broker -- reprezinta brokerul care i-a fost asociat clientului
 */
public abstract class Client implements Factory<Client>{
    private int id;
    private String nume;
    private String adresa;
    private int nrParticipari;
    private int nrLicitatiiCastigate;
    private ArrayList<Investitie> listaInvestitii = new ArrayList<>();
    private ArrayList<Produs> produseCastigate = new ArrayList<>();
    private Broker broker = null;

    public Client() {

    }

    public Client(int id, String nume, String adresa) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.nrLicitatiiCastigate = 0;
        this.nrParticipari = 0;
    }

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

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getNrParticipari() {
        return nrParticipari;
    }

    public void setNrParticipari(int nrParticipari) {
        this.nrParticipari = nrParticipari;
    }

    public int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    public void setNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
    }

    public ArrayList<Investitie> getListaInvestitii() {
        return listaInvestitii;
    }

    @Override
    public Client factory(Client obj) {
        return this;
    }

    public void setListaInvestitii(ArrayList<Investitie> listaInvestitii) {
        this.listaInvestitii = listaInvestitii;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    /**
     * Metoda care afiseaza produsele din casa de licitatii; proprietate a
     * clientului ce sa gaseste in enunt, anume capacitatea de a vizualiza
     * produsele din cadrul casei de licitatii
     * @param casaLicitatii casa de licitatii, locul in care sunt stocate si
     *                      produsele pe care dorim sa le afisam
     * @throws InterruptedException exceptie aruncata in cazul in care asteptarea
     * "primeste" anumite interferente din exterior
     */
    public synchronized void afiseazaProduse(CasaLicitatii casaLicitatii)
            throws InterruptedException {
        wait(350);
        for (Produs produs : casaLicitatii.getProduse()) {
            // afisare caracteristici la comun
            System.out.println("ID produs: " + produs.getId());
            System.out.println("Nume produs: " + produs.getNume());
            System.out.println("Pret minim: " + produs.getPretMinim());
            // afisare date specifice fiecarui produs in parte
            if (produs instanceof Bijuterie) {
                System.out.println("Material bijuterie: " +
                        ((Bijuterie) produs).getMaterial());
                System.out.println("Piatra pretioasa: " +
                        ((Bijuterie) produs).isPiatraPretioasa());
            } else if (produs instanceof Tablou) {
                System.out.println("Nume pictor: " +
                        ((Tablou) produs).getNumePictor());
                System.out.println("Culoare: " +
                        ((Tablou) produs).getCuloare());
            } else {
                System.out.println("Tip: " +
                        ((Mobila) produs).getTip());
                System.out.println("Material mobila: " +
                        ((Mobila) produs).getMaterial());
            }
            System.out.println();
        }
    }

    /**
     * Metoda prin care un client se inscrie la o licitatie pentru un anumit
     * produs
     * @param casaLicitatii locul unde se gaseste produsul cu un anumit id
     * @param idProdus id-ul produsului pe care se doreste a se face licitarea
     */
    public void liciteaza(CasaLicitatii casaLicitatii, int idProdus) {
        for (Produs produs : casaLicitatii.getProduse()) {
            if (produs.getId() == idProdus) {
                this.inscriereLicitatie(casaLicitatii, produs);
                return;
            }
        }
        System.out.println("Nu exista produsul cu acest id!!\n");
    }

    /**
     * Metoda prin care un client se inscrie la o licitatie pentru un produs,
     * isi genereaza un pret maxim pe care si-l poate permite a plati, se
     * genereaza in mod determinist si un numar de participanti la respectiva
     * licitatie, si un numar maxim de pasi; in cazul in care pentru produsul
     * dorit exista deja o licitatie activa, atunci clientul doar se va inscrie
     * la aceasta, doar stabilind un cost maxim pentru respectivul produs
     * @param casaLicitatii casa de licitatii unde se va realiza licitatia pentru
     *                      produsul dorit
     * @param produs produsul pentru care se va deschide / clientul se va inscrie
     *               la licitatie
     */
    public synchronized void inscriereLicitatie(CasaLicitatii casaLicitatii,
                                                Produs produs) {
        // Avem doua tipuri de clienti, pentru care se stabilesc preturi maxime
        // dupa euristici diferite
        double pretMaxim = 0;
        if (this instanceof PersoanaFizica) {
            pretMaxim = ((PersoanaFizica) this).getVenitTotal() * 0.7;
            if (pretMaxim < produs.getPretMinim()) {
                System.out.println("Nu puteti licita pentru acest produs!");
                System.out.println("Nu aveti venit suficient\n");
                return;
            }
        }
        if (this instanceof PersoanaJuridica) {
            pretMaxim = ((PersoanaJuridica) this).getCapitalSocial() * 0.65;
            if (pretMaxim < produs.getPretMinim()) {
                System.out.println("Nu puteti licita pentru acest produs!");
                System.out.println("Capitalul companiei nu este suficient\n");
                return;
            }
        }
        // adaugare licitatie noua in casa de licitatii, sau doar inscrierea
        // clientului la licitatie, in cazul in care aceasta deja exista
        if (casaLicitatii.getLicitatii().isEmpty()) {
            creareLicitatie(casaLicitatii, produs, pretMaxim);
            return;
        } else {
            for(Licitatie licitatie : casaLicitatii.getLicitatii()) {
                if(licitatie.getIdProdus() == produs.getId()) {
                    for(Investitie investitie : this.getListaInvestitii()) {
                        if(investitie.getIdLicitatie() == licitatie.getId()) {
                            System.out.println("Deja s-a licitat pentru" +
                                    " acest produs!\n");
                            return;
                        }
                    }
                    this.getListaInvestitii().add(
                            new Investitie(licitatie.getId(), pretMaxim)
                            .setSumaInvestita(this, produs.getPretMinim())
                    );
                    // asocierea unui broker clientului, in cazul in care
                    // cel din urma nu are deja
                    casaLicitatii.asociazaBroker(this);
                    licitatie.setNrCurentParticipanti();
                    if(licitatie.getNrParticipanti() ==
                    licitatie.getNrCurentParticipanti()) {
                        casaLicitatii.startLicitatie(licitatie);
                    }
                    return;
                }
            }
        }
        creareLicitatie(casaLicitatii, produs, pretMaxim);

    }

    /**
     * Metoda care creaza o licitatie, in functie de produsul licitat, si de
     * pretul maxim pe care il poate oferi clientul pe produsul respectiv
     * @param casaLicitatii casa de licitatii unde va fi adaugata licitatia
     *                      dorita
     * @param produs produs pe baza caruia se va genera noua licitatie
     * @param pretMaxim pretul maxim pe care si-l permite clientul sa il dea
     *                  pe produs
     */
    @Test
    @DisplayName("Existenta casa licitatii; pretMaxim valid")
    private void creareLicitatie(CasaLicitatii casaLicitatii,
                                 Produs produs, double pretMaxim) {
        Assertions.assertAll( () -> assertNotEquals(casaLicitatii, null),
                () -> assertTrue(pretMaxim > 0));
        Licitatie licitatieNoua = new Licitatie(
                casaLicitatii.getClienti().size() / 2,
                produs.getId(),
                casaLicitatii.getClienti().size() / 2 + 1);
        casaLicitatii.getLicitatii().add(licitatieNoua);
        this.getListaInvestitii().add(
                new Investitie(licitatieNoua.getId(), pretMaxim)
                        .setSumaInvestita(this, produs.getPretMinim())
        );
        casaLicitatii.asociazaBroker(this);
        licitatieNoua.setNrCurentParticipanti();
    }

}
