package CasaLicitatii;

import java.io.FileReader;
import java.util.*;

import angajat.Administrator;
import angajat.Broker;
import client.Client;
import client.ClientFactory;
import client.Companie;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import produs.Produs;
import produs.ProdusBuilder;

/**
 * Clasa care citeste dintr-un fisier JSON toata casa de licitatii, si anume:
 * clientii, produsele si angajatii
 * Ca design pattern pentru aceasta clasa am folosit Singleton, intrucat avem o
 * instanta unica a acestei clase
 */
public class CreareCasaLicitatii {
    private static CreareCasaLicitatii instantaUnica;
    private static JSONObject JSON;

    private CreareCasaLicitatii() {

    }

    /**
     * Instantierea unica a singletonului
     *
     * @return instanta obtinuta
     */
    public static CreareCasaLicitatii Instanta() {
        if (instantaUnica == null) {
            instantaUnica = new CreareCasaLicitatii();
        }
        return instantaUnica;
    }

    /**
     * Initializare obiectul de tip json
     *
     * @param jsonName numele fisierului
     */
    public void initializareJson(String jsonName) {
        try {
            JSON = (JSONObject) (new JSONParser().parse(
                    new FileReader(jsonName))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda care are rolul de a citi toata casa de licitatii, ceea ce presupune
     * crearea listei de clienti, a listei de produse si a listei de angajati
     *
     * @param caseLicitatii returneaza casa de licitatii astfel formata
     */
    public void citesteCasaLicitatii(List<CasaLicitatii> caseLicitatii) {
        JSONArray JSONcaseLicitatii = (JSONArray) JSON.get("caseLicitatii");

        for (Object o : JSONcaseLicitatii) {
            CasaLicitatii casaLicitatii = new CasaLicitatii();
            JSONObject JSONcasaLicitatii = (JSONObject) o;
            casaLicitatii.setNume((String) JSONcasaLicitatii.get("nume"));

            CreareCasaLicitatii.citesteClienti(casaLicitatii, JSONcasaLicitatii);
            CreareCasaLicitatii.citesteAngajati(casaLicitatii, JSONcasaLicitatii);
            CreareCasaLicitatii.citesteProduse(casaLicitatii, JSONcasaLicitatii);

            caseLicitatii.add(casaLicitatii);

        }
    }

    /**
     * Metoda care citeste clientii din json; parseaza din fisier campul clienti
     * si creeaza, folosind design pattern-ul factory un client, fie el persoana
     * fizica, fie persoana juridica
     *
     * @param casaLicitatii     locul unde sunt adaugati toti clientii
     * @param JSONcasaLicitatii obiectul de tip json care reprezinta intreaga
     *                          casa de licitatii
     */
    private static void citesteClienti(CasaLicitatii casaLicitatii,
                                       JSONObject JSONcasaLicitatii) {

        JSONArray JSONlistaClienti = (JSONArray) JSONcasaLicitatii
                .get("clienti");
        Client client;
        for (Object o : JSONlistaClienti) {
            JSONObject JSONclient = (JSONObject) o;
            // Cazul in care clientul nostru este o persoana fizica, i se atribuie
            // caracteristicile ce il definesc
            if (JSONclient.get("tipPersoana").equals("FIZIC")) {
                client = ClientFactory.
                        creeazaClient(JSONclient.get("tipPersoana").toString(),
                                JSONclient.get("id"), JSONclient.get("nume"),
                                JSONclient.get("adresa"),
                                JSONclient.get("dataNastere"),
                                JSONclient.get("venitTotal")
                        );
                // Cazul in care avem o persoana juridica, detaliile despre acesta
                // vor fi diferite
            } else {
                Companie companie;
                if (JSONclient.get("tipCompanie").equals("SA")) {
                    companie = Companie.SA;
                } else {
                    companie = Companie.SRL;
                }
                client = ClientFactory.
                        creeazaClient(JSONclient.get("tipPersoana").toString(),
                                JSONclient.get("id"), JSONclient.get("nume"),
                                JSONclient.get("adresa"),
                                companie,
                                JSONclient.get("capitalTotal")
                        );
            }
            casaLicitatii.getClienti().add(client);
        }
    }

    /**
     * Metoda care citeste angajatii din json; parseaza din fisier campul
     * angajati si creeaza un angajat, fie el broker sau administrator
     *
     * @param casaLicitatii     locul unde sunt adaugati toti angajatii
     * @param JSONcasaLicitatii obiectul de tip json care reprezinta intreaga
     *                          casa de licitatii
     */

    private static void citesteAngajati(CasaLicitatii casaLicitatii,
                                        JSONObject JSONcasaLicitatii) {
        // Citirea administratorului (sau administratorilor, in cazul in care
        // sunt mai multi)
        JSONArray JSONadministratori = (JSONArray) JSONcasaLicitatii.
                get("administrator");
        for (Object o : JSONadministratori) {
            JSONObject JSONadministrator = (JSONObject) o;
            Administrator administrator = new Administrator(
                    JSONadministrator.get("nume").toString(),
                    Integer.parseInt(JSONadministrator
                            .get("experienta").toString()),
                    Double.parseDouble(JSONadministrator
                            .get("notaConcurs").toString())
            );
            casaLicitatii.getAngajati().add(administrator);
        }
        // Citirea brokerilor din json
        JSONArray JSONbrokeri = (JSONArray) JSONcasaLicitatii.get("brokeri");
        for (Object o : JSONbrokeri) {
            JSONObject JSONbroker = (JSONObject) o;
            Broker broker = new Broker(
                    JSONbroker.get("nume").toString(),
                    Integer.parseInt(JSONbroker.get("experienta").toString()),
                    Double.parseDouble(JSONbroker.get("notaConcurs").toString())
            );
            casaLicitatii.getAngajati().add(broker);
        }
    }

    /**
     * Metoda care citeste produsele din json, din campul produse; acestea se
     * construiesc folosind design pattern-ul builder, care returneaza un obiect
     * fie de tipul Tablou, fie Bijuterie, fie Mobila, in functie de specificator
     *
     * @param casaLicitatii     casa unde vor fi inserate produsele
     * @param JSONcasaLicitatii obiectul de tip json care reprezinta intreaga
     *                          casa de licitatii
     */
    private static void citesteProduse(CasaLicitatii casaLicitatii,
                                       JSONObject JSONcasaLicitatii) {

        JSONArray JSONproduse = (JSONArray) JSONcasaLicitatii.get("produse");
        for (Object o : JSONproduse) {
            JSONObject JSONprodus = (JSONObject) o;
            Produs produs = new ProdusBuilder(JSONprodus
                    .get("tipProdus").toString())
                    .withId(Integer.parseInt(JSONprodus.get("id").toString()))
                    .withNume(JSONprodus.get("nume").toString())
                    .withPretMinim(Double.parseDouble(JSONprodus
                            .get("pretMinim").toString()))
                    .withNumePictor((String) JSONprodus.get("numePictor"))
                    .withCuloare((String) JSONprodus.get("culoare"))
                    .withMaterialBijuterie((String) JSONprodus
                            .get("materialBijuterie"))
                    .withPiatraPretioasa((Boolean) JSONprodus
                            .get("piatraPretioasa"))
                    .withMaterialMobila((String) JSONprodus
                            .get("materialMobila"))
                    .withTipMobila((String) JSONprodus.get("tip"))
                    .build();
            casaLicitatii.getProduse().add(produs);
        }
    }
}
