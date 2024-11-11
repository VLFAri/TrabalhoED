package hash;

/**
 * A classe HashEntry representa uma entrada em uma tabela hash, 
 * contendo uma chave e um valor associado.
 *
 * @param <K> o tipo da chave
 * @param <V> o tipo do valor
 */
public class HashEntry<V> {
    private V valor;
    
    /**
     * Construtor para criar uma nova entrada de hash com um valor.
     *
     * @param valor o valor a ser associado
     */
    public HashEntry(V valor) {
        this.valor = valor;
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
