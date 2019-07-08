import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Ward on 19/03/2019.
 */
public class MakeRuns extends JFileChooser {

    public static int RunSize;
    public static File inputFile;
    public static String fileLocation;

    public static ArrayList list = new ArrayList();

    public static File tempFile;

    public static BufferedReader BR;
    public static FileReader FR;

    public static int maxSize;
    public static int itemsInArray = 0;
    public static Data[] theHeap;

    public static int counter;

    public static void main(String args[]) {

        System.out.println("This Java Program will sort a file of standard numbers into a list from smallest to largest using external sorting methods");
        System.out.println("____________________________________________________________");
        System.out.println("Please select a .txt file to sort");

        //So far it will accept any file and break if its not a .txt file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        inputFile = fileChooser.getSelectedFile();
        fileLocation = inputFile.getParent();
        System.out.println("You have selected: " + inputFile.getName());
        System.out.println(fileLocation);

        System.out.println("____________________________________________________________");
        System.out.println("Please Enter a number, greater then 0, specifying the size of each run");
        System.out.println("NOTE: This represents how much Ram your machine has to spend on this sort:");

        RunSize = getRunSize();

        System.out.println("____________________________________________________________");
        System.out.println("____________________________________________________________");
        System.out.println("You have selected to split '" + inputFile.getName() + "' at location '" + fileLocation + "' into runs of size " + RunSize + " units");

        minHeapSort(inputFile, fileLocation, RunSize);

        //tempFile.deleteOnExit();
    }


    public static int getRunSize() {
        Scanner input = new Scanner(System.in);

        try {
            RunSize = Integer.parseInt(input.nextLine());

            System.out.println(RunSize);

            return RunSize;
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("_________________________________________");
            System.out.println("Please enter a valid input (Whole Number Value)");
            return getRunSize();
        }
    }

    public static void minHeapSort(File inputFile, String fileLocation, int RunSize){

        createTempFile(fileLocation);
        list = generateList(inputFile);
        printList(list);

        /*
        Will have to put in some sort of test to make
        sure your RunSize isn't greater then your list
         */

        theHeap = new Data[RunSize];
        counter = RunSize;

        for(int i=0; i < RunSize; i++){
            //insert(i, new Data((Integer)list.get(i)));
        }
    }

    public static void insert(int index, Data newData){
        theHeap[index] = newData;
    }

    public static void incrementTheArray(){
        itemsInArray++;
    }

    public static Data remove(){
        int tempItemsInArray = itemsInArray - 1;
        Data root = theHeap[0];
        theHeap[0] = theHeap[--itemsInArray];

        heapTheArray(0);

        return root;
    }

    public static void heapTheArray(int index) {

        int largestChild;

        Data root = theHeap[index];

        while (index < itemsInArray / 2) {

            // Get the index for the leftChild
            int leftChild = 2 * index + 1;

            // Get the index for the rightChild
            int rightChild = leftChild + 1;

            // If leftChild is less then rightChild
            // save rightChild in largestChild
            if (rightChild < itemsInArray && theHeap[leftChild].key < theHeap[rightChild].key) {
                System.out.println("Put Value " + theHeap[rightChild]
                        + " in largestChild");
                largestChild = rightChild;
            } else {
                System.out.println("Put Value " + theHeap[leftChild]
                        + " in largestChild");
                // Otherwise save leftChild in largestChild
                largestChild = leftChild;
            }
            // If root is greater then the largestChild
            // jump out of while

            if (root.key >= theHeap[largestChild].key)
                break;
            System.out.println("Put Index Value " + theHeap[largestChild]
                    + " in Index " + index);
            // Save the value in largest child into the top
            // index
            theHeap[index] = theHeap[largestChild];
            index = largestChild;
            System.out.println();
            System.out.println();
        }

        theHeap[index] = root;

    }


    public static void createTempFile(String Location){
        try{
            tempFile = File.createTempFile("temp Sorting File", ".txt", new File(Location));
        }catch (IOException e){
            System.out.println(e.toString());
            System.out.println("____________________________________________________________");
            e.printStackTrace();
        }
    }

    public static ArrayList generateList(File inputfile){

        try{
            BR = new BufferedReader(new FileReader(inputfile));

            String string;
            while ((string = BR.readLine()) != null){
                String[] eachItem = string.split("\\s+");
                for (String s : eachItem) {
                    list.add(s);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    public static void writeToTempFile(String output){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write(output);
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void printList(ArrayList list){
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }
}