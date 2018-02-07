package com.lc.utils.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dicongyan on 2017/7/17.
 *
 */
public class FileOperateUtil {
    /**
     * 复制文件或文件夹
     *
     * @param srcPath
     * @param destDir
     *            目标文件所在的目录
     * @return
     */
    private static Logger logger = LoggerFactory.getLogger(FileOperateUtil.class);

    public static boolean copyGeneralFile(String srcPath, String destDir) {
        boolean flag = false;
        File file = new File(srcPath);
        if (!file.exists()) {
            logger.info("源文件或源文件夹不存在!" + srcPath);
            return false;
        }
        if (file.isFile()) { // 源文件
            flag = copyFile(srcPath, destDir);
        } else if (file.isDirectory()) {
            flag = copyDirectory(srcPath, destDir);
        }

        return flag;
    }

    /**
     * 复制文件
     *
     * @param srcPath
     *            源文件绝对路径
     * @param destDir
     *            目标文件所在目录
     * @return boolean
     */
    private static boolean copyFile(String srcPath, String destDir) {
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) { // 源文件不存在
            logger.info("源文件不存在" + srcPath);
            return false;
        }
        // 获取待复制文件的文件名
        String fileName = srcPath
                .substring(srcPath.lastIndexOf(File.separator));
        String destPath = destDir + fileName;
        if (destPath.equals(srcPath)) { // 源文件路径和目标文件路径重复
            logger.info("源文件路径和目标文件路径重复!");
            return false;
        }
        File destFile = new File(destPath);
        if (destFile.exists() && destFile.isFile()) { // 该路径下已经有一个同名文件,进行覆盖
            deleteGeneralFile(destDir);
        }

        File destFileDir = new File(destDir);
        destFileDir.mkdirs();
        try {
            FileInputStream fis = new FileInputStream(srcPath);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int c;
            while ((c = fis.read(buf)) != -1) {
                fos.write(buf, 0, c);
            }
            fis.close();
            fos.close();

            flag = true;
        } catch (IOException e) {
            //
        }

        return flag;
    }

    /**
     *
     * @param srcPath
     *            源文件夹路径
     *            目标文件夹所在目录
     * @return
     */
    private static boolean copyDirectory(String srcPath, String destDir) {
        boolean flag = false;

        File srcFile = new File(srcPath);
        if (!srcFile.exists()) { // 源文件夹不存在
            logger.info("源文件夹不存在" + srcPath);
            return false;
        }
        /*// 获得待复制的文件夹的名字，比如待复制的文件夹为"E://dir"则获取的名字为"dir"
        String dirName = getDirName(srcPath);
        // 目标文件夹的完整路径
        String destPath = destDir + File.separator + dirName;*/
        String destPath = destDir;
        // logger.info("目标文件夹的完整路径为：" + destPath);

        if (destPath.equals(srcPath)) {
            return false;
        }
        File destDirFile = new File(destPath);
        if (destDirFile.exists()) { // 目标位置有一个同名文件夹
            logger.info("目标位置已有同名文件夹!" + srcPath);
            return false;
        }
        destDirFile.mkdirs(); // 生成目录

        File[] fileList = srcFile.listFiles(); // 获取源文件夹下的子文件和子文件夹
        if (fileList.length == 0) { // 如果源文件夹为空目录则直接设置flag为true，这一步非常隐蔽，debug了很久
            flag = true;
        } else {
            for (File temp : fileList) {
                if (temp.isFile()) { // 文件
                    flag = copyFile(temp.getAbsolutePath(), destPath);
                } else if (temp.isDirectory()) { // 文件夹
                    flag = copyDirectory(temp.getAbsolutePath(), destPath);
                }
                if (!flag) {
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * 获取待复制文件夹的文件夹名
     *
     * @param dir
     * @return String
     */
    private static String getDirName(String dir) {
        if (dir.endsWith(File.separator)) { // 如果文件夹路径以"//"结尾，则先去除末尾的"//"
            dir = dir.substring(0, dir.lastIndexOf(File.separator));
        }
        return dir.substring(dir.lastIndexOf(File.separator) + 1);
    }

    /**
     * 删除文件或文件夹
     *
     * @param path
     *            待删除的文件的绝对路径
     * @return boolean
     */
    public static boolean deleteGeneralFile(String path) {
        boolean flag = false;

        File file = new File(path);
        if (!file.exists()) { // 文件不存在
            logger.info("要删除的文件不存在！" + path);
        }

        if (file.isDirectory()) { // 如果是目录，则单独处理
            flag = deleteDirectory(file.getAbsolutePath());
        } else if (file.isFile()) {
            flag = deleteFile(file);
        }

        return flag;
    }

    /**
     * 删除文件
     *
     * @param file
     * @return boolean
     */
    private static boolean deleteFile(File file) {
        return file.delete();
    }

    /**
     * 删除目录及其下面的所有子文件和子文件夹，注意一个目录下如果还有其他文件或文件夹
     * 则直接调用delete方法是不行的，必须待其子文件和子文件夹完全删除了才能够调用delete
     *
     * @param path
     *            path为该目录的路径
     */
    private static boolean deleteDirectory(String path) {
        boolean flag = true;
        File dirFile = new File(path);
        if (!dirFile.isDirectory()) {
            return flag;
        }
        File[] files = dirFile.listFiles();
        for (File file : files) { // 删除该文件夹下的文件和文件夹
            // Delete file.
            if (file.isFile()) {
                flag = deleteFile(file);
            } else if (file.isDirectory()) {// Delete folder
                flag = deleteDirectory(file.getAbsolutePath());
            }
            if (!flag) { // 只要有一个失败就立刻不再继续
                break;
            }
        }
        flag = dirFile.delete(); // 删除空目录
        return flag;
    }

    /**
     * 由上面方法延伸出剪切方法：复制+删除
     *
     * @param destDir
     *            同上
     */
    public static boolean cutGeneralFile(String srcPath, String destDir) {
        if (!copyGeneralFile(srcPath, destDir)) {
            logger.info("复制失败导致剪切失败!" + srcPath);
            return false;
        }
        if (!deleteGeneralFile(srcPath)) {
            logger.info("删除源文件(文件夹)失败导致剪切失败!" + srcPath);
            return false;
        }

        logger.info("剪切成功!");
        return true;
    }
}
