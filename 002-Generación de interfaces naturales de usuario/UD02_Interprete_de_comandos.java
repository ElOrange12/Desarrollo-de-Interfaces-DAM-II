import java.text.Normalizer;

public interface CommandInterpreter {
    String detectIntent(String inputText);
}

public class RobustCommandInterpreter implements CommandInterpreter {
    @Override
    public String detectIntent(String inputText) {
        if (inputText == null || inputText.trim().isEmpty()) {
            return "EMPTY_INPUT";
        }
        
        String normalized = Normalizer.normalize(inputText.trim().toLowerCase(), Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");
        
        if (normalized.contains("camara") && 
           (normalized.contains("abrir") || normalized.contains("abre") || normalized.contains("enciende"))) {
            return "OPEN_CAMERA";
        }
        
        if (normalized.contains("ayuda")) {
            return "ENABLE_HELP_MODE";
        }
        
        if (normalized.contains("salir")) {
            return "CLOSE_APP";
        }
        
        return "UNKNOWN";
    }
}

public class Main {
    public static void main(String[] args) {
        RobustCommandInterpreter interpreter = new RobustCommandInterpreter();
        
        String[][] pruebas = {
            {null, "EMPTY_INPUT"},
            {"   ", "EMPTY_INPUT"},
            {"Ábre la cámara por favor", "OPEN_CAMERA"},
            {"necesito Ayúda urgente", "ENABLE_HELP_MODE"},
            {"quiero salir ya", "CLOSE_APP"},
            {"enciende el micro", "UNKNOWN"}
        };
        
        System.out.printf("%-30s | %-20s | %-20s%n", "Entrada", "Intención esperada", "Intención obtenida");
        System.out.println("----------------------------------------------------------------------------------");
        for (String[] prueba : pruebas) {
            String entrada = prueba[0];
            String esperada = prueba[1];
            String obtenida = interpreter.detectIntent(entrada);
            String entradaPrint = entrada == null ? "null" : "\"" + entrada + "\"";
            System.out.printf("%-30s | %-20s | %-20s%n", entradaPrint, esperada, obtenida);
        }
    }
}
