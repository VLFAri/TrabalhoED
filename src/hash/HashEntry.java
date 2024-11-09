package hash;

/**
 * A classe HashEntry representa uma entrada em uma tabela hash, 
 * contendo uma chave e um valor associado.
 *
 * @param <K> o tipo da chave
 * @param <V> o tipo do valor
 */
public class HashEntry<K, V> {
    private K key;
    private V valor;
    
    /**
     * Construtor para criar uma nova entrada de hash com uma chave e um valor.
     *
     * @param key a chave a ser associada
     * @param valor o valor a ser associado
     */
    public HashEntry(K key, V valor) {
        this.key = key;
        this.valor = valor;
    }
    
    /**
     * Retorna uma representação em string da entrada de hash.
     *
     * @return uma string no formato "(chave, valor)"
     */
    public String toString() {
        return "(" + key + ", " + valor + ")";
    }

    /**
     * Retorna a chave associada a esta entrada.
     *
     * @return a chave
     */
    public K getKey() {
        return key;
    }

    /**
     * Define a chave para esta entrada.
     *
     * @param key a chave a ser definida
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Retorna o valor associado a esta entrada.
     *
     * @return o valor
     */
    public V getValor() {
        return valor;
    }

    /**
     * Define o valor para esta entrada.
     *
     * @param valor o valor a ser definido
     */
    public void setValor(V valor) {
        this.valor = valor;
    }
}