package com.changgou.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ThumbnailatorUtil {

    /* //对原图加水印,然后顺时针旋转90度,最后压缩为80%保存
        generateRotationWatermark();

        //转换图片格式,将流写入到输出流
        generateOutputstream();

        //按比例缩放图片
        generateScale();

        //生成缩略图到指定的目录
        generateThumbnail2Directory();

        //将指定目录下所有图片生成缩略图
        generateDirectoryThumbnail();
    */

    public  void specifySsize(File file, String outFilePath) {
        //使用给定的图片生成指定大小的图片
        generateFixedSizeImage(file,outFilePath);
    }

    public  void byFactor(File file, String outFilePath) {
        //使用给定的图片生成指定大小的图片
        generateScale(file,outFilePath);

    }


    /**
     * 使用给定的图片生成指定大小的图片
     */
    private static void generateFixedSizeImage(File file, String outFilePath){
        try {
            Thumbnails.of(file).size(300,300).toFile(outFilePath);
        } catch (IOException e) {
            System.out.println("原因: " + e.getMessage());
        }
    }

    /**
     * 对原图加水印,然后顺时针旋转90度,最后压缩为80%保存
     */
    private static void generateRotationWatermark(){
        try {
            Thumbnails.of("data/2016010208.jpg").
                    size(160,160). // 缩放大小
                    rotate(90). // 顺时针旋转90度
                    watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("data/newmeinv.jpg")),0.5f). //水印位于右下角,半透明
                    outputQuality(0.8). // 图片压缩80%质量
                    toFile("data/2016010208_new.jpg");
        } catch (IOException e) {
            System.out.println("原因: " + e.getMessage());
        }
    }

    /**
     * 转换图片格式,将流写入到输出流
     */
    private static void generateOutputstream(){
        try(OutputStream outputStream = new FileOutputStream("data/2016010208_outputstream.png")) { //自动关闭流
            Thumbnails.of("data/2016010208.jpg").
                    size(500,500).
                    outputFormat("png"). // 转换格式
                    toOutputStream(outputStream); // 写入输出流
        } catch (IOException e) {
            System.out.println("原因: " + e.getMessage());
        }
    }

    /**
     * 按比例缩放图片
     */
    private static void generateScale(File file, String outFilePath){
        try {
            Thumbnails.of(file).
                    //scalingMode(ScalingMode.BICUBIC).
                            scale(0.4). // 图片缩放80%, 不能和size()一起使用
                    outputQuality(0.4). // 图片质量压缩80%
                    toFile(outFilePath);
        } catch (IOException e) {
            System.out.println("原因: " + e.getMessage());
        }
    }

    /**
     * 生成缩略图到指定的目录
     */
    private static void generateThumbnail2Directory(File file, String outFilePath){
        try {
            Thumbnails.of("data/2016010208.jpg","data/meinv.jpg").
                    //scalingMode(ScalingMode.BICUBIC).
                            scale(0.8). // 图片缩放80%, 不能和size()一起使用
                    toFiles(new File("data/new/"), Rename.NO_CHANGE);//指定的目录一定要存在,否则报错
        } catch (IOException e) {
            System.out.println("原因: " + e.getMessage());
        }
    }

    /**
     * 将指定目录下所有图片生成缩略图
     */
    private static void generateDirectoryThumbnail(){
        try {
            Thumbnails.of(new File("data/new").listFiles()).
                    //scalingMode(ScalingMode.BICUBIC).
                            scale(0.8). // 图片缩放80%, 不能和size()一起使用
                    toFiles(new File("data/new/"), Rename.SUFFIX_HYPHEN_THUMBNAIL);//指定的目录一定要存在,否则报错
        } catch (IOException e) {
            System.out.println("原因: " + e.getMessage());
        }
    }


    /**
     * 根据文件扩展名判断文件是否为图片格式
     * @param fileName
     * @return
     */

    public static String getFileExtention(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension;
    }
    public  boolean isImage(String extension) {
        String[] imageExtension = new String[]{"jpeg", "jpg", "gif", "bmp", "png"};

        for (String e : imageExtension) {
            if (extension.toLowerCase().equals(e)) {
                return true;
            }
        }
        return false;
    }
}
