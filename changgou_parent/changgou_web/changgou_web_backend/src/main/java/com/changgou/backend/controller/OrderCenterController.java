package com.changgou.backend.controller;

import com.alibaba.fastjson.JSON;
import com.changgou.entity.Page;
import com.changgou.entity.PageResult;
import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.order.feign.OrderFeign;
import com.changgou.order.pojo.Order;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ordercenter")
public class OrderCenterController {

    @Autowired
    private OrderFeign orderFeign;

    @Value("${templateName}")
    public String templateName;


    @RequestMapping("/toOrderCenter")
    public String toOrderCenter() {
        return "mange";
    }

    @PostMapping("/findPage")
    @ResponseBody
    public Result findPage(@RequestBody Map searchMap) {
        Result<PageResult> result = orderFeign.findPageBackend(searchMap);
        PageResult data = result.getData();
        return new Result(true, StatusCode.OK, "", data);
    }


    @RequestMapping("/export")
    public void eport(HttpServletRequest request, HttpServletResponse response,@RequestBody List<Order> list) {

//        Result<PageResult> page = orderFeign.findPage(map);
//        PageResult pageResult = page.getData();
//        List list = pageResult.getRows();
        try {
            ClassPathResource classPathResource = new ClassPathResource(templateName);
            File file = classPathResource.getFile();

//            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + templateName;
            //基于提供的Excel模板文件在内存中创建一个Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(file));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);


            //写入的行数
            if (list != null && list.size() > 0) {


                int size = list.size();
                for (int i = 0; i < size; i++) {
                    //从第二行开始
//                    XSSFRow row = sheet.getRow(i + 1);
                    XSSFRow row = sheet.createRow(i+1);
//                    String jsonString = JSON.toJSONString(list.get(i));
//                    Order order = JSON.parseObject(jsonString, Order.class);
                    Order order = list.get(i);
                    for (int j = 0; j < 8; j++) {

                        row.createCell(0).setCellValue(order.getId());
                        row.createCell(1).setCellValue(order.getUsername());
                        row.createCell(2).setCellValue(order.getReceiverContact());
                        row.createCell(3).setCellValue(order.getReceiverMobile());
                        row.createCell(4).setCellValue(order.getPayMoney());
                        if (order.getPayType().equals("0") ) {
                            row.createCell(5).setCellValue("货到付款");
                        }
                        if (order.getPayType().equals("1")) {
                            row.createCell(5).setCellValue("在线支付");
                        }

                        if (order.getSourceType().equals("1")) {
                            row.createCell(6).setCellValue("web");
                        }
                        if (order.getSourceType().equals("2")) {
                            row.createCell(6).setCellValue("app");
                        }
                        if (order.getSourceType().equals("3")) {
                            row.createCell(6).setCellValue("微信公众号");
                        }
                        if (order.getSourceType().equals("4")) {
                            row.createCell(6).setCellValue("微信小程序");
                        }
                        if (order.getSourceType().equals("5")) {
                            row.createCell(6).setCellValue("H5手机页面");
                        }
                        if (order.getOrderStatus().equals("0")) {
                            row.createCell(7).setCellValue("待支付");
                        }
                        if (order.getOrderStatus().equals("1")) {
                            row.createCell(7).setCellValue("待发货");
                        }
                        if (order.getOrderStatus().equals("2")) {
                            row.createCell(7).setCellValue("待收货");
                        }
                        if (order.getOrderStatus().equals("3")) {
                            row.createCell(7).setCellValue("已完成");
                        }
                        if (order.getOrderStatus().equals("4")) {
                            row.createCell(7).setCellValue("已关闭");
                        }
                    }

                }
                //使用输出流进行表格下载,基于浏览器作为客户端下载
                OutputStream out = response.getOutputStream();
                response.setContentType("application/vnd.ms-excel");//代表的是Excel文件类型
                response.setHeader("content-Disposition", "attachment;filename=orderList.xlsx");//指定以附件形式进行下载
                excel.write(out);

                out.flush();
                out.close();
                excel.close();
            }
            //return new Result(true,StatusCode.OK,"");
        } catch (IOException e) {
            e.printStackTrace();
           // return null;
        }
    }
}
