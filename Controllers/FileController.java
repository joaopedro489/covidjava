package Controllers;
import Models.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.lang.*;

public class FileController {
    public static void escreverArquivoTsv(String pasta, String nomeArquivo, List pesquisa, boolean append){

        File filePesquisa = new File(pasta + "/" + nomeArquivo + ".tsv");
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
    public static void escreverArquivoSer(String pasta, String nomeArquivo, List objects){
        OutputStream file = null;
        ObjectOutputStream output = null;
        try {
            file = new FileOutputStream(pasta + "/"+ nomeArquivo + ".ser");
            output = new ObjectOutputStream(file);
        } catch(FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo!");
            System.exit(1);
            return;
        } catch(IOException e){
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
            return;
        }
    }
    public static ArrayList lerArquivoSer(String pasta, String nomeArquivo){
        InputStream file = null;
        ObjectInputStream input = null;
        try{
            file = new FileInputStream(pasta + "/" + nomeArquivo + ".ser");
            input = new ObjectInputStream(file);
        } catch(FileNotFoundException e){
            System.out.println("Erro ao abrir o arquivo!");
            System.exit(1);
            return null;
        } catch(IOException e){
            System.out.println("Erro ao o arquivo!");
            System.exit(1);
            return null;
        }
        try{
            return (ArrayList) input.readObject();
        } catch(IOException e){
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
