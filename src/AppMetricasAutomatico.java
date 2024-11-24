import extracao.GerenciadorDeDocumentos;
import hash.HashTable;
import trie.Trie;
import java.util.*;
import compactacao.ArvoreHuffman;
import java.io.IOException;

/**
 * Classe de teste do trabalho, onde as entradas são automáticas
 * (diretório de documentos, funções de hash e palavras buscadas).
 */

public class AppMetricasAutomatico {

    public static void main(String[] args) throws IOException {

		// Instancias
		GerenciadorDeDocumentos gerente = new GerenciadorDeDocumentos();
		Map<String,String> artigos;
		Map<String,ArvoreHuffman> huffmanMap = new HashMap<>();
		Map<String,Trie> trieMap = new HashMap<>();
		Map<String,List<String>> palavrasArtigos;
		HashTable<String,String> hash;
		String caminhoCorpus = "dados\\Corpus";
		String caminhoPalavrasBusca = "dados\\100_palavras.txt";
		long tempoInicial;
		long tempoFinal;
		long tempoTotal;
		long memoriaInicial;
		long memoriaFinal;
		long memoriaTotal;
		String documento_escolhido = "Bayesian_Coordinate_Differential_Privacy.txt";


		// Inserindo
		tempoInicial = System.nanoTime();
		System.gc(); // Força coleta de lixo
		memoriaInicial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		artigos = gerente.carregarConteudoArquivos(caminhoCorpus);

		System.gc(); // Força coleta de lixo
		memoriaFinal = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		tempoFinal = System.nanoTime();
		memoriaTotal = (memoriaFinal - memoriaInicial) / (1024*1024);
		System.out.println("Memoria antes  da inserção: " + memoriaInicial);
		System.out.println("Memoria depois da inserção: " + memoriaFinal);
		System.out.println("Consumo de memória pela inserção: " + memoriaTotal + " MB");
		tempoTotal = tempoFinal - tempoInicial;
		System.out.println("Tempo total de inserção: " + tempoTotal/1e6 + " ns");
		System.out.println();
			
		// Comprimindo
		tempoInicial = System.nanoTime();
		System.gc(); // Força coleta de lixo
		memoriaInicial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		for (Map.Entry<String, String> entry : artigos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			ArvoreHuffman huffman = new ArvoreHuffman(value);
			huffmanMap.put(key, huffman);
			artigos.put(key, huffman.Comprimir(value));
		}

		System.gc(); // Força coleta de lixo
		memoriaFinal = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		tempoFinal = System.nanoTime();
		memoriaTotal = (memoriaFinal - memoriaInicial) / (1024*1024);
		System.out.println("Memoria antes  da compressão: " + memoriaInicial);
		System.out.println("Memoria depois da compressão: " + memoriaFinal);
		System.out.println("Consumo de memória pela compressão: " + memoriaTotal + " MB");
		tempoTotal = tempoFinal - tempoInicial;
		System.out.println("Tempo total de compressão: " + tempoTotal/1e6 + " ns");
		System.out.println();
		

		// Inserindo na Hash
		String[] funcoesHash = {"divisao", "djb2"}; // Definição das funções, para a execução automática
		for (String funcaoHashEscolhida : funcoesHash) {

			tempoInicial = System.nanoTime();
			System.gc(); // Força coleta de lixo
			memoriaInicial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

			hash = new HashTable<String,String>(artigos.size(), funcaoHashEscolhida);
			hash.inserir(artigos);

			memoriaFinal = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			tempoFinal = System.nanoTime();
			memoriaTotal = (memoriaFinal - memoriaInicial);
			System.out.println("Consumo de memória pela Tabela Hash por " + funcaoHashEscolhida + ": " + memoriaTotal + " bytes");
			tempoTotal = tempoFinal - tempoInicial;
			System.out.println("Tempo total de inserção na Hash " + funcaoHashEscolhida + ": " + tempoTotal/1e6 + " ns");
			System.out.println();

			// Descomprimindo
			tempoInicial = System.nanoTime();
			System.gc(); // Força coleta de lixo
			memoriaInicial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

			String descomprimido = huffmanMap.get(documento_escolhido).Descomprimir(hash.getValor(documento_escolhido));

			memoriaFinal = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			tempoFinal = System.nanoTime();
			memoriaTotal = (memoriaFinal - memoriaInicial) / (1024*1024);
			System.out.println("Consumo de memória pela descompressão (na Tabela Hash " + funcaoHashEscolhida + "): " + memoriaTotal + " MB");
			tempoTotal = tempoFinal - tempoInicial;
			System.out.println("Tempo total de descompressão (na Tabela Hash " + funcaoHashEscolhida + "): " + tempoTotal/1e6 + " ns");
			System.out.println();

		}
		artigos.clear(); // "Exclui" artigos para reduzir uso de memória.

		// Criando Tries
		tempoInicial = System.nanoTime();
		System.gc(); // Força coleta de lixo
		memoriaInicial = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		palavrasArtigos = gerente.carregarPalavrasParaBusca(caminhoCorpus); // Cria um map com <nome dos artigos, lista de palavras do artigo>
		for (Map.Entry<String, List<String>> entry : palavrasArtigos.entrySet()) {
			String nomeArtigo = entry.getKey(); // Nome do artigo
			List<String> palavras = entry.getValue(); // Lista de palavras do artigo

			// Criando a Trie para o artigo
			Trie trie = new Trie();
			for (String palavra : palavras) {
				trie.inserir(palavra); // Insere cada palavra na Trie
			}

			// Associa a Trie ao nome do artigo no mapa
			trieMap.put(nomeArtigo, trie);
		}

		memoriaFinal = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		tempoFinal = System.nanoTime();
		memoriaTotal = (memoriaFinal - memoriaInicial) / (1024*1024);
		System.out.println("Consumo de memória pela trie: " + memoriaTotal + " MB");
		tempoTotal = tempoFinal - tempoInicial;
		System.out.println("Tempo total de criação das Tries: " + tempoTotal/1e6 + " ms");
		System.out.println();

		palavrasArtigos.clear(); // "Exclui" palavrasArtigo para reduzir uso de memória.
		
		// Buscando Palavras

		// Insere as palavras do arquivo de palavras para testar em uma lista
		System.out.println("Buscando as palavras do arquivo '../dados/100_palavras.txt'...");
		List<String> expressoesBusca = carregarExpressoes(caminhoPalavrasBusca);

		Map<String, Long> temposBusca = new LinkedHashMap<>(); // Map para armazenar os tempos de busca de cada palavra
		int totalEncontradas = 0; // Para contar o número de palavras encontradas

		for (String palavra : expressoesBusca) {

			long inicioTempoBusca = System.nanoTime(); // Marca o tempo de início da busca
			boolean contemPalavra = false; // Usado para ver se palavra foi encontrada
			for (Map.Entry<String, Trie> entry : trieMap.entrySet()) {
				Trie value = entry.getValue(); // Obter o valor (Trie)

				if(value.buscarPalavra(palavra)) {
					// System.out.println(key); // Imprime o artigo na qual a palavra foi encontrada
					contemPalavra = true;
				}
			}
			
			long finalTempoBusca = System.nanoTime(); // Para de medir o tempo de busca na Trie
			long tempoBusca = finalTempoBusca - inicioTempoBusca; // Calcula o tempo de busca na Trie (final - inicial)
			temposBusca.put(palavra, tempoBusca);
			// Se for encontrada, conta mais um no total se encontradas
			if(contemPalavra){
				totalEncontradas++;
			}
		}

		// Printa o tempo de busca de cada uma das palavras
	
		/*

		System.out.println("\nTempos de busca para cada palavra (em ms):");
		for (Map.Entry<String, Long> entry : temposBusca.entrySet()) {
			System.out.println("Palavra: " + entry.getKey() + ", Tempo: " + (entry.getValue())/1e6 + " ms");
		}

		*/

		// Calcular o tempo total de busca
		long totalBusca = temposBusca.values().stream().mapToLong(Long::longValue).sum();
		System.out.println("\nTempo total de busca: " + totalBusca/1e6 + " ms");

		// Calcular o tempo médio de busca
		System.out.println("Tempo médio de busca: " + (totalBusca/1e6)/temposBusca.size() + " ms");

		// Palavras encontradas
		System.out.println("Total de palavras encontradas: " + totalEncontradas + " de " + expressoesBusca.size());
	}

	// Esse método serve para carregar as expressões do arquivo com as 100 palavras em uma lista
	public static List<String> carregarExpressoes(String caminhoArquivo) {
		List<String> expressoes = new ArrayList<>();
		try (Scanner scanner = new Scanner(new java.io.File(caminhoArquivo))) {
			while (scanner.hasNextLine()) {
				expressoes.add(scanner.nextLine().trim());
			}
		} catch (IOException e) {
			System.out.println("Erro ao carregar o arquivo de expressões: " + e.getMessage());
		}
		return expressoes;
    }
}