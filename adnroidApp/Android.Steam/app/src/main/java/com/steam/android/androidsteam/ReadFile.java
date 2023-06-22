package com.steam.android.androidsteam;

/**
 * Created by beicky on 18/4/16.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public static void test(String fileDir) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(fileDir);
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        System.out.println("进入线程");
        if (files == null) {// 如果目录为空，直接退出
            System.out.println("没有文件" + files);
            return;
        }
        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
                test(f.getAbsolutePath());
            }
        }
        for (File f1 : fileList) {

            System.out.println(f1.getName());
        }
    }

    public static void main(String[] args) {
        test("F:/apache-tomcat-7.0.57-windows-x64");
    }
}
