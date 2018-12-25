package com.zl.uncompress.utils;

import android.util.Log;


import com.zl.uncompress.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static android.content.ContentValues.TAG;
import static net.lingala.zip4j.util.InternalZipConstants.BUFF_SIZE;

/**
 * 项目名称：ArchvierSample
 * 类描述：
 * 创建人：zhanglin
 * 创建时间：2018/12/17 10:40
 * 修改人：Administrator
 * 修改时间：2018/12/17 10:40
 * 修改备注：
 */
public class UnZipUtils {
    /**
     * 解压文件
     *
     * @param unZipPath 解压后的目录
     * @param zipPath   压缩文件目录
     * @return 成功返回 true，否则 false
     */
    public static String unZipFile(String unZipPath, String zipPath) {
        unZipPath = createSeparator(unZipPath);
        BufferedOutputStream bos = null;
        ZipInputStream zis = null;

        String result ;

        try {
            String filename;
            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipPath)));
            ZipEntry ze;
            byte[] buffer = new byte[BUFF_SIZE];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "ze.getName() = " + filename);
                }
                createSubFolders(filename, unZipPath);
                if (ze.isDirectory()) {
                    File fmd = new File(unZipPath + filename);
                    fmd.mkdirs();
                    continue;
                }
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "unzip file = " + unZipPath + filename);
                }
                bos = new BufferedOutputStream(new FileOutputStream(unZipPath + filename));
                while ((count = zis.read(buffer)) != -1) {
                    bos.write(buffer, 0, count);
                }
                bos.flush();
                bos.close();
            }
            result = "解压完成";
        } catch (IOException e) {
            e.printStackTrace();
            result = "解压失败";
        } finally {
            try {
                if (zis != null) {
                    zis.closeEntry();
                    zis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    private static void createSubFolders(String filename, String path) {
        String[] subFolders = filename.split("/");
        if (subFolders.length <= 1) {
            return;
        }

        String pathNow = path;
        for (int i = 0; i < subFolders.length - 1; ++i) {
            pathNow = pathNow + subFolders[i] + "/";
            File fmd = new File(pathNow);
            if (fmd.exists()) {
                continue;
            }
            fmd.mkdirs();
        }
    }

    private static String createSeparator(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (path.endsWith("/")) {
            return path;
        }
        return path + '/';
    }
}
