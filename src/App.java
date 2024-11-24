import extracao.GerenciadorDeDocumentos;
import hash.HashTable;
import trie.Trie;
import java.util.*;
import compactacao.ArvoreHuffman;
import java.io.IOException;

/* Nomes dos integrantes do grupo 01: 
 * Ari Vargas Leal Filho
 * Beatriz da Silva Ponces (apresentadora)
 * Daniel Garcia Amaral
 * Rodrigo Ranzi de Souza
 */

/**
 * Essa é a classe Main do trabalho, na qual serão inseridas todas as informações que o usuário colocar para casos de entrada (caminho do diretório dos documentos, escolha das hashs e palavra que deseja buscar).
 * Ela chama todas as funções e possibilita que o programa faça as suas funções
 */

public class App{

	public static void main(String[] args) throws IOException {

		// Instancias
		Scanner teclado = new Scanner(System.in);
		GerenciadorDeDocumentos gerente = new GerenciadorDeDocumentos();
		Map<String,String> artigos;
		Map<String,ArvoreHuffman> huffmanMap = new HashMap<>();
		Map<String,Trie> trieMap = new HashMap<>();
		Map<String,List<String>> palavrasArtigos;
		HashTable<String,String> hash;

		// Inserindo
		System.out.print("> Inserir documentos: ");
		String pasta = teclado.next(); //Caminho de diretório digitado para usuário
		artigos = gerente.carregarConteudoArquivos(pasta);
		System.out.println("");
		
		//Caso "artigos" seja null, significa que ou o caminho digitado pelo usuário é inválido
		// ou o caminho não possuia arquivos, e assim, o programa não poderá fazer o seu trabalho
		if(artigos != null) {
			
			// Comprimindo
			for (Map.Entry<String, String> entry : artigos.entrySet()) {
				String key = entry.getKey(); // Obter a chave (nome do artigo)
				String value = entry.getValue(); // Obter o valor (artigo)

				// Criando Huffman
				ArvoreHuffman huffman = new ArvoreHuffman(value);
				huffmanMap.put(key, huffman);

				// Substituindo por artigo Comprimido
				artigos.put(key, huffman.Comprimir(value));
			}

			// Inserindo na Hash
			System.out.print("> Qual a função de hashing (divisao/djb2): ");
			String funcaoHashEscolhida = teclado.next(); //Usuário vai escolher qual cálculo de hash vai usar
			System.out.println("");
			while (!funcaoHashEscolhida.equals("divisao") && !funcaoHashEscolhida.equals("djb2")) {
				System.out.println("Função de hashing indisponível!");
				System.out.print("> Qual a função de hashing (divisao/djb2): ");
				funcaoHashEscolhida = teclado.nextLine();
				System.out.println("");
			}
			hash = new HashTable<String,String>(artigos.size(), funcaoHashEscolhida); // Instancia de hash
			hash.inserir(artigos); //Aqui ele vai indexar os arquivos comprimidos na Hash 
			System.out.println("Documentos indexados com sucesso!");
			artigos.clear(); // "Exclui" artigos para reduzir uso de memória.

			// Criando Tries
			palavrasArtigos = gerente.carregarPalavrasParaBusca(pasta); // Cria um map com <nome dos artigos, lista de palavras do artigo>
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
			palavrasArtigos.clear(); // "Exclui" palavrasArtigo para reduzir uso de memória.
			
			// Buscando Palavras
			System.out.print("> Buscar palavra: ");
			String palavra = teclado.next(); //Usuário irá digitar a palavra 
			System.out.println("");
			System.out.println("A palavra '" + palavra + "' foi encontrada nos seguintes documentos:");

			boolean contemPalavra = false; // Usado para ver se palavra foi encontrada
			for (Map.Entry<String, Trie> entry : trieMap.entrySet()) {
				String key = entry.getKey(); // Obter a chave (nome do artigo)
				Trie value = entry.getValue(); // Obter o valor (Trie)

				if(value.buscarPalavra(palavra)) {
					System.out.println(key); // Imprime o artigo na qual a palavra foi encontrada
					contemPalavra = true;
				}
			}
			
			if(!contemPalavra) { // Palavra não foi encontrada em nenhum artigo.
				System.out.println("Palavra não encontrada em nenhum documento!");
			} 
			
			// Mostrar conteúdo
			else {
				System.out.println("");
				System.out.print("> Do(s) documento(s) que tem a palavra, me mostre o conteúdo do documento: ");
				String documento_escolhido = teclado.next();
				System.out.println(huffmanMap.get(documento_escolhido).Descomprimir(hash.getValor(documento_escolhido)));
			}
		}

		teclado.close();
	}
}