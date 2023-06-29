package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

/*Fazer um programa para ler um conjunto de produtos a partir de um arquivo em formato .csv (suponha que exista pelo menos um produto).
Em seguida mostrar o preço médio dos produtos. Depois, mostrar os nomes, em ordem decrescente, dos produtos que possuem preço
inferior ao preço médio. Veja exemplo na próxima página.*/

import entities.Product;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> list = new ArrayList<>();
		System.out.println("Enter full file path:");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			while (line != null) {

				String[] fields = line.split(",");

				list.add(new Product(fields[0], Double.parseDouble(fields[1])));
				line = br.readLine();

			}
			// D:\Curso\Curso
			// Java\cap17-programacao-funcional-expressoes-lambda\arquivos\in.txt

			double avg = list.stream().
			// fazendo uma lista so com os preços
					map(p -> p.getPrice()).reduce(0.0, (x, y) -> x + y) / list.size();

			System.out.println("Average price: " + String.format("%.2f", avg));

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> names = list.stream()
					// Filtro todos os produtos que sao menores que o valor da media
					.filter(p -> p.getPrice() < avg)
					// Faço a separação dos NOMES PRODUTOS E PREÇO PRODUTO: PEGANDO SOMENTE OS NOMES
					// DA LISTA
					.map(x -> x.getName())
					// FAÇO A ORDENAÇÃO EM ORDEM DECRESCENTE -> FAZENDO UMA FUÇÃO COMPARATOR, QUE
					// COLOCA OS PRODUTOS EM ORDEM CRESCENTE, DEPOIS PASSANDO UMA FUNÇÃO DEFOULT
					// REVERSED QUE COLOCA A LISTA EM ORDEM DECRESCENTE
					.sorted(comp.reversed()).collect(Collectors.toList());

			names.forEach(System.out::println);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}

}
