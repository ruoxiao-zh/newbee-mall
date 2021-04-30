package ltd.newbee.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author Richard
 * @Date 2021/4/23 2:44 PM
 */
@Controller
public class UploadTestController {
    
    private final static String FILE_UPLOAD_PATH = "/Users/ellison/code/newbee-mall/src/main/resources/static/upload/";
    
    @RequestMapping(value = "/upload/test", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败";
        }
        
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffixName);
        
        //生成文件名称通用方法
        SimpleDateFormat sdf         = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random           r           = new Random();
        String           newFileName = sdf.format(new Date()) + r.nextInt(100) + suffixName;
        System.out.println(newFileName);
        
        try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path   path  = Paths.get(FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "上传成功，图片地址为：/upload/" + newFileName;
    }
    
    @GetMapping("/upload/test")
    public String upload() {
        return "upload-test";
    }
}
