import extracao.InserirDocumentosEComprimeComHuffman;
import hash.HashTable;
import hash.HashEntry;
import java.util.Scanner;
import hash.HashTable;
import java.util.Map;
import java.util.LinkedList;
import java.io.IOException;

/**
 * Essa é a claase Main do trabalho, na qual serão inseridas todas as informações que o usuário colocar para casos de entrada (caminho do diretório dos documentos, escolha das hashs e palavra que deseja buscar).
 * Ela chama todas as funções e possibilita que o programa faça as suas funções
 */

public class App{

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in); //Objeto para entrada
		System.out.print("> Inserir documentos: ");
		String documento = sc.next(); //Caminho de diretório digitado para usuário
		InserirDocumentosEComprimeComHuffman idch = new InserirDocumentosEComprimeComHuffman(); //objeto que instancia a classe InserirDocumentosECompactaComHuffman 
		Map<String,String> artigosComprimidos = idch.insereDocumentosEComprime(documento); //Retorna um map com chave Integer e conteudo String com os textos de cada arquivo comprimido
		System.out.println("");
		
		//Caso "artigosComprimidos" seja null, significa que ou o caminho digitado pelo usuário é inválido ou o caminho não possuia arquivos, e assim, o programa não poderá fazer o seu trabalho
		if(artigosComprimidos != null) { //Mas caso seja válido e tenha arquivos TXT
			System.out.print("> Qual a função de hashing (divisao/djb2): ");
			String funcaoHashEscolhida = sc.next(); //Usuário vai escolher qual cálculo de hash vai usar
			
			//Usuário deve escolher entre divisao e djb2
			if(funcaoHashEscolhida.equals("divisao") || funcaoHashEscolhida.equals("djb2")) {
				LinkedList<HashEntry<String>>[] insere_hash;
				if(funcaoHashEscolhida.equals("divisao") ) { //Se for divisao
					HashTable<String> hash_table = new HashTable<String>(artigosComprimidos.size(),"divisao");
					insere_hash = hash_table.inserir(artigosComprimidos);
				}
				
				else { //Se for djb2
					HashTable<String> hash_table = new HashTable<String>(artigosComprimidos.size(),"djb2");
					insere_hash = hash_table.inserir(artigosComprimidos);
				}
				
				System.out.println("");
				System.out.print("> Buscar palavra: ");
				String palavra = sc.next();
				
				
			}
			
			else { //Nesse caso, o tipo escolhido não é válido para esse programa.
				System.out.println("Função de hashing não disponível (função de hashing não disponibilizada e/ou digitada errada! Por favor rode o código denovo");
			}
			
		}
	}
}

