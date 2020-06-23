//package com.changgou.backend.utils;
//
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class POIUtil {
//    private final static String xls = "xls";
//    private final static String xlsx = "xlsx";
//    private final static String DATE_FORMAT = "yyyy/MM/dd";
//
//    // 导出
//    public static void eport(HttpServletRequest request, HttpServletResponse response, String templateName,List list,Integer num) {
//        try {
//            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + templateName;
//            //基于提供的Excel模板文件在内存中创建一个Excel表格对象
//            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));
//            //读取第一个工作表
//            XSSFSheet sheet = excel.getSheetAt(0);
//
//
//            //写入的行数
//            int size = list.size();
//            for (int i = 0; i < size; i++) {
//                //从第二行开始
//                XSSFRow row = sheet.getRow(i+2);
//                for (int j = 0; j < num; j++) {
//                    row.getCell(j).setCellValue();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
