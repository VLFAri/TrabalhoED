import extracao.InserirDocumentosEComprimeComHuffman;
import hash.HashTable;
import hash.HashEntry;
import trie.Trie;
import java.util.*;
import java.io.IOException;

/* Nomes dos integrantes do grupo 01: 
 * Ari Vargas Leal Filho
 * Beatriz da Silva Ponces (apresentadora)
 * Daniel Garcia Amaral
 * Rodrigo Ranzi de Souza
 */

/**
 * Essa é a classe de teste do trabalho, na qual as entradas já estão definidas 
 * (caminho do diretório dos documentos, escolha das hashs e palavras buscadas).
 * 
 */

public class AppMetricasAutomatico{

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in); //Objeto para que o usuário consiga inserir a entrada
		System.out.print("> Inserir documentos: ");
		String documento = sc.next(); //Caminho de diretório digitado para usuário

		System.out.println("Inserindo documentos do diretório '" + documento + "'...");
		
		long tempoInicialInsercao = System.nanoTime(); // Marca tempo inicial da inserção

		InserirDocumentosEComprimeComHuffman idch = new InserirDocumentosEComprimeComHuffman(); //objeto que instancia a classe InserirDocumentosECompactaComHuffman 
		
		/* O método abaixo irá retornas um map que tem como chave o nome do artigo e como conteudo o valor do arquivo comprimido. Ele será usado para inserção na Hash.
		 * O true descrito como parametro será utilizadp para que o programa saiba que deve fazer a compressão com Huffman (caso fosse false, ele não iria comprimir (será utilizado
		 * em outra parte do código). 
		 */

		Map<String,String> artigosComprimidos = idch.insereDocumentosEComprime(documento,true); 

		long tempoFinalInsercao = System.nanoTime(); // Marca tempo final da inserção
		long tempoInsercao = tempoFinalInsercao - tempoInicialInsercao; // Calcula o tempo (final - inicial)
		System.out.println("Tempo de inserção: " + tempoInsercao/1e6 + " ms");


		System.out.println("");
		
		//Caso "artigosComprimidos" seja null, significa que ou o caminho digitado pelo usuário é inválido ou o caminho não possuia arquivos, e assim, o programa não poderá fazer o seu trabalho
		if(artigosComprimidos != null) { //Mas caso seja válido e tenha arquivos TXT
			
			/* O método abaixo retorna um mapa que tem como chave o nume do arquivo e um array de strings como valor, onde cada posição do array é uma palavra
			 * do arquivo que dá nome a chave. Separamos em palavras para que seja possível inserir na Trie, já a mesma não pode inserir várias palavras, ou seja,
			 * um texto de uma só vez. Resumindo, cada chave terá um array com cada palavra do artigo (nas posições do array) que representa a chave
			 */
			Map<String,List<String>> palavrasNComprimidos = idch.textos_nComprimidos(documento);

			
			//System.out.print("> Qual a função de hashing (divisao/djb2): ");
			//String funcaoHashEscolhida = sc.next(); //Usuário vai escolher qual cálculo de hash vai usar
			
			//Usuário deve escolher entre divisao e djb2
			//if(funcaoHashEscolhida.equals("divisao") || funcaoHashEscolhida.equals("djb2")) {


			String[] funcoesHash = {"divisao", "djb2"}; // Definição das funções, para a execução automática

			// Nesse for, será executado todo o fluxo para cada uma das funções hash.
			for (String funcaoHashEscolhida : funcoesHash) {

				System.out.println("\n===== Testando função de hashing: " + funcaoHashEscolhida + " =====");

				// Mede memória antes de criar a HashTable e indexar
				System.gc(); // Força coleta de lixo
				long memoriaInicialHash = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

				HashTable<String,String> hash;
				if(funcaoHashEscolhida.equals("divisao") ) { //Se for divisao
					hash = new HashTable<String,String>(artigosComprimidos.size(),"divisao"); //Cria um objeto que instancia a classe HashTable se baseando no cálculo de divisão
				}
				
				else { //Se for djb2
					hash = new HashTable<String,String>(artigosComprimidos.size(),"djb2"); //Cria um objeto que instancia a classe HashTable se baseando no cálculo djb2
				}
				
				hash.inserir(artigosComprimidos); //Aqui ele vai indexar os arquivos comprimidos na Hash

				// Medir memória após a Tabela Hash ser preenchida
                long memoriaFinalHash = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.out.println("Consumo de memória pela Tabela Hash: " + (memoriaFinalHash - memoriaInicialHash) / (1024*1024) + " MB");
				
				System.out.println("");
				
				//A partir daqui utilizaremos o Map que criamos antes, com o array de Strings, para inserir na Trie

				// Insere as palavras do arquivo de palavras para testar em uma lista
				System.out.println("Buscando as palavras do arquivo '../dados/100_palavras.txt'...");
				List<String> expressoesBusca = carregarExpressoes("../dados/100_palavras.txt");


				// Map para armazenar os tempos de busca de cada palavra
                Map<String, Long> temposBusca = new LinkedHashMap<>();

                // Medir memória antes de inserir na Trie
				System.gc();
				long memoriaInicialTrie = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

				int totalEncontradas = 0; // Para contar o número de palavras encontradas

				for (String palavra : expressoesBusca) {

					boolean verifica = false; //boolean usado para ver se alguma palavra foi encontrada em algum artigo

					long inicioTempoBusca = System.nanoTime(); // Marca o tempo de início da busca

					for(String word: palavrasNComprimidos.keySet()) { //Aqui ele vai percorrer todo o Mapa e ver se encontra a palavra utilizando a Trie
						Trie new_trie = new Trie(); //Objeto que instancia a classe Trie
						for(int i=0;i<palavrasNComprimidos.get(word).size();i++) { //Percorre o array da chave que estiver sendo percorrida no momento
							new_trie.inserir(palavrasNComprimidos.get(word).get(i)); //Insere cada palavra do array na Trie
						}
						
						if(new_trie.buscarPalavra(palavra)) { //O método irá retornar true se encontrar a palavra que o usuário digitou ou false caso contrário
							//System.out.println(word); //Imprime o artigo na qual a palavra foi encontrada
							verifica = true; //Se achou, "verifica" agora fica true
						} 
					} //Laço continua rodando até todas as chaves serem percorridas.

					long finalTempoBusca = System.nanoTime(); // Para de medir o tempo de busca na Trie
        			long tempoBusca = finalTempoBusca - inicioTempoBusca; // Calcula o tempo de busca na Trie (final - inicial)
        			temposBusca.put(palavra, tempoBusca);
					
					// Se for encontrada, conta mais um no total se encontradas
					if(verifica){
						totalEncontradas++;
					}

					/*
					if(!verifica) { //Caso verifica seja falso, significado que a palavra não foi encontrada em nenhum artigo.
						System.out.println("Palavra não encontrada em nenhum documento!");
					} */

				}

				// Medir memória após inserir na Trie
				long memoriaFinalTrie = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long memoriaUtilizadaTrie = memoriaFinalTrie - memoriaInicialTrie;
				System.out.println("Memória utilizada Trie: " + memoriaUtilizadaTrie / (1024*1024) + " MB");

				// Printa o tempo de busca de cada uma das palavras
				System.out.println("\nTempos de busca para cada palavra (em ms):");
                for (Map.Entry<String, Long> entry : temposBusca.entrySet()) {
                    System.out.println("Palavra: " + entry.getKey() + ", Tempo: " + (entry.getValue())/1e6 + " ms");
                }

				// Calcular o tempo total de busca
                long totalBusca = temposBusca.values().stream().mapToLong(Long::longValue).sum();
                System.out.println("\nTempo total de busca: " + totalBusca/1e6 + " ms");

				// Calcular o tempo médio de busca
                System.out.println("Tempo médio de busca: " + (totalBusca/1e6)/temposBusca.size() + " ms");

				// Palavras encontradas
                System.out.println("Total de palavras encontradas: " + totalEncontradas + " de " + expressoesBusca.size());

				
				/*
				else { //Caso tenha sido encontrada...
					System.out.println("");
					System.out.print("> Do(s) documento(s) que tem a palavra, me mostre o conteúdo do documento: ");
					String documento_escolhido = sc.next(); //Usuário vai digitar qual dos artigos que tem a palavra que ele inseriu ele quer abrir
					
					/* O mapa abaixo se baseia analogamente ao mapa que utilizamos para inserir os arquivos comprimidos na Hash (aritgosComprimidos), com o mesmo sistema de inserção.
					 * A diferença é que dessa vez ele não vai comprimir, ele vai trazer o texto da forma que ele foi extraido para que assim o programa possa mostrar 
					 * no terminal o artigo que o usuário pediu que abrisse.
					 * Dá para perceber a diferença no boolean que está como parâmetro, já que la em "artigosComprimidos" era true, significava que era para comprimir, nesse caso, como é
					 * false, não é para comprimir.  
					 
					Map<String,String> artigosNComprimidos = idch.insereDocumentosEComprime(documento,false);
					
					//Aqui o método vai retornar o texto do arquivo
					hash.mostrarConteudo(documento_escolhido,artigosNComprimidos); // 
				}			
				*/				
			}
			
		}
	}

	// Esse método serve para carregar as expressões do arquivo com as 100 palavras em uma lista
	public static List<String> carregarExpressoes(String caminhoArquivo) {
		List<String> expressoes = new ArrayList<>();
		try (Scanner scanner = new Scanner(new java.io.File(caminhoArquivo))) {
			while (scanner.hasNextLine()) {
				expressoes.add(scanner.nextLine().trim());
			}
		} catch (IOException e) {
			System.out.println("Erro ao carregar o arquivo de expressões: " + e.getMessage());
		}
		return expressoes;
    }
}
