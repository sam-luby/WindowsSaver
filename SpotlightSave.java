import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;

public class Saver {
	static String src = "C:\\Users\\samwl\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
    static String dest = "C:\\Users\\samwl\\Documents\\SpotlightPics";
	
    public static void main(String[] args) throws IOException 
    {
    	File sourceFolder = new File(src);
        File destinationFolder = new File(dest);
        copyFolder(sourceFolder, destinationFolder);
        changeExt(destinationFolder);
        deleteSmallShit(destinationFolder);
        deletePortraitShit(destinationFolder);
    }
    /**
     * This function recursively copy all the sub folder and files from sourceFolder to destinationFolder
     * */
    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException
    {
        //Check if sourceFolder is a directory or file
        //If sourceFolder is file; then copy the file directly to new location
        if (sourceFolder.isDirectory()) 
        {
            //Verify if destinationFolder is already present; If not then create it
            if (!destinationFolder.exists()) 
            {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
             
            //Get all files from source directory
            String files[] = sourceFolder.list();

            //Iterate over all files and copy them to destinationFolder one by one
            for (String file : files) 
            {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            //Copy the file content from one place to another 
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    
    public static void changeExt(File destinationFolder) {
    	File[] list = destinationFolder.listFiles();
    	
    	for(int i=0; i < list.length; i++) {
    		File f = new File("C:\\Users\\samwl\\Documents\\SpotlightPics\\"+list[i].getName());
    		String newName = dest+"\\"+"pic_"+i+".png";
    		f.renameTo(new File(newName));
    	}
    	System.out.println("All files converted");
    }
    
    public static void deleteSmallShit(File destinationFolder) throws IOException {
    	File[] list = destinationFolder.listFiles();
    	for(File f : list) {
    		if(f.length() < 250000) {
    			f.delete();
    		}
    	}
    }
    
    public static void deletePortraitShit(File destinationFolder) throws IOException {
    	File[] list = destinationFolder.listFiles();
    	for(File f : list ) {
    		Image pic = ImageIO.read(f);
    		if(pic.getHeight(null) == 1920) {
    			f.delete();
    		}
    	}
    }
}