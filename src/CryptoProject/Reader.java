package CryptoProject;

import java.io.*;
import java.util.Scanner;

public class Reader {
    public Reader() {
    }

    public static void createfile() {
        try {
            File myObj = new File("cryptolist.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writefile(Cryptocurrency[] cryptol) {
        try {
            FileWriter myWriter = new FileWriter("cryptolist.txt");
            for(int i = 0; i < 20; i++) {
                myWriter.write(cryptol[i].name + "/" + cryptol[i].amount + "/" + cryptol[i].dollarinvested + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void readfile() throws FileNotFoundException {
        //Cryptoc[] cc = new Cryptoc[Main.CryptoList.length];
        Scanner in = new Scanner(new FileReader("cryptolist.txt"));
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while(in.hasNextLine()) {
            String str = in.nextLine();
            String[] parts = str.split("/");
            //cc[i].name = parts[0];
            //if (parts[0].equals(Main.CryptoList[i].name)) {
                Main.CryptoList[i].amount = Double.parseDouble(parts[1]);
                Main.CryptoList[i].dollarinvested = Double.parseDouble(parts[2]);
                i++;
            //}
        }
        in.close();
        //return cc;
    }

}
