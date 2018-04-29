package util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelWorker {
    public static List<List<String>> parse(String filepath) throws InvalidFormatException , IOException {
        Workbook workbook = WorkbookFactory.create(new File(filepath));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        List<List<String>> tableData = new ArrayList<>();
        for(Row row: sheet){
            List<String> tableRow = new ArrayList<>();
            for (Cell cell : row){
                String cellValue = dataFormatter.formatCellValue(cell);
                tableRow.add(cellValue);
            }
            tableData.add(tableRow);
        }
        workbook.close();
        return tableData;
    }
    public static void createFile(List<List<String>> tableData, String filePath, List<List<Integer>> valuesToHighlightInColumns){

        Workbook workbook = new HSSFWorkbook();
        CreationHelper creationHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Результаты");
        int columnIterator = 0;
        int rowIterator = 0;
        int neededValuesIterator = 0;
        for(List<String> stringList : tableData){
            Row row = sheet.createRow(rowIterator);
            columnIterator = 0;
            for(String s : stringList){
                Cell cell = row.createCell(columnIterator);
                cell.setCellValue(s);
                columnIterator++;
            }
            rowIterator++;
        }

        for(int j = 0; j < tableData.get(0).size();j++) {
            for (int i = 0; i < tableData.size(); i++) {
                if (i != 0 && j != 0 && neededValuesIterator != valuesToHighlightInColumns.get(j-1).size())
                    if (i == valuesToHighlightInColumns.get(j - 1).get(neededValuesIterator) + 1) {
                        Cell cell = sheet.getRow(i).getCell(j);
                        HSSFCellStyle cellStyle = ((HSSFWorkbook) workbook).createCellStyle();
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cellStyle.setFillForegroundColor(HSSFColor.RED.index);
                        cell.setCellStyle(cellStyle);
                        neededValuesIterator++;
                    }
            }
            neededValuesIterator = 0;
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
