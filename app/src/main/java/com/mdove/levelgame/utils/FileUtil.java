package com.mdove.levelgame.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import com.mdove.levelgame.utils.log.LogUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
/**
 * Created by MDove on 2018/10/20.
 */
public class FileUtil {

    public static boolean isDirectoryCanRead(String dirPath){
        if(TextUtils.isEmpty(dirPath)) return false;
        File file = new File(dirPath);
        if(!file.isDirectory() || !file.canRead()) return false;
        return true;
    }

    public static boolean isDirectoryCanWrite(String dirPath){
        if(TextUtils.isEmpty(dirPath)) return false;
        File file = new File(dirPath);
        if(!file.isDirectory() || !file.canWrite()) return false;
        return true;
    }

    public static boolean isDirectoryCanReadWrite(String dirPath){
        if(TextUtils.isEmpty(dirPath)) return false;
        File file = new File(dirPath);
        if(!file.isDirectory() || !file.canRead() || !file.canWrite()) return false;
        return true;
    }

    public static boolean isDirectoryExists(String dirPath){
        if(TextUtils.isEmpty(dirPath)) return false;
        File file = new File(dirPath);
        return file.exists();
    }

    public static boolean isFileCanRead(String filePath){
        if(TextUtils.isEmpty(filePath)) return false;
        File file = new File(filePath);
        if(!file.isFile() || !file.canRead()) return false;
        return true;
    }

    public static String getFileName(String filePath){
        if(TextUtils.isEmpty(filePath)) return null;
        File file = new File(filePath);
        return file.getName();
    }

    public static void copyFile(File src, File dst) throws IOException{
        //if(!src.isFile() || !dst.isFile()) return;
        //LogUtil.d("copy begin");
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        byte[] buf = new byte[1024];
        int len;
        while((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static void deleteFile(File file){
        if(file != null && file.exists() && file.isFile()){
            file.delete();
         /*if(file.delete()){
            LogUtil.d("文件已删除：" + file.getName());
         }*/
        }
    }

    public static void deleteDirectoryFiles(File directory){
        if(directory != null && directory.exists() && directory.isDirectory()){
            try{
                for(File child : directory.listFiles()){
                    if(child.isDirectory()){
                        deleteDirectoryFiles(child);
                    }else{
                        child.delete();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static String file2BinaryString(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveBytes(String dirPath, String fileName, byte[] bytes){
        File d = new File(dirPath);
        if(!d.exists()){
            d.mkdirs();
        }
        String jpegName = dirPath + "/" + fileName;
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            bos.write(bytes);
            bos.flush();
            bos.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveBitmap(String dirPath, String fileName, Bitmap b){
        File d = new File(dirPath);
        if(!d.exists()){
            d.mkdirs();
        }
        String jpegName = dirPath + "/" + fileName;
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取assets下的json文件
     */
    public static String loadJsonFromAssets(Context context, String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        String result = "";
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            result = new String(buffer, "utf-8");
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        long sizeFrom = file.length();
      /*DecimalFormat df = new DecimalFormat("#.00");
      String size=df.format((double) sizeFrom / 1048576) + "MB";*/
        return sizeFrom;
    }

    /**
     * 检查文件是否有效
     * @param dst 目标文件
     * @param logClassTag 用于记录日志的tag
     * @return
     */
    public static boolean checkFileValid(File dst, String logClassTag) {
        final String TAG = logClassTag + "-checkFileValid";
        try {
            if (dst == null) {
                LogUtils.e(TAG, "文件不存在");
                return false;
            }
            if (!dst.exists()) {//文件不存在
                LogUtils.e(TAG, "文件不存在：" + dst.getAbsolutePath());
                return false;
            }
            long fileSize = dst.length();
            LogUtils.d(TAG, "文件大小：" + fileSize + " byte，" + dst.getAbsolutePath());
            if (fileSize < 1024) {//文件过小
//          LogUtil.e(TAG, "文件大小异常：" + fileSize + " byte，" + dst.getAbsolutePath());
                return false;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.toString());
            return false;
        }
        return true;
    }

    public static boolean fileExist(String path) {
        if (TextUtils.isEmpty(path)) return false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (file.length() < 1024) {
            return false;
        }
        return true;
    }


    /***
     * 获取人人车相册随机生成一个照片的路径
     * @return
     */
    public static String getRenRenPhotoPath(){
        String dir = Environment.getExternalStorageDirectory() + "/人人车相册";
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File newImageFile = new File(fileDir, UUID.randomUUID().toString() + ".jpg");
        return newImageFile.getPath();
    }

}
