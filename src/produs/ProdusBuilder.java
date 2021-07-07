package produs;

/**
 * Builder pentru un produs; poate returna, in functie de tipul produsului,
 * direct subtipul produsului dorit, fara a ne mai face prea multe griji pentru
 * detaliile fiecarui produs in parte
 */
public class ProdusBuilder {

    private final String tip;
    private final Bijuterie bijuterie = new Bijuterie();
    private final Tablou tablou = new Tablou();
    private final Mobila mobila = new Mobila();

    public ProdusBuilder(String tip) {
        this.tip = tip;
    }

    /**
     * Setam numele produsului
     * @param nume numele dorit
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withNume(String nume) {
        if(tip.equals("Bijuterie")) {
            bijuterie.setNume(nume);
        }
        else if(tip.equals("Tablou")) {
            tablou.setNume(nume);
        }
        else {
            mobila.setNume(nume);
        }
        return this;
    }

    /**
     * Cream id-ul pentru produs
     * @param id id-ul produsului
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withId(int id) {
        if(tip.equals("Bijuterie")) {
            bijuterie.setId(id);
        }
        else if(tip.equals("Tablou")) {
            tablou.setId(id);
        }
        else {
            mobila.setId(id);
        }
        return this;
    }

    /**
     * Setam pretul minim pentru un produs
     * @param pretMinim pretul cu care va fi initiat produsul
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withPretMinim(double pretMinim) {
        if(tip.equals("Bijuterie")) {
            bijuterie.setPretMinim(pretMinim);
        }
        else if(tip.equals("Tablou")) {
            tablou.setPretMinim(pretMinim);
        }
        else {
            mobila.setPretMinim(pretMinim);
        }
        return this;
    }

    /**
     * Exista piatra pretioasa?
     * @param piatraPretioasa boolean, logic ce face
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withPiatraPretioasa(Boolean piatraPretioasa) {
        if(piatraPretioasa != null) {
            bijuterie.setPiatraPretioasa(piatraPretioasa);
        }
        return this;
    }

    /**
     * setam pentru produs materialul din care este constituita bijuteria
     * @param materialBijuterie tipul materialului
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withMaterialBijuterie(String materialBijuterie) {
        if(materialBijuterie != null){
            bijuterie.setMaterial(materialBijuterie);
        }
        return this;
    }

    /**
     * Setam numele pictorului care a realizat pictura
     * @param numePictor numele pe care il voim initializa
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withNumePictor(String numePictor) {
        if(numePictor != null){
            tablou.setNumePictor(numePictor);
        }
        return this;
    }

    /** Setare culoare pentru tablou
     * @param culoare culoare pe care o are tabloul
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withCuloare(String culoare) {
        Culoare culoareTablou;
        if(culoare != null) {
                if(culoare.equals("ULEI")) {
                    culoareTablou = Culoare.ULEI;
                }
                else if(culoare.equals("TEMPERA")) {
                    culoareTablou = Culoare.TEMPERA;
                }
                else {
                    culoareTablou = Culoare.ACRILIC;
                }
                tablou.setCulori(culoareTablou);
        }

        return this;
    }

    /**
     * Setare tip mobila
     * @param tipMobila tipul mobilei (colectie, tip, etc)
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withTipMobila(String tipMobila) {
        if(tipMobila != null) {
            mobila.setTip(tipMobila);
        }
        return this;
    }

    /**
     * Setarea materialului mobilei
     * @param materialMobila materialul din care este confectionata mobila
     * @return pointer catre adresa obiectului pe care urmeaza sa il construim
     */
    public ProdusBuilder withMaterialMobila(String materialMobila) {
        if(materialMobila != null) {
            mobila.setMaterial(materialMobila);
        }
        return this;
    }

    /**
     * Metoda care va construi obiectul dorit, in functie de tipul acestuia
     * @return Adresa obiectului astfel creat
     */
    public Produs build() {
        if(tip.equals("Bijuterie")) {
            return bijuterie;
        }
        else if(tip.equals("Tablou")) {
            return tablou;
        }
        else {
            return mobila;
        }
    }
}
