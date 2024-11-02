package trabalhoed;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;

public class Trabalho {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Inserir documentos: ");
		String documento = sc.next();
		InserirDocumento id = new InserirDocumento();
		id.inserirArtigos(documento);
		
		
	
	}
}
