package common;

import com.bj58.spat.scf.client.SCFInit;
import com.bj58.spat.scf.client.proxy.builder.ProxyFactory;
import com.bj58.xxzl.teemodfp.contract.ITeemoAppCommonService;
import com.bj58.xxzl.teemodfp.contract.ITeemoAppSpecialService;
import com.bj58.xxzl.teemodfp.entity.AppCommonBean;
import com.bj58.xxzl.teemodfp.entity.AppSpecialBean;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class saveAppBatch {

    public static ITeemoAppCommonService APP_COMMON_SERVICE = ProxyFactory.create(ITeemoAppCommonService.class, "tcp://teemodb/TeemoAppCommonService");
    public static ITeemoAppSpecialService APP_SPECIAL_SERVICE = ProxyFactory.create(ITeemoAppSpecialService.class, "tcp://teemodb/TeemoAppSpecailService");

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

    public static void save(Map<Integer, List<String>> vals){
        vals.forEach((row, colVals) -> {
            System.out.println("行" + row + "的数据保存开始");
            if (colVals.size() != 7) {
                System.out.println("行" + row + "的数据不对，值为：" + colVals);
            }
            int appType = Integer.valueOf(colVals.get(0).trim());
            String appName = colVals.get(1).trim();
            String pkgName = colVals.get(2).trim();
            String client = colVals.get(3).trim();
            String typeId = colVals.get(4).trim();
            String installCStr = colVals.get(5).trim();
            String desc = colVals.get(6).trim();
            String user = "zhangdanyang02";
            if (appType == 1) {
                //专项类
                AppSpecialBean appSpecialBean = new AppSpecialBean();
                appSpecialBean.setAppName(appName);
                appSpecialBean.setPkgName(pkgName);
                if ("aos".equals(client)) {
                    appSpecialBean.setClientType(2);
                } else if ("ios".equals(client)) {
                    appSpecialBean.setClientType(1);
                }
                appSpecialBean.setTypeId(typeId);
                if (!"".equals(installCStr)) {
                    appSpecialBean.setCidInstallCt(Integer.parseInt(installCStr));
                }
                appSpecialBean.setAppDesc(desc);
                appSpecialBean.setAddTime(new Date());
                appSpecialBean.setOperUser(user);
                appSpecialBean.setStatus(1);
                appSpecialBean.setUpdateTime(new Date());
                appSpecialBean.setTypeSrc(3);
                try {
                    APP_SPECIAL_SERVICE.save(appSpecialBean);
                } catch (Exception e) {
                    System.out.println("行" + row + "的数据保存失败，值为：" + colVals);
                }
            } else if (appType == 2) {
                AppCommonBean appCommonBean = new AppCommonBean();
                appCommonBean.setAppName(appName);
                appCommonBean.setPkgName(pkgName);
                if ("aos".equals(client)) {
                    appCommonBean.setClientType(2);
                } else if ("ios".equals(client)) {
                    appCommonBean.setClientType(1);
                }
                appCommonBean.setTypeId(typeId);
                if (!"".equals(installCStr)) {
                    appCommonBean.setCidInstallCt(Integer.parseInt(installCStr));
                }
                appCommonBean.setAppDesc(desc);
                appCommonBean.setAddTime(new Date());
                appCommonBean.setOperUser(user);
                appCommonBean.setStatus(1);
                appCommonBean.setUpdateTime(new Date());
                appCommonBean.setTypeSrc(3);
                try {
                    APP_COMMON_SERVICE.save(appCommonBean);
                } catch (Exception e) {
                    System.out.println("行" + row + "的数据保存失败，值为：" + colVals);
                }
            }
            System.out.println("行" + row + "的数据保存成功");
        });
    }

    public static void main(String[] args) {
        SCFInit.initScfKeyByValue("ayqGhwiFRHkquhy50nM9OwdJG5tHjgXn");
        String path = "/Users/zhangdanyang/Desktop/文档/项目文档/设备指纹/反作弊/网赚类APP.xlsx";
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

}
