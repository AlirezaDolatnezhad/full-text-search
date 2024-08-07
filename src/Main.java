import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

     static HashMap<String,String> hash_map;
     static String folderPath = "D:\\Alireza\\java projects\\directoryPath";


    public static void main(String[] args) throws IOException {

       // LinkedList<String> file_names=null;
        hash_map= new HashMap<String, String>();

        if(KeywordsEngine.readAllFiles(folderPath,hash_map)){
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.println("Please enter the number of the words you want to search: ");
            int numberOfWords = scanner.nextInt();
            scanner.nextLine();
            String[] words = new String[numberOfWords];
            System.out.println("Please enter the words: ");

            for (int k = 0; k < numberOfWords; k++) {
                words[k] = scanner.nextLine();
            }

           // String keyWords = scanner.nextLine().trim().toLowerCase();
            KeywordsEngine.searchWords(words, hash_map);

        }
    }
}
