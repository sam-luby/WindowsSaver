package saver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;	

import saver.GuiWindow.Params;

public class SpotlightSaver {
	
//	static String src = "C:\\Users\\samwl\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets";
//	static String src = "";
//	static String dest = "C:\\Users\\samwl\\Documents\\SpotlightPics";
	private static String sourceFolder = "";
	private static String destinationFolder = "";
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
    public static void main(String[] args) throws Exception {
		Params params = launchWindow(sourceFolder, destinationFolder);
		
		List<File> files = parseParams(params);
		copyFolder(files.get(0), files.get(1));
        deleteSmallShit(files.get(1));
        changeExt(files.get(1));   
        System.exit(0);
	}
    
    private static List<File> parseParams(Params params) {
    	java.util.List<java.io.File> files = new ArrayList<>();
    	if(params.sourcePath != null) {
    		File sourceFolder = new File(params.sourcePath);
    		files.add(sourceFolder);
    	} else {
    		JOptionPane.showMessageDialog(null, "No source folder given, program will exit.");
    		System.exit(0);
    	}
    	if(params.destinationPath != null) {
    		File destinationFolder = new File(params.destinationPath);
    		files.add(destinationFolder);
    	} else {
    		JOptionPane.showMessageDialog(null, "No destination folder given, program will exit.");
    		System.exit(0);
    	}
    	return files;
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
                System.out.println("Directory created : " + destinationFolder);
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
    		String newName = destinationFolder.toString()+"\\"+"pic_"+i+".png";
    		f.renameTo(new File(newName));
    	}
    	System.out.println("All files converted");
    }
    
    public static void deleteSmallShit(File destinationFolder) throws IOException {
    	File[] list = destinationFolder.listFiles();
    	for(File f : list) {
    		if(f.length() < 200000) {
    			f.delete();
    		} 
    	}
    }
    
    private static Params launchWindow(final String src, final String dest) throws InvocationTargetException, InterruptedException {
    	final GuiWindow window = new GuiWindow();
    	SwingUtilities.invokeAndWait(new Runnable() {
    		@Override
    		public void run() {
    			window.showGui(src, dest);
    		}
    	});
    	return window.getParams();
    }
}