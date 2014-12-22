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
     * ����Դ�ļ����н�ѹ����
     * @param path
     */
    public static void skinFileHandler(String path){
        skinFils.clear();
        // �����Ǳ���ĳָ��·�����ҳ�����Ƥ����ѹ����
        List<String> zipFiles = FileUtil.getAllSkinZipFiles(path);
        // Ȼ���ǰ�ѹ���ļ���ѹ��ָ��·����
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
                    // TODO �Ԅ����ɤ��줿 catch �֥�å�
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO �Ԅ����ɤ��줿 catch �֥�å�
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * ���ÿؼ��ı�����Դ���е��Ч���ģ�
     * 
     * @param view �ؼ�
     * @param res ��Դ����
     * @param res_normal �а���Ч��ʱ������Դ
     * @param res_press �а���Ч��ʱ������Դ
     */
    public static void setViewBackGroundRes(View view ,String res,String res_normal,String res_press){
        if(choosedSkinPath == null){// ʹ��Ĭ����Դ
            setViewBackGroundRes(view,res);
        }else{// ʹ��ָ����Դ
            if(!res.endsWith("xml")){
                String path = choosedSkinPath + res; 
                Bitmap bm = BitmapFactory.decodeFile(path);
                if(bm != null){
                    view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bm));
                }else{// ָ����Դ�Ҳ�����
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
     * ���ؼ����ñ�����Դ���޵��Ч����
     * @param view
     * @param res
     */
    private static void setViewBackGroundRes(View view ,String res){
        /**
         * ����getIdentifier�������ٷ����ͣ�
         * ���Ƽ�ʹ�����������������Ϊ��ͨ��ID��������Դ�����ͨ��������������Դ��˵Ч�ʸ���
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
