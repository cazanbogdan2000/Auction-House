package CasaLicitatii;

/**
 * Design pattern folosit pentru a urmari diferite activitati din casa de licitatii
 * momentan informeaza clientii si brokerii de rezultatul unei licitatii
 */
public class ObservatorCasaLicitatii {
    CasaLicitatii casaLicitatii;

    public ObservatorCasaLicitatii(CasaLicitatii casaLicitatii) {
        this.casaLicitatii = casaLicitatii;
        this.casaLicitatii.ataseazaObservator(this);
    }

    public void actualizeaza() {
        System.out.print("A inceput licitatia pentru produsul cu id-ul ");
    }
}
