import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil 
{	
    private static final String INPUT_FILE = "558_schema.xml";
    private static final String OUTPUT_FILE = "text.zip";
	
	public static void main( String[] args )
    {
		ZipUtil z = new ZipUtil();
		z.zipIt(INPUT_FILE, OUTPUT_FILE);
    }
	
	
	public void zipIt(String inputFile, String outputFile){
		
		byte[] buffer = new byte[1024];
		
		try{
            // Writer
    		FileOutputStream fos = new FileOutputStream(outputFile);
    		ZipOutputStream zos = new ZipOutputStream(fos);
			
			// Reader 
			FileInputStream fin = new FileInputStream(inputFile);
    		ZipEntry ze= new ZipEntry(inputFile);
			
			// Start writing
    		zos.putNextEntry(ze);

    		int len;
    		while ((len = fin.read(buffer)) > 0) {
				// Contine writing
    			zos.write(buffer, 0, len);
    		}

    		fin.close();
    		zos.closeEntry();
           
    		//remember close it
    		zos.close();
          
    		System.out.println("Done");

    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
	}


}