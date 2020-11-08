package Controllers;
import Models.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.lang.*;

public class FileController {
    public static void escreverArquivoTsv(String nomeArquivo, List<Medicao> pesquisa){
        File filePesquisa = new File("resources/" + nomeArquivo + ".tsv");
        FileOutputStream output = null;

        try {
            output = new FileOutputStream(filePesquisa);
        }
        catch(FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo!");
            return;
        }
        try {
            for(int i = 0; i<pesquisa.size(); i++){
                output.write(pesquisa.get(i).toString().getBytes());
            }
            output.close();
        }
        catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo!");
            return;
        }
    }
    public static void escreverArquivo(String nomeArquivo, ArrayList objects){
        OutputStream file = null;
        ObjectOutputStream output = null;
        try {
            file = new FileOutputStream("resources/"+ nomeArquivo + ".ser");
            output = new ObjectOutputStream(file);
        } catch(FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo!");
            return;
        } catch(IOException e){
            System.out.println("Erro ao abrir o arquivo!");
            return;
        }
        try {
            output.writeObject(objects);

            output.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo!");
            return;
        }
    }
    public static ArrayList lerArquivo(String nomeArquivo){
        InputStream file = null;
        ObjectInputStream input = null;
        try{
            file = new FileInputStream("resources/" + nomeArquivo + ".ser");
            input = new ObjectInputStream(file);
        } catch(FileNotFoundException e){
            System.out.println("Erro ao abrir o arquivo!");
            return null;
        } catch(IOException e){
            System.out.println("Erro ao o arquivo!");
            return null;
        }
        try{
            return (ArrayList) input.readObject();
        } catch(IOException e){
            System.out.println("Erro ao ler o arquivo");
            return null;
        } catch (ClassNotFoundException e){
            System.out.println("Classe nao encontrada");
            return null;
        }
    }
}
