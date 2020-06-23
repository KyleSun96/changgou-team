package com.changgou.file.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.util.FastDFSClient;
import com.changgou.file.util.FastDFSFile;
import com.changgou.util.IdWorker;
import com.changgou.util.ThumbnailatorUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/file")
public class FileController {


    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file){
        try{
            //判断文件是否存在
            if (file == null){
                throw new RuntimeException("文件不存在");
            }
            //获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)){
                throw new RuntimeException("文件不存在");
            }

            //获取文件的扩展名称  abc.jpg   jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            //获取文件内容
            byte[] content = file.getBytes();

            ThumbnailatorUtil tb = new ThumbnailatorUtil();

            //判断是否为图片格式
            if (tb.isImage(extName)) {
                //判断图片大小是否大于1.5M
                if (file.getSize()>1.5*1024*1024){

                    //转成file文件
                    File tmp = File.createTempFile("tmp", null);
                    file.transferTo(tmp);
                    tmp.deleteOnExit();

                    IdWorker idWorker = new IdWorker();

                    String path = "D:\\test\\" + idWorker.nextId() + ".jpg";
                    tb.byFactor(tmp,path);
                    File file1 = new File(path);
                    FileInputStream fileInputStream = new FileInputStream(file1);
                    file = new MockMultipartFile(file1.getName(),file1.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(),fileInputStream);

                    originalFilename = file.getOriginalFilename();

                    content = file.getBytes();



                }
            }


            //创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename,content,extName);

            //基于工具类进行文件上传,并接受返回参数  String[]
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);

            //封装返回结果
            String url = FastDFSClient.getTrackerUrl()+uploadResult[0]+"/"+uploadResult[1];
            return new Result(true,StatusCode.OK,"文件上传成功",url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(false, StatusCode.ERROR,"文件上传失败");
    }


}
