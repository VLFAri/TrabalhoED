package compactacao;

/**
 * Classe que representa um nó na árvore de Huffman.
 */
public class TrieNode implements Comparable<TrieNode> {

    /** Caractere armazenado no nó. */
    private char caractere;

    /** Frequência associada ao caractere. */
    private int frequencia;

    /** Referência para o nó filho à esquerda. */
    private TrieNode esquerdo;

    /** Referência para o nó filho à direita. */
    private TrieNode direito;

    /**
     * Construtor para criar um nó intermediário sem caractere.
     *
     * @param frequencia A frequência associada ao nó.
     * @param esquerdo O nó filho à esquerda.
     * @param direito O nó filho à direita.
     */
    public TrieNode(int frequencia, TrieNode esquerdo, TrieNode direito) { 
        this.caractere = '\0'; // Indica que este nó não representa um caractere específico
        this.frequencia = frequencia;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

    /**
     * Construtor para criar um nó folha com um caractere.
     *
     * @param caractere O caractere armazenado no nó.
     * @param frequencia A frequência associada ao caractere.
     */
    public TrieNode(char caractere, int frequencia) { 
        this.caractere = caractere;
        this.frequencia = frequencia;
        this.esquerdo = null;
        this.direito = null;
    }

    /**
     * Compara este nó com outro nó TrieNode com base na frequência.
     *
     * @param outroNo O outro nó com o qual será comparado.
     * @return Um valor negativo se este nó tiver menor frequência que o outro nó,
     *         zero se tiverem a mesma frequência, ou um valor positivo se tiver maior frequência.
     */
    @Override
    public int compareTo(TrieNode outroNo) {
        return this.frequencia - outroNo.frequencia;
    }

    /**
     * Retorna o caractere armazenado neste nó.
     *
     * @return char O caractere armazenado.
     */
    public char getCaractere() {
        return caractere;
    }

    /**
     * Retorna a frequência com que o caractere aparece no texto.
     *
     * @return int A frequência do caractere.
     */
    public int getFrequencia() {
        return frequencia;
    }

    /**
     * Modifica o caractere armazenado neste nó.
     *
     * @param caractere O novo caractere a ser armazenado.
     */
    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }

    /**
     * Modifica a frequência com que o caractere aparece no texto.
     *
     * @param frequencia A nova frequência do caractere.
     */
    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    /**
     * Retorna o filho esquerdo do nó atual.
     *
     * @return TrieNode O nó filho à esquerda.
     */
    public TrieNode getEsquerdo() {
        return esquerdo;
    }

    /**
     * Modifica o nó filho à esquerda.
     *
     * @param esquerdo O novo nó filho à esquerda.
     */
    public void setEsquerdo(TrieNode esquerdo) {
        this.esquerdo = esquerdo;
    }

    /**
     * Retorna o filho direito do nó atual.
     *
     * @return TrieNode O nó filho à direita.
     */
    public TrieNode getDireito() {
        return direito;
    }

    /**
     * Modifica o nó filho à direita.
     *
     * @param direito O novo nó filho à direita.
     */
    public void setDireito(TrieNode direito) {
        this.direito = direito;
    }

}
