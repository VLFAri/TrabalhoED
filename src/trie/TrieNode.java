package trie;
import java.util.*;

public class TrieNode {
    
    private Map<Character, TrieNode> filhos;
    private boolean fimPalavra;

    // Construtor padrão
    public TrieNode() {
        this.filhos = new HashMap<>();
        this.fimPalavra = false;
    }

    // Construtor para definir explicitamente o estado de `fimPalavra`
    public TrieNode(boolean fimPalavra) {
        this.filhos = new HashMap<>();
        this.fimPalavra = fimPalavra;
    }

    /**
     * @return Map<Character, TrieNode> return the filhos
     */
    public Map<Character, TrieNode> getFilhos() {
        return filhos;
    }

    /**
     * @param filhos the filhos to set
     */
    public void setFilhos(Map<Character, TrieNode> filhos) {
        this.filhos = filhos;
    }

    /**
     * @return boolean return the fimPalavra
     */
    public boolean isFimPalavra() {
        return fimPalavra;
    }
    
    /**
     * @param fimPalavra the fimPalavra to set
     */
    public void setFimPalavra(boolean fimPalavra) {
        this.fimPalavra = fimPalavra;
    }

}
