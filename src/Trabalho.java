package trabalhoed;
import java.util.Scanner;
import java.util.List;
import java.io.IOException;

public class Trabalho { //Classe main do trabalho

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in); //Objeto para entrada
		System.out.print("Inserir documentos: ");
		String documento = sc.next(); //Caminho de diretório digitado para usuário
		InserirDocumentosEComprimeComHuffman idch = new InserirDocumentosEComprimeComHuffman(); //objeto que instancia a classe InserirDocumentosECompactaComHuffman 
		List<String> artigosComprimidos = idch.insereDocumentosEComprime(documento); //Retorna uma lista de String com os textos de cada arquivo comprimido
		
		if(artigosComprimidos != null) {
			
		}
	}
}

