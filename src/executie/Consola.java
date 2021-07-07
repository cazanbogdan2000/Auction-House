package executie;

import CasaLicitatii.CasaLicitatii;
import angajat.AdministratorThread;
import client.ClientThread;

/**
 * Clasa care se ocupa de rularea activitatilor intretinute de catre clienti si
 * de catre administrator(i), folosind multithreading: in enunt se spune ca
 * un client poate vedea produsele, iar un administrator poate adauga produse
 * in cadrul casei de licitatii in acelasi timp, prin urmare, o astfel de
 * abordare era necesara
 */
public abstract class Consola {

    public static void citesteComenzi(CasaLicitatii casaLicitatii,
                                      String numarInput) {
        Thread administrator = new Thread(new AdministratorThread(casaLicitatii,
                numarInput)
        );
        administrator.start();

        Thread clienti = new Thread(new ClientThread(casaLicitatii,
                numarInput)
        );
        clienti.start();
    }

    /**
     * metoda care ruleaza meniul pentru un fir de executie
     * @param casaLicitatii pentru care se realizeaza comenzile
     */
    protected abstract void meniu(CasaLicitatii casaLicitatii);
}
