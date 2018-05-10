package Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class MyExcelWriter {


    Workbook wb = new HSSFWorkbook();

    private static String fileSeparator = System.getProperty("file.separator");
    private static String dir = System.getProperty("user.dir");
    private static String testDataDir = dir + fileSeparator + "test-data";

    public File writeToExcelFile(HashMap<String, HashMap<String, Integer>> hm, String fileName) {

        Iterator<String> it1 = hm.keySet().iterator();
        while (it1.hasNext()) {
            String sheetName = it1.next();
            HashMap<String, Integer> sheetData = hm.get(sheetName);
            HashMap<String, Integer> sortedDataSheet = sortHashMapByValues(sheetData);

            String[] columns = {"Item name", "Price"};

            CreationHelper createHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet(sheetName);//ToDO

            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 14);
            headerFont.setColor(IndexedColors.RED.getIndex());

            // Create a CellStyle with the font
            CellStyle headerCellStyle = wb.createCellStyle();
            headerCellStyle.setFont(headerFont);

            CellStyle itemPriceStyle = wb.createCellStyle();
            itemPriceStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));

            // Create a Row
            Row headerRow = sheet.createRow(0);

            // Creating cells
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Create Other rows and cells
            int rowNum = 1;
            Iterator it2 = sortedDataSheet.keySet().iterator();
            while (it2.hasNext()) {

                String itemName = (String) it2.next();
                int itemPrice = sortedDataSheet.get(itemName);

                Row row = sheet.createRow(rowNum++);
                row.createCell(0)
                        .setCellValue(itemName);

                Cell itemPriceCell = row.createCell(1);
                itemPriceCell.setCellValue(itemPrice);
                itemPriceCell.setCellStyle(itemPriceStyle);
            }

            // Resize all columns to fit the content size
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

        }

        // Write the output to a file

        String fp = testDataDir + fileSeparator + fileName+".xls";
        File xlsFile = new File(fp);
        FileOutputStream fileOut;
        try{
            fileOut = new FileOutputStream(xlsFile);
            wb.write(fileOut);
            fileOut.close();
            wb.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return xlsFile;
    }

    public static LinkedHashMap<String, Integer> sortHashMapByValues(
            HashMap<String, Integer> passedMap) {
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
