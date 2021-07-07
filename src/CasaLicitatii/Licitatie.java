package CasaLicitatii;

/**
 * Aceasta clasa detine toate detaliile despre licitatia unui produs, si anume:
 * id -- id-ul licitatiei
 * nrParticipanti -- numarul de participanti la care licitatia este pornita
 * nrCurentParticipanti -- numarul curent de participanti inscrisi la licitatie
 * idProdus -- idul produsului pentru care se liciteaza
 * nrPasiMaxim -- numarul maxim de pasi in care se va desfasura intreaga
 * licitatie
 */
public class Licitatie {
    private int id;
    private int nrParticipanti;
    private int nrCurentParticipanti = 0;
    private int idProdus;
    private int nrPasiMaxim;
    private static int idCounter = 0;

    public Licitatie(int nrParticipanti, int idProdus, int nrPasiMaxim) {
        Licitatie.idCounter++;
        this.id = idCounter;
        this.nrParticipanti = nrParticipanti;
        this.idProdus = idProdus;
        this.nrPasiMaxim = nrPasiMaxim;
    }

    /*
    Getteri si setteri sugestivi
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getNrPasiMaxim() {
        return nrPasiMaxim;
    }

    public void setNrPasiMaxim(int nrPasiMaxim) {
        this.nrPasiMaxim = nrPasiMaxim;
    }

    public int getNrCurentParticipanti() {
        return nrCurentParticipanti;
    }

    public void setNrCurentParticipanti() {
        this.nrCurentParticipanti++;
    }
}
