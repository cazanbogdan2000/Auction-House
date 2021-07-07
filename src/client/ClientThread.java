package client;

import CasaLicitatii.CasaLicitatii;
import executie.Consola;

import java.io.File;
import java.util.Scanner;

public class ClientThread extends Consola implements Runnable{
    CasaLicitatii casaLicitatii;
    String numarInput;

    public ClientThread(CasaLicitatii casaLicitatii, String numarInput) {
        this.casaLicitatii = casaLicitatii;
        this.numarInput = numarInput;
    }

    @Override
    protected synchronized void meniu(CasaLicitatii casaLicitatii) {
        try {
            String numeFisier = "./inputs/ClientCommands" + this.numarInput;
            File input = new File(numeFisier);
            Scanner reader = new Scanner(input);
            Client client = null;
            while(reader.hasNext()) {
                String[] comanda = reader.nextLine().split(" ");
                if(comanda[0].equals("login_as") && comanda[1].equals("client")) {
                    if(comanda.length == 2) {
                        System.out.println("Nu s-a introdus Id pentru client");
                        continue;
                    }
                    for(Client c : casaLicitatii.getClienti()) {
                        if(c.getId() == Integer.parseInt(comanda[2])) {
                            client = c;
                        }
                    }
                    if (client == null) {
                        System.out.println("nu s-a gasit clientul cu acest Id");
                    }
                }
                else if(comanda[0].equals("afiseazaproduse")) {
                    if(client == null) {
                        System.out.println("Nu exista client logat, " +
                                "nu se poate efectua comanda");
                        continue;
                    }
                    client.afiseazaProduse(casaLicitatii);
                }
                else if(comanda[0].equals("exit") || comanda[0].equals("quit")) {
                    client = null;
                }
                else if(comanda[0].equals("liciteazaprodus")) {
                    if(comanda.length == 1) {
                        System.out.print("Nu s-a introdus nici-un produs!!");
                        continue;
                    }
                    if(client == null) {
                        System.out.println("Nu exista client logat, " +
                                "nu se poate efectua comanda");
                        continue;
                    }
                    client.liciteaza(casaLicitatii, Integer.parseInt(comanda[1]));
                }
                else {
                    System.out.print("Comanda inexistenta; te rugam mai incearca");
                }
            }
        }
        catch (Exception e) {
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
