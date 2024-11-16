package hash;
import compactacao.ArvoreHuffman;
import java.util.*;

/**
 * A classe HashTable implementa uma tabela hash genérica para armazenar pares chave-valor.
 * Utiliza listas encadeadas para lidar com colisões.
 *
 * @param <V> o tipo do valor
 */
public class HashTable<K,V> {

    private LinkedList<HashEntry>[] tabelaHash; // Array de listas encadeadas para armazenar as entradas da tabela hash
    private int size; // Tamanho da tabela hash
    private String funcaoHashing; // Armazena qual função hash utilizar.

    /**
     * Constrói uma nova tabela hash com o tamanho especificado.
     *
     * @param size o tamanho da tabela hash
     * @param funcaoHashing a função escolhida para determinar a posição dos valores (divisao ou djb2)
     */
    public HashTable(int size, String funcaoHashing) {
        tabelaHash = new LinkedList[size];
        this.size = size;
        this.funcaoHashing = funcaoHashing;
    }

    private int getPosicao(String value) {
		if(funcaoHashing == "divisao"){
            return hashDivisao(value);
        } else if (funcaoHashing == "djb2"){
            return hashDJB2(value);
        } else {
            return -1;
        }
	}

    private int hashDivisao (String texto) {
        int soma = 0;
        for (char c : texto.toCharArray()) {
            soma += (int)c ;
        }
        return soma % size;
    }

    private int hashDJB2 (String texto) {
        long hash = 5381;
        for (char c : texto.toCharArray()) {
        	hash = (( hash << 5) + hash ) + c; // hash * 33
        }
        return Math.abs((int) (hash % size)) ; // hash % 2147483647
    }

    /**
     * Retorna uma lista de valores associados à chave especificada na tabela hash.
     *
     * @param key a chave cuja lista de valores deve ser retornada
     * @return uma lista de valores associados à chave ou null se a chave não existir
     */
    public LinkedList<String> getValor(String key) {
        int posicao;
        LinkedList<String> valoresEncontrados = new LinkedList<String>();

        // Verifica se a chave é nula
        if (key == null) {
            return null;
        }

        // Obtém a posição na tabela hash
        posicao = getPosicao(key);

        // Verifica se a lista na posição é nula
        if (tabelaHash[posicao] == null) {
            return null; // Nenhum elemento com essa chave
        } else {
            // Itera pela lista na posição para encontrar os valores
            LinkedList<HashEntry> listaAtual = tabelaHash[posicao];
            for (int i = 0; i < listaAtual.size(); i++) {
                HashEntry entradaAtual = listaAtual.get(i);
                if (key.equals(entradaAtual.getValor())) {
                    valoresEncontrados.add(entradaAtual.getValor()); // Adiciona o valor encontrado
                }
            }
            return valoresEncontrados; // Retorna a lista de valores encontrados
        }
    }

    /**
     * Insere um par chave-valor na tabela hash.
     *
     * @param key a chave a ser inserida
     * @param valor o valor a ser associado à chave
     * @return true se a inserção for bem-sucedida, false se a chave for nula ou se a chave já existe com o mesmo valor.
     */
    public boolean inserir(Map<String,String> arquivos) {
    	
    	for(String a: arquivos.keySet()) {
    		String texto = arquivos.get(a);
    		String nome = a;
    		
	        int posicao;
	
	        // Verifica se a chave já existe com o mesmo valor
	        LinkedList<String> valorAtualParaChave = getValor(texto);
	        if (valorAtualParaChave != null && valorAtualParaChave.contains(texto)) {
	            return false; // Valor já existente para a chave
	        }
	
	        // Obtém a posição na tabela hash
	        posicao = getPosicao(texto);
	
	        // Obtém a lista atual na posição
	        LinkedList<HashEntry> listaAtual = tabelaHash[posicao];
	
	        // Se a posição é nula, cria uma nova lista
	        if (listaAtual == null) {
	            listaAtual = new LinkedList<>();
	        }
	
	        listaAtual.add(new HashEntry(nome,texto)); // Adiciona a nova entrada
	        tabelaHash[posicao] = listaAtual; // Atualiza a tabela hash
	        
    	}
    	
    	System.out.println("Documentos indexados com sucesso!");
        return true; // Inserido com sucesso
    }
    
    public void mostrarConteudo(String name, Map <String,String> docs) {
		for(int i = 0; i < tabelaHash.length; i++) {
			if(tabelaHash[i] == null) {
				continue;
			}
			else {
				LinkedList<HashEntry> currentList = tabelaHash[i];
				for (int j = 0; j < currentList.size(); j++) {
					if(currentList.get(j).getNome().equals(name)){
						ArvoreHuffman arvore = new ArvoreHuffman(docs.get(name));
						String texto = arvore.Descomprimir(currentList.get(j).getValor());
						
						String[] paragraphs = texto.split("     ");
						System.out.println(name + ":");				        
				        for (String paragraph : paragraphs) {
				            System.out.print("    "); // Espaçamento inicial do parágrafo
				            String[] words = paragraph.trim().split("\\s+");
				            int wordCount = 0;
				            
				            // Formatar linha com limite de palavras
				            for (String word : words) {
				                if (wordCount >= 20) {
				                    System.out.println();
				                    wordCount = 0;
				                }
				                System.out.print(word + " ");
				                wordCount++;
				            }
				            System.out.println("");
				        }
				    }
				}
					
			}
		}
	}	
}
