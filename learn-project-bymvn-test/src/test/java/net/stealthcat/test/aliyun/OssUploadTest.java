package net.stealthcat.test.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;

public class OssUploadTest {
    public static void main(String[] args) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "STS.NTn1d7qaSCPktiGhBtGt94Kiy";
        String accessKeySecret = "BF2CfYiRLfQaT9npeFF2UMrLMfKG8vMFQW1R3r6rw3k6";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, "CAIS0AR1q6Ft5B2yfSjIr5fbet6DnL5y9JKAdk/2jEIhS/sV247Cmzz2IH1EfnloAOkasPwzlWtR6fwelrUqEMQbHhOYNJAps8oMqlj7JpfZv8u84YADi5CjQcIJ6KUAmp28Wf7waf+AUBXGCTmd5MMYo9bTcTGlQCZuW//toJV7b9MRcxClZD5dfrl/LRdjr8lo1xGzUPG2KUzSn3b3BkhlsRYe72Rk8vaHxdaAzRDcgVbmqJcSvJ+jC4C8Ys9gG519XtypvopxbbGT8CNZ5z9A9qp9kM49/izc7P6QH35b4RiNL8/Z7tQNXwhiffobHa9YrfHgmNhlvvDSj43t1ytVOeZcX0akQ5u7ku7ZHP+oLt8jaYvjP3PE3rLpMYLu4T48ZXUSODtDYcZDUHhrEk4RUjXdI6Of8UrWSQC7Wsr217otg7Fyyk3s8MaHAkWLX7SB2DwEB4c4aEokVW4RxnezW6UBaRBpbld7Bq6cV5lOdBRZoK+KzQrJTX9Ez2pLmuD6e/LOs7oDVJ37WZtKyuh4Y49d4U8rVEjPQqiykT0pFgpfTK1RzbPmNLKm9baB25/zW+PdDe0dsVgoIFKOpiGWG3RLNn+ztJ9xbkeE+sKUnfeSqJgwQQB27YwPVFiIeIZmoQw+u/LstBnK+76/Wyzt5XR/uPugptkQtRA8I6372bPI52eP7Ub9O/dpxJ3lP0R0WgmydnBDx/Sfu2kKvRhpkRvvZkZFuwzIjDruJJFKiaXUnC9efo5XmPXFTQmn8l5pAMmy/60xXudvbEeQ4AFvIMMCGoABIqcnh9b4OH12vCFJ7VnekCDYiEKfPyqN7KJUfI9os8taTqpLuFvq4XNXHrK2FE5Tm6KagBrs++l68JqxWhsY3W6e32j/V2U/M5m9MSsq6asi2PMjhCiXAIH44N3GwlShsx9+/zShbg3jNC9ZlpW8zgTV25NW16SiwTD8QafzvKA=");

// 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest("outin-9363c89c0c4811ea8cf800163e1c94a4", "sv/4af782ec-1713a2cd435/4af782ec-1713a2cd435.mp4", new File("C:\\Users\\qian.yang\\Downloads\\CgsZj15y-ymARRb0AAb3CbYtXhc225.mp4"));

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

// 上传文件。
        ossClient.putObject(putObjectRequest);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
