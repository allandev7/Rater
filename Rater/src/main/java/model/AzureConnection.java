package model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.file.CloudFile;
import com.microsoft.azure.storage.file.CloudFileClient;
import com.microsoft.azure.storage.file.CloudFileDirectory;
import com.microsoft.azure.storage.file.CloudFileShare;

public class AzureConnection {
	//String para conexão
	public static final String storageConnectionString =
		    "DefaultEndpointsProtocol=https;" +
		    "AccountName=rater;" +
		    "AccountKey=hWdeapYU1cxuqzSFUx1vCfzHBAsz/AkgnU0L08fGJ/5W+56LJ2bUCaSB5IYM5WnJu8OlUulV1bO4iUvXo8yo8A==";
    
    public void down(String nomeArquivo) {
    		try {
    			// definindo a conexão
				CloudStorageAccount conta = CloudStorageAccount.parse(storageConnectionString);
				
				//criar cliente de arquivo para obter acesso ao compartilhamento
				CloudFileClient cliente = conta.createCloudFileClient();
				
				//criar objeto pera obter o share
				CloudFileShare share = cliente.getShareReference("rater");
				
				//criar objeto para acessar destino raiz
				CloudFileDirectory root = share.getRootDirectoryReference();
				
				//criar objeto para acessar pasta do arquivo
				CloudFileDirectory destino = root.getDirectoryReference("imagens");
				
				//criar objeto para pegar arquivo
				CloudFile arquivo =  destino.getFileReference(nomeArquivo);
				
				//fazer download no local desejado
				arquivo.downloadToFile("C:\\Rater/imagens/" + nomeArquivo);
				
				
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (StorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
    
    public void upload(String caminho, String nome) {
    	//definindo a conta 
    	try {
    		// definindo a conexão
			CloudStorageAccount conta = CloudStorageAccount.parse(storageConnectionString);
			
			//criar cliente de arquivo para obter acesso ao compartilhamento
			CloudFileClient cliente = conta.createCloudFileClient();
			
			//criar objeto pera obter o share
			CloudFileShare share = cliente.getShareReference("rater");
			
			//criar objeto para acessar destino raiz
			CloudFileDirectory root = share.getRootDirectoryReference();
			
			//criar objeto para acessar pasta do arquivo
			CloudFileDirectory destino = root.getDirectoryReference("imagens");
			
			//definir onde o arquivo vai ficar e o nome do mesmo
			CloudFile localArquivo = destino.getFileReference(nome);
			
			//fazer upload
			localArquivo.uploadFromFile(caminho);
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
