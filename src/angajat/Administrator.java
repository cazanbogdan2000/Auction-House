package angajat;

import CasaLicitatii.CasaLicitatii;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import produs.Produs;
import produs.ProdusBuilder;

/**
 * Clasa administrator, care mosteneste clasa angajat;
 * Un administrator poate sa adauge produse noi in casa de licitatii
 */
public class Administrator extends Angajat {

    public Administrator(String nume, int experienta, double notaConcurs) {
        super(nume, experienta, notaConcurs);
    }

    /**
     * Calculeaza salariul dupa o formula aleasa de mine
     *
     * @return salariul ce i se cuvine
     */
    @Override
    public double calculSalariu() {
        return (super.getExperienta() + super.getNotaConcurs()) * 1500;
    }

    /**
     * Metoda care adauga un nou produs; primeste casa de licitatii in care
     * trebuie sa adauge, si un string care reprezinta detaliile produsului ce
     * se vrea a fi adaugat
     *
     * @param casaLicitatii casa de licitatii in care se doreste adaugarea
     *                      produsului
     * @param detaliiProdus detaliile produsului, care se prelucreaza, urmand
     *                      ca apoi sa se construiasca produsul, folosind
     *                      design pattern-ul builder
     */
    @Test
    @DisplayName("Nu exista produs de adaugat")
    public synchronized void adaugaProdus(CasaLicitatii casaLicitatii,
                                          String detaliiProdus) {
        String[] caracteristici = detaliiProdus.split(",");
        //prelucram datele legate de produs
        for (int i = 1; i < caracteristici.length; i++) {
            caracteristici[i] = caracteristici[i].substring(1);
        }
        String materialBijuterie = null, tip = null, materialMobila = null,
                numePictor = null, culoare = null, piatraPretioasa = null;
        if (caracteristici[0].equals("Bijuterie")) {
            materialBijuterie = caracteristici[4];
            piatraPretioasa = caracteristici[5];
        } else if (caracteristici[0].equals("Tablou")) {
            numePictor = caracteristici[4];
            culoare = caracteristici[5];
        } else {
            tip = caracteristici[4];
            materialMobila = caracteristici[5];
        }
        //construim produsul, indiferent de ce tip este el (polimorfism)
        Produs produs = new ProdusBuilder(caracteristici[0])
                .withId(Integer.parseInt(caracteristici[1]))
                .withNume(caracteristici[2])
                .withPretMinim(Double.parseDouble(caracteristici[3]))
                .withNumePictor(numePictor)
                .withCuloare(culoare)
                .withMaterialBijuterie(materialBijuterie)
                .withPiatraPretioasa(Boolean.parseBoolean(piatraPretioasa))
                .withMaterialMobila(materialMobila)
                .withTipMobila((tip))
                .build();
        //Testare folosind unit testing daca produsul s-a creat cu succes
        Assertions.assertNotEquals(produs, null);
        casaLicitatii.getProduse().add(produs);
        System.out.println("Produsul a fost adaugat cu succes");
        System.out.println();
    }

}
