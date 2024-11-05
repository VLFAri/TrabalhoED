import java.io.*;
import java.util.Scanner;

public class InserirDocumento {
	public File inserirPasta(String docs) {
		File diretorio = new File(docs);
		return diretorio;
	}
		
	public void inserirArtigos(String documento) throws FileNotFoundException{
		File[] listOfFiles;
		File dir = inserirPasta(documento);
		if (dir.isDirectory()) {
			listOfFiles = dir.listFiles();
			for(int i=0;i<listOfFiles.length;i++) {
				File arquivo = listOfFiles[i];
				Scanner escrevendoArquivo = new Scanner(arquivo);

				while (escrevendoArquivo.hasNext()){
					String conteudo = escrevendoArquivo.nextLine();

				}
				escrevendoArquivo.close();
			}
		
			System.out.println("Documentos inseridos com sucesso!");
		}
		else {
			System.out.println("Diretório inexistente");
		}	
	}	
	
}
