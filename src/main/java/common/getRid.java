package common;

import com.alibaba.fastjson.JSONObject;
import com.bj58.spat.scf.client.SCFInit;
import com.bj58.spat.scf.client.proxy.builder.ProxyFactory;
import com.bj58.xxzl.teemodfp.contract.ITeemoFpService;
import com.bj58.xxzl.teemodfp.entity.TeemoResponse;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class getRid {

    public static ITeemoFpService FP_SERVICE = ProxyFactory.create(ITeemoFpService.class, "tcp://teemodfp/TeemoFpServiceImpl");

    /**
     * @param path    文件路径
     * @param rowNum  从第几行开始读，初始值为0
     * @param columns 需要读取哪几列的值
     * @return key：行_列 value：值
     * @throws Exception 读取以.xlsx结尾的excel,最多65536行
     */
    public static Map<Integer, List<String>> readXlsxExcel(String path, int rowNum, List<Integer> columns) throws Exception {
        System.out.println("开始读取excel");
        Map<Integer, List<String>> resultMap = new HashMap<Integer, List<String>>();
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
                ArrayList<String> colVals = new ArrayList<>();
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
                    colVals.add(value);
                }
                resultMap.put(rowIndex, colVals);
            }
        }
        System.out.println("读取excel完成");
        return resultMap;
    }

    public static void save(Map<Integer, List<String>> vals) {
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        vals.forEach((row, colVals) -> {
            String cid = colVals.get(0).trim();
            System.out.println("行" + row + "的数据保存开始," + "col:" + cid);
            try {
                TeemoResponse teemoResponse = FP_SERVICE.getRidByCid("teemo",cid);//checkCidValid(cid);

                if (teemoResponse.getCode() == 200) {
                    String rid =teemoResponse.getData();
                    if("".equals(rid)){
                        Map<String, String> tempMap = new HashMap<String, String>();
                        tempMap.put("cid",cid);
                        tempMap.put("rid",rid);
                        dataList.add(tempMap);
                    }else {
                        rid = decryptStr(rid, "7F8591BFEDBDB546");
                        System.out.println(cid + "===" + rid);
                        Map<String, String> tempMap = new HashMap<String, String>();
                        tempMap.put("cid", cid);
                        tempMap.put("rid", rid);
                        dataList.add(tempMap);
                    }
////
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            System.out.println("行" + row + "的数据保存成功");
        });
        List<String> columnNames = Arrays.asList("cid","rid");
        ExcelUtil.writeExcel(dataList,columnNames,"/Users/zhangdanyang/Desktop/文档/项目文档/设备指纹/反作弊数据分析/cid-client2.xlsx");
    }

    public static void main(String[] args) {
        SCFInit.initScfKeyByValue("zBbi2/AiI1u6fcf2oM4P+kmsrKP3EAPT");
        String path = "/Users/zhangdanyang/Desktop/文档/项目文档/设备指纹/反作弊数据分析/未关联到的cid1.xlsx";
        int rowNum = 1;
        Integer[] totalCols = new Integer[]{0, 1, 2, 3, 4, 5, 6};
        List<Integer> columns = Arrays.asList(totalCols);
        try {
            Map<Integer, List<String>> vals = readXlsxExcel(path, rowNum, columns);
            save(vals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decryptStr(String target, String salt) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt.getBytes(Charsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] resultBytes = cipher.doFinal(Base64.decodeBase64(target));
            return new String(resultBytes, "UTF-8");
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

}
