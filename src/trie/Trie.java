package trie;

public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Método para inserir uma palavra na Trie
    public void inserir(String palavra) {
        TrieNode node = root;
        for (char c : palavra.toCharArray()) { // Pega letra por letra da palavra
            /*
             * Se, entre seus filhos da arvore, não existir c
             * é criado um filho c.
             * 
             * Se existir c, mas for o "último" do galho da arvore,
             * associa-a ao valor fornecido e retorna nulo.
             * 
             * Caso contrário, retorna o valor atual.
             */
            node.getFilhos().putIfAbsent(c, new TrieNode());
            node = node.getFilhos().get(c); // vai para o próximo
        }
        node.setFimPalavra(true);
    }

    // Método para buscar uma palavra completa na Trie
    public boolean buscarPalavra(String palavra) {
        TrieNode node = root;
        for (char c : palavra.toCharArray()) { // Pega letra por letra
            node = node.getFilhos().get(c);
            if (node == null) {
                return false;
            }
        }
        return node.isFimPalavra();
    }

    // Método para verificar se existe alguma palavra que começa com o prefixo
    public boolean buscarPrefixo(String prefixo) {
        TrieNode node = root;
        for (char c : prefixo.toCharArray()) {
            node = node.getFilhos().get(c);
            if (node == null) {
                return false;
            }
        }
        return true;
    }
}
