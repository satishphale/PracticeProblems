package TriangleCounting;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TriangleCounting {


    public static void main(String args[]) throws Exception
    {
        TriangleCounting tc = new TriangleCounting();
        String current_dir = System.getProperty("user.dir");
        String data =  tc.readAsString(current_dir+"/triangle_counting_data.txt");
    }

    private String readAsString(String s) throws Exception {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        return data;
    }
}
