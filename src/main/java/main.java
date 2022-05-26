import java.io.*;
import java.util.List;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;



public class main {
    static {
        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
    }
    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Enter text to find : ");
        String targettext =   reader.readLine();
        int counter=0;
        System.out.println("Results: ");
        try {
            File directory = new File("F:\\Téléchargements");//change this to your documents directory 

            FilenameFilter pdffilter = new FilenameFilter() {

                public boolean accept(File file, String filename) {

                    return filename.toLowerCase().endsWith(".pdf");
                }

            };
            FilenameFilter docxfilter = new FilenameFilter() {

                public boolean accept(File file, String filename) {

                    return filename.toLowerCase().endsWith(".docx");
                }

            };
            File[] pdffiles = directory.listFiles(pdffilter);

            for (File f : pdffiles) {
                try{
                    PDDocument document = PDDocument.load(f);
                    PDFTextStripper pdftextStripper = new PDFTextStripper();
                    String content = pdftextStripper.getText(document);
                    if (content.contains(targettext)){
                        System.out.println("-> " + f.getName());
                        counter++;
                    }

                    document.close();
                }
                catch(Exception e){}

            }
            File[] docxfiles = directory.listFiles(docxfilter);
            for (File f : docxfiles) {
                try{
                    XWPFDocument document =  new XWPFDocument(new FileInputStream(f));

                    List<XWPFParagraph> paragraphs = document.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs){
                        if (paragraph.getText().contains(targettext)){
                            System.out.println("-> " + f.getName());
                            counter++;
                        }


                    }
                    document.close();
                }
                catch (Exception e) {}

            }
            System.out.println("Found " + counter);


        } catch (Exception e) {

        }
    }
}
