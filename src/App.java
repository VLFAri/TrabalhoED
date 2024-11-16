import extracao.InserirDocumentosEComprimeComHuffman;
import hash.HashTable;
import hash.HashEntry;
import java.util.*;
import java.io.IOException;

/* Nomes dos integrantes do grupo 01: 
 * Ari Vargas Leal Filho
 * Beatriz da Silva Ponces (apresentadora)
 * Daniel Garcia Amaral
 * Rodrigo Ranzi de Souza
 */

/**
 * Essa é a classe Main do trabalho, na qual serão inseridas todas as informações que o usuário colocar para casos de entrada (caminho do diretório dos documentos, escolha das hashs e palavra que deseja buscar).
 * Ela chama todas as funções e possibilita que o programa faça as suas funções
 */

public class App{

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in); //Objeto para que o usuário consiga inserir a entrada
		System.out.print("> Inserir documentos: ");
		String documento = sc.next(); //Caminho de diretório digitado para usuário
		InserirDocumentosEComprimeComHuffman idch = new InserirDocumentosEComprimeComHuffman(); //objeto que instancia a classe InserirDocumentosECompactaComHuffman 
		
		/* O método abaixo irá retornas um map que tem como chave o nome do artigo e como conteudo o valor do arquivo comprimido. Ele será usado para inserção na Hash.
		 * O true descrito como parametro será utilizadp para que o programa saiba que deve fazer a compressão com Huffman (caso fosse false, ele não iria comprimir (será utilizado
		 * em outra parte do código). 
		 */
		Map<String,String> artigosComprimidos = idch.insereDocumentosEComprime(documento,true); 
		System.out.println("");
		
		//Caso "artigosComprimidos" seja null, significa que ou o caminho digitado pelo usuário é inválido ou o caminho não possuia arquivos, e assim, o programa não poderá fazer o seu trabalho
		if(artigosComprimidos != null) { //Mas caso seja válido e tenha arquivos TXT
			
			/* O método abaixo retorna um mapa que tem como chave o nume do arquivo e um array de strings como valor, onde cada posição do array é uma palavra
			 * do arquivo que dá nome a chave. Separamos em palavras para que seja possível inserir na Trie, já a mesma não pode inserir várias palavras, ou seja,
			 * um texto de uma só vez. Resumindo, cada chave terá um array com cada palavra do artigo (nas posições do array) que representa a chave
			 */
			Map<String,List<String>> palavrasNComprimidos = idch.textos_nComprimidos(documento);

			System.out.print("> Qual a função de hashing (divisao/djb2): ");
			String funcaoHashEscolhida = sc.next(); //Usuário vai escolher qual cálculo de hash vai usar
			
			//Usuário deve escolher entre divisao e djb2
			if(funcaoHashEscolhida.equals("divisao") || funcaoHashEscolhida.equals("djb2")) {
				HashTable<String,String> hash;
				if(funcaoHashEscolhida.equals("divisao") ) { //Se for divisao
					hash = new HashTable<String,String>(artigosComprimidos.size(),"divisao"); //Cria um objeto que instancia a classe HashTable se baseando no cálculo de divisão
				}
				
				else { //Se for djb2
					hash = new HashTable<String,String>(artigosComprimidos.size(),"djb2"); //Cria um objeto que instancia a classe HashTable se baseando no cálculo djb2
				}
				
				hash.inserir(artigosComprimidos); //Aqui ele vai indexar os arquivos comprimidos na Hash
				
				System.out.println("");
				
				//A partir daqui utilizaremos o Map que criamos antes, com o array de Strings, para inserir na Trie
				System.out.print("> Buscar palavra: ");
				String palavra = sc.next(); //Usuário irá digitar a palavra 
				System.out.println("A palavra '" + palavra + "' foi encontrada nos seguintes documentos:");
				boolean verifica = false; //boolean usado para ver se alguma palavra foi encontrada em algum artigo
				for(String word: palavrasNComprimidos.keySet()) { //Aqui ele vai percorrer todo o Mapa e ver se encontra a palavra utilizando a Trie
					Trie trie = new Trie(); //Objeto que instancia a classe Trie
					for(int i=0;i<palavrasNComprimidos.get(word).size();i++) { //Percorre o array da chave que estiver sendo percorrida no momento
						trie.inserir(palavrasNComprimidos.get(word).get(i)); //Insere cada palavra do array na Trie
					}
					if(trie.buscarPalavra(palavra)) { //O método irá retornar true se encontrar a palavra que o usuário digitou ou false caso contrário
						System.out.println(word); //Imprime o artigo na qual a palavra foi encontrada
						verifica = true; //Se achou, "verifica" agora fica true
					}
				} //Laço continua rodando até todas as chaves serem percorridas.
				
				if(!verifica) { //Caso verifica seja falso, significado que a palavra não foi encontrada em nenhum artigo.
					System.out.println("Palavra não encontrada em nenhum documento!");
				}
				
				else { //Caso tenha sido encontrada...
					System.out.println("");
					System.out.print("> Do(s) documento(s) que tem a palavra, me mostre o conteúdo do documento: ");
					String documento_escolhido = sc.next(); //Usuário vai digitar qual dos artigos que tem a palavra que ele inseriu ele quer abrir
					
					/* O mapa abaixo se baseia analogamente ao mapa que utilizamos para inserir os arquivos comprimidos na Hash (aritgosComprimidos), com o mesmo sistema de inserção.
					 * A diferença é que dessa vez ele não vai comprimir, ele vai trazer o texto da forma que ele foi extraido para que assim o programa possa mostrar 
					 * no terminal o artigo que o usuário pediu que abrisse.
					 * Dá para perceber a diferença no boolean que está como parâmetro, já que la em "artigosComprimidos" era true, significava que era para comprimir, nesse caso, como é
					 * false, não é para comprimir.  
					 */
					Map<String,String> artigosNComprimidos = idch.insereDocumentosEComprime(documento,false);
					
					//Aqui o método vai retornar o texto do arquivo
					hash.mostrarConteudo(documento_escolhido,artigosNComprimidos); // 
				}							
			}
			
			else { //Nesse caso, o tipo escolhido não é válido para esse programa.
				System.out.println("Função de hashing não disponível (função de hashing não disponibilizada e/ou digitada errada! Por favor rode o código denovo");
			}
			
		}
	}
}
