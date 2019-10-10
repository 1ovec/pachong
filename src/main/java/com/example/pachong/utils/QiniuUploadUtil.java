package com.example.pachong.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUploadUtil {

  private final String accessKey = "uw3nNALVi0SEtAZiWQyXSgn9yKvS0_8tY_GxTMxq";

  private final String secretKey = "XW4L7ovn-pnfeGElbnwYVOyqsvcOoMNud60oDs8R";

  private final String bucket = "huaplus";

  private Auth auth = Auth.create(accessKey, secretKey);

  Configuration cfg = new Configuration(Zone.zone2());

  UploadManager uploadManager = new UploadManager(cfg);

  private String getUploadToken(String fileName) {
    return auth.uploadToken(bucket, fileName);
  }

  public void upload(byte[] file, String fileName) throws QiniuException {
    Response response = uploadManager.put(file, fileName, getUploadToken(fileName));
  }

}
