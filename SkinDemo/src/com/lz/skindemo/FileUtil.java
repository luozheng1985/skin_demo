package com.lz.skindemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 到指定路径下获取皮肤资源
     * 
     * @param path zip文件所在路径
     * @return
     */
    public static List<String> getAllSkinZipFiles(String path){
        List<String> zipFiles = new ArrayList<String>();
        File file = new File(path);
        if(file.exists()){
            File[] files = file.listFiles();
            if(files.length>0){
                for (File f : files) {
                    if(f.getName().startsWith("skin") && f.getName().endsWith("zip")){
                        zipFiles.add(f.getAbsolutePath());
                    }
                }
            }
        }
        return zipFiles;
    }
    
    
    
    
}
