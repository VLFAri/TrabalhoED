package extracao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InserirDocumentosEComprimeComHuffman {
	public File inserirPasta(String docs) {
		File diretorio = new File(docs);
		return diretorio;
	}
		
	public String extrairConteudo(File arquivo) throws FileNotFoundException{
		Scanner escrevendoArquivo = new Scanner(arquivo);

		String texto = "";
		while (escrevendoArquivo.hasNext()){
			String conteudo = escrevendoArquivo.nextLine();
			texto += conteudo + " ";	
		}
		escrevendoArquivo.close();
		return texto;
	}
	
	
	public List<String> insereDocumentosEComprime(String documento) throws FileNotFoundException{
		List<String> lista = new ArrayList<>();
		File[] listOfFiles;
		File dir = inserirPasta(documento);		
		if(dir.isDirectory()) {
			listOfFiles = dir.listFiles();
			for(int i=0;i<listOfFiles.length;i++) {
				if(listOfFiles[i].isFile()) {
					String texto = extrairConteudo(listOfFiles[i]);
				}
			}
			
			System.out.println("Documentos inseridos com sucesso!");
			return lista;
		}
		else {
			System.out.println("Diretório inexistente. Por favor, rode o código novamente e digite um caminho válido.");
			return null;
		}
		
		
	}
}