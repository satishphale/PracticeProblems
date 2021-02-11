package FileMultiSplit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileMultiSplit {


    private String readAsString(String str) throws Exception
    {
        String data = new String(Files.readAllBytes(Paths.get(str)));
        return data;
    }
    public static void main(String[] args) throws Exception
    {
        FileMultiSplit fms = new FileMultiSplit();
        String currentDirectory = System.getProperty("user.dir");
        BufferedWriter outputWriter;// = new BufferedWriter(new FileWriter(currentDirectory+"/output.txt"));
        //reading the data from file ods_data
        String data = fms.readAsString(currentDirectory + "/inc_data2.txt");

        String[] input = data.split(" ");

        for (int i=1;i<input.length;i++)
        {
            outputWriter = new BufferedWriter(new FileWriter(currentDirectory+"/output"+i+".txt"));
            outputWriter.write(i+"\t");
            while (i % 20 != 0)
            {
                outputWriter.write(input[i]+" ");
                i++;
            }

            outputWriter.flush();
            outputWriter.close();
        }

    }
}
