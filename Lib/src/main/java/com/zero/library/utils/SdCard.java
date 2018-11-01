package com.zero.library.utils;

import android.os.Environment;

import java.io.File;

/**
 * create by szl on 2017/8/18
 */

public class SdCard {

    public static boolean getSDState() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {

        }
    }

}
