package Controllers;
import Models.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.lang.*;

public class FileController {
    public static void escreverArquivo(String nomeArquivo, ArrayList<Model> objects){
        OutputStream file = new FileOutputStream("resources/"+ nomeArquivo + ".ser");
        ObjectOutputStream output = null;
        try {
            output = new ObjectOutputStream(file);
        } catch(FileNotFoundException e) {
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
    public static ArrayList<Model> lerArquivo(String nomeArquivo){
        InputStream file = new FileInputStream("resources/" + nomeArquivo + ".ser");
        ObjectInputStream input = null;
        try{
            input = new ObjectInputStream(file);
        } catch(FileNotFoundException e){
            System.out.println("Erro ao abrir o arquivo!");
            return null;
        }
        try{
            return (ArrayList<Model>) input.readObject();
        } catch(IOException e){
            System.out.println("Erro ao ler o arquivo");
            return null;
        }
    }
}
