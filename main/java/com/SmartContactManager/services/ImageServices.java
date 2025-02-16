package com.SmartContactManager.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

    String uploadImage(MultipartFile contactImage , String filename);

    String getUrlFromPublicId(String publicId);

}
