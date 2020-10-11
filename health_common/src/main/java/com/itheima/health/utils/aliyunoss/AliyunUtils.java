package com.itheima.health.utils.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/** AliyunUtils
 * 阿里云工具类   该配置文件 默认操作杭州区域！ 其他区域 操作 需要修改区域信息代码
 */
public class AliyunUtils {

    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    public  static  String accessKeyId = "LTAI4G8d3QWV3J8jcg6UUuRM";
    public  static String accessKeySecret = "byW4UOaIJ7Gm0JouiTzdkHexIHdtvB";
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    public  static  String endpoint = "oss-cn-shanghai.aliyuncs.com";
    public  static  String bucketName = "heima110cyc";//  空间名称


    /**
     *  删除文件
     *  deleteFilename  阿里云上的文件名称 或者  文件目录/文件名称
     */
    public static void deleteFile(String deleteFilename) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, deleteFilename);

        // 关闭OSSClient。
        ossClient.shutdown();

    }

    /**
     * 上传本地文件
     * @throws FileNotFoundException
     */
    public static void uploadLocalDiskFileToAliyun(String  uploadfilePath,String uuidfilename) {

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = new FileInputStream(uploadfilePath);
            ossClient.putObject(bucketName, uuidfilename ,inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



    /**
     * 上传文件字节数组
     * @throws FileNotFoundException
     */
    public static void uploadMultiPartFileToAliyun(byte[] bytes,String uuidfilename) {

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            ossClient.putObject(bucketName, uuidfilename, new ByteArrayInputStream(bytes));

            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
