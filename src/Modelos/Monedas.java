package Modelos;

import java.util.Map;

public record Monedas(String base_code, Map< String, Double> conversion_rates) {
}
