package com.lc.utils.weiChat;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

/**
 * Created by dell on 2017/3/21.
 */
public class FileUtil {


    /** */
    /**
     * 文件转化为字节数组
     *
     * @Author feihaitao
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            for (int n; (n = stream.read(b)) != -1;) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    /** */
    /**
     * 把字节数组保存为一个文件
     *
     * @EditTime 2007-8-13 上午11:45:56
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path){
        File f = new File(path); // 输入要删除的文件位置
        if(f.exists()){
            f.delete();
        }
    }
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
    /** */
    /**
     * 从字节数组获取对象
     *
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:46:34
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    /** */
    /**
     * 从对象获取一个字节数组
     *
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:46:56
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }



    public static void download(String urlString, String filename,String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"/"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    public static void fileCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }
    /**
     * 移动指定文件或文件夹(包括所有文件和子文件夹)
     *

     * @throws Exception
     */
    public static void MoveFolderAndFileWithSelf(String from, String to) throws Exception {
        try {
            File dir = new File(from);
            // 目标
            to +=  File.separator + dir.getName();
            File moveDir = new File(to);
            if(dir.isDirectory()){
                if (!moveDir.exists()) {
                    moveDir.mkdirs();
                }
            }else{
                File tofile = new File(to);
                dir.renameTo(tofile);
                return;
            }

            //System.out.println("dir.isDirectory()"+dir.isDirectory());
            //System.out.println("dir.isFile():"+dir.isFile());

            // 文件一览
            File[] files = dir.listFiles();
            if (files == null)
                return;

            // 文件移动
            for (int i = 0; i < files.length; i++) {
                System.out.println("文件名："+files[i].getName());
                if (files[i].isDirectory()) {
                    MoveFolderAndFileWithSelf(files[i].getPath(), to);
                    // 成功，删除原文件
                    files[i].delete();
                }
                File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());
                // 目标文件夹下存在的话，删除
                if (moveFile.exists()) {
                    moveFile.delete();
                }
                files[i].renameTo(moveFile);
            }
            dir.delete();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 复制单个文件(可更名复制)
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名(注：目录路径需带文件名)
     * @return
     */
    public static void CopySingleFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制单个文件(原名复制)
     * @param oldPathFile 准备复制的文件源
     * @param targetPath 拷贝到新绝对路径带文件名(注：目录路径需带文件名)
     * @return
     */
    public static void CopySingleFileTo(String oldPathFile, String targetPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            String targetfile = targetPath + File.separator +  oldfile.getName();
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(targetfile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹的内容(含自身)
     * @param oldPath 准备拷贝的目录
     * @param newPath 指定绝对路径的新目录
     * @return
     */
    public static void copyFolderWithSelf(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File dir = new File(oldPath);
            // 目标
            newPath +=  File.separator + dir.getName();
            File moveDir = new File(newPath);
            if(dir.isDirectory()){
                if (!moveDir.exists()) {
                    moveDir.mkdirs();
                }
            }
            String[] file = dir.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath +
                            "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) { //如果是子文件夹
                    copyFolderWithSelf(oldPath + "/" + file[i], newPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *  If a deletion fails, the method stops attempting to delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static void main(String[] args) {
        try {
            System.out.println(encodeBase64File("D://20160506150937.jpg"));
        }catch (Exception e){

        }


    }
}
