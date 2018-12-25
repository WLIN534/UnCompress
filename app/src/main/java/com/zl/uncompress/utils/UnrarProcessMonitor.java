package com.zl.uncompress.utils;

import com.github.junrar.UnrarCallback;
import com.github.junrar.Volume;
import com.github.junrar.impl.FileVolume;

import java.io.IOException;


/**
 * 项目名称：ArchvierSample
 * 类描述：
 * 创建人：zhanglin
 * 创建时间：2018/12/18 14:06
 * 修改人：Administrator
 * 修改时间：2018/12/18 14:06
 * 修改备注：
 */
public class UnrarProcessMonitor implements UnrarCallback {
    private String fileName;

    public UnrarProcessMonitor(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 返回false的话，对于某些分包的rar是没办法解压正确的
     * */
    @Override
    public boolean isNextVolumeReady(Volume volume) {
        try {
            fileName = ((FileVolume) volume).getFile().getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void volumeProgressChanged(long l, long l1) {
        //输出进度
        System.out.println("Unrar "+fileName+" rate: "+(double)l/l1*100+"%");
    }
}
