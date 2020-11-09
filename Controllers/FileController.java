package Controllers;
import Models.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Souza, Gabriel Ottoboni, João Pedro Silva
 *
 *<h1>
 *     Classe que representa a Controller do Arquivo para o projeto final.
 *     Feito para ler e escrever arquivos com os dados da COVID.
 *</h1>
 */
public class FileController {

    /**
     * Exporta os dados pesquisados para um arquivo .tsv.
     * @param nomeArquivo nome do arquivo.
     * @param pesquisa dados retornados pela busca.
     */
    public static void escreverArquivoTsv(String caminhoArquivo, List<Medicao> pesquisa) {

        File filePesquisa = new File(caminhoArquivo + ".tsv");
        FileOutputStream output;

        try {
            output = new FileOutputStream(filePesquisa);
        }
        catch(FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo!");
            return;
        }
        try {
            for (Medicao medicao : pesquisa) {
                output.write(medicao.toString().getBytes());
            }
            output.close();
        }
        catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo!");
        }
    }

    /**
     * Exporta os dados da API para um arquivo .ser,
     * para servir como banco de dados.
     * @param nomeArquivo nome do arquivo.
     * @param objects dados a serem salvos no arquivo.
     */
    public static void escreverArquivoSer(String caminhoArquivo, List objects) {
        OutputStream file;
        ObjectOutputStream output;
        try {
            file = new FileOutputStream(caminhoArquivo + ".ser");
            output = new ObjectOutputStream(file);
        } catch(IOException e) {
            System.out.println("Erro ao abrir o arquivo!");
            System.exit(1);
            return;
        }
        try {
            output.writeObject(objects);
            output.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo!");
            System.exit(1);
        }
    }

    /**
     * Lê os dados do arquivo e caso funcione, retorna esses dados.
     * @param nomeArquivo nome do arquivo.
     * @return se funcionar, retorna um ArrayList com o dados do arquivo.
     * Caso não funcione, retorna null.
     */
    public static ArrayList lerArquivo(String caminhoArquivo) {
        InputStream file;
        ObjectInputStream input;
        try{
            file = new FileInputStream(caminhoArquivo + ".ser");
            input = new ObjectInputStream(file);
        } catch(FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo!");
            System.exit(1);
            return null;
        } catch(IOException e) {
            System.out.println("Erro ao o arquivo!");
            System.exit(1);
            return null;
        }
        try {
            return (ArrayList) input.readObject();
        } catch(IOException e) {
            System.out.println("Erro ao ler o arquivo");
            System.exit(1);
            return null;
        } catch (ClassNotFoundException e){
            System.out.println("Classe nao encontrada");
            System.exit(1);
            return null;
        }
    }
}
