package com.welian.utils;

import org.sean.framework.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;

/**
 * created by GaoXiang on 2018/6/21
 */
public class POIUtils {
    /**
     * 设置表格行宽高
     * @param sheet sheet对象
     */
    public static void setDefaultHeighAndWidth(HSSFSheet sheet){
        sheet.setDefaultRowHeightInPoints(20);
        sheet.setDefaultColumnWidth(20);
    }

    /**
     * 获取默认字体样式的单元格样式
     * @param wb excel文本
     */
    public static HSSFCellStyle setDefaultFrontCellStyle(HSSFWorkbook wb) {


        HSSFCellStyle cellStyle= wb.createCellStyle();
        HSSFFont fontStyle=wb.createFont();
        //设置字体样式

        fontStyle.setFontName("宋体");

        //设置字体高度

        fontStyle.setFontHeightInPoints((short)20);

        //设置字体颜色

        fontStyle.setColor(HSSFColor.BLUE.index);

        //设置粗体

        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        //设置斜体

        fontStyle.setItalic(true);

        //设置下划线

        fontStyle.setUnderline(HSSFFont.U_SINGLE);

        //字体也是单元格格式的一部分，所以从属于HSSFCellStyle

        // 将字体对象赋值给单元格样式对象
        cellStyle.setFont(fontStyle);

        // 将单元格样式应用于单元格

       return cellStyle;


    }

    /**
     * 检验是否数字类型
     * @param cell 列值
     * @return
     */
    public static Boolean checkNumber(HSSFCell cell){
        if(cell==null){
            return false;
        }
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        if(cell.getStringCellValue().trim().matches("\\d+")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 获取数字类型的值
     * @param cell 列值
     * @return
     */
    public static Integer getNumberValue(HSSFCell cell){
        if(cell==null){
            return 0;
        }
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        if(!cell.getStringCellValue().trim().matches("\\d+")){
            return 0;
        }
        return Integer.parseInt(cell.getStringCellValue().trim());
    }
    public static String getStringCellValue(HSSFCell cell){
        if(cell==null){
            return "";
        }
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }
    /**
     * 检验是否该行是无效行
     * @param row 行值
     * @return true 该行有有效值，false 该行无有效值
     */
    public static boolean checkValidCell(HSSFRow row) {
        int columeNum = row.getPhysicalNumberOfCells();
        for(int i=0;i<columeNum;i++){
            if(row.getCell(i)!=null&&getStringCellValue(row.getCell(i))!=""){
                return true;
            }
        }
        return false;
    }

    /**
     * 把某行数设置单元格格式为文本格式
     * @param wb 文本
     * @param sheet sheet
     * @param startIndex 开始index
     * @param endIndex 结束index
     * @param cellIndex 第几行
     */
    public static void createCellTextFormat(HSSFWorkbook wb,HSSFSheet sheet,Integer startIndex,Integer endIndex,Integer cellIndex) {
        for(int i=startIndex;i<=endIndex;i++){
            HSSFRow rowID = sheet.createRow(i);
            CellStyle style1 = wb.createCellStyle();
            // 设置为文本格式，防止手机号变成科学计数法
            DataFormat format = wb.createDataFormat();
            style1.setDataFormat(format.getFormat("@"));
            rowID.createCell((short) 1).setCellStyle(style1);
        }
    }

    /**
     * 获取有效的行数 （无效行不算）
     * @param sheet 画布
     * @return
     */
    public static int getValidRowNum(HSSFSheet sheet) {
        CellReference cellReference = new CellReference("A4");
        boolean flag ;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
            Row r = sheet.getRow(i);
            if(r == null){
                //如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
                continue;
            }
            flag = false;
            for(Cell c : r){
                if(c.getCellType() != Cell.CELL_TYPE_BLANK){
                    flag = true;
                    break;
                }
            }
            if(flag){
                i++;
                continue;
            }else{//如果是空白行（即可能没有数据，但是有一定格式）
                if(i == sheet.getLastRowNum()){
                    //如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                }else{
                    //如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
                }
            }
        }
        return sheet.getLastRowNum()+1;
    }

    /**
     * 获取某一行有效的列数
     * @param row 行数index
     * @return
     */
    public static int getCelNumByRow(HSSFRow row) {
        Integer validCellNum=0;
        for(int i=0;i<row.getPhysicalNumberOfCells();i++){
            if(row.getCell(i)!=null&& StringUtil.isNotEmpty(getStringCellValue(row.getCell(i)))){
                validCellNum+=1;
            }
        }
        return validCellNum;
    }

    /**
     * empty -> -
     * @param inputString
     * @return
     */
    public static String empty2Line(String inputString) {
        if(StringUtil.isEmpty(inputString)){
            return "-";
        }
        return inputString;
    }
}
