package com.kun.common.widge.layout;


import com.kun.common.R;
import com.kun.common.tools.PhoneHelper;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/9/3.
 * 加减控件       [-]23[+]
 *
 *  控制组件大小
 *  <com.ud361.app.common.widge.layout.LayoutAddCount
 android:id="@+id/ud361_layout_goodsattribute_select_addcount"
 android:layout_marginTop="@dimen/space_10"
 android:layout_width="@dimen/space_120"
 android:layout_height="@dimen/space_45" />

 添加监听  CountChangeListener

 */

public class LayoutAddCount extends LinearLayout implements View.OnClickListener, TextView.OnEditorActionListener {
  private TextView tv_add, tv_cub;

  public EditText et_count;

  private String count = "1";

  private int minCount = 1;

  private int maxCount = 9999;

  private String unit="";//单位

  private CountChangeListener countChangeListener;

  /**
   * 设置单位
   * @param unit
   */
  public void setUnit(String unit) {
    this.unit = unit;
    et_count.setText(count+unit);
    correctCount();
  }

  public LayoutAddCount(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(context);
    initData();
    initListener();
  }

  private void initView(Context context) {
    LayoutInflater.from(context).inflate(R.layout.mainlib_layout_add_count, this);
    tv_add = findViewById(R.id.mainlib_layout_add_count_tv_add);
    tv_cub = findViewById(R.id.mainlib_layout_add_count_tv_cub);
    et_count = findViewById(R.id.mainlib_layout_add_count_et);
  }

  private void initData() {
    et_count.setText(count);
  }

  private void initListener() {
    tv_add.setOnClickListener(this);
    tv_cub.setOnClickListener(this);
    et_count.setOnEditorActionListener(this);
    et_count.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        correctCount();
        if (countChangeListener != null) {
          countChangeListener.countChange(getCount());
        }
      }
    });
  }

  public void setCount(int count) {
    this.count = count + "";
    if(!unit.equals("")){
      this.count = count + unit;
    }
    this.et_count.setText(this.count);
  }

  public void setMinCount(int minCount) {
    if (minCount > maxCount) {
      Toast.makeText(this.getContext(), "最小值为 比最大值大", Toast.LENGTH_SHORT).show();
      minCount = this.minCount;
    }
    this.minCount = minCount;
    correctCount();
  }

  public void setMaxCount(int maxCount) {

    if (maxCount < minCount) {
      Toast.makeText(this.getContext(), "最大值为 比最小值小", Toast.LENGTH_SHORT).show();
      maxCount = this.maxCount;
    }
    this.maxCount = maxCount;
    correctCount();
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (et_count.getText().toString().equals("")) {
      if(!unit.equals("")){
        et_count.setText(minCount + unit);
      }else {
        et_count.setText(minCount + "");
      }
    }
    int a;
    if(!unit.equals("")){
      a = Integer.valueOf(getNumber(et_count.getText().toString()));
    }else {
      a = Integer.valueOf(et_count.getText().toString());
    }

    if (id == R.id.mainlib_layout_add_count_tv_add) {
      if (a != maxCount) {
        a++;
      }
    } else if (id == R.id.mainlib_layout_add_count_tv_cub) {
      if (a != minCount) {
        a--;
      }
    }
    if(!unit.equals("")){
      count = a + unit;
    }else {
      count = a + "";
    }

    et_count.setText(count);
    if (countChangeListener != null) {
      countChangeListener.countChange(getCount());
    }
  }

  //获取count
  public int getCount() {
    if(!unit.equals("")){
      count = Integer.valueOf(getNumber(et_count.getText().toString())) + "";
    }else {
      count = Integer.valueOf(et_count.getText().toString()) + "";
    }

    return Integer.valueOf(count);
  }

  /**
   * 修正count
   */
  private void correctCount() {

      if(!unit.equals("")){
        if (et_count.getText().toString().equals("")){
          et_count.setText(minCount + unit);
        }

      }else {
        if (et_count.getText().toString().equals("")){
          et_count.setText(minCount + "");
        }
    }
    int c;
    if(!unit.equals("")){
       c = Integer.valueOf(getNumber(et_count.getText().toString()));
    }else {
       c = Integer.valueOf(et_count.getText().toString());
    }


    if (c < minCount) {
      Toast.makeText(this.getContext(), "最小值为" + minCount, Toast.LENGTH_SHORT).show();
      c = minCount;
      if(!unit.equals("")){
        et_count.setText(c + unit);
      }else {
        et_count.setText(c + "");
      }

    }
    if (c > maxCount) {
      Toast.makeText(this.getContext(), "最大值为" + maxCount, Toast.LENGTH_SHORT).show();
      c = maxCount;
      if(!unit.equals("")){
        et_count.setText(c + unit);
      }else {
        et_count.setText(c + "");
      }
    }
    if (et_count.getText().toString().length() > 2) {
      LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
      et_count.setLayoutParams(params);
      et_count.setPadding(PhoneHelper.dip2px(getContext(),5),0,PhoneHelper.dip2px(getContext(),5),0);
    } else {
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
      et_count.setLayoutParams(params);
    }
  }

  @Override
  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {//点击软键盘回车才会触发
    correctCount();
    if (countChangeListener != null) {
      countChangeListener.countChange(getCount());
    }
    return false;
  }


  public interface CountChangeListener {
    void countChange(int count);
  }

  public void setCountChangeListener(CountChangeListener countChangeListener) {
    this.countChangeListener = countChangeListener;
  }

  /**
   * 去掉单位 提取数字
   * @param str
   * @return
   */
  private String getNumber(String str){
      str=str.trim();
      String str2="";
      if(str != null && !"".equals(str)){
        for(int i=0;i<str.length();i++){
          if(str.charAt(i)>=48 && str.charAt(i)<=57){
            str2+=str.charAt(i);
          }
        }
      }
    return str2;
  }
}
