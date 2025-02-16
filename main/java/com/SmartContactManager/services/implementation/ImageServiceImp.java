package com.SmartContactManager.services.implementation;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SmartContactManager.helpers.AppConatants;
import com.SmartContactManager.services.ImageServices;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageServiceImp implements ImageServices {


     private Cloudinary cloudinary;
     
    public ImageServiceImp(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile contactImage , String filename) {
        

           try {
               
                  byte[] data= new byte[contactImage.getInputStream().available()];
                  contactImage.getInputStream().read(data);
                   cloudinary.uploader().upload(data , ObjectUtils.asMap(
                    "public_id", filename
                   ));
                   return this.getUrlFromPublicId(filename);
           } 
           catch (IOException e) 
           {
                 e.printStackTrace();
                 return null;
            }
            
    }


    @Override
    public String getUrlFromPublicId(String publicId) {
       return cloudinary
       .url()
       .transformation(
        new Transformation<>()
        .width(AppConatants.CONTACT_IMAGE_WIDTH)
        .height(AppConatants.CONTACT_IMAGE_HEIGHT)
        .crop(AppConatants.CONTACT_IMAGE_CROP)
       )
       .generate(publicId);
    }

       

}
