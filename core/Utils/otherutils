package WebUtils;
import java.util.Map;

import org.testng.asserts.SoftAssert;

import com.bsc.qa.facets.ffpojo.factory.sampleTest;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OtherUtilities extends sampleTest{

	// Validating in ExcelValues and FACETS database column values
	public static void validate(Map<String, String> ExeclValuesMap,	Map<String, String> queryDataMap,SoftAssert softAssertion,ExtentTest logger) {
		 
		for(String key:ExeclValuesMap.keySet()){
			if(key.trim().equals("DBColumnName")){					
				String excelColumnName=ExeclValuesMap.get("DBColumnName").trim();
				if(queryDataMap.containsKey(excelColumnName)){
					String excelDBColumnValue=ExeclValuesMap.get("DBColumnValue").trim();
					String SQLDBColumnValue=queryDataMap.get(excelColumnName);
					//softAssertion.assertEquals(excelDBColumnValue,SQLDBColumnValue, " < ExcelDbcolumnValue: " + excelDBColumnValue + "DBValue:" + SQLDBColumnValue + " >");
					 String status="Fail";
					 if(SQLDBColumnValue.equalsIgnoreCase(excelDBColumnValue)){
				          status="Pass";
				          logger.log(LogStatus.PASS,"Database ColumnName: " + excelColumnName + " |---------- ActualValue: " +SQLDBColumnValue+ "----------ExpectedlValue: " + excelDBColumnValue+"|----------Status:"+status);
				    }else{
				          logger.log(LogStatus.FAIL,"Database ColumnName: " + excelColumnName + " |---------- ActualValue: " +SQLDBColumnValue+ "----------ExpectedlValue: " + excelDBColumnValue+"|----------Status:"+status);
				          }
					
					//logger.log(LogStatus.INFO,"Database ColumnName: " + excelColumnName + " |excelDBColumnValue: " +excelDBColumnValue+ "---------------SQLDBColumnValue: " + SQLDBColumnValue);
					logger.log(LogStatus.INFO, SQLDBColumnValue+" Verified successfully.");
				}else{
					 softAssertion.assertTrue(queryDataMap.containsKey(excelColumnName), "Element " + excelColumnName + " is not present in Database" );
					 logger.log(LogStatus.FAIL, "The column:"+excelColumnName+" is not present Database");
				}
			}
		}
		
	}



	public static void printTestCaseDeatilsInReport(Map<String, String> data) {		
		String strSUCName= data.get("SUC Name").toString();		
		String strTestCaseId = data.get("Test Case ID").toString();		
		String strTestCaseName = data.get("Test Case Name").toString();		
		String strStepNumber = data.get("Step Number").toString();
		//String strInputFileName = data.get("Input File Name").toString();
		logger.log(LogStatus.INFO," SUC Name: " + strSUCName + ", Test Case ID: " + strTestCaseId + ", Test Case Name: " + strTestCaseName + ", Step Number:" + strStepNumber);
		//logger.log(LogStatus.INFO," Input File Name: " + strInputFileName);
	}
	

	//below method is to check whether the given number is numeric or not
		public static boolean isNumeric(String str) {
		    int size = str.length();
		    for (int i = 0; i < size; i++) {
		        if (!Character.isDigit(str.charAt(i))) {
		            return false;
		        }
		    }

		    return size > 0;
		} 

}
