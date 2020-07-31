package com.kun.common.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * 点击次数工具栏
 * eg:点击五次跳转到开发者模式
 */
public class HelperTimeClick {
    /**
     * 需要点击的次数
     */
    private int count=5;
    /**
     * 当前次数
     */
    private int currentCount=0;
    /**
     * 最大间隔时间
     */
    private long dulationTime=1000;
    /**
     * 上次点击的时间
     */
    private long lastTime=0;
    /**
     * 点击返回监听
     */
    private HelperTimeClickListener helperTimeClickListener;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 要跳转的模块名称
     */
    private String enterName="";

    public HelperTimeClick( ) {
    }

    public HelperTimeClick(Context context,String enterName){
        this.context=context;
        this.enterName=enterName;
    }
    /**
     * 点击调用
     */
   public void click(){
       if(lastTime==0){
           lastTime=System.currentTimeMillis();
           currentCount++;
           return;
       }

       if(System.currentTimeMillis()-lastTime<dulationTime) {
           lastTime=System.currentTimeMillis();
           currentCount++;
           if(currentCount==5){
               if(helperTimeClickListener!=null){
                   helperTimeClickListener.clickOk();
               }
               reset();
           }
           else if(5-currentCount<3){
               if(context!=null){
                   Toast.makeText(context,"再点击"+(5-currentCount)+"次进入"+enterName,Toast.LENGTH_SHORT).show();
               }
           }
       }else {
           reset();
       }
    }

    /**
     * 重置
     */
    private void reset(){
        currentCount=0;
        lastTime=0;
    }

    /**
     *  设置返回监听
     */
    public void setHelperTimeClickListener(HelperTimeClickListener helperTimeClickListener) {
        this.helperTimeClickListener = helperTimeClickListener;
    }

    /**
     * 点击监听接口
     */
    public interface HelperTimeClickListener{
        /**
         * 点击成功 执行下一步逻辑
         */
       void clickOk();
    }
}
