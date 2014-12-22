package com.lz.skindemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Environment;
import android.view.View;

public class LZR {
    
    public static List<String> skinFils = new ArrayList<String>();;
    public static final String SDCARDROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String skinCacheDir = SDCARDROOT;
    public static String choosedSkinPath;
    public static Context context;
    public static String packageName;
    
    public static void clearData(){
        choosedSkinPath = null;
        context = null;
    }
    
    /**
     * 对资源文件进行解压操作
     * @param path
     */
    public static void skinFileHandler(String path){
        skinFils.clear();
        // 首先是遍历某指定路径，找出所有皮肤的压缩包
        List<String> zipFiles = FileUtil.getAllSkinZipFiles(path);
        // 然后是把压缩文件解压到指定路径中
        if(zipFiles.size()>0){
            for(String p : zipFiles){
                File zipFile = new File(p);
                String fileName = zipFile.getName().split("\\.")[0];
                File skinDir = new File(skinCacheDir,fileName.trim());
                skinFils.add(skinDir.getAbsolutePath() + File.separator);
                try {
                    if(!skinDir.exists()){
                        skinDir.mkdirs();
                       ZipUtils.upZipFile(zipFile, skinDir.getAbsolutePath()) ;
                    }
                } catch (ZipException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 设置控件的背景资源（有点击效果的）
     * 
     * @param view 控件
     * @param res 资源名字
     * @param res_normal 有按下效果时正常资源
     * @param res_press 有按下效果时按下资源
     */
    public static void setViewBackGroundRes(View view ,String res,String res_normal,String res_press){
        if(choosedSkinPath == null){// 使用默认资源
            setViewBackGroundRes(view,res);
        }else{// 使用指定资源
            if(!res.endsWith("xml")){
                String path = choosedSkinPath + res; 
                Bitmap bm = BitmapFactory.decodeFile(path);
                if(bm != null){
                    view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bm));
                }else{// 指定资源找不到了
                    setViewBackGroundRes(view,res);
                }
            }else{
                String res_normal_path = choosedSkinPath + res_normal; 
                String res_press_path = choosedSkinPath + res_press; 
                Bitmap bm_normal = BitmapFactory.decodeFile(res_normal_path);
                Bitmap bm_press = BitmapFactory.decodeFile(res_press_path);
                if(bm_normal==null || bm_press == null){
                    setViewBackGroundRes(view,res);
                }else{
                    Drawable dw_normal = new BitmapDrawable(context.getResources(), bm_normal);
                    Drawable dw_press = new BitmapDrawable(context.getResources(), bm_press);
                    
                    StateListDrawable sd = new StateListDrawable();
                    //sd.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, dw_press);
                    //sd.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, dw_press);
                    sd.addState(new int[]{android.R.attr.state_focused}, dw_press);
                    sd.addState(new int[]{android.R.attr.state_pressed}, dw_press);
                    //sd.addState(new int[]{android.R.attr.state_enabled}, dw_press);
                    sd.addState(new int[]{}, dw_normal);
                    view.setBackgroundDrawable(sd);
                }
            }
        }
    }
    
    /**
     * 给控件设置背景资源（无点击效果）
     * @param view
     * @param res
     */
    private static void setViewBackGroundRes(View view ,String res){
        /**
         * 关于getIdentifier方法，官方解释：
         * 不推荐使用这个函数，这是因为，通过ID来检索资源相对于通过名字来检索资源来说效率更高
         */
        int resID = context.getResources().getIdentifier(res.split("\\.")[0], "drawable", packageName); 
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resID);
        if(bm != null){
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(),bm));
        }else{
            Drawable image = context.getResources().getDrawable(resID);
            view.setBackgroundDrawable(image);
        }
    }

}
