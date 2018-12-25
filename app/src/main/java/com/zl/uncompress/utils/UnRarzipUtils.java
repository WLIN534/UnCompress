package com.zl.uncompress.utils;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 项目名称：ArchvierSample
 * 类描述：rar解压
 * jar:下载地址：http://mvnrepository.com/artifact/com.github.junrar/junrar
 * 存在问题: junrar只支持PC上WinRAR5.0以下版本压缩生成的文件，WinRAR5.0压缩出来的文件格式其实是rar5格式，是解压不出来的。
 * WinRAR5.0以下版本压缩生成的文件是rar4,可以解压出来。
 * 创建人：zhanglin
 * 创建时间：2018/12/17 10:16
 * 修改人：Administrator
 * 修改时间：2018/12/17 10:16
 * 修改备注：
 */
public class UnRarzipUtils {

    /**
     * @param rarFileName rar file name rar源文件地址
     * @param outFilePath output file path 解压后的文件存放地址
     * @return success Or Failed
     * @throws Exception
     * @author zhanglin
     */
    public static String unRarFile(String rarFileName, String outFilePath) throws Exception {

        try {
            Archive archive = new Archive(new File(rarFileName), new UnrarProcessMonitor(rarFileName));
            if (archive == null) {
                throw new FileNotFoundException(rarFileName + " NOT FOUND!");
            }
            if (archive.isEncrypted()) {
                throw new Exception(rarFileName + " IS ENCRYPTED!");
            }
            List<FileHeader> files = archive.getFileHeaders();
            for (FileHeader fh : files) {
                if (fh.isEncrypted()) {
                    throw new Exception(rarFileName + " IS ENCRYPTED!");
                }
                String fileName = fh.getFileNameW();
                if (fileName != null && fileName.trim().length() > 0) {
                    String saveFileName = outFilePath + File.separator + fileName;
                    File saveFile = new File(saveFileName);
                    File parent = saveFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    if (!saveFile.exists()) {
                        saveFile.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    try {
                        archive.extractFile(fh, fos);
                        fos.flush();
                        fos.close();
                    } catch (RarException e) {
                        if (e.getType().equals(RarException.RarExceptionType.notImplementedYet)) {
                        }
                    } finally {
                    }
                }
            }
            return "解压完成";
        } catch (Exception e) {
            if (e instanceof RarException) {
                return "不支持rar文档解压";
            } else {
                return "解压失败";
            }

        }
    }
}
