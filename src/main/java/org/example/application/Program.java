package org.example.application;
import org.example.entities.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    //home/vitor/temp/Product.txt

    public static void main(String[] args) {

        // Define o padrão de localização para Estados Unidos, afetando formatos de números, datas, etc.
        Locale.setDefault(Locale.US);
        // Inicializa um Scanner para captura de entrada do usuário
        Scanner sc = new Scanner(System.in);

        // Solicita ao usuário para inserir o caminho do arquivo e lê essa entrada
        System.out.print("Entre com o caminho da pasta: ");
        String path = sc.nextLine();

        // Tenta abrir o arquivo no caminho especificado
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Lista para armazenar os objetos Product lidos do arquivo
            List<Product> list = new ArrayList<>();

            // Lê a primeira linha do arquivo
            String line = br.readLine();
            while (line != null) {
                // Divide a linha em nome e preço baseado na vírgula
                String[] fields = line.split(",");
                // Adiciona um novo produto à lista com os dados extraídos
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                // Lê a próxima linha do arquivo
                line = br.readLine();
            }

            // Calcula a média dos preços dos produtos
            Double avg = list.stream()
                    .map(p -> p.getPrice())
                    .reduce(0.0, (x, y) -> x + y) / list.size();

            System.out.println("Preço medio : " + String.format("%.2f",avg));

            // Define um comparador para strings ignorando maiúsculas e minúsculas, e depois inverte a ordem
            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            // Obtém uma lista de nomes de produtos cujo preço é menor que a média
            List<String> names = list.stream()
                    .filter(p -> p.getPrice() < avg)
                    .map(p -> p.getName())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            // Imprime os nomes dos produtos filtrados e ordenados
            names.forEach(System.out::println);

        } catch (IOException e) {
            // Caso ocorra uma exceção de IO, imprime a mensagem de erro
            System.out.println("Erro: " + e.getMessage());
        }
        // Fecha o scanner para evitar vazamento de recursos
        sc.close();
    }
}
