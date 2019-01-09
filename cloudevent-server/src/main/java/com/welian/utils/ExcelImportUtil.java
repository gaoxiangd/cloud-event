package com.welian.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jackhuang on 17/7/26.
 */
public class ExcelImportUtil {

    public static List<Map<String, String>> parseExcel(InputStream fis) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            HSSFWorkbook book = new HSSFWorkbook(fis);
            HSSFSheet sheet = book.getSheetAt(0);
            int firstRow = sheet.getFirstRowNum();
            int lastRow = sheet.getLastRowNum();
            //除去表头和第一行
            for (int i = firstRow + 1; i < lastRow + 1; i++) {
                Map<String, String> map = new HashMap<>();
                HSSFRow row = sheet.getRow(i);
                int firstCell = row.getFirstCellNum() + 1; // 跳过序号
                int lastCell = row.getLastCellNum();
                for (int j = firstCell; j < lastCell; j++) {
                    HSSFCell cell2 = sheet.getRow(i).getCell(j);
                    if (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                    }
                    String value = cell2.getStringCellValue();
                    map.put(String.valueOf(j), value);
                }
//                System.out.println(map);
                data.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
