package com.zero.library.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zero.library.Library;

import java.io.File;
import java.io.IOException;

/**
 * create by szl on 2017/8/18
 */

public class SdCard {
    /**
     * 判断SD卡是否存在
     * @return
     */
    public static boolean getSDState() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 删除文件夹下的所有文件
     * @param file
     */
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

    /**
     * 创建文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File createSDFile(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        return file;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteSDFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static File createSDDir(String path) {
        File dir = new File(path);
        boolean result = dir.mkdirs();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹(文件)是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * sdcard
     *
     * @return
     */
    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    /**
     * sdcard/package/childFile
     * @param childFile
     * @return
     */
    public static String getExternalAppPath(String childFile) {
        String externalPath = getSDPath() + Library.CONTEXT.getPackageName() + File.separator + (TextUtils.isEmpty(childFile) ? "" : childFile ) + File.separator;
        if (!isFileExists(externalPath))
            createSDDir(externalPath);
        showPathTag(externalPath);
        return externalPath;
    }

    private static void showPathTag(String path) {
        Log.i("aiya", path);
    }
}
