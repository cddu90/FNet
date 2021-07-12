
package WebUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



/**
 * ExcelUtils class that gets test data from excel file to be used for driving tests.
 * 
 *
 */
public class ExcelUtils
{
	private static XSSFSheet excelWSheet;
	private static XSSFWorkbook excelWBook;
	private static XSSFCell cell;
	private static CacheManager cacheManager;
	private static Cache cache;
	

	public HashMap<String, String> testData = new HashMap<String, String>();	
	private boolean found = false;

	/**
	 * @return the found
	 */
	protected boolean isFound() {
		return found;
	}

	/**
	 * @param found the found to set
	 */
	protected void setFound(boolean found) {
		this.found = found;
	}


	/**
	 * Default constructor
	 */
	public ExcelUtils() {
		
	}
	
	/**
	 * Constructor to load with logger
	 * 
	 * @param path
	 * @param sheetName
	 * @param testCaseId
	 * @param logger
	 */
	public ExcelUtils(String path, String sheetName, String testCaseId, ExtentTest logger) 
	{
		try 
		{
			logger.log(LogStatus.INFO, "Accessing Data Dictionary.");			
			
			try
			{
				logger.log(LogStatus.INFO, "Environment: " + sheetName);			
				logger.log(LogStatus.INFO, "Data File: " + path);				
				FileInputStream ExcelFile = new FileInputStream(path);
				excelWBook = new XSSFWorkbook(ExcelFile);
				excelWSheet = excelWBook.getSheet(sheetName);
				
				getTestCaseData(testCaseId,logger);
			}
			catch (FileNotFoundException e) 
			{
				logger.log(LogStatus.ERROR, "FileNotFoundException encountered");	
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				logger.log(LogStatus.ERROR, "IOException encountered");			
				e.printStackTrace();
			}			
		} 
		catch (Exception e)
		{
			logger.log(LogStatus.ERROR, "Exception thrown from ExcelUtils constructor");			
			e.printStackTrace();			
		}
	}
	
	/**
	 * Initialize the cache and load excel data from the sheet 
	 * 
	 * @param filePath
	 * @param sheetName
	 */
	public static void initCache(String filePath, String sheetName) {
		initCache(getColumnArray(filePath, sheetName), getTableArray(filePath, sheetName));
	}

	/**
	 * Initialize the cache by loading the entire object array into the cache using 
	 * the testMethodName as the key
	 * 
	 * @param columnArray
	 * @param testDataArray
	 */
	private static void initCache(Object[][] columnArray, Object[][] testDataArray) {
		//Object[][] dataColumnArray = null;
		//Object[][] dataTableArray = null;
		Map<String, String> dataMap = new HashMap<String, String>();
		if (cacheManager == null) {
			cacheManager = CacheManager.create();
			cacheManager.addCache("testCache");
		}
		cache = cacheManager.getCache("testCache");

		for (int i=0; i<testDataArray.length; i++) {
			dataMap = new HashMap<String, String>();
			for (int j=0; j< columnArray[0].length; j++) {
				dataMap.put(columnArray[0][j].toString(), testDataArray[i][j].toString());
			}
			
			if (dataMap.get("Test Case ID") != null) {
				cache.put( new Element( dataMap.get("Test Case ID").toString(), dataMap ) );
			}
		}
		
		//for ( Object key : cache.getKeys() ) {
			//01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script 
			//System.out.println("-----------" + key.toString());
			//System.out.println(cache.get(key));
			
		//}
	}
	
//	public static void cacheWprDataFromFacetsLikeQuery(Map<String, String> dataMap, String methodName) {
//		Object[][] dataColumnArray = null;
//		Object[][] dataTableArray = null;
//		String wprQueryWithParameterValues;
//
//		if ( dataMap.get("FACETS_LIKE_INNER_QUERY") != null && !"".equals(dataMap.get("FACETS_LIKE_INNER_QUERY")) ) {
//			dataColumnArray = new DBUtils().getFacetsColumnArray(dataMap.get("FACETS_LIKE_INNER_QUERY").toString());
//			dataTableArray = new DBUtils().getFacetsTableArray(dataMap.get("FACETS_LIKE_INNER_QUERY").toString());
//		}
//		if ( dataMap.get("WPR_LIKE_QUERY") != null && !"".equals(dataMap.get("WPR_LIKE_QUERY")) ) {
//			wprQueryWithParameterValues = inputParameterValuesLikeQuery(dataMap.get("WPR_LIKE_QUERY").toString(), dataColumnArray, dataTableArray);
//			dataColumnArray = new DBUtils().getWprColumnArray(wprQueryWithParameterValues);
//			dataTableArray = new DBUtils().getWprTableArray(wprQueryWithParameterValues);
//			initDBCache(dataMap, dataColumnArray, dataTableArray, dataMap.get("TestMethod").toString());
//		}
//
//	}
//	
//	public static void cacheWprDataFromFacetsInQuery(Map<String, String> dataMap, String methodName) {
//		Object[][] dataColumnArray = null;
//		Object[][] dataTableArray = null;
//		String wprQueryWithParameterValues;
//
//		if ( dataMap.get("FACETS_IN_INNER_QUERY") != null && !"".equals(dataMap.get("FACETS_IN_INNER_QUERY")) ) {
//			dataColumnArray = new DBUtils().getFacetsColumnArray(dataMap.get("FACETS_IN_INNER_QUERY").toString());
//			dataTableArray = new DBUtils().getFacetsTableArray(dataMap.get("FACETS_IN_INNER_QUERY").toString());
//		}
//		if ( dataMap.get("WPR_IN_QUERY") != null && !"".equals(dataMap.get("WPR_IN_QUERY")) ) {
//			wprQueryWithParameterValues = inputParameterValuesInQuery(dataMap.get("WPR_IN_QUERY").toString(), dataColumnArray, dataTableArray);
//			dataColumnArray = new DBUtils().getWprColumnArray(wprQueryWithParameterValues);
//			dataTableArray = new DBUtils().getWprTableArray(wprQueryWithParameterValues);
//			initDBCache(dataMap, dataColumnArray, dataTableArray, dataMap.get("TestMethod").toString());
//		}
//
//	}
	
	/**
	 * Insert parameter values in the LIKE clause of the query
	 * 
	 * @param sqlQuery
	 * @param dataColumnArray
	 * @param dataTableArray
	 * @return
	 */
//	private static String inputParameterValuesLikeQuery( String sqlQuery, Object[][] dataColumnArray, Object[][] dataTableArray) {
//		for (int i = 0; i < dataColumnArray[0].length; i++) {
//			sqlQuery = sqlQuery.replace(dataColumnArray[0][i].toString(), dataTableArray[0][i].toString());
//		}
//		return sqlQuery;
//	}
//	
//	/**
//	 * Insert parameters values in the IN clause of the query
//	 * 
//	 * @param sqlQuery
//	 * @param dataColumnArray
//	 * @param dataTableArray
//	 * @return
//	 */
//	private static String inputParameterValuesInQuery( String sqlQuery, Object[][] dataColumnArray, Object[][] dataTableArray) {
//		StringBuilder inPrameter = new StringBuilder();
//		for (int i = 0; i < dataTableArray.length; i++) {
//			inPrameter.append("'").append(dataTableArray[i][0].toString()).append("'");
//			if (i != dataTableArray.length - 1) {
//				inPrameter.append(", ");
//			}
//		}
//		sqlQuery = sqlQuery.replace(dataColumnArray[0][0].toString(), inPrameter.toString());
//		return sqlQuery;
//	}
	
	/**
	 * 
	 * @param columnArray
	 * @param testDataArray
	 * @param methodName
	 */
//	private static void initDBCache(Map<String, String> dataMap, Object[][] columnArray, Object[][] testDataArray, String methodName) {
//		if (cacheManager == null) {
//			cacheManager = CacheManager.create();
//			cacheManager.addCache("testCache");
//		}
//		cache = cacheManager.getCache("testCache");
//
//		for (int i=0; i<testDataArray.length; i++) {
//			for (int j=0; j< columnArray[0].length; j++) {
//				if (columnArray[0][j] != null && testDataArray[i][j] != null) {
//					dataMap.put(columnArray[0][j].toString(), testDataArray[i][j].toString());
//				}
//			}
//			cache.put( new Element( methodName, dataMap ) );
//		}
//		for ( Object key : cache.getKeys() ) {
//			System.out.println(key.toString());
//			System.out.println(cache.get(key));
//		}
//	}
	
	/**
	 * Get individual test method data
	 * 
	 * @param testMethodName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getTestMethodData(String testMethodName) {
		Map<String, String> dataMap = null;
		
		System.out.println("Test Method Name: " + testMethodName);
		
		Element element = cache.get(testMethodName);
		
		////01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script
		//System.out.println("Cache Element: " + element);
		
		if (element != null) {
			dataMap = ((Map<String, String>) element.getObjectValue());
			
		}
		return dataMap;
	}
	
	/**
	 * Assemble the test case's populated data fields from the data spreadsheet 
	 * 
	 * @param testCaseID
	 * @param logger
	 */
	public void getTestCaseData(String testCaseID, ExtentTest logger) 
	{
		String key;
		String value;
		int colCt = 0;
		try 
		{
			testData.clear();
			colCt = getColumnCount(logger);
			logger.log(LogStatus.INFO, "Columns count = " + colCt);
			logger.log(LogStatus.INFO, "Searching for test case in datasheet");	
			
			for (int rowNum = 0; !(getCellData(rowNum, 0, logger) == null || getCellData(rowNum, 0,logger).isEmpty()); rowNum++)
			{
				logger.log(LogStatus.INFO, "Checking row #" + rowNum);				
				if (getCellData(rowNum, 0,logger).equals(testCaseID))
				{
					found = true;
					logger.log(LogStatus.INFO, "Test case found in datasheet");		
					logger.log(LogStatus.INFO, "Data dictionary values:");		
					
					for (int colNum = 0; colNum < colCt; colNum++)
					{
						value = getCellData(rowNum, colNum,logger);
						if (value != null && !value.isEmpty())							
						{
							key = getCellData(0, colNum,logger);								
							testData.put(key, value);

							if (!"".equals(value))
								logger.log(LogStatus.INFO, "     " + key + " = " + value);
						}
					}
				}				
			}
			if (testData.size() == 0)
			{
				logger.log(LogStatus.INFO, "Test data not found in datasheet");				
			}
		} 
		catch (Exception e)
		{
			logger.log(LogStatus.ERROR, "Exception thrown from ExcelUtils.getTestCaseData(" +  testCaseID + ")");	
			e.printStackTrace();
		}
	}
		
	/**
	 * Returns a count of the number of columns
	 * 
	 * @param logger
	 * @return column count as an integer
	 */
	public static int getColumnCount(ExtentTest logger)  
	{
		int rowNum = 0;
		int colNum = 0;
		int colCt = 0;
		try 
		{
			while (getCellData(rowNum, colNum,logger) == null || getCellData(rowNum, colNum,logger).isEmpty())
			{
				colCt++;
				colNum++;
			}
		} 
		catch (Exception e)
		{
			logger.log(LogStatus.ERROR, "Exception thrown from ExcelUtils.countDataColumns()");			
			e.printStackTrace();
		}
		return colCt;
	}

	/**
	 * Returns the cell value as a String
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param logger
	 * @return cell value as a string
	 */
	public static String getCellData(int rowNum, int colNum, ExtentTest logger) 
	{
		try
		{
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = null;
			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
				cellData = cell.getStringCellValue();
			}
			else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) 
			{
				cellData = String.valueOf(cell.getNumericCellValue());
			}
			else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)
			{
				cellData = "";
			}
			return cellData;
		} 
		catch (Exception e)
		{
			logger.log(LogStatus.ERROR, "Exception encountered in ExcelUtils.getCellData(" + rowNum + "," + colNum + ")");			
			return "";
		}
	}

	/**
	 * WIP by @ainapu01 
	 * Experimental method. Get data from database if excel sheet is blank
	 * 
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public static Object[][] getTestData(String filePath, String sheetName) {   
		String[][] tabArray = null;
		int startRow = 1;
		int startCol = 0;
		int ci=0;
		int cj=0;
		int totalRows = 0;
		int totalCols = 0;
		
		try {
			FileInputStream excelFile = new FileInputStream(filePath);
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
			totalRows = excelWSheet.getPhysicalNumberOfRows()-1;
			if (totalRows < 2) {
				System.out.println("Sheet Empty: " + sheetName);
			} else {
				totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells()-1;

				tabArray=new String[totalRows][totalCols+1];
				for (int i=startRow;i<=totalRows;i++, ci++) {           	   
					for (int j=startCol;j<=totalCols;j++, cj++){
						if(getCellData(i,j)!=""){
							tabArray[ci][cj]=getCellData(i,j);	
						}
					}
				}
			}
		}catch (FileNotFoundException e){
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}catch (IOException e){
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return(tabArray);
	}

	/**
	 * Get Excel data as an array
	 * 
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public static Object[][] getTableArray(String filePath, String sheetName) {   
		String[][] tableArray = null;
		int startRow = 1;
		int startCol = 0;
		int ci,cj;
		int totalRows;
		int totalCols;
	
		try {
			FileInputStream excelFile = new FileInputStream(filePath);			
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
			if (excelWSheet == null) {
				excelWSheet = excelWBook.getSheet("Sheet1");
			}
			totalRows = excelWSheet.getPhysicalNumberOfRows()-1;
			//totalRows = excelWSheet.getLastRowNum()-2;
		
			totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells()-1;
			
			tableArray=new String[totalRows][totalCols+1];
			ci=0;
			for (int i=startRow;i<=totalRows;i++, ci++) {           	   
				cj=0;
				for (int j=startCol;j<=totalCols;j++, cj++){
					//System.out.println(ci+":"+cj+"->"+i+":"+j);
					if(getCellData(i,j) != null && getCellData(i,j).trim()!=""){
						tableArray[ci][cj]=getCellData(i,j);
					}
					else {
						tableArray[ci][cj]="";
					}
				}
			}
		}catch (FileNotFoundException e){
			System.out.println("Could not read the Excel sheet");
		}catch (IOException e){
			System.out.println("Could not read the Excel sheet");
		}
		//System.out.println("Im in end of getTableArray");
		return(tableArray);
	}


	/**
	 * Get columns names array
	 * 
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public static Object[][] getColumnArray(String filePath, String sheetName) {   
		String[][] columnArray = null;
		int ci;
		int totalRows;
		int totalCols;
		try {
			FileInputStream excelFile = new FileInputStream(filePath);			
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
			if (excelWSheet == null) {
				excelWSheet = excelWBook.getSheet("Sheet1");
			}
			totalRows = excelWSheet.getPhysicalNumberOfRows()-1;
			totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells()-1;
			columnArray=new String[totalRows][totalCols+1];
			ci=0;

			for (int j=0;j<=totalCols;j++){
				if(getCellData(ci,j).trim()!=""){
					columnArray[ci][j]=getCellData(ci,j);
				}
			}
		}catch (FileNotFoundException e){
			System.out.println("Could not read the Excel sheet: " + sheetName);
		}catch (IOException e){
			System.out.println("Could not read the Excel sheet: " + sheetName);
		}
		return(columnArray);
	}

	/**
	 * Get cell value as a string
	 * 
	 * @param rowNum
	 * @param colNum
	 * @return cellData as a String
	 */
	public static String getCellData(int rowNum, int colNum) {
		String cellData = null;
		try{ //System.out.println("Row Number  Balu::::"+rowNum+" column No:  "+colNum);
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			if(cell!=null){
				int dataType = cell.getCellType();
				if  (dataType == 3) {
					return "";
				}else{
					DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
					 
					cellData  = formatter.formatCellValue(cell);
					//cellData = cell.getStringCellValue();
				}
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return cellData;
	}

}
