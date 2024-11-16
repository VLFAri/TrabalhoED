package compactacao;
import java.util.*;

/**
 * Implementação de uma árvore de Huffman para compressão de dados.
 * 
 * A árvore de Huffman é um método de compressão que usa uma estrutura binária
 * para representar caracteres com códigos de tamanhos variáveis, atribuindo
 * códigos binários curtos aos caracteres mais frequentes, reduzindo assim o
 * tamanho de arquivos de texto.
 * 
 * Saiba mais em: https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/
 */
public class ArvoreHuffman {
	
	/** Nó raiz da árvore de Huffman */
	private TrieNode root;

	/** Tabela que armazena caracteres e seus códigos de Huffman gerados
	 * 
	 * As chaves representam caracteres ou símbolos, e os valores
	 * correspondem aos códigos binários de compressão gerados pela Árvore de Huffman.
	 */
	private Map<Character, String> tabelaHuffman;
	
	/**
     * Construtor que cria a árvore de Huffman com base na string de entrada.
     * @param texto Texto a ser comprimido.
     */
	public ArvoreHuffman(String entrada) {

		/*	Cria uma tabela chave-valor (Map), onde a chave é um caracter
			e o valor é quantas vezes esse caracter aparece no texto inserido. */
        Map<Character, Integer> frequencia = calcularFrequencia(entrada);

		// Cria tabela Huffman
		tabelaHuffman = new HashMap<>();

		// Preenche tabela Huffman
        construirArvore(frequencia);
    }

	/**
	 * Calcula a frequência de cada caractere em uma string de entrada.
	 * 
	 * @param entrada A string cujas frequências dos caracteres serão calculadas.
	 * @return Um mapa (Map) onde cada chave é um caractere e o valor é a frequência desse caractere na entrada.
	 */
	private Map<Character, Integer> calcularFrequencia(String entrada) {

		Map<Character, Integer> frequencia = new HashMap<>();
		
		// Percorre cada caractere na string de entrada, transformando-a em um array de caracteres
		for (char caractere : entrada.toCharArray()) {
			
			/*	Atualiza a contagem do caractere no mapa:
				Se o caractere já existe, incrementa a contagem atual em 1.
				Caso contrário, inicia a contagem desse caractere com 1. */
			frequencia.put(caractere, frequencia.getOrDefault(caractere, 0) + 1);

			/*
			
				O método put() serve para atualizar/inserir na "tabela".

				Se a chave existe, atualiza o valor.
				Se a chave não existe, adiciona essa chave na tabela e já atribui o valor especificado.

				put(chave, valor);
			
			*/

			/*

				O método getOrDefault() retorna o valor da chave especificada.
				Se essa chave não existir, um valor padrão será retornado.

				getOrDefault(chave, valorPadrao);

			*/
		}
		
		// Retorna o mapa contendo a frequência de cada caractere
		return frequencia;
	}
	
	/**
     * Constrói a árvore de Huffman com base nas frequências dos caracteres.
     * @param frequencia Mapa de frequências dos caracteres.
     */
	private void construirArvore(Map<Character, Integer> frequencia) {

		/*
			Fila de prioridade para organizar os nós pela frequência
			símbolos menos frequentes devem ser adicionados primeiro
		*/
		PriorityQueue<TrieNode> fila = new PriorityQueue<TrieNode>();
		
		// Cria nós folha para cada caractere e os adiciona na fila
        for (Map.Entry<Character, Integer> entrada : frequencia.entrySet()) {
            fila.add(new TrieNode(entrada.getKey(), entrada.getValue()));
        }
		
		// Enquanto tiver nó para ser inserido na Trie:
		while(fila.size() > 1) {
			// Cria um novo nó com as referências para os filhos 
			TrieNode left = fila.poll();
			TrieNode right = fila.poll();
			
			// Novo nó com a frequencia = soma das frequencias de seus filhos.
			TrieNode newNode = new TrieNode(left.getFrequencia() + right.getFrequencia(), 
											left, 
											right);
			fila.add(newNode); // Adiciona o novo nó na fila
		}
		
		root = fila.poll(); // Trie está criada
		gerarCodigosHuffman(root, "");
	}
	
	/**
     * Gera os códigos de Huffman recursivamente e os armazena na tabela de codificação.
     * @param no Nó atual da árvore.
     * @param codigo Código binário acumulado até o nó atual.
     */
	private void gerarCodigosHuffman(TrieNode node, String code) {
		// Caso base: chegamos no fim da árvore; Encerra o loop.
		if (node == null) return;
		
		// Se o nó for folha, ele adiciona o nó na tabela de símbolos do Huffman
		// com a codificação correspondente
		if(node.getEsquerdo() == null && node.getDireito() == null) {
			tabelaHuffman.put(node.getCaractere(), code);
		}
		
		// Desce para os filhos da direita e da esquerda
		gerarCodigosHuffman(node.getEsquerdo(), code + "0"); // Se for filho da esquerda: adiciona 0 à representação
		gerarCodigosHuffman(node.getDireito(), code + "1"); // Se for filho da direita: adiciona 1 à representação
	}
	
	/**
     * Comprime a string de entrada usando a tabela de códigos de Huffman.
     * @param texto Texto a ser comprimido.
     * @return String comprimida em formato binário.
     */
	public String Comprimir(String texto) {
		String comprimido = "";
		// Percorre a string a ser comprimida e pega o caracter
		for(int i = 0; i < texto.length(); i++) {
			// Adiciona na string comprimida a representação huffman do caractere
			comprimido += tabelaHuffman.get(texto.charAt(i));
		}
		return comprimido;
	}
	
	/**
     * Descomprime uma string binária usando a árvore de Huffman.
     * @param comprimido Texto binário comprimido.
     * @return Texto original descomprimido.
     */
	public String Descomprimir(String comprimido) {
		String descomprimido = "";
		TrieNode noAtual = root;
		
		// Percorre cada bit e navega na árvore
        for (char bit : comprimido.toCharArray()) { // recebe 0 ou 1 do arquivo comprimido
            noAtual = (bit == '0') ? noAtual.getEsquerdo() : noAtual.getDireito(); // se for 0 vai para esquerda na arvore. se nao, vai para a direita.
            
            // Se alcançar uma folha, recupera o caractere e reinicia na raiz da arvore
            if (noAtual.getEsquerdo() == null && noAtual.getDireito() == null) {
                descomprimido += noAtual.getCaractere();
                noAtual = root;
            }
        }
        return descomprimido.toString();
	}
}
