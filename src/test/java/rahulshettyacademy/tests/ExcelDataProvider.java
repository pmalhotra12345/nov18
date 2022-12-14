package rahulshettyacademy.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
public class ExcelDataProvider {
 
  	WebDriver driver;
  	@BeforeMethod
  	  public void setUp() {
           	  System.out.println("Start test");
        	  System.setProperty("webdriver.chrome.driver", "C:\\DRIVERS\\chromedriver.exe");
       	  driver = new ChromeDriver();
        	  
//        		WebDriverManager.chromedriver().setup();
//        		WebDriver driver = new ChromeDriver();
//        		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        		driver.manage().window().maximize();
//        		
        		
        	  String url = "https://www.google.com";
        	  driver.get(url);
        	  driver.manage().window().maximize();
        	 
  	  }
  	
  	@DataProvider(name ="excel-data")
  	public Object[][] excelDP() throws IOException{
        	//We are creating an object from the excel sheet data by calling a method that reads data from the excel stored locally in our system
        	Object[][] arrObj = getExcelData(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//ExcelData.xlsx","TestData");
        	return arrObj;
  	}
  	//This method handles the excel - opens it and reads the data from the respective cells using a for-loop & returns it in the form of a string array
  	public String[][] getExcelData(String fileName, String sheetName){
        	
        String[][] data = null;   	
  	  	try
  	  	{
  	   	FileInputStream fis = new FileInputStream(fileName);
  	   	XSSFWorkbook wb = new XSSFWorkbook(fis);
  	   	XSSFSheet sh = wb.getSheet(sheetName);
  	   	XSSFRow row = sh.getRow(0);
  	   	int noOfRows = sh.getPhysicalNumberOfRows();
  	   	int noOfCols = row.getLastCellNum();
  	   	Cell cell;
  	   	data = new String[noOfRows-1][noOfCols];
  	   	
  	   	for(int i =1; i<noOfRows;i++){
  		     for(int j=0;j<noOfCols;j++){
  		    	   row = sh.getRow(i);
  		    	   cell= row.getCell(j);
  		    	   data[i-1][j] = cell.getStringCellValue();
  	   	 	   }
  	   	}
  	  	}
  	  	catch (Exception e) {
  	     	   System.out.println("The exception is: " +e.getMessage());
           	}
        	return data;
  	}
  	@Test(dataProvider ="excel-data")
  	public void search(String keyWord1, String keyWord2){
        	WebElement txtBox = driver.findElement(By.xpath("//input[@class='gLFyf gsfi']"));
        	  txtBox.sendKeys(keyWord1," ",keyWord2);
        	  Reporter.log("Keyword entered is : " +keyWord1+ " " +keyWord2);
        	  txtBox.sendKeys(Keys.ENTER);
        	  Reporter.log("Search results are displayed.");
  	}
  	@AfterMethod
  	public void burnDown(){
        	driver.quit();
  	} 	
}