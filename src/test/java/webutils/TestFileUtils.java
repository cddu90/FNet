package WebUtils;
/**
 * Serhiy Malyy 
 * Class allows to locate most recent file in folder by last date/time modified
 * It enables file-based test to run and select files under test from folder and eliminates the need to hardcode file name
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFileUtil {

	String FlatFileRootDirectory;
	String InterfaceSpecoficFolder; 
	
	
	public TestFileUtil(String flatFileRootDirectory,String interfaceSpecoficFolder) {
		
		FlatFileRootDirectory = flatFileRootDirectory;
		InterfaceSpecoficFolder = interfaceSpecoficFolder;
	}

	public static File lastFileModified(String dir) {
	    File fl = new File(dir);
	    File[] files = fl.listFiles(new FileFilter() {          
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    });
	    long lastMod = Long.MIN_VALUE;
	    File choice = null;
	    for (File file : files) {
	        if (file.lastModified() > lastMod) {
	            choice = file;
	            lastMod = file.lastModified();
	        }
	    }
	    return choice; //lastFileModified
	}
	
	public File getTestFile(){
		
		String completePath = FlatFileRootDirectory + "\\" + InterfaceSpecoficFolder;
		
		System.out.println("Test file root directory is:" + FlatFileRootDirectory);
		System.out.println("Test file spefic directory is:" + InterfaceSpecoficFolder);
		
		File objFile = lastFileModified (completePath);
		
		return objFile;
	
	}
	
	public String getCompleteTestFilePath(){
		
		String completePath = FlatFileRootDirectory + "\\" + InterfaceSpecoficFolder;
		
		System.out.println("Complete path to test file is:"+ completePath);
		
		File objFile = lastFileModified (completePath);
		
		String obsPath = objFile.getAbsolutePath();	
		
		System.out.println("----------------------test File Info -------------------------------------");
		System.out.println(" ");
		System.out.println("Absolute file under test is :"+ obsPath);
		System.out.println(" ");
		System.out.println("----------------------test File Info -------------------------------------");
		return obsPath;
	
				
		
	}

	public static void copyFile(Path htmlSourcePath, Path htmlReportDestinationPath) throws IOException {
		Files.copy(htmlSourcePath, htmlReportDestinationPath,StandardCopyOption.REPLACE_EXISTING);	
		
		
	}

public static  List<Map<String,String>> parseFileWithHeaderByDelimeter(String delimeter,String testFlatFileCompletePath) throws IOException{
	
			 //String testFlatFileCompletePath="\\\\bsc\\it\\ITQA_Automation\\Care1st Member migration\\Automation\\SUC automation\\Required Documents\\12. SUC546265-DRX To MAM\\MAPDP_Complete_2018_20180401.bsc180401111900.txt";
			 File inputFile = new File(testFlatFileCompletePath);
			 List<Map<String,String>> listOfRows=new ArrayList<Map<String,String>>();
			 String line=null;
			 List<String[]> rowsList=new ArrayList<String[]>();
		 	 if (!inputFile.exists()) { 
		 		  throw new IllegalStateException("File not found: " + testFlatFileCompletePath);
			  } else {
								System.out.println("Starting of File Reading . . . . . .  . . . . ");
								FileReader fileReader =  new FileReader(testFlatFileCompletePath);
								@SuppressWarnings("resource")
								BufferedReader bufferedReader =        new BufferedReader(fileReader);
									
						        while((line = bufferedReader.readLine()) != null) {
					                String[] columnsArray = line.split(delimeter);
					                rowsList.add(columnsArray);
						       		}   
						       System.out.println("__________________________________________________________");
						       //  Map map=new HashMap();
						       String[] columns=rowsList.get(0);
						       int numberOfRows=rowsList.size();
						      //  int columnLength=rowsList.get(0).length;
								
						      for(int rowNo=1;rowNo<numberOfRows;rowNo++){
									Map<String,String> rowMap=new HashMap<String,String>();
									int columnLength=rowsList.get(rowNo).length;
									for(int i=0;i<columnLength;i++){
										rowMap.put(columns[i].toUpperCase(), rowsList.get(rowNo)[i]);	//System.out.println(rowNo+"  columnlength::"+columnLength+" index: "+i+"  "+rowsList.get(rowNo)[i]);
									}
									listOfRows.add(rowMap);
							}
		    	}
		 	return listOfRows;
}




public static  List<Map<String,String>> parseFileWithDelimeter(String testFlatFileCompletePath) throws IOException{
	
	 //String testFlatFileCompletePath="\\\\bsc\\it\\ITQA_Automation\\Care1st Member migration\\Automation\\SUC automation\\Required Documents\\12. SUC546265-DRX To MAM\\MAPDP_Complete_2018_20180401.bsc180401111900.txt";
	 File inputFile = new File(testFlatFileCompletePath);
	 List<Map<String,String>> listOfRows=new ArrayList<Map<String,String>>();
	 String line=null;
	 String cvsSplitBy = "\t";
	 List<String[]> rowsList=new ArrayList<String[]>();
	 if (!inputFile.exists()) { 
		  throw new IllegalStateException("File not found: " + testFlatFileCompletePath);
	  } else {
						System.out.println("Starting of File Reading . . . . . .  . . . . ");
						FileReader fileReader =  new FileReader(testFlatFileCompletePath);
						@SuppressWarnings("resource")
						BufferedReader bufferedReader =        new BufferedReader(fileReader);
							
				        while((line = bufferedReader.readLine()) != null) {
			                String[] columnsArray = line.split(cvsSplitBy);
			                rowsList.add(columnsArray);
				       		}   
				       //  Map map=new HashMap();
				       String[] columns=rowsList.get(0);
				       int numberOfRows=rowsList.size();
				      //  int columnLength=rowsList.get(0).length;
						
				      for(int rowNo=1;rowNo<numberOfRows;rowNo++){
							Map<String,String> rowMap=new HashMap<String,String>();
							int columnLength=rowsList.get(rowNo).length-1;
							//System.out.println("rowNo:"+rowNo);
							int TotalColumnLength = columns.length;
							String str="";
							for(int i=0;i<TotalColumnLength;i++){
								
								
								if(i>columnLength){
									 str="";
								}else{
									 str=rowsList.get(rowNo)[i];		
								}
								
								rowMap.put(columns[i].toUpperCase(), str);	//System.out.println(rowNo+"  columnlength::"+columnLength+" index: "+i+"  "+rowsList.get(rowNo)[i]);
							}
							listOfRows.add(rowMap);
					}
   	}
	return listOfRows;
}

public static  Map<String,String> getRowMap(String primaryKeyColumnName, String primaryKeyValue, List<Map<String, String>> rowsList) {
	Map<String,String> map =new HashMap<String,String>();
		for(Map<String,String> rowMap:rowsList){
			if(primaryKeyValue.equals(rowMap.get(primaryKeyColumnName))){
				return rowMap;
				}
			}
	return map;
}

public static  Map<String,String> getRowMapWithTwoColumnValues(String primaryKeyColumnName, String primaryKeyValue,String secondaryKeyColumnName,String secondaryKeyValue, List<Map<String, String>> rowsList) {
	Map<String,String> map =new HashMap<String,String>();
	for(Map<String,String> rowMap:rowsList){
		if(primaryKeyValue.equals(rowMap.get(primaryKeyColumnName)) && secondaryKeyValue.equals(rowMap.get(secondaryKeyColumnName))){
			return rowMap;
			}
		}
return map;
}

// for getting multi row with two column values
public static  List<Map<String,String>> getMultiRowMapWithTwoColumnValues(String primaryKeyColumnName, String primaryKeyValue,String secondaryKeyColumnName,String secondaryKeyValue, List<Map<String, String>> rowsList) {
	
	List<Map<String,String>>listOfRecords=new ArrayList<Map<String,String>>();//list or all records in flat files
	for(Map<String,String> rowMap:rowsList){
		if(primaryKeyValue.equals(rowMap.get(primaryKeyColumnName)) && secondaryKeyValue.equals(rowMap.get(secondaryKeyColumnName))){
			listOfRecords.add(rowMap);
			}
		}
return listOfRecords;
}

public static List< Map<String,String>> getMultiRowMap(String primaryKeyColumnName, String primaryKeyValue, List<Map<String, String>> rowsList) {

	 List<Map<String,String>>listOfRecords=new ArrayList<Map<String,String>>();//list or all records in flat files
	for(Map<String,String> map:rowsList){
		if(primaryKeyValue.equals(map.get(primaryKeyColumnName))){
			listOfRecords.add(map);
			
			}
		}
	return listOfRecords;

}


/// This is a test method to Parse File,we don't use in real code
public static void parseFile(String testFlatFileCompletePath,String delimeter) throws IOException{
	FileReader fileReader =  new FileReader(testFlatFileCompletePath);
	@SuppressWarnings("resource")
	BufferedReader bufferedReader =  new BufferedReader(fileReader);
	String line=null;
		while((line = bufferedReader.readLine()) != null) {
        //String[] columnsArray = line.split(delimeter);
    	System.out.println("Lenie data: "+line);
    	}
	}


// This function added to newly
//new function to Check RefID id Not present
public static  boolean CheckRefIDNotPresent(String refID,String testFlatFileCompletePath) throws IOException{
	
	 //String testFlatFileCompletePath="\\\\bsc\\it\\ITQA_Automation\\Care1st Member migration\\Automation\\SUC automation\\Required Documents\\12. SUC546265-DRX To MAM\\MAPDP_Complete_2018_20180401.bsc180401111900.txt";
	//String testFlatFileCompletePath= = "C:\\Users\\bgujja01\\Desktop\\0409Daily Extracts\\Daily Extracts";
	 File sFile = new File(testFlatFileCompletePath);
	 //String sPath = testFlatFileCompletePath;
	 boolean RefIDPresentflag = true; 
	 String sFileData = "";
	 if (!sFile.isDirectory()) { 
		  throw new IllegalStateException("File not found: " + testFlatFileCompletePath);
	  } else if (sFile.list().length > 0) {
		  File[] listOfFiles = sFile.listFiles();
		  for (int i=0;i<sFile.listFiles().length;i++)
		  {
			  if (listOfFiles[i].isFile())
			  {
				  @SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new FileReader(listOfFiles[i]));
				  
				  String strLine = br.readLine();
				  while (strLine != null)
				  {
					  if (strLine.contains(refID))
				      {
						  RefIDPresentflag = false;
						  System.out.println("Failed");
					  }
					  
					  strLine = br.readLine();
					  //sFileData = sFileData+strLine;
				  }
	
			  }
			  if (sFileData.contains(refID))
		      {
				  RefIDPresentflag = false;
				  System.out.println("Failed to Verify The Ref ID in Extract File");
			  }
		  }
		  if (RefIDPresentflag == true)
		  System.out.println("Successfully Verified The Exlcusion of Ref ID "+refID+" in Extract Files");
		  
		  }
	return RefIDPresentflag;
	  }

public static String parseCOBFile(String testFlatFileCompletePath,String primaryKey) throws IOException {
	FileReader fileReader =  new FileReader(testFlatFileCompletePath);
	@SuppressWarnings("resource")
	BufferedReader bufferedReader =  new BufferedReader(fileReader);
	String line=null;
	
		while((line = bufferedReader.readLine()) != null) {
        //String[] columnsArray = line.split(delimeter);
			if(line.contains(primaryKey)){
				 //contractNumber=line.substring(33,38);
				return line;
				 
			}
    	}
		return line;
}
public static List<String> parseCOBFileForAllRows(String testFlatFileCompletePath,String primaryKey) throws IOException {
	FileReader fileReader =  new FileReader(testFlatFileCompletePath);
	@SuppressWarnings("resource")
	BufferedReader bufferedReader =  new BufferedReader(fileReader);
	List<String>list=new ArrayList<String>(); 
	String line=null;
		while((line = bufferedReader.readLine()) != null) {
        //String[] columnsArray = line.split(delimeter);
			if(line.contains(primaryKey)){
				 //contractNumber=line.substring(33,38);
				list.add(line) ;
			}
    	}
		return list;
}	             					
}
