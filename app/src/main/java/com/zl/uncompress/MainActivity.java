package com.zl.uncompress;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zl.uncompress.utils.Un7zipUtils;
import com.zl.uncompress.utils.UnRarzipUtils;
import com.zl.uncompress.utils.UnZipUtils;

/**
 * 解压zip 、rar、7z
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUnRar, btnUnZip, btnUn7z;

    private String source = "/storage/emulated/0/test1.rar";//解压前zip源文件的地址
    private String destpath = "/storage/emulated/0/testrar";//解压后的文件存放地址
    private String source1 = "/storage/emulated/0/test.7z";//解压前zip源文件的地址
    private String destpath1 = "/storage/emulated/0/test7z";//解压后的文件存放地址
    private String source2 = "/storage/emulated/0/test.zip";//解压前zip源文件的地址
    private String destpath2 = "/storage/emulated/0/testzip";//解压后的文件存放地址

    private static final int UN_RAR_RESULT = 0X01;//解压rar的结果
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UN_RAR_RESULT:
                    Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE",};
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
        btnUn7z = findViewById(R.id.btn_7z);
        btnUnRar = findViewById(R.id.btn_rar);
        btnUnZip = findViewById(R.id.btn_zip);

        btnUn7z.setOnClickListener(this);
        btnUnRar.setOnClickListener(this);
        btnUnZip.setOnClickListener(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_7z:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //解压7z
                            String unMsg = Un7zipUtils.apacheUn7Z(source1, destpath1);
                            Message msg = new Message();
                            msg.what = UN_RAR_RESULT;
                            msg.obj = unMsg;
                            handler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_rar:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //解压rar
                            String unMsg = UnRarzipUtils.unRarFile(source, destpath);
                            Message msg = new Message();
                            msg.what = UN_RAR_RESULT;
                            msg.obj = unMsg;
                            handler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_zip:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //解压zip
                            String unMsg = UnZipUtils.unZipFile(destpath2, source2);
                            Message msg = new Message();
                            msg.what = UN_RAR_RESULT;
                            msg.obj = unMsg;
                            handler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}
