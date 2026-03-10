package Principal;

import Modelos.Monedas;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConversor {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner lectura = new Scanner(System.in);

        while (true) {

            System.out.println("******************************************************");
            System.out.println("Sea bienvenido/a al conversor de monedas :3");
            System.out.println("");
            System.out.println("1) Dólar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dolar");
            System.out.println("3) Dolar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dolar");
            System.out.println("5) Dolar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dolar");
            System.out.println("7) Salir");
            System.out.println("Elija una opcion valida:");
            System.out.println("******************************************************");
            var busqueda = lectura.nextLine();

            String monedaBase = "";
            String monedaObjetivo = "";

            if (busqueda.equals("1")) {
                monedaBase = "USD";
                monedaObjetivo = "ARS";
            } else if (busqueda.equals("2")) {
                monedaBase = "ARS";
                monedaObjetivo = "USD";

            }
             else if (busqueda.equals("3")) {
                monedaBase = "USD";
                monedaObjetivo = "BRL";
            } else if (busqueda.equals("4")) {
                monedaBase = "BRL";
                monedaObjetivo = "USD";
            }
            else if (busqueda.equals("5")) {
                monedaBase = "USD";
                monedaObjetivo = "COP";
            } else if (busqueda.equals("6")) {
                monedaBase = "COP";
                monedaObjetivo = "USD";
            } else if (busqueda.equals("7")) {
                break;
            }else{
                System.out.println("Ingrese un numero valido! \n" + "Vuelvalo a intentar despues de " +
                        "reiniciar el programa"); break;}

            System.out.println("Ingrese la cantidad que quiere convertir: ");
            var textitoCantidad = lectura.nextLine();
            double cantidad = Double.valueOf(textitoCantidad);

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();

            HttpClient cliente = HttpClient.newHttpClient();

            String direccion = "https://v6.exchangerate-api.com/v6/56f8c21c155079e59ec7d051/latest/" +
                    monedaBase;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .GET()
                    .build();
            try {
                HttpResponse<String> response = cliente
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();
                //System.out.println(json);
                Monedas Monedas = gson.fromJson(json, Monedas.class);

                double tasaDeConversion = Monedas.conversion_rates().get(monedaObjetivo);

                double cantidadConvertida = cantidad * tasaDeConversion;


                System.out.println("Moneda original: " + Monedas.base_code());
                System.out.println("Cantidad convertida: "+ cantidadConvertida + monedaObjetivo);

            } catch (Exception e) {
                System.out.println("Error al obtener datos de la API");
            }


        }
    }
}
