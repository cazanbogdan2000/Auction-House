package client;

/**
 * Clasa care implementeaza design pattern-ul factory pentru client
 */
public class ClientFactory {

    /**
     * Metoda care creeaza clientul in functie de tipul pe care ni-l dorim
     * @param tip tipul clientului
     * @param o un numar variabil de parametri, ce reprezinta detaliile clientului
     *          ce urmeaza a fi creat; acestea sunt diferite in functie de tipul
     *          clientului (fie fizic, fie juridic)
     * @return returneaza clientul astfel creat
     */
    public static Client creeazaClient(String tip, Object ... o) {
        return switch (tip) {
            case "FIZIC" -> new PersoanaFizica(Integer.parseInt(o[0].toString()),
                    ((String) o[1]), ((String) o[2]), ((String) o[3]),
                    Double.parseDouble(o[4].toString()));
            case "JURIDIC" -> new PersoanaJuridica(Integer.parseInt(o[0].toString()),
                    ((String) o[1]), ((String) o[2]), ((Companie) o[3]),
                    Double.parseDouble(o[4].toString()));
            default -> null;
        };
    }
}
