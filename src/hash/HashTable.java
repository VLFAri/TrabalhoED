package hash;

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
    @SuppressWarnings("unchecked")
    public HashTable(int size, String funcaoHashing) {
        tabelaHash = new LinkedList[size];
        this.size = size;
        this.funcaoHashing = funcaoHashing;
    }

    private int getPosicao(String value) {
	if(funcaoHashing.equals("divisao")){
            return hashDivisao(value);
        } else if (funcaoHashing.equals("djb2")){
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
        return Math.abs((int) (hash % size)) ; 
    }

    /**
     * Retorna o valor associado à chave especificada.
     *
     * @param chave a chave a ser buscada
     * @return o valor associado ou null se não encontrado
     */
    public String getValor(String chave) {
        int posicao = getPosicao(chave);
        LinkedList<HashEntry> listaAtual = tabelaHash[posicao];

        if (listaAtual != null) {
            for (HashEntry entry : listaAtual) {
                if (chave.equals(entry.getNome())) {
                    return entry.getValor();
                }
            }
        }
        return null; // Chave não encontrada
    }

    /**
     * Insere um mapa de chave-valor na tabela hash.
     *
     * @param artigos o mapa contendo chaves e valores a serem inseridos
     * @return true se todos os itens forem inseridos com sucesso
     */
    public boolean inserir(Map<String, String> artigos) {
        for (String chave : artigos.keySet()) {
            String valor = artigos.get(chave);
            int posicao = getPosicao(chave);

            // Se a lista na posição é nula, inicializa
            if (tabelaHash[posicao] == null) {
                tabelaHash[posicao] = new LinkedList<>();
            }

            // Verifica se a chave já existe na lista
            LinkedList<HashEntry> listaAtual = tabelaHash[posicao];
            for (HashEntry entry : listaAtual) {
                if (chave.equals(entry.getNome())) {
                    entry.setValor(valor); // Atualiza o valor se a chave já existe
                    return true;
                }
            }

            // Adiciona a nova entrada
            listaAtual.add(new HashEntry(chave, valor));
        }
        return true;
    }
}
