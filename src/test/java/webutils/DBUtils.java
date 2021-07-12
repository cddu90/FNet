package WebUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bsc.bqsa.AutomationStringUtilities;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class DBUtils {

	private Connection dbConnection;
	String DBName;
	String DBUser; 
	String DBPwd; 
	String DBServer; 
	String DBPort;
	
	public DBUtils(String dBName, String dBUser, String dBPwd, String dBServer,
			String dBPort) {
	
		DBName = dBName;
		DBUser = dBUser;
		DBPwd = dBPwd;
		DBServer = dBServer;
		DBPort = dBPort;
	}

	
	public void setUpDBConnection() {

		
		if (DBName != null && !"".equals(DBName)) {
						
			String uID = AutomationStringUtilities.decryptValue((DBUser.trim())); 
			String pWD = AutomationStringUtilities.decryptValue((DBPwd.trim())); 
			
			/*String uID = DBUser.trim(); 
			String pWD = DBPwd.trim(); */
			
//			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//			System.out.println("uID");
//			System.out.println("pWD");
//			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
						
            //connection string 
			String facetsConnectionString = "jdbc:oracle:thin:" + uID + "/" + pWD + "@" + DBServer.trim() + ":" + DBPort.trim() + ":" + DBName.trim();
			//System.out.println(facetsConnectionString);
			
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("Connection string: " + facetsConnectionString);
				
				dbConnection = DriverManager.getConnection(facetsConnectionString);
				
				if (dbConnection != null) {
					System.out.println("Connected to the Database: " + DBName);
					//logger.log(LogStatus.INFO, "Database portNumber: "+DBPort);
					
				}
				
			} catch (SQLException ex) {
				
				System.out.println("ERROR: SQL Exception when connecting to the Facets database: " + DBName);
				ex.printStackTrace();
				
			} catch (ClassNotFoundException ex) {
				System.out.println("ERROR: JDBC Driver Class Not Found when connecting to the Facets database: " + DBName);
				ex.printStackTrace();
				
			}
		}
	
	}			
	
	
	
	/**
	 * Close the database connection 
	 * 
	 */
	public void teardownDBConnection() {
		if (dbConnection != null) {
			try {
				System.out.println("Closing Database Connection...");
				dbConnection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Get Table data as an array object
	 * 
	 * @param query
	 * @return two dimentional object array with data returned by executing the query
	 */

	public Object[][] getArrayOfTableData(String query) {
		
		String[][] tableArray = null;
		
		int totalCols = 0;
		int totalRows = 0;
		int ci = 0;
		int cj = 0;
		
		Statement statement;
		ResultSet resultSet;
		setUpDBConnection();
		
		try {
	
			statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery(query);
			resultSet.last();
			totalRows = resultSet.getRow();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			totalCols = rsmd.getColumnCount();
			
			tableArray = new String[totalRows][totalCols];
			resultSet.beforeFirst();

			while (resultSet.next()) {
				int i = 1;
				while (i <= totalCols) {
					tableArray[ci][cj] = resultSet.getString(i++);
					cj++;
				}
				ci++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		teardownDBConnection();
		return tableArray;
	}
	
	
	public String getSingleStringFromDatabase(String sqlQuery) {
		Object[][] data = getArrayOfTableData(sqlQuery);
		return (String) data[0][0];
	}
	
	
public Object[][] get2DStringFromDBWithUnique(String query, String strUniqueIDValueOne, String strUniqueIDValueTwo) {		
		
		String[][] strReturnValue;
		String strUpdatedSQL;

		if (strUniqueIDValueOne == null || strUniqueIDValueOne.isEmpty()) {
			// run query without any replacements in it 
			strReturnValue = (String[][]) getArrayOfTableData(query);
			  
			} else {

				// replace uniqueIDValue. Please note that query should have STR_VAR_UNIQUE_ID_VALUE item in it
				strUpdatedSQL = query.replace("STR_VAR_UNIQUE_ID_VALUE_ONE",strUniqueIDValueOne.trim());
               
				// if secondary unique id is blank 
				if (strUniqueIDValueTwo == null || strUniqueIDValueTwo.isEmpty()) {
					
					// run query dont update unique id value STR_VAR_UNIQUE_ID_VALUE_TWO
					strReturnValue = (String[][]) getArrayOfTableData(strUpdatedSQL);
				
				  }else{
					
					// replace uniqueIDValueTwo. Please note that query should have STR_VAR_UNIQUE_ID_VALUE_Two item in it
					 strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_TWO",strUniqueIDValueTwo.trim());
					////01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script
					
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					 // System.out.println(strUpdatedSQL);
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
					  
					 // run query
					 strReturnValue = (String[][]) getArrayOfTableData(strUpdatedSQL);
					
				}
				
				
			}
	
		return strReturnValue;

	}
	
	

	public String getSingleStringFromDatabaseWithUniqueValue(String query, String strUniqueIDValueOne, String strUniqueIDValueTwo) {		
		
		String strReturnValue, strUpdatedSQL;

		if (strUniqueIDValueOne == null || strUniqueIDValueOne.isEmpty()) {
			// run query without any replacements in it 
			strReturnValue = getSingleStringFromDatabase(query);
			  
			} else {

				// replace uniqueIDValue. Please note that query should have STR_VAR_UNIQUE_ID_VALUE item in it
				strUpdatedSQL = query.replace("STR_VAR_UNIQUE_ID_VALUE_ONE",strUniqueIDValueOne.trim());
               
				// if secondary unique id is blank 
				if (strUniqueIDValueTwo == null || strUniqueIDValueTwo.isEmpty()) {
					
					// run query dont update unique id value STR_VAR_UNIQUE_ID_VALUE_TWO
					strReturnValue = getSingleStringFromDatabase(strUpdatedSQL);
				
				  }else{
					
					// replace uniqueIDValueTwo. Please note that query should have STR_VAR_UNIQUE_ID_VALUE_Two item in it
					 strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_TWO",strUniqueIDValueTwo.trim());
					////01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script
					
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					 // System.out.println(strUpdatedSQL);
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
					  
					 // run query
					 strReturnValue = getSingleStringFromDatabase(strUpdatedSQL);
					
				}
				
				
			}
	
		return strReturnValue;

	}
	
	
	
	/**
	 * Execute a query and get a ResultSet 
	 * @param query
	 * @return resultSet
	 */
	public ResultSet getDataResultSet(String query) {

		Statement statement;
		ResultSet resultSet = null;
		setUpDBConnection();
		try {
		
		   statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
			
			resultSet = statement.executeQuery(query);
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		teardownDBConnection();
		return resultSet;
	}
	
	
	public Object[][] getColumnArray(String query) {
		
		String[][] tableArray = null;
		int totalCols = 0;
		int totalRows = 0;
		Statement statement;
		ResultSet resultSet;
		setUpDBConnection();
		try {

			statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			resultSet = statement.executeQuery(query);
			resultSet.last();
			totalRows = resultSet.getRow();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			totalCols = rsmd.getColumnCount();
			tableArray = new String[totalRows][totalCols];
			resultSet.beforeFirst();
			resultSet.next(); 
			
			int i = 0;
			
			if (totalRows > 0) {
				while (i < totalCols) {
					tableArray[0][i++] = rsmd.getColumnName(i);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		teardownDBConnection();
		return tableArray;
	}

	
	/**
	 * Execute a query and get a ArrayList of hashMap 
	 * @param query
	 * @return ArrayList(hashMap)
	 */
	public ArrayList<Map<String, Object>> resultSetToArrayList(String query) throws SQLException{
        
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Statement statement;
		ResultSet resultSet = null;
		setUpDBConnection();
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		System.out.println(query);
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		try {
		
		  statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		
          resultSet = statement.executeQuery(query);
		
          ResultSetMetaData metaData = resultSet.getMetaData();
          
          int columns = metaData.getColumnCount();  
          // System.out.println("Columns" + columns); 
	      
          // for each row 
		  while (resultSet.next()){
			  
			     //HashMap<String, Object> row = new HashMap(columns);
			     Map<String,Object> row = new HashMap<String, Object>((columns));
			    
			     // for each column in db resylts set 
			     for(int i=1; i<=columns; ++i){ 
			    	// System.out.println("i ==> " + i); 
			    	 
			         row.put(metaData.getColumnName(i),resultSet.getObject(i)); // <==  MAP<ColumnName, RowValue>
			        
			        // System.out.println("Column Name "+ md.getColumnName(i)); 
			       //  System.out.println("Cell Value "+ resultSet.getObject(i));
			     
		         }
			     
			      list.add(row); // Place map to  array list 
			      
		 	} // while (resultSet.next()){
          
	
		
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		teardownDBConnection();
		return list;

	  }	
	
	
	/**
	 * Execute a query and get # delimited, single dimention array
	 * @param query
	 * @return ArrayList(hashMap)
	 */
public List<Map<String,String>> resultSetToDictionary(String query) throws SQLException{
		
		String[] tableArray = null;
		String[] emptyTableArray = null;
		Boolean emptyResultSet = true;
		Statement statement;
		ResultSet resultSet = null;
		Map<String,String> queryDataMap=new HashMap<String,String>();
		List<Map<String,String>>listOfRecords=new ArrayList<Map<String,String>>();	
		setUpDBConnection();
			String[] queriesArray=query.split(";");
			for(String queryString:queriesArray){
				resultSet=getQueryDataFromDB(queryString);
		        ResultSetMetaData metaData = resultSet.getMetaData();
		        int columns = metaData.getColumnCount();
		        int recordsCount=0;
		        int columnsCount=0;
		       /* for(int i=1;i<=columns;i++){
		        	System.out.println("column Name: "+metaData.getColumnLabel(i));
		        }*/
		        int rowcount = 0;
		        if (resultSet.last()) {
		          rowcount = resultSet.getRow();
		          resultSet.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
		        }
		        while (resultSet.next()){	
		        	  recordsCount++;
		        	  for(int i=1;i<=columns;i++){
		        		  columnsCount++;
		        		  queryDataMap.put(metaData.getColumnLabel(i),resultSet.getString(metaData.getColumnLabel(i)));
		        	  	}
		        	  listOfRecords.add(queryDataMap);
		          }
		        System.out.println("Records Count: "+rowcount);
		        System.out.println("Columns Count: "+columnsCount);
			}
		
			/*HashMap<String,String> sqlQueryValuesMap=new HashMap<String,String>();
			sqlQueryValuesMap.put("Record_Type_Header", "H");
			sqlQueryValuesMap.put("Contract_Number_Headere", "H5928");
			sqlQueryValuesMap.put("Payment_Date", "20180418");
			sqlQueryValuesMap.put("Record_Type_Body", "PD");
			sqlQueryValuesMap.put("Contract_Number_Body", "H5928");
			sqlQueryValuesMap.put("PBP_Number", "002");
			sqlQueryValuesMap.put("HIC_Number", "L52795415278");
			sqlQueryValuesMap.put("Premium_Adjustment_Period_Start_Date", "20180401");
			sqlQueryValuesMap.put("Premium_Adjustment_Period_End_Date", "20180430");
			sqlQueryValuesMap.put("Number_of_Months_in_Premium_Adjustment_Period", "01");
			sqlQueryValuesMap.put("LEP_Amount", "68.00");
			sqlQueryValuesMap.put("Record_Type_Trailer", "CT3");
			listOfRecords.add(sqlQueryValuesMap);*/
  		  // if db does not have records 
	   	return listOfRecords;

	  }	
	
	private ResultSet getQueryDataFromDB(String query) {

		Statement statement;
		ResultSet resultSet = null;
		
		setUpDBConnection();
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		System.out.println(query);
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		
		try{
		
		  statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		System.out.println(" creation of statement success");
          resultSet = statement.executeQuery(query);
		} catch (SQLException ex) {
  			ex.printStackTrace();
  		}
          return resultSet;
		
	}

	// This is hardcoded columns using index...
	  /*   // for each row 
	  while (resultSet.next()){	
		  System.out.println("Inside ResultSet iteration");
		  		//  String Record_Type_Body=resultSet.getString(11);//Need TO
		  		String Record_Type_Header=resultSet.getString(1);
		  		String Contract_Number_Header=resultSet.getString(1);
		  		 String Payment_Date=resultSet.getString(1);
		  	      String Contract_Number_Body=resultSet.getString(8);
				  String PBP_Number=resultSet.getString(9);
				  String HIC_Number=resultSet.getString(11);
				  String Premium_Adjustment_Period_Start_Date=resultSet.getString(16);
				  String Premium_Adjustment_Period_End_Date=resultSet.getString(17);
				//  String Number_of_Months_in_Premium_Adjustment_Period=resultSet.getString(11);//Need TO
				  String LEP_Amount=resultSet.getString(20);
				  String Record_Type_Trailer=resultSet.getString(1);
				  
			//	  queryDataMap.put("Record_Type_Header",Record_Type_Header);
			//	  queryDataMap.put("Contract_Number_Header",Contract_Number_Header);
			//	  queryDataMap.put("Payment_Date",Payment_Date);
			//	  queryDataMap.put("Record_Type_Body"," ");
				  queryDataMap.put("Contract_Number_Body",Contract_Number_Body);
				  queryDataMap.put("PBP_Number",PBP_Number);
				  queryDataMap.put("HIC_Number",HIC_Number);
			//	  queryDataMap.put("Premium_Adjustment_Period_Start_Date",Premium_Adjustment_Period_Start_Date);
			//	  queryDataMap.put("Premium_Adjustment_Period_End_Date",Premium_Adjustment_Period_End_Date);
			//	  queryDataMap.put("Number_of_Months_in_Premium_Adjustment_Period"," ");
				  queryDataMap.put("LEP_Amount",LEP_Amount);
			//	  queryDataMap.put("Record_Type_Trailer",Record_Type_Trailer);
				  
		 
		      
	 	} // while (resultSet.next()){
   			
    	*/
	

	public String[] resultSetToDelimitedArray(String query) throws SQLException{
		
		String[] tableArray = null;
		String[] emptyTableArray = null;
		Boolean emptyResultSet = true;
		Statement statement;
		ResultSet resultSet = null;
		Map<String,String> queryDataMap=new HashMap<String,String>();
		setUpDBConnection();
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		System.out.println(query);
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
		String backupColumnName = null;
		
		try {
		
		  statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		System.out.println(" creation of statement success");
          resultSet = statement.executeQuery(query);
          System.out.println("_____________________________________________________________________");
		System.out.println("Query execuited and  Resultset :  "+resultSet);
		System.out.println("_____________________________________________________________________");  
		ResultSetMetaData metaData = resultSet.getMetaData();
          System.out.println("Resultset Metadata:  "+metaData);
          System.out.println("_____________________________________________________________________");
          int columns = metaData.getColumnCount();  
           System.out.println("No of Columns" + columns); 
          for(int i=1;i<columns;i++){
        	  //System.out.println("column numner:: "+i);
        	  System.out.println("Column Name: "+metaData.getColumnName(i));
          }
          System.out.println("_____________________________________________________________________");
          /*backupColumnName = metaData.getColumnName(1).toString();
          
          emptyTableArray = new String[columns];
          
          emptyTableArray[0] = backupColumnName + "#" + "BLANK";
              
          tableArray = new String[columns];*/
          
          // for each row 
		  while (resultSet.next()){	
			  System.out.println("Inside ResultSet iteration");
			  		//  String Record_Type_Body=resultSet.getString(11);//Need TO
			  		String Record_Type_Header=resultSet.getString(1);
			  		String Contract_Number_Header=resultSet.getString(1);
			  		 String Payment_Date=resultSet.getString(1);
			  	      String Contract_Number_Body=resultSet.getString(8);
					  String PBP_Number=resultSet.getString(9);
					  String HIC_Number=resultSet.getString(11);
					  String Premium_Adjustment_Period_Start_Date=resultSet.getString(16);
					  String Premium_Adjustment_Period_End_Date=resultSet.getString(17);
					//  String Number_of_Months_in_Premium_Adjustment_Period=resultSet.getString(11);//Need TO
					  String LEP_Amount=resultSet.getString(20);
					  String Record_Type_Trailer=resultSet.getString(1);
					  
					  queryDataMap.put("Record_Type_Header",Record_Type_Header);
					  queryDataMap.put("Contract_Number_Header",Contract_Number_Header);
					  queryDataMap.put("Payment_Date",Payment_Date);
					  queryDataMap.put("Record_Type_Body"," ");
					  queryDataMap.put("Contract_Number_Body",Contract_Number_Body);
					  queryDataMap.put("PBP_Number",PBP_Number);
					  queryDataMap.put("HIC_Number",HIC_Number);
					  queryDataMap.put("Premium_Adjustment_Period_Start_Date",Premium_Adjustment_Period_Start_Date);
					  queryDataMap.put("Premium_Adjustment_Period_End_Date",Premium_Adjustment_Period_End_Date);
					  queryDataMap.put("Number_of_Months_in_Premium_Adjustment_Period"," ");
					  queryDataMap.put("LEP_Amount",LEP_Amount);
					  queryDataMap.put("Record_Type_Trailer",Record_Type_Trailer);
					  
			  //System.out.println(resultSet);
			     // for each column in db resylts set 
			   /*  for(int i=1; i<=columns; ++i){ 

			    	 emptyResultSet = false;
			         tableArray[(i-1)]= metaData.getColumnName(i).toString() + "#" + resultSet.getObject(i).toString();
			     
		         }*/
			     
			      
		 	} // while (resultSet.next()){
	     			
          	
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		  // if db does not have records 
	   	if (!emptyResultSet){
	     		
	     	System.out.println("Record(s) Exist");
	    	teardownDBConnection();
	    	return tableArray;
	     	
	     }else{

	     	System.out.println("Record(s) Does Not Exist");
	     	teardownDBConnection();
	    	return emptyTableArray;
	     		     	
	     	}

	  }	
	
	
public String[] getDelimitedArrayFromDB(String query, String strUniqueIDValueOne, String strUniqueIDValueTwo) {		
		
		String  strUpdatedSQL;
		String[] returnArray = null;

		if (strUniqueIDValueOne == null || strUniqueIDValueOne.isEmpty()) {
			// run query without any replacements in it 
			try {
				returnArray = resultSetToDelimitedArray(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			} else {

				// replace uniqueIDValue. Please note that query should have STR_VAR_UNIQUE_ID_VALUE item in it
				strUpdatedSQL = query.replace("STR_VAR_UNIQUE_ID_VALUE_ONE",strUniqueIDValueOne.trim());
               
				// if secondary unique id is blank 
				if (strUniqueIDValueTwo == null || strUniqueIDValueTwo.isEmpty()) {
					
					// run query dont update unique id value STR_VAR_UNIQUE_ID_VALUE_TWO
					try {
						returnArray = resultSetToDelimitedArray(strUpdatedSQL);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				  }else{
					
					// replace uniqueIDValueTwo. Please note that query should have STR_VAR_UNIQUE_ID_VALUE_Two item in it
					 strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_TWO",strUniqueIDValueTwo.trim());
					////01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script
					
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					 // System.out.println(strUpdatedSQL);
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
					  
					 // run query
					 
					 try {
						returnArray = resultSetToDelimitedArray(strUpdatedSQL);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
	
		return returnArray;

	}
	

public String[] getDelimitedArrayFromDB(String query, String strUniqueIDValueOne, String strUniqueIDValueTwo, String strUniqueIDValueThree) {		
	
	String  strUpdatedSQL;
	String[] returnArray = null;

	if (strUniqueIDValueOne == null || strUniqueIDValueOne.isEmpty()) {
		// run query without any replacements in it 
		try {
			returnArray = resultSetToDelimitedArray(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		} else {

			// replace uniqueIDValue. Please note that query should have STR_VAR_UNIQUE_ID_VALUE item in it
			strUpdatedSQL = query.replace("STR_VAR_UNIQUE_ID_VALUE_ONE",strUniqueIDValueOne.trim());
           
			// if secondary unique id is blank 
			if (strUniqueIDValueTwo == null || strUniqueIDValueTwo.isEmpty()) {
				
				// run query dont update unique id value STR_VAR_UNIQUE_ID_VALUE_TWO
				try {
					returnArray = resultSetToDelimitedArray(strUpdatedSQL);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			  }else{
	
				// if secondary unique id is blank 
			 if (strUniqueIDValueThree == null || strUniqueIDValueThree.isEmpty()) {
					
					// replace uniqueIDValueTwo. Please note that query should have STR_VAR_UNIQUE_ID_VALUE_Two item in it
					 strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_TWO",strUniqueIDValueTwo.trim());
					////01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script
					
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					 // System.out.println(strUpdatedSQL);
					 // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
					 try {
							returnArray = resultSetToDelimitedArray(strUpdatedSQL);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
				
				}else{
					
					strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_TWO",strUniqueIDValueTwo.trim());
					strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_THREE",strUniqueIDValueThree.trim());
					
					 try {
							returnArray = resultSetToDelimitedArray(strUpdatedSQL);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
				
				}
				 
				
			}
			
			
		}

	return returnArray;

}
	
	
	
	
	/**
	 * Execute a query and get a getUniqueResultSetToArrayList of hashMap 
	 * @param query, uniqueid
	 * @return ArrayList(Array<hashMap>)
	 * Example calling: getUniqueResultSetToArrayList(query, strUniqueIDValue) 
	 * Or
	 * Example calling: getUniqueResultSetToArrayList(query, null, null) 
	 */
	public ArrayList<Map<String, Object>>getUniqueResultSetToArrayList(String query, String strUniqueIDValue, String strUniqueIDValueTwo) {
        
		String strUpdatedSQL; 
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if (strUniqueIDValue == null || strUniqueIDValue.isEmpty()) {
			// run query without any replacements in it 
			try {
				list = resultSetToArrayList(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			} else {

				// replace uniqueIDValue. Please note that query should have STR_VAR_UNIQUE_ID_VALUE item in it
				strUpdatedSQL = query.replace("STR_VAR_UNIQUE_ID_VALUE_ONE",strUniqueIDValue);
               
				// if secondary unique id is blank 
				if (strUniqueIDValueTwo == null || strUniqueIDValueTwo.isEmpty()) {
					
					// run query dont update unique id value STR_VAR_UNIQUE_ID_VALUE_TWO
				   try {
					   
					System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
					System.out.println("Running Query:");
					System.out.println(strUpdatedSQL);
					System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");   
					
					list = resultSetToArrayList(strUpdatedSQL);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				  }else{
					  

					
					// replace uniqueIDValueTwo. Please note that query should have STR_VAR_UNIQUE_ID_VALUE_Two item in it
					 strUpdatedSQL = strUpdatedSQL.replace("STR_VAR_UNIQUE_ID_VALUE_TWO",strUniqueIDValueTwo);
					////01/16/2018 - Serhiy Commenting out the print statement to minimize console output clutter .Enable back when debugging script
						
					  
					 // run query
				      try {
				    	  
						System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
						System.out.println("Running Query:");
						System.out.println(strUpdatedSQL);
						System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");    
				    	  
						list = resultSetToArrayList(strUpdatedSQL);
						
					} catch (SQLException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
	
		return list;
	
	}
}
