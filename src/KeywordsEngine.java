import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KeywordsEngine {
    public static String[] AllFilesName=null;
    static File[] listOfFiles=null;
    public static void set_all_files(String folderPath){
        File folder = new File(folderPath);
        listOfFiles = folder.listFiles();
        int len= listOfFiles.length;
        AllFilesName=new String[len];
        for (int i = 0; i < listOfFiles.length; i++) {
            AllFilesName[i]=listOfFiles[i].getName();
        }
    }

    public static boolean readAllFiles(String folderPath, HashMap<String, String> hash_map) throws IOException {
        set_all_files(folderPath);
//        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println(i + 1 + "." +AllFilesName[i]);

                    BufferedReader br = null;
                    String line = null;
                    String filename = AllFilesName[i];
                    File file = listOfFiles[i];
                    br = new BufferedReader(new FileReader(file));

                    updateWordFileMap(hash_map, br, filename);
                }
            }
        } else {
            return false;
        }

        return true;
    }

    private static void updateWordFileMap(HashMap<String, String> hash_map, BufferedReader br, String filename) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split(" ");
            for (int j = 0; j < words.length; j++) {
                String temp = hash_map.get(words[j]);

                if ((temp != null) && !(temp.contains(filename))) {
                    temp = temp + ";" + filename;
                    hash_map.put(words[j].toLowerCase(), temp);
                } else if ((words[j] != "")) {
                    hash_map.put(words[j], filename);
                }
            }
        }
    }

    public static void searchWords(String[] words, HashMap<String, String> hash_map) {
        int l = words.length;

        String[][] files_name = new String[l][];
        int[] type = new int[l];
        //Map<String, Set<String>> groups = new HashMap<>();
        groupingWords(hash_map,words);


   }

    public static void groupingWords(HashMap<String, String> hash_map,String[] words) {
        ArrayList<String> plus = new ArrayList<>();
        ArrayList<String> minus = new ArrayList<>();
        ArrayList<String> essential = new ArrayList<>();
        for (String s : words) {

            if (s.startsWith("+")) {
                plus.add(s.substring(1));

            } else if (s.startsWith("-")) {
                minus.add(s.substring(1));

            } else {
                essential.add(s.substring(0));

            }
        }
        System.out.println("==============================================================");
        System.out.println("words with (+) prefix :"+ plus);
        System.out.println("words with (-) prefix :"+ minus);
        System.out.println("words without prefix :"+essential);
        Set<String> setz = null;
        Set<String> setz2 = null;


        for ( int z = 0; z < plus.size(); z++) {
            String word = plus.get(z);
            if(z==0)
                setz=new HashSet(printFilesName(hash_map, word));
            else {
                setz.addAll(printFilesName(hash_map, word));
            }

            if(z ==(plus.size())-1){
                System.out.println("plus set:"+setz);
                for ( int z1 = 0; z1 < essential.size(); z1++) {
                    String word1 = essential.get(z1);
                    setz.retainAll(printFilesName(hash_map, word1));
                    if(z1== (essential.size()-1)){
                        System.out.println("essentials set:"+setz);
                        Set<String> temp_set1=new HashSet<String>(Arrays.asList(AllFilesName));
                        for (int z2 = 0; z2 < minus.size(); z2++) {
                            String word2 = minus.get(z2);
                            if(z2==0)
                                setz2=new HashSet(printFilesName(hash_map, word2));
                            else {
                                setz2.addAll(printFilesName(hash_map, word2));
                            }
                            if(z2==(minus.size()-1)){
                                System.out.println("minus set:"+setz);
                                temp_set1.removeAll(setz2);
                                setz.retainAll(temp_set1);
                            }

                        }


                    }
                }
            }

        }



        System.out.println(setz);


    }

    private static Set<String> printFilesName(HashMap<String, String> hash_map, String word) {
        String temp = hash_map.get(word);
        Set<String> set=null;
        String[] filesName;
        if (temp != null) {
            filesName = temp.split(";");
            set = Set.of(filesName);
            System.out.println(set);
        }
        else {
            System.out.println("the keyword " + word + " is not founded in all files");
        }
        return set;
    }


}

