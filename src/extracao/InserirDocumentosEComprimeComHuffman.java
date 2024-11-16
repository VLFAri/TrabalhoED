package extracao;
import java.io.*;
import java.util.*;
/**
 * Nessa classe, iremos inserir os artigos da pasta na qual o caminho digitado pelo usuário aponta e extrair o conteúdo de cada arquivo.
 * Após a extração do texto, o mesmo será comprimido em binário através do código de Huffman (classe ArvoreHuffman)
 * O método principal (que foi chamado pela main) retornará uma tabela com um inteiro como chave (número do artigo na qual o texto foi comprimido) e uma String que representa o conteudo comprimido de cada arquivo.
 */

public class InserirDocumentosEComprimeComHuffman { 
	
	//Esse método fará com que a String digitada pelo usuário se torne um caminho para diretório
	public File inserirPasta(String docs) {
		File diretorio = new File(docs);
		return diretorio;
	}
	
	//Método irá extrair o conteúdo de cada arquivo da pasta	
	public String extrairConteudoParaComprimirouNao(File arquivo) throws FileNotFoundException{
		Scanner escrevendoArquivo = new Scanner(arquivo);

		String texto = ""; //String para guardar o texto
		while (escrevendoArquivo.hasNext()){ //Enquanto o arquivo tiver linha, vai rodando...
			String conteudo = escrevendoArquivo.nextLine(); //Armazena o conteudo da linha na variável
			texto += conteudo + " "; //Concatena com o que já foi armazenado do arquivo
		}
		escrevendoArquivo.close(); //Quando terminar, fecha o arquivo
		return texto.replaceAll("\\s$", ""); //Retorna o texto extraído
	}
	
	public List<String> extrairConteudoParaBuscar(File arquivo) throws FileNotFoundException{
		List<String> conteudo_arquivos = new ArrayList<String>();
		Scanner escrevendoArquivo = new Scanner(arquivo);
		while(escrevendoArquivo.hasNext()){
			String conteudo = escrevendoArquivo.next();
			if (!conteudo.matches("^[^\\p{L}\\d].*") || !conteudo.matches("^[^\\p{L}\\d].*") ) {
			   conteudo = conteudo.replaceAll("^[^a-zA-Z0-9]+|[^a-zA-Z0-9]+$", "");
			}
			conteudo_arquivos.add(conteudo);
		}
		escrevendoArquivo.close();
		return conteudo_arquivos;
		
	}
	
	//Esse método retornara uma tabela com uma chave Integer (que significa o número do artigo na qual o texto pertence
	//E o conteúdo String, que será o conteúdo comprimido de cada arquivo representado pela chave
	public Map<String,String> insereDocumentosEComprime(String documento,boolean e_pComprimir) throws FileNotFoundException{
		//Tabela de map que irá guardar o nome do aritgo como chave e o conteudo comprimido em binário como a String
		Map<String,String> tabela_armazenaInformacao = new HashMap<>();
		
		//Array que será utilizado para armazenar arquivos
		File[] listOfFiles;
		
		//Essa váriavel irá chamar o método que transforma a String digitada pelo usuário em caminho para diretório
		File dir = inserirPasta(documento);
		
		//Caso o usuário tenha digitado um caminho válido...
		if(dir.isDirectory()) {
			listOfFiles = dir.listFiles(); //A variável criada outrora irá armazenar cada arquivo do diteptorio dir em uma posição (se houver arquivos)
			for(int i=0;i<listOfFiles.length;i++) { //Laço irá percorrer cada arquivo da pasta
				if(listOfFiles[i].isFile()) { //Se for um arquivo txt
					String texto = extrairConteudoParaComprimirouNao(listOfFiles[i]); //Essa variável irá chamar o método que ectrai o conteúdo do arquivo
					ArvoreHuffman ah = new ArvoreHuffman(texto); //Método para instanciar a classe ArvoreHuffman (compressão de dados)
					if(e_pComprimir) {
						tabela_armazenaInformacao.put(listOfFiles[i].getName(),ah.Comprimir(texto)); //Irá armazenar na tabela o nOME do artigo/arquivo como chave e o texto comprimido (comprimido pela classe ArovreHuffman)
					}
					else {
						tabela_armazenaInformacao.put(listOfFiles[i].getName(),texto); //Irá armazenar na tabela o nOME do artigo/arquivo como chave e o texto comprimido (comprimido pela classe ArovreHuffman)
					}
				}
			}
			
			if(!tabela_armazenaInformacao.isEmpty()) { //Se a tabela não está vazia, logo tem arquivos!
				if(e_pComprimir) {
					System.out.println("Documentos inseridos com sucesso!");
				}
				return tabela_armazenaInformacao; //Retornará a tabela com os valores associados
			}
			else { //Caso contrário, não tem!
				System.out.println("Diretório sem arquivos, por favor rode denovo e digite outro caminho");
				return null; //Irá retornar null e o código não irá rodar
			}
		}
		
		//Caso contrário...
		else {
			System.out.println("Diretório inexistente. Por favor, rode o código novamente e digite um caminho válido.");
			return null; //Irá retornar null e o código não irá rodar 
		}
		
		
	}
	
	public Map<String,List<String>> textos_nComprimidos(String documento) throws FileNotFoundException{
		//Tabela de map que irá guardar o nome do aritgo como chave e o conteudo comprimido em binário como a String
		Map<String,List<String>> tabela_armazenaInformacao = new HashMap<>();
				
		//Array que será utilizado para armazenar arquivos
		File[] listOfFiles;
				
		//Essa váriavel irá chamar o método que transforma a String digitada pelo usuário em caminho para diretório
		File dir = inserirPasta(documento);
				
		//Caso o usuário tenha digitado um caminho válido...
		if(dir.isDirectory()) {
			listOfFiles = dir.listFiles(); //A variável criada outrora irá armazenar cada arquivo do diteptorio dir em uma posição (se houver arquivos)
			for(int i=0;i<listOfFiles.length;i++) { //Laço irá percorrer cada arquivo da pasta
				if(listOfFiles[i].isFile()) { //Se for um arquivo txt
					List<String> palavras_texto = extrairConteudoParaBuscar(listOfFiles[i]); //Essa variável irá chamar o método que ectrai o conteúdo do arquivo
					tabela_armazenaInformacao.put(listOfFiles[i].getName(),palavras_texto); //Irá armazenar na tabela o nOME do artigo/arquivo como chave e o texto comprimido (comprimido pela classe ArovreHuffman)		
				}
			}
			return tabela_armazenaInformacao;
		}
		
		return null;
	}
}

