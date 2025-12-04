package common_functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelManager {
	private static XSSFWorkbook workbook;
	public static final String FILE_PATH = new File("src/test/resources/Test.xlsx").getAbsolutePath();

	private ExcelManager() {
	}

	/*********************
	 * Gets workbook object.
	 ********************/
	public static XSSFWorkbook getWorkbook() throws IOException {
		FileInputStream fis = new FileInputStream(new File(FILE_PATH));
		workbook = new XSSFWorkbook(fis);
		return workbook;
	}

	/*********************
	 * Close the workbook and release memory.
	 ********************/
	public static void closeWorkbook() throws IOException {
		try {
			if (workbook != null) {
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			workbook = null;
		}
	}

	/******************
	 * Update the workbook with required value
	 ******************/
	public static void UpdatePostETLUpdate(String Sheetname, String AttributetoUpdate, String Columnname,String Updateval) throws IOException {
		XSSFWorkbook wb = ExcelManager.getWorkbook();
		XSSFSheet sheet = wb.getSheet(Sheetname);

		if (sheet == null) {
			System.out.println("Sheet '" + Sheetname + "' not found.");
			return;
		}
		int headerrow = 0;
		int AttributesColumn = 0;
		int columntoUpdate = 0;
		switch (Columnname) {
		case "Pre_ETLUpdate":
			columntoUpdate = 1;
			break;
		case "Post_ETLUpdate":
			columntoUpdate = 2;
			break;
		default:
			System.out.println("Unknown column");
		}

		// Handle blank Updateval only for Pre_ETLUpdate
		if (Updateval == null || Updateval.trim().isEmpty()) {
			Updateval = "Empty";
		}

		XSSFRow header = sheet.getRow(headerrow);
		if (header == null) {
			System.out.println("Header row is missing.");
			return;
		}

		int lastRow = sheet.getLastRowNum();
		boolean updated = false;

		for (int i = 0; i <= lastRow; i++) {
			XSSFRow Row = sheet.getRow(i);

			if (Row != null) {
				Object attrCell = Row.getCell(AttributesColumn);
				String attributecell = (attrCell != null) ? attrCell.toString().trim() : "";

				if (attributecell.equalsIgnoreCase(AttributetoUpdate)) {
					XSSFCell postETLCell = Row.getCell(columntoUpdate);
					postETLCell.setCellValue(Updateval);
					System.out.println("Updated '" + AttributetoUpdate + "' in row " + (i + 1)
							+ " with Pre_ETLUpdate = " + Updateval);
					updated = true;
					break;
				}
			}
		}
		if (!updated) {
			System.out.println("Attribute '" + AttributetoUpdate + "' not found in sheet.");
		} else {
			ExcelManager.saveWorkbook();
		}
	}

	/******************
	 * Saving workbook
	 ******************/
	public static void saveWorkbook() {
		if (workbook != null) {
			try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
				workbook.write(fos);
				System.out.println("Workbook saved to: " + FILE_PATH);
			} catch (IOException e) {
				System.err.println("Error saving workbook: " + e.getMessage());
			}
		} else {
			System.out.println("No workbook loaded to save.");
		}
	}

	/******************************************************
	 * To get all the attributes from sheetname and returns as list
	 ******************************************************/
	public static List<String> GetAttributes_Col_Values(String Sheetname) throws IOException {
		XSSFWorkbook wb = ExcelManager.getWorkbook();
		XSSFSheet sheet = wb.getSheet(Sheetname);

		List<String> attributeValues = new ArrayList<>();

		int lastRow = sheet.getLastRowNum();

		for (int i = 1; i <= lastRow; i++) { // assuming row 0 is header
			XSSFRow row = sheet.getRow(i);
			if (row != null) {
				XSSFCell valueCell = row.getCell(0);
				String Attribvalue = (valueCell != null) ? valueCell.toString().trim() : "";
				attributeValues.add(Attribvalue);
			}
		}
		return attributeValues;
	}

}
