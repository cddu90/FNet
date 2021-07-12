
package WebUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * ExcelUtils class that gets test data from excel file to be used for driving
 * tests.
 * 
 *
 */
public class ExcelUtilities {
	private static XSSFSheet excelWSheet;
	private static XSSFWorkbook excelWBook;
	private static XSSFCell cell;
	private static CacheManager cacheManager;
	private static Cache cache;
	private static FileOutputStream fout;

	public HashMap<String, String> testData = new HashMap<String, String>();
	private boolean found = false;

	/**
	 * @return the found
	 */
	protected boolean isFound() {
		return found;
	}

	/**
	 * @param found
	 *            the found to set
	 */
	protected void setFound(boolean found) {
		this.found = found;
	}

	/**
	 * Default constructor
	 */
	public ExcelUtilities() {

	}

	/**
	 * Constructor to load with logger
	 * 
	 * @param path
	 * @param sheetName
	 * @param testCaseId
	 * @param logger
	 */
	public ExcelUtilities(String path, String sheetName) {
		try {
			System.out.println("path in ExcelUtiols:" + path + "  | sheetname:"
					+ sheetName);
			FileInputStream ExcelFile = new FileInputStream(path);
			excelWBook = new XSSFWorkbook(ExcelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExcelUtilities(String path, String sheetName, String testCaseId,
			ExtentTest logger) {
		try {
			logger.log(LogStatus.INFO, "Accessing Data Dictionary.");

			try {
				logger.log(LogStatus.INFO, "Environment: " + sheetName);
				logger.log(LogStatus.INFO, "Data File: " + path);
				FileInputStream ExcelFile = new FileInputStream(path);
				excelWBook = new XSSFWorkbook(ExcelFile);
				excelWSheet = excelWBook.getSheet(sheetName);

				getTestCaseData(testCaseId, logger);
			} catch (FileNotFoundException e) {
				logger.log(LogStatus.ERROR, "FileNotFoundException encountered");
				e.printStackTrace();
			} catch (IOException e) {
				logger.log(LogStatus.ERROR, "IOException encountered");
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.log(LogStatus.ERROR,
					"Exception thrown from ExcelUtils constructor");
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
		initCache(getColumnArray(filePath, sheetName),
				getTableArray(filePath, sheetName));
	}

	/**
	 * Initialize the cache by loading the entire object array into the cache
	 * using the testMethodName as the key
	 * 
	 * @param columnArray
	 * @param testDataArray
	 */
	private static void initCache(Object[][] columnArray,
			Object[][] testDataArray) {

		Map<String, String> dataMap = new HashMap<String, String>();
		if (cacheManager == null) {
			cacheManager = CacheManager.create();
			cacheManager.addCache("testCache");
		}
		cache = cacheManager.getCache("testCache");

		for (int i = 0; i < testDataArray.length; i++) {
			dataMap = new HashMap<String, String>();
			for (int j = 0; j < columnArray[0].length; j++) {
				dataMap.put(columnArray[0][j].toString(),
						testDataArray[i][j].toString());
			}

			if (dataMap.get("Test Case ID") != null) {
				cache.put(new Element(dataMap.get("Test Case ID").toString(),
						dataMap));
			}
		}

	}

	public static void writeToWorkBook(String path) throws Exception {

		fout = new FileOutputStream(new File(path));
		excelWBook.write(fout);
	}

	public static void closefile() throws Exception {

		fout.close();
		
	}

	public static void setCellData(String sheetname_Datasheet, int rowNo,
			int colNum, String cell) throws IOException {
		
		excelWSheet = excelWBook.getSheet(sheetname_Datasheet);
		System.out.println("rows:"+excelWSheet.getLastRowNum());
		if (excelWSheet.getRow(rowNo) == null) {
			excelWSheet.createRow(rowNo).createCell(colNum).setCellValue(cell);
		} else {
			System.out.println("inside setcelldata row");
			if (excelWSheet.getRow(rowNo).getCell(colNum) == null) {
				excelWSheet.getRow(rowNo).createCell(colNum).setCellValue(cell);
System.out.println("value set in if");
			} else {
				excelWSheet.getRow(rowNo).getCell(colNum).setCellValue(cell);
				System.out.println("value set in else");
			}
		}

	}

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

		// //01/16/2018 - Serhiy Commenting out the print statement to minimize
		// console output clutter .Enable back when debugging script
		// System.out.println("Cache Element: " + element);

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
	public void getTestCaseData(String testCaseID, ExtentTest logger) {
		String key;
		String value;
		int colCt = 0;
		try {
			testData.clear();
			colCt = getColumnCount(logger);
			logger.log(LogStatus.INFO, "Columns count = " + colCt);
			logger.log(LogStatus.INFO, "Searching for test case in datasheet");

			for (int rowNum = 0; !(getCellData(rowNum, 0, logger) == null || getCellData(
					rowNum, 0, logger).isEmpty()); rowNum++) {
				logger.log(LogStatus.INFO, "Checking row #" + rowNum);
				if (getCellData(rowNum, 0, logger).equals(testCaseID)) {
					found = true;
					logger.log(LogStatus.INFO, "Test case found in datasheet");
					logger.log(LogStatus.INFO, "Data dictionary values:");

					for (int colNum = 0; colNum < colCt; colNum++) {
						value = getCellData(rowNum, colNum, logger);
						if (value != null && !value.isEmpty()) {
							key = getCellData(0, colNum, logger);
							testData.put(key, value);

							if (!"".equals(value))
								logger.log(LogStatus.INFO, "     " + key
										+ " = " + value);
						}
					}
				}
			}
			if (testData.size() == 0) {
				logger.log(LogStatus.INFO, "Test data not found in datasheet");
			}
		} catch (Exception e) {
			logger.log(LogStatus.ERROR,
					"Exception thrown from ExcelUtils.getTestCaseData("
							+ testCaseID + ")");
			e.printStackTrace();
		}
	}

	/**
	 * Returns a count of the number of columns
	 * 
	 * @param logger
	 * @return column count as an integer
	 */
	public static int getColumnCount(ExtentTest logger) {
		int rowNum = 0;
		int colNum = 0;
		int colCt = 0;
		try {
			while (getCellData(rowNum, colNum, logger) == null
					|| getCellData(rowNum, colNum, logger).isEmpty()) {
				colCt++;
				colNum++;
			}
		} catch (Exception e) {
			logger.log(LogStatus.ERROR,
					"Exception thrown from ExcelUtils.countDataColumns()");
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
	public static String getCellData(int rowNum, int colNum, ExtentTest logger) {
		try {
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData = null;
			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
				cellData = cell.getStringCellValue();
			} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
				cellData = String.valueOf(cell.getNumericCellValue());
			} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK) {
				cellData = "";
			}
			return cellData;
		} catch (Exception e) {
			logger.log(LogStatus.ERROR,
					"Exception encountered in ExcelUtils.getCellData(" + rowNum
							+ "," + colNum + ")");
			return "";
		}
	}

	/**
	 * WIP by @ainapu01 Experimental method. Get data from database if excel
	 * sheet is blank
	 * 
	 * @param filePath
	 * @param sheetName
	 * @return
	 */
	public static Object[][] getTestData(String filePath, String sheetName) {
		String[][] tabArray = null;
		int startRow = 1;
		int startCol = 0;
		int ci = 0;
		int cj = 0;
		int totalRows = 0;
		int totalCols = 0;

		try {
			FileInputStream excelFile = new FileInputStream(filePath);
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
			totalRows = excelWSheet.getPhysicalNumberOfRows() - 1;
			if (totalRows < 2) {
				System.out.println("Sheet Empty: " + sheetName);
			} else {
				totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells() - 1;

				tabArray = new String[totalRows][totalCols + 1];
				for (int i = startRow; i <= totalRows; i++, ci++) {
					for (int j = startCol; j <= totalCols; j++, cj++) {
						if (getCellData(i, j) != "") {
							tabArray[ci][cj] = getCellData(i, j);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
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
		int ci, cj;
		int totalRows;
		int totalCols;

		try {
			FileInputStream excelFile = new FileInputStream(filePath);
			excelWBook = new XSSFWorkbook(excelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
			if (excelWSheet == null) {
				excelWSheet = excelWBook.getSheet("Sheet1");
			}
			totalRows = excelWSheet.getPhysicalNumberOfRows() - 1;
			System.out.println("The total rows are!!!! " + totalRows);
			// totalRows = excelWSheet.getLastRowNum()-2;

			totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells() - 1;

			System.out.println("The total columns are!!!" + totalCols);
			tableArray = new String[totalRows][totalCols + 1];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j <= totalCols; j++, cj++) {
					// System.out.println(ci+":"+cj+"->"+i+":"+j);
					if (getCellData(i, j) != null
							&& getCellData(i, j).trim() != "") {
						tableArray[ci][cj] = getCellData(i, j);
					} else {
						tableArray[ci][cj] = "";
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
		}
		// System.out.println("Im in end of getTableArray");
		return (tableArray);
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
			System.out.println("environment:"+sheetName);
			excelWSheet = excelWBook.getSheet(sheetName);
			if (excelWSheet == null) {
				excelWSheet = excelWBook.getSheet("Sheet1");
			}
			totalRows = excelWSheet.getPhysicalNumberOfRows() - 1;
			totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells() - 1;
			columnArray = new String[totalRows][totalCols + 1];
			ci = 0;

			for (int j = 0; j <= totalCols; j++) {
				if (getCellData(ci, j).trim() != "") {
					columnArray[ci][j] = getCellData(ci, j);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet: " + sheetName);
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet: " + sheetName);
		}
		return (columnArray);
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
		try { // System.out.println("Row Number  Balu::::"+rowNum+" column No:  "+colNum);
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			if (cell != null) {
				DataFormatter formatter = new DataFormatter(); // creating
																// formatter
																// using the
																// default
																// locale

				cellData = formatter.formatCellValue(cell);
				return cellData;
			}
			/*
			 * if (DateUtil.isCellDateFormatted(cell)) { SimpleDateFormat sdf =
			 * new SimpleDateFormat("MM/dd/yyyy"); String date =
			 * sdf.format(cell.getDateCellValue());
			 * 
			 * return date; } else{
			 * 
			 * DataFormatter formatter = new DataFormatter(); //creating
			 * formatter using the default locale
			 * 
			 * cellData = formatter.formatCellValue(cell); return cellData;
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static String getFieldNameCorrespondingColumnName(String dbColumnName) {
		int totalRows = excelWSheet.getPhysicalNumberOfRows() - 1;
		if (totalRows < 2) {
			System.out.println("Sheet Empty.....!!");
		} else {
			excelWSheet.getRow(0).getPhysicalNumberOfCells();
			for (int rowNo = 1; rowNo < totalRows; rowNo++) {

				if (dbColumnName.equals(getCellData(rowNo, 1))) {
					// System.out.println("Mapping file values: "+getCellData(rowNo,1)+"-------"+getCellData(rowNo,2));
					return getCellData(rowNo, 2);
				}
			}
		}
		return null;
	}

	public static List<String> getErrorCodesList(String string) {
		int totalRows = excelWSheet.getPhysicalNumberOfRows() - 1;
		List<String> list = new ArrayList<String>();
		// if (totalRows < 2) {System.out.println("Sheet Empty.....!!");}
		// int totalCols = excelWSheet.getRow(0).getPhysicalNumberOfCells()-1;
		for (int rowNo = 1; rowNo < totalRows; rowNo++) {
			list.add(getCellData(rowNo, 2));

		}

		return list;
	}

/*	public static void setExecutionStatus(boolean flag,String inputDataSheetPath, String sheetName, String testCaseId) throws Exception {
		// Object[][] columnArray = ExcelUtilities.getColumnArray(inputDataSheetPath,sheetName);
		 Object[][] testDataArray = ExcelUtilities.getTableArray(inputDataSheetPath,sheetName);
		 String status="Passed";
			if(!flag){
				status="Failed";
			}
		 for(int row=0;row<=testDataArray.length-1;row++){//loop through the data in test data sheet
	  //below checking the method name with the keyword name in test data sheet
			 if(testCaseId.equalsIgnoreCase(testDataArray[row][0].toString())){
				 System.out.println("sheetname:"+sheetName+"|"+row);
				setCellData(sheetName, row+1, 4, status);
				break;
		 }
}
		 writeToWorkBook(inputDataSheetPath);
		 closefile();
	}*/
	
	public static void setResultsValue( String inputDataSheetPath, String sheetName, String testCaseId, int columnNumber, String ResultValue) throws Exception {
		try{
		 Object[][] testDataArray = ExcelUtilities.getTableArray(inputDataSheetPath,sheetName);

		 
		 for(int row=0;row<=testDataArray.length-1;row++){//loop through the data in test data sheet
	  //below checking the method name with the keyword name in test data sheet
			 if(testCaseId.equalsIgnoreCase(testDataArray[row][0].toString())){
				 System.out.println("sheetname:"+sheetName+"|"+row);
				setCellData(sheetName, row+1, columnNumber, ResultValue);
				break;
			 }
		 }
		 writeToWorkBook(inputDataSheetPath);
		 closefile();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
}
