package hash;

/**
 * A classe HashEntry representa uma entrada em uma tabela hash, 
 * contendo uma chave e um valor associado.
 *
 * @param <K> o tipo da chave
 * @param <V> o tipo do valor
 */
public class HashEntry {
	private String nome;
    private String valor;
    
    /**
     * Construtor para criar uma nova entrada de hash com um valor.
     *
     * @param valor o valor a ser associado
     */
    public HashEntry(String nome, String valor) {
    	this.nome = nome;
        this.valor = valor;
    }
    
    /**
     * Retorna o nome associado a esta entrada.
     *
     * @return o valor
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome para esta entrada.
     *
     * @param nome o nome a ser definido
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o valor associado a esta entrada.
     *
     * @return o valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define o valor para esta entrada.
     *
     * @param valor o valor a ser definido
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
