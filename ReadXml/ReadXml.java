package ReadXml;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ReadXml {


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

//Build Document
        Document document = builder.parse(new File("Content.xml"));

//Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

//Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

//Get all employees
        NodeList nList = document.getElementsByTagName("Submission");
        //System.out.println(root.getNodeName()+"============================");


        HashMap<String, Integer> map=new HashMap<String, Integer>();

        Integer str = map.get("ABC");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            //System.out.println("$$$$$"+node.getNodeName());    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Print each employee's detail
                Element eElement = (Element) node;
                String id = eElement.getAttribute("Team");
                int marks = Integer.parseInt(eElement.getAttribute("Mark"));
                //System.out.print("submission id : " + eElement.getAttribute("xml:id"));
                //System.out.print(" ID : " + eElement.getAttribute("Team"));
                //System.out.println(" Marks : " + eElement.getAttribute("Mark"));
                if (map.get(id)==null&&!id.equals("P4"))
                    map.put(id,marks);
                else if(!id.equals("P4"))
                {
                    int curr_marks = map.get(id);
                    if (marks>curr_marks)
                        map.put(id,marks);
                }

            }
        }

        Iterator<String> iterator = map.keySet().iterator();

        //Create blank workbook
//        XSSFWorkbook workbook = new XSSFWorkbook();
        HSSFWorkbook workbook = new HSSFWorkbook();


        //Create a blank sheet
        HSSFSheet spreadsheet = workbook.createSheet();//.createSheet( " Employee Info ");

        //Create row object
        HSSFRow row;

        int rowid = 0;
        row = spreadsheet.createRow(rowid++);//.createRow(rowid++);
        Cell cell = row.createCell(0);
        cell.setCellValue("Id");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(("Marks"));

        while(iterator.hasNext())
        {
            String key   = iterator.next();
            int mark = map.get(key);
            row = spreadsheet.createRow(rowid++);//.createRow(rowid++);
            Cell cell2 = row.createCell(0);
            cell2.setCellValue((key));
            Cell cell3 = row.createCell(1);
            cell3.setCellValue((mark+1));


            System.out.print("Id: "+key+" Marks: "+mark+"\n");
        }
        FileOutputStream out = new FileOutputStream(
                new File("Writesheet.xlsx"));

        workbook.write(out);
        out.close();

    }
}
