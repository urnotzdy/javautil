package common;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 操作excel
 * @Author zhangdanyang02
 * @Date 2019/6/28 10:56
 **/
public class ExcelUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * @param path    文件路径
     * @param rowNum  从第几行开始读，初始值为0
     * @param columns 需要读取哪几列的值
     * @return key：行_列 value：值
     * @throws Exception 读取以.xls结尾的excel,最多65536行
     */
    public static Map<String, String> readXlsExcel(String path, int rowNum, List<Integer> columns) throws Exception {
        System.out.println("开始读取excel");
        Map<String, String> resultMap = new HashMap<String, String>();
        File file = new File(path);
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 获取行
            for (int rowIndex = rowNum; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                //获取列
                for (Integer columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    if (!columns.contains(columnIndex)) {
                        continue;
                    }
                    String value = "";
                    HSSFCell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                //日期和数字格式
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    } else {
                                        value = "";
                                    }
                                } else {
                                    value = NumberToTextConverter.toText(cell.getNumericCellValue());
                                }
                                break;
                            case FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (!cell.getStringCellValue().equals("")) {
                                    value = cell.getStringCellValue();
                                } else {
                                    value = cell.getNumericCellValue() + "";
                                }
                                break;
                            case BLANK:
                                break;
                            case ERROR:
                                value = "";
                                break;
                            case BOOLEAN:
                                value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                                break;
                            default:
                                value = "";
                        }
                    }
                    if (columnIndex == 0 && value.trim().equals("")) {
                        break;
                    }
                    resultMap.put(rowIndex + "_" + columnIndex, value);
                }
            }
        }
        in.close();
        System.out.println("开始读取excel");
        return resultMap;
    }

    /**
     * @param path    文件路径
     * @param rowNum  从第几行开始读，初始值为0
     * @param columns 需要读取哪几列的值
     * @return key：行_列 value：值
     * @throws Exception 读取以.xlsx结尾的excel,最多65536行
     */
    public static Map<String, String> readXlsxExcel(String path, int rowNum, List<Integer> columns) throws Exception {
        System.out.println("开始读取excel");
        Map<String, String> resultMap = new HashMap<String, String>();
        File file = new File(path);
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet st = wb.getSheetAt(sheetIndex);
            // 获取行
            for (int rowIndex = rowNum; rowIndex <= st.getLastRowNum(); rowIndex++) {
                XSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                //获取列
                for (Integer columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    if (!columns.contains(columnIndex)) {
                        continue;
                    }
                    String value = "";
                    XSSFCell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                //日期和数字格式
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    }
                                } else {
                                    value = NumberToTextConverter.toText(cell.getNumericCellValue());
                                }
                                break;
                            case FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (!cell.getStringCellValue().equals("")) {
                                    value = cell.getStringCellValue();
                                } else {
                                    value = cell.getNumericCellValue() + "";
                                }
                                break;
                            case ERROR:
                                value = "";
                                break;
                            case BLANK:
                                break;
                            case BOOLEAN:
                                value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                                break;
                            default:
                                value = "";
                        }
                    }
                    if (columnIndex == 0 && value.trim().equals("")) {
                        break;
                    }
                    resultMap.put(rowIndex + "_" + columnIndex, value);
                }
            }
        }
        System.out.println("读取excel完成");
        return resultMap;
    }

    /**
     * @param dataList    需写入excel的数据
     * @param cloumnNames map中key的名称
     * @param path        写入文件路径
     */
    public static void writeExcel(List<Map<String, String>> dataList, List<String> cloumnNames, String path) {
        OutputStream out = null;
        FileInputStream in = null;
        try {
            File file = new File(path);//读取xlsx文件

            Workbook workBook = null;
            in=new FileInputStream(file);
            if (file.getName().endsWith(EXCEL_XLS)) {     //Excel&nbsp;2003
                workBook = new HSSFWorkbook(in);
            } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
                workBook = new XSSFWorkbook(in);
            }

            Sheet sheet = workBook.getSheetAt(0);
            //删除所有数据，除去标题
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(path);
            workBook.write(out);

            //往Excel中写新数据
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map<String, String> dataMap = dataList.get(j);
                for (int k = 0; k < cloumnNames.size(); k++) {
                    // 在一行内循环
                    Cell cell = row.createCell(k);
                    cell.setCellValue(dataMap.get(cloumnNames.get(k)));
                }
            }

            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out = new FileOutputStream(path);
            workBook.write(out);
            System.out.println("数据导出成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        String path = "D:\\IdeaWorkspace2018\\teemo\\javautil\\src\\main\\resources\\excel\\uid.xlsx";
        int rowNum = 1;
        List<Integer> columns = Arrays.asList(0);
        try {
            Map<String, String> contextMap = readXlsxExcel(path, rowNum, columns);
            Set<String> keys = contextMap.keySet();
            List<String> columnNames = Arrays.asList("uid");
            List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
            for (String key : keys) {
                Map<String, String> tempMap = new HashMap<String, String>();
                tempMap.put("uid", contextMap.get(key));
                dataList.add(tempMap);
            }
            String writePath = "D:\\IdeaWorkspace2018\\teemo\\javautil\\src\\main\\resources\\excel\\write.xlsx";
            writeExcel(dataList, columnNames, writePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
