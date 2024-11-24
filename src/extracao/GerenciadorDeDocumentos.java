package extracao;

import java.io.*;
import java.util.*;

/**
 * Classe responsável por gerenciar a extração e preparação de textos de documentos em um diretório fornecido.
 * Lida com a leitura de arquivos, extração de conteúdo e processamento de palavras para futuras buscas.
 */
public class GerenciadorDeDocumentos {

    /**
     * Transforma uma string representando um caminho em um diretório.
     *
     * @param caminho Caminho fornecido pelo usuário.
     * @return Objeto {@link File} representando o diretório, ou {@code null} se o caminho for inválido.
     */
    public File transformaEmPasta(String caminho) {
        File diretorio = new File(caminho);
        if (diretorio.isDirectory()) {
            return diretorio;
        } else {
            return null;
        }
    }

    /**
     * Extrai o conteúdo completo de um arquivo.
     *
     * @param arquivo O arquivo a ser processado.
     * @return String contendo o conteúdo do arquivo.
     * @throws FileNotFoundException Se o arquivo não for encontrado.
     */
    public String extrairConteudo(File arquivo) throws FileNotFoundException {
		/*
		 * StringBuilder pode ser usada quando você deseja modificar
		 * uma cadeia de caracteres sem criar um novo objeto.
		 * Por exemplo, o uso da classe StringBuilder pode melhorar
		 * o desempenho ao concatenar várias cadeias de caracteres em um loop.
		 */
        StringBuilder conteudo = new StringBuilder();
		
        try (Scanner imput = new Scanner(arquivo)) {
            while (imput.hasNextLine()) {
                conteudo.append(imput.nextLine()).append("\n");
            }
        }
        return conteudo.toString().replaceAll("\\s$", "\n");
    }

    /**
     * Extrai e processa palavras de um arquivo, removendo caracteres especiais das extremidades.
     *
     * @param arquivo O arquivo a ser processado.
     * @return Lista de palavras extraídas e processadas.
     * @throws FileNotFoundException Se o arquivo não for encontrado.
     */
    public List<String> extrairConteudoParaBuscar(File arquivo) throws FileNotFoundException {
        List<String> conteudoArquivos = new ArrayList<>();
        try (Scanner imput = new Scanner(arquivo)) {
            while (imput.hasNext()) {
                String conteudo = imput.next();
                conteudo = conteudo.replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
                conteudoArquivos.add(conteudo);
            }
        }
        return conteudoArquivos;
    }

    /**
     * Prepara um mapa com os conteúdos dos arquivos de um diretório.
     *
     * @param diretorio Caminho para o diretório contendo os arquivos.
     * @return Mapa com o nome dos arquivos como chave e o conteúdo como valor.
     * @throws FileNotFoundException Se algum arquivo não for encontrado.
     */
    public Map<String, String> carregarConteudoArquivos(String diretorio) throws FileNotFoundException {
        File pasta = transformaEmPasta(diretorio);
        if (pasta == null) {
            System.exit(0);
        }

        Map<String, String> tabelaInformacoes = new HashMap<>();
        File[] listaArquivos = pasta.listFiles();

        if (listaArquivos != null) {
            for (File arquivo : listaArquivos) {
                if (arquivo.isFile()) {
                    tabelaInformacoes.put(arquivo.getName(), extrairConteudo(arquivo));
                }
            }
        }
        return tabelaInformacoes;
    }

    /**
     * Prepara um mapa de palavras para busca, extraídas de arquivos em um diretório.
     *
     * @param diretorio Caminho para o diretório contendo os arquivos.
     * @return Mapa com o nome dos arquivos como chave e uma lista de palavras como valor.
     * @throws FileNotFoundException Se algum arquivo não for encontrado.
     */
    public Map<String, List<String>> carregarPalavrasParaBusca(String diretorio) throws FileNotFoundException {
        File pasta = transformaEmPasta(diretorio);
		if (pasta == null) {
            System.exit(0);
        }

        Map<String, List<String>> tabelaInformacoes = new HashMap<>();
        File[] listaArquivos = pasta.listFiles();

        if (listaArquivos != null) {
            for (File arquivo : listaArquivos) {
                if (arquivo.isFile()) {
                    tabelaInformacoes.put(arquivo.getName(), extrairConteudoParaBuscar(arquivo));
                }
            }
        }
        return tabelaInformacoes;
    }
}