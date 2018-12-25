# UnCompress
zip、7z、rar 压缩包解压

jar:下载地址：http://mvnrepository.com/artifact/com.github.junrar/junrar
 
注：* 存在问题: junrar只支持PC上WinRAR5.0以下版本压缩生成的文件，WinRAR5.0压缩出来的文件格式其实是rar5格式，是解压不出来的。
 * WinRAR5.0以下版本压缩生成的文件是rar4,可以解压出来。
 
 AndroidManifest.xml里添加权限：
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> 
 <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
