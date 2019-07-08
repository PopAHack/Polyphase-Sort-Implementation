package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    //Global vars
    private static DataNode[] heap;
    private static File emptyFile;
    private static int numberRuns = 0;

    public static void main(String[] args) {
        try {
            createTestFile();

            //gets the input file and it distributes it across the tx files, leaving remaining runs in the inputFile.

            //Cycle:
            numberRuns = Integer.parseInt(args[0]);
            while(0 != distributeInputFile())//while there are no remaining runs left after polyphase(give it the number runs)
            {
                polyPhase(false);
            }
            polyPhase(true);//do the last terms

            System.out.println("Sort successfully finished, results stored in Input file.");
        }catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    public static void createTestFile()
    {
        try {
            //put number of runs into the input file, with ordered data
            //initialise variables
            System.out.println("Creating test Input file...");
            int numberRuns = 11;
            int numberData = 9;
            File thisDir = new File(System.getProperty("user.dir"));
            File inputFile = new File(thisDir, "Input");

            //refresh file
            inputFile.delete();
            inputFile.createNewFile();

            //write out the runs
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile.getPath()));
            for(int i = 1; i <= numberRuns; i++)
            {
                for(int j = 1; j <= numberData; j++)
                    writer.write(j + "\n");
                writer.write("\u001F" + "\n");
            }
            writer.close();
            System.out.println("Created test Input file");
        }catch  (Exception ex)
        {
            System.out.println("cteateTestFile(): " + ex.toString());
        }
    }

    //#####################################################

    //PolyPhase
    //global vars for PolyPhase
//    private static int t1Count;
//    private static int t2Count;
//    private static int t3Count;
//    private static int t4Count;

    private static void polyPhase(Boolean lastIteration)
    {
        try {
            //loop until only one item in heap
            System.out.println("Starting one polyphase...");

            File thisDir = new File(System.getProperty("user.dir"));
            File inputFile = new File(thisDir, "Input");
            File t1 = new File(thisDir, "T1");
            File t2 = new File(thisDir, "T2");
            File t3 = new File(thisDir, "T3");
            File t4 = new File(thisDir, "T4");
            //initialise readers
            BufferedReader t1Reader = new BufferedReader(new FileReader(t1.getPath()));
            BufferedReader t2Reader = new BufferedReader(new FileReader(t2.getPath()));
            BufferedReader t3Reader = new BufferedReader(new FileReader(t3.getPath()));
            BufferedReader t4Reader = new BufferedReader(new FileReader(t4.getPath()));

            if(t1Reader.readLine() == null) emptyFile = t1;
            else if(t2Reader.readLine() == null) emptyFile = t2;
            else if(t3Reader.readLine() == null) emptyFile = t3;
            else if(t4Reader.readLine() == null) emptyFile = t4;

            t1Reader.close();
            t2Reader.close();
            t3Reader.close();
            t4Reader.close();

//            t1Count = 0;
//            t2Count = 0;
//            t3Count = 0;
//            t4Count = 0;

            t1Reader = new BufferedReader(new FileReader(t1.getPath()));
            t2Reader = new BufferedReader(new FileReader(t2.getPath()));
            t3Reader = new BufferedReader(new FileReader(t3.getPath()));
            t4Reader = new BufferedReader(new FileReader(t4.getPath()));
            File prevEmptyFile = emptyFile;

            while (loadMinHeap(t1Reader, t2Reader, t3Reader, t4Reader)) {
                phase(t1Reader, t2Reader, t3Reader, t4Reader);
                prevEmptyFile = emptyFile;

//                //these take the line pointers off of the break data lines after a run is finished
//                //highly important
//                if(!emptyFile.getName().equals(t1.getName()))
//                    //t1Count += 1;
//                    t1Reader.readLine();
//
//                if(!emptyFile.getName().equals(t2.getName()))
//                    //t2Count += 1;
//                    t2Reader.readLine();
//
//                if(!emptyFile.getName().equals(t3.getName()))
//                    //t3Count += 1;
//                    t3Reader.readLine();
//
//                if(!emptyFile.getName().equals(t4.getName()))
//                    //t4Count += 1;
//                    t4Reader.readLine();

                System.out.println(emptyFile.getName());
//                System.out.println(t1Count);
//                System.out.println(t2Count);
//                System.out.println(t3Count);
//                System.out.println(t4Count);

//                t1Reader = new BufferedReader(new FileReader(t1.getPath()));
//                t2Reader = new BufferedReader(new FileReader(t2.getPath()));
//                t3Reader = new BufferedReader(new FileReader(t3.getPath()));
//                t4Reader = new BufferedReader(new FileReader(t4.getPath()));

                String line1 = t1Reader.readLine();
                String line2 = t2Reader.readLine();
                String line3 = t3Reader.readLine();
                String line4 = t4Reader.readLine();

//                for(int i = 0; i <= t1Count; i++)
//                    line1 = t1Reader.readLine();
//                for(int i = 0; i <= t2Count; i++)
//                    line2 = t2Reader.readLine();
//                for(int i = 0; i <= t3Count; i++)
//                    line3 = t3Reader.readLine();
//                for(int i = 0; i <= t4Count; i++)
//                    line4 = t4Reader.readLine();


//                t1Reader.close();
//                t2Reader.close();
//                t3Reader.close();
//                t4Reader.close();

                //these check which file is the next phase output file
                if(line1 == null && !emptyFile.getName().equals(t1.getName())) {
                    emptyFile = t1;
                    t1Reader.close();
                    t1.delete();
                    t1.createNewFile();
                    //t1Count = 0;
                    t1Reader = new BufferedReader(new FileReader(t1.getPath()));

                }else if(line2 == null && !emptyFile.getName().equals(t2.getName())) {
                    emptyFile = t2;
                    t2Reader.close();
                    t2.delete();
                    t2.createNewFile();
                    //t2Count = 0;
                    t2Reader = new BufferedReader(new FileReader(t2.getPath()));


                }else if(line3 == null && !emptyFile.getName().equals(t3.getName())) {
                    emptyFile = t3;
                    t3Reader.close();
                    System.out.println(t3.delete());
                    t3.createNewFile();
                    //t3Count = 0;
                    t3Reader = new BufferedReader(new FileReader(t3.getPath()));


                }else if(line4 == null && !emptyFile.getName().equals(t4.getName())) {
                    emptyFile = t4;
                    t4Reader.close();
                    t4.delete();
                    t4.createNewFile();
                    //t4Count = 0;
                    t4Reader = new BufferedReader(new FileReader(t4.getPath()));
                }
            }

            //append the single run to the input file
            BufferedWriter inputWriter = new BufferedWriter(new FileWriter(inputFile.getPath(), true));
            BufferedReader resultReader = new BufferedReader(new FileReader(prevEmptyFile.getPath()));

            String line;
            //get to the run we want
//            if(prevEmptyFile.getName().equals(t1.getName()))
//                for(int i = 0; i<t1Count-1; i++) {
//                    resultReader.readLine();
//                }
//            if(prevEmptyFile.getName().equals(t2.getName()))
//                for(int i = 0; i<t2Count-1; i++) {
//                    resultReader.readLine();
//               }
//            if(prevEmptyFile.getName().equals(t3.getName()))
//                for(int i = 0; i<t3Count-1; i++) {
//                    resultReader.readLine();
//                }
//            if(prevEmptyFile.getName().equals(t4.getName()))
//                for(int i = 0; i<t4Count-1; i++) {
//                    resultReader.readLine();
//                }
            while(!(line = resultReader.readLine()).equals("\u001F"))
            {
                inputWriter.write(line + "\n");
            }
            if(!lastIteration) {
                inputWriter.write("\u001F" + "\n");
                numberRuns++;
            }

            resultReader.close();
            inputWriter.close();
        }catch (Exception ex)
        {
            System.out.println("polyphase(): " + ex.toString());
        }
    }

    private static void phase(BufferedReader t1Reader, BufferedReader t2Reader,BufferedReader t3Reader,BufferedReader t4Reader)
    {
        try {
            DataNode smallestItem;
            System.out.println("Starting a phase...");

            t1Broke = 0;
            t2Broke = 0;
            t3Broke = 0;
            t4Broke = 0;

            //we know that the output file is the empty one, which is the global variable emptyFile, initiated in the loadMinHeap() method.
            BufferedWriter outputFileWriter = new BufferedWriter(new FileWriter(emptyFile.getPath(), true));
            //System.out.println("here: " +t1Reader.readLine());
            while (null != (smallestItem = replaceHeap(t1Reader, t2Reader, t3Reader, t4Reader)))//Loop until all runs are empty
            {
                outputFileWriter.write(smallestItem.getValue() + "\n");//appending to the end of the output file
            }
            outputFileWriter.write("\u001F" + "\n");//end of run

            outputFileWriter.close();
            System.out.println("Finished a phase.");

        }catch (Exception ex)
        {
            System.out.println("phase(): " + ex.toString());
        }
    }

    private static Boolean loadMinHeap(BufferedReader t1Reader, BufferedReader t2Reader, BufferedReader t3Reader, BufferedReader t4Reader)
    {
        try {
            //assume all files exist
            File thisDir = new File(System.getProperty("user.dir"));
            File t1 = new File(thisDir, "T1");
            File t2 = new File(thisDir, "T2");
            File t3 = new File(thisDir, "T3");
            File t4 = new File(thisDir, "T4");

            //initialise heap
            heap = new DataNode[4];//three input files and 1 element to keep track of the size

            //initialise readers
//            BufferedReader t1Reader = new BufferedReader(new FileReader(t1.getPath()));
//            BufferedReader t2Reader = new BufferedReader(new FileReader(t2.getPath()));
//            BufferedReader t3Reader = new BufferedReader(new FileReader(t3.getPath()));
//            BufferedReader t4Reader = new BufferedReader(new FileReader(t4.getPath()));

            //get the first item from the files WITH data into the heap
            String line;
            int count =0;
            if(!emptyFile.getName().equals(t1.getName()))
            {
//                for(int i =0; i < t1Count; i++)
//                    t1Reader.readLine();
                if(null != (line = t1Reader.readLine())) {
                    DataNode firstItem = new DataNode(line, "t1");
                    insertHeap(firstItem);
                    //t1Count++;
                    count++;
                }
            }

            if(!emptyFile.getName().equals(t2.getName()))
            {
//                for(int i =0; i < t2Count; i++)
//                    t2Reader.readLine();
                if(null != (line = t2Reader.readLine())) {
                    DataNode firstItem = new DataNode(line, "t2");
                    insertHeap(firstItem);
                    //t2Count++;
                    count++;
                }
            }

            if(!emptyFile.getName().equals(t3.getName()))
            {
//                for(int i =0; i < t3Count; i++)
//                    t3Reader.readLine();
                if(null != (line = t3Reader.readLine())) {
                    DataNode firstItem = new DataNode(line, "t3");
                    insertHeap(firstItem);
                    //t3Count++;
                    count++;
                }
            }

            if(!emptyFile.getName().equals(t4.getName()))
            {
//                for(int i =0; i < t4Count; i++)
//                    t4Reader.readLine();
                if(null != (line = t4Reader.readLine())) {
                    DataNode firstItem = new DataNode(line, "t4");
                    insertHeap(firstItem);
                    //t4Count++;
                    count++;
                }
            }

//            t1Reader.close();
//            t2Reader.close();
//            t3Reader.close();
//            t4Reader.close();

            //if there is only one file with runs in it, then we have finished the polyphase
            System.out.println("count: " + count);
            if(count == 1)
                return false;
            else
                return true;

        }catch  (Exception ex)
        {
            System.out.println("loadMinHeap(): " + ex.toString());
            return false;
        }
    }

    //#######################################################

    //(min)heap API
    //insert
    private static void insertHeap(DataNode item)
    {
        try {
            //this is a basic (min) heap method refurnished to take in DataNodes rather than int's.
            //may be a bit weird looking because of that.

            //we don't use the first item in the array
            if (heap[0] == null)
                heap[0] = new DataNode("0", "size");//throwaway datanode to store the amount of items in our heap

            int PQLength = Integer.parseInt(heap[0].getValue());//get the length of the PQ


            //set it in the first available spot
            int index = PQLength + 1;
            heap[index] = item;

            //reorder PQ
            while (index != 1 && heap[index / 2].getValue().compareTo(heap[index].getValue())>0) {//a>b
                //swap with parent
                DataNode placeholder = heap[index / 2];
                heap[index / 2] = heap[index];
                heap[index] = placeholder;
                //decrement for next recurse
                index = index / 2;
            }
            heap[0].setValue(String.valueOf(Integer.parseInt(heap[0].getValue()) + 1));//increment PQ size indicator
            PQLength += 1;

            System.out.println("Size of heap: " + PQLength);
        }catch (Exception ex)
        {
            System.out.println("insertHeap(): " + ex.toString());
        }
    }

    //replace
    private  static int t1Broke = 0;
    private  static int t2Broke = 0;
    private  static int t3Broke = 0;
    private  static int t4Broke = 0;
    private static DataNode replaceHeap(BufferedReader t1Reader, BufferedReader t2Reader,BufferedReader t3Reader,BufferedReader t4Reader) {
        String lineTemp = "empty";
        try {
            //replace with the next data line from the returned smallest values file
            DataNode returnNode;
            if (heap[0] == null) heap[0] = new DataNode("0", "size");//initialise our memory list length
            if(heap[0].getValue().equals("0"))return null; //nothing in queue
            int PQLength = Integer.parseInt(heap[0].getValue());

            if (heap.length > PQLength + 1)//if we can fit it in, load it it into memory.  Otherwise it is lost
                heap[PQLength + 1] = heap[1];
            returnNode = heap[1];


            File thisDir = new File(System.getProperty("user.dir"));
            File t1 = new File(thisDir, "T1");
            File t2 = new File(thisDir, "T2");
            File t3 = new File(thisDir, "T3");
            File t4 = new File(thisDir, "T4");

            //initialise readers
//            BufferedReader t1Reader = new BufferedReader(new FileReader(t1.getPath()));
//            BufferedReader t2Reader = new BufferedReader(new FileReader(t2.getPath()));
//            BufferedReader t3Reader = new BufferedReader(new FileReader(t3.getPath()));
//            BufferedReader t4Reader = new BufferedReader(new FileReader(t4.getPath()));

            //Skip through the run, until either a data node is found or all runs are finished
            String fileName = "t1";
            String line = null;

            Boolean broke = false;

            if(t1Broke == 0) {
                if (fileName.equals("t1") && !emptyFile.getName().equals("t1")) {//if its t1 and not the output fil
                    line = t1Reader.readLine();//get the next line of data
                    if (line.equals("\u001F")) {//if it's the end of run symbol
                        t1Broke = 1;//mark the run as read
                        broke = true;
                    }
                }
            }else
                broke = true;
                //Exactly the same as the t1 case
            if(t2Broke == 0) {
                if (fileName.equals("t2") && !emptyFile.getName().equals("t2")) {//if its t1 and not the output fil
                    line = t2Reader.readLine();//get the next line of data
                    if (line.equals("\u001F")) {//if it's the end of run symbol
                        t2Broke = 1;//mark the run as read
                        broke = true;
                    }
                }
            }else
                broke = true;

                //Exactly the same as the t1 case
            if(t3Broke == 0) {
                if (fileName.equals("t3") && !emptyFile.getName().equals("t3")) {//if its t1 and not the output fil
                    line = t3Reader.readLine();//get the next line of data
                    if (line.equals("\u001F")) {//if it's the end of run symbol
                        t3Broke = 1;//mark the run as read
                        broke = true;
                    }
                }
            }else
                broke = true;

                //Exactly the same as the t1 case
            if(t4Broke == 0) {
                if (fileName.equals("t4") && !emptyFile.getName().equals("t4")) {//if its t1 and not the output fil
                    line = t4Reader.readLine();//get the next line of data
                    if (line.equals("\u001F")) {//if it's the end of run symbol
                        t4Broke = 1;//mark the run as read
                        broke = true;
                    }
                }
            }else
                broke = true;


//            t1Reader.close();
//            t2Reader.close();
//            t3Reader.close();
//            t4Reader.close();
            lineTemp = line;

            //if it's not the end of a run, proceed as normal
            if(!broke) {

                //add the new item to our heap
                //if(line.equals("\u001F"));
                DataNode item = new DataNode(line, fileName);
                heap[1] = item;

                int index = 1;
                //now downheap the root item
                while (true) {
                    int childIndex;
                    //test escape conditions
                    if (index * 2 > PQLength) return returnNode;//done no children
                    if (index * 2 + 1 > PQLength && index * 2 <= PQLength)//one child
                        if (heap[index * 2].getValue().compareTo(heap[index].getValue())>=0) return returnNode;//done // a>=b

                    if (index * 2 + 1 <= PQLength) {
                        //see if both children are bigger than root
                        if (heap[index * 2 + 1].getValue().compareTo(heap[index].getValue())>=0 && heap[index * 2].getValue().compareTo(heap[index].getValue())>= 0)//a>=b, a>=b
                            return returnNode;//done both children

                        if (heap[index * 2].getValue().compareTo(heap[index * 2 + 1].getValue()) < 0)//<
                            childIndex = index * 2;
                        else
                            childIndex = index * 2 + 1;
                    } else
                        childIndex = index * 2;

                    DataNode placeHolderIndex = heap[index];
                    heap[index] = heap[childIndex];
                    heap[childIndex] = placeHolderIndex;
                    index = childIndex;
                }

            }else//if our run is over, find the smallest of the two root children and set root. -1 from PQLength and then Downheap
            {
                int index = 1;

                if (1 == PQLength)//no children, return root and set to fail next call
                {
                    PQLength = 0;
                    heap[0].setValue(String.valueOf(PQLength));
                    return returnNode;//last one in every run
                }

                if (2 == PQLength)//one child
                {
                    heap[1] = heap[2];
                    heap[0].setValue(String.valueOf(PQLength-1));
                    PQLength -= 1;
                }
                if (3 == PQLength) {
                    //see if both children are bigger than root
                    if (heap[2].getValue().compareTo(heap[3].getValue())<0)//a<b
                    {
                        heap[1] = heap[2];
                        heap[2] = heap[3];
                        heap[3] = null;
                        PQLength = 2;
                        heap[0].setValue(String.valueOf(PQLength));

                    }
                    else
                    {
                        heap[1] = heap[3];
                        heap[3] = null;
                        PQLength = 2;
                        heap[0].setValue(String.valueOf(PQLength));
                    }
                }
            }
               return returnNode;
        } catch (Exception ex) {
            System.out.println("Replace: " + ex.toString());
            System.out.println(lineTemp + " " + (t1Broke + t2Broke + t3Broke + t4Broke));
        }
        return null;
    }

    //#######################################################

    private static int distributeInputFile() {
        try {
            //set up necessary files
            System.out.println("Distributing runs across tx files");
            File thisDir = new File(System.getProperty("user.dir"));
            File t1 = new File(thisDir, "T1");
            File t2 = new File(thisDir, "T2");
            File t3 = new File(thisDir, "T3");
            File t4 = new File(thisDir, "T4");
            File inputFile = new File(thisDir, "Input");

            //delete and refresh the files.
            t1.delete();
            t2.delete();
            t3.delete();
            t4.delete();
            t1.createNewFile();
            t2.createNewFile();
            t3.createNewFile();
            t4.createNewFile();

            String line;

            //start distribution algorithm

            //if number of runs is greater than 3, then:
            //find the three largest sequential fibonacci numbers which the sum of is less than or equal too the number of runs
            //else set special cases
            int t1RunSize;
            int t2RunSize = 0;
            int t3RunSize = 0;
            int totalSum = 0;

            if(numberRuns <=0)
            {
                System.out.println("No runs to merge, returning...");
                return 0;
            }
            if (numberRuns <= 3)
            {
                t1RunSize = 1;
                switch (numberRuns)
                {
                    case 2:
                    {
                        t2RunSize = 1;
                        break;
                    }
                    case 3:
                    {
                        t2RunSize = 1;
                        t3RunSize = 1;
                        break;
                    }
                }
                totalSum = numberRuns;
            }else {

                int seq1 = 1;
                int seq2 = 1;
                int seq3 = 2;
                totalSum = seq1 + seq2 + seq3;

                while (totalSum <= numberRuns)//go until the next loop will have the total more than the number of runs
                {
                    if(totalSum - seq1 + seq3 + seq2 > numberRuns)break;
                    seq1 = seq2;
                    seq2 = seq3;
                    seq3 =  seq1 + seq2;//we shuffled the top two down then added them to get the next term in the sequence
                    totalSum = seq1 + seq2 + seq3;
                }
                t1RunSize = seq3;
                t2RunSize = seq2;
                t3RunSize = seq1;
                System.out.println("Dis: Found 3 terms  of the fibonacci seq");
                System.out.println("Dis: seq1 : " + seq1 + " seq2: " + seq2 + " seq3: " + seq3);
            }

            //now we have the three terms.
            //t1 gets seq3 runs, t2 gets seq2 runs, t3 gets seq1 runs, t4 has 0 runs, the remaining runs are stroed in the input file.

            //distribution:
            BufferedWriter t1Writer = new BufferedWriter(new FileWriter(t1.getPath()));
            BufferedWriter t2Writer = new BufferedWriter(new FileWriter(t2.getPath()));
            BufferedWriter t3Writer = new BufferedWriter(new FileWriter(t3.getPath()));
            BufferedReader inputReader = new BufferedReader(new FileReader(inputFile.getPath()));

            //write seq3 numbers of runs from input file into t1
            for (int i = 0; i < t1RunSize; i++) {
                while (!(line = inputReader.readLine()).equals("\u001F")) {
                    t1Writer.write(line + "\n");
                }
                t1Writer.write("\u001F" + "\n");
            }
            t1Writer.close();

            //write seq2 numbers of runs from input file into t2
            for (int i = 0; i < t2RunSize; i++) {
                while (!(line = inputReader.readLine()).equals("\u001F")) {
                    t2Writer.write(line + "\n");
                }
                t2Writer.write("\u001F" + "\n");
            }
            t2Writer.close();

            //write seq3 numbers of runs from input file into t1
            for (int i = 0; i < t3RunSize; i++) {
                while (!(line = inputReader.readLine()).equals("\u001F")) {
                    t3Writer.write(line + "\n");
                }
                t3Writer.write("\u001F" + "\n");
            }
            t3Writer.close();

            //clear t4 so it has 0 runs
            t4.delete();
            t4.createNewFile();

            //get remaining runs alone in inputFile
            List<String> inputRemainderRunsList = new ArrayList<>();
            while ((line = inputReader.readLine()) != null) {
                inputRemainderRunsList.add(line);
            }
            inputReader.close();

            //refresh inputFile and create writer
            inputFile.delete();
            inputFile.createNewFile();
            BufferedWriter inputFileWriter = new BufferedWriter(new FileWriter(inputFile.getPath()));

            //put the remaining runs back into it
            for (int i = 0; i < inputRemainderRunsList.size(); i++) {
                inputFileWriter.write(inputRemainderRunsList.get(i) + "\n");
            }
            inputFileWriter.close();
            System.out.println("Dis: Distributed runs and left input file with the remaining (" + (numberRuns - totalSum) + ") runs in it.");
            numberRuns -= totalSum;
            return numberRuns;
        } catch (Exception ex) {
            System.out.println("Distribution(): " + ex.toString());
        }
        return 0;
    }
}