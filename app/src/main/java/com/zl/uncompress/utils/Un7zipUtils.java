package com.zl.uncompress.utils;

import android.util.Log;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 项目名称：ArchvierSample
 * 类描述： 解压.7z压缩文件
 * 创建人：zhanglin
 * 创建时间：2018/12/17 10:13
 * 修改人：Administrator
 * 修改时间：2018/12/17 10:13
 * 修改备注：
 */
public class Un7zipUtils {
    /**
     * 解压7z文件
     *
     * @param orgPath 源压缩文件地址
     * @param tarPath 解压后的文件存放历史
     * @throws IOException
     */
    public static String apacheUn7Z(String orgPath, String tarPath) {
        try {
            BufferedOutputStream os = null;
            SevenZFile sevenZFile = new SevenZFile(new File(orgPath));
            int offset = 0;
            String szName = "";
            while (true) {
                //获取下一个文件
                SevenZArchiveEntry entry = sevenZFile.getNextEntry();
                if (entry == null) {
                    break;
                }
                szName = entry.getName();
                System.out.println("###########name:" + szName);
                if (entry.isDirectory()) {
                    szName = szName.substring(0, szName.length() - 1);
                    System.out.println("###########nameaaaaa:" + szName);
                    File f = new File(tarPath + File.separator + szName);
                    if (!f.exists()) {
                        boolean mkResult = f.mkdirs();
                    }
                } else {
                    File file = new File(tarPath + File.separator + szName);
                    Log.d("LOG_TAG", "upZipFile::file = " + file.getPath());
                    File parent = new File(file.getParent());
                    if (!parent.exists()) {
                        boolean mkP = parent.mkdirs(); //Trace.d(logTag, "upZipFile::mkP = " + mkP);
                    }
//                    try {
                    if (!file.exists()) {
                        boolean fr = file.createNewFile(); // Trace.d(logTag, "upZipFile:: fr = " + fr);
                    }
//                    } catch (IOException newFileException) {
//                        Log.d("LOG_TAG", "upZipFile::createNewFile exception = " + newFileException.toString());
//                        newFileException.printStackTrace();
//                    }
                    os = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] content = new byte[(int) entry.getSize()];
                    int count = 0;
                    while ((count = sevenZFile.read(content, 0, (int) entry.getSize())) > 0) { //System.out.println("count:"+count);
                        os.write(content, 0, count);
                    }
                    //TODO 根据字符串做相应处理
                    content = null;
                }
            }
            sevenZFile.close();
            return "解压完成";
        } catch (Exception e) {
            e.printStackTrace();
            return "解压失败";
        }


    }
}
