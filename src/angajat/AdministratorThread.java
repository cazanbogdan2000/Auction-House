package angajat;

import CasaLicitatii.CasaLicitatii;
import executie.Consola;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clasa ce implementeaza interfata Runnable; reprezinta un thread in cadrul
 * proiectului
 */
public class AdministratorThread extends Consola implements Runnable {
    CasaLicitatii casaLicitatii;
    String numarInput;

    public AdministratorThread(CasaLicitatii casaLicitatii, String numarInput) {
        this.casaLicitatii = casaLicitatii;
        this.numarInput = numarInput;
    }

    /**
     * Functia meniu, suprascrisa din clasa Consola, in care sunt interpretate
     * comenzile pentru un administrator; administratorul se logheaza cu numele
     * sau complet, urmand ca apoi sa adauge produsele dorite; la final, se va
     * deloga folosind una dintre comenzile exit sau quit
     *
     * @param casaLicitatii casa de licitatii pe care se realizeaza comenzile
     *                      date
     */
    @Override
    protected void meniu(CasaLicitatii casaLicitatii) {
        Administrator administrator = null;
        try {
            //Deschiderea fisierului cu comenzi pentru administrator
            String numeFisier = "./inputs/AdminCommands" + this.numarInput;
            File input = new File(numeFisier);
            Scanner reader = new Scanner(input);
            while (reader.hasNext()) {
                //Citire comanda
                String[] comanda = reader.nextLine().split(" ");
                //Comanda de logare a administratorului
                if (comanda[0].equals("login_as") && comanda[1].
                        equals("administrator")) {
                    if (comanda.length == 2) {
                        System.out.println("Nu s-a introdus nume administrator");
                        continue;
                    }
                    StringBuilder numeAdmin = new StringBuilder();
                    for (int i = 2; i < comanda.length - 1; i++) {
                        numeAdmin.append(comanda[i]).append(" ");
                    }
                    numeAdmin.append(comanda[comanda.length - 1]);
                    for (Angajat angajat : casaLicitatii.getAngajati()) {
                        if (angajat instanceof Administrator &&
                                angajat.getNume().equals(numeAdmin.toString())) {
                            administrator = (Administrator) angajat;
                        }
                    }
                    if (administrator == null) {
                        System.out.println("nu s-a gasit acest administrator");
                    }
                }
                // Comanda de adaugare a produsului
                else if (comanda[0].equals("adaugaprodus")) {
                    if (administrator == null) {
                        System.out.println("Nu exista administrator logat, " +
                                "nu se poate efectua comanda");
                        continue;
                    }
                    StringBuilder detaliiProdus = new StringBuilder();
                    for (int i = 1; i < comanda.length - 1; i++) {
                        detaliiProdus.append(comanda[i]).append(" ");
                    }
                    detaliiProdus.append(comanda[comanda.length - 1]);
                    administrator.adaugaProdus(casaLicitatii,
                            detaliiProdus.toString());
                }
                // Comenzi de delogare
                else if (comanda[0].equals("exit") || comanda[0].equals("quit")) {
                    administrator = null;
                } else {
                    System.out.print("Comanda inexistenta; te rugam mai incearca");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        this.meniu(casaLicitatii);
    }
}
