import CasaLicitatii.CasaLicitatii;
import CasaLicitatii.CreareCasaLicitatii;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clasa principala a programului, care ruleaza toate comenzile si functionali-
 * tatea programului; Se va citi de la tastatura un numar, care reprezinta
 * numarul testului care se doreste a fi rulat, urmand ca apoi sa se execute
 * automat restul comenzilor
 */
public class Main {

    @Test
    @DisplayName("Nu exista casa de licitatii")
    public static void main(String[] args) {
        // Crearea unei liste de case de licitatii, citite dintr-un fisier json
        CreareCasaLicitatii creareCasaLicitatii = CreareCasaLicitatii.Instanta();
        List<CasaLicitatii> caseLicitatii = new ArrayList<>();
        Assertions.assertNotEquals(caseLicitatii, null);
        creareCasaLicitatii.initializareJson("./src/executie/input.json");
        creareCasaLicitatii.citesteCasaLicitatii(caseLicitatii);
        // Citirea testului care se doreste a fi rulat
        Scanner scanner = new Scanner(System.in);
        System.out.print("Citeste numarul inputului dorit: ");
        String numarInput = scanner.nextLine();
        for(CasaLicitatii casaLicitatii : caseLicitatii) {
            // Inceperea unei zile lucratoare, cu multe licitatii si operatii
            // diferite
            casaLicitatii.startZiLucratoare(numarInput);
        }
    }
}
