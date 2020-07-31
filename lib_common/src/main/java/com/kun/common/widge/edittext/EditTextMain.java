package com.kun.common.widge.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kun.common.R;
import com.kun.common.tools.LayoutHelper;
import com.kun.common.tools.PhoneHelper;
import com.kun.common.widge.textview.TextViewIconFont;

/**
 * Created by Administrator on 2018/8/31.
 * 自定义组要通用Edit
 *
 * 使用
 *  1、填写hint内容 eg: edittextmain:hintText="我是hint内容"
 *  1、填写左侧内容 eg: edittextmain:leftText="我是左侧内容"
 *  2、显示获取验证码按钮 eg:  edittextmain:showRightButtom="true"
 *                            edittextmain:rightButtonText="获取验证码"
 *  3、设置edittext距离左边界 eg:  edittextmain:editcontentmarginleft="@dimen/space_8"
 *  4、开启查看密码功能 eg:  edittextmain:passwordShowFunction="true"
 *  5、限制字数 eg:  edittextmain:maxlangth="18"
 */

public class EditTextMain extends LinearLayout implements View.OnClickListener {

  public TextView tv_left;

  public EditText et;

  public ImageView iv_right;

  public TextViewIconFont tv_right;

  public Button bt_right;

  private RightTextViewListener rightTextViewListener;

  private RightButtomListener rightButtomListener;

  public LinearLayout ll_base;

  //显示右侧图片眼睛样式 是否眼睛睁开还是闭上 默认闭上（不显示密码）
  private int iv_type = 1;//1 图片为竖线分割线  2图片为眼睛图片

  //密码显示功能
  private boolean passwordShowFunction = false;

  private boolean showEyes = false;

  //输入数字键盘
  private boolean numberInput = false;

  private float editContentMarginLeft;

  private String hintText = "请输入手号";

  private String leftText ;

  private boolean showRightButtom;//显示右侧按钮

  private String rightButtonText = "获取验证码";

  private String maxLangth;

  private static int timeLess=60*1000;//按钮倒计时

  public EditTextMain(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(context, attrs);
    initData();
    initListener();
  }

  private void initView(Context context, @Nullable AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.edittextmain);
    editContentMarginLeft = typedArray.getDimension(R.styleable.edittextmain_editcontentmarginleft, 0);
    passwordShowFunction = typedArray.getBoolean(R.styleable.edittextmain_passwordShowFunction, false);
    showRightButtom = typedArray.getBoolean(R.styleable.edittextmain_showRightButtom, false);
    numberInput = typedArray.getBoolean(R.styleable.edittextmain_numberInput, false);
    hintText = typedArray.getString(R.styleable.edittextmain_hintText);
    leftText = typedArray.getString(R.styleable.edittextmain_leftText);
    rightButtonText = typedArray.getString(R.styleable.edittextmain_rightButtonText);
    maxLangth = typedArray.getString(R.styleable.edittextmain_maxlangth);
    ll_base = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.mainlib_layout_edit, this);
    tv_left = findViewById(R.id.mainlib_layout_edit_left_tv);
    et = findViewById(R.id.mainlib_layout_edit_et);
    iv_right = findViewById(R.id.mainlib_layout_edit_right_iv);
    tv_right = findViewById(R.id.mainlib_layout_edit_right_tv);
    bt_right = findViewById(R.id.mainlib_layout_edit_right_bt);

    if (editContentMarginLeft != 0) {
      et.setPadding((int) editContentMarginLeft, 0, 0, 0);
    }
    if (passwordShowFunction) {
      showRightImageViewEyesVisibleOff();
    }
    if (showRightButtom) {
      setRightButtom(rightButtonText);
    }
    if (numberInput) {
      et.setInputType(InputType.TYPE_CLASS_NUMBER);
    }
    if (maxLangth != null) {
      try {
        et.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.valueOf(maxLangth))});
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if(leftText!=null){
      setLeftTextView(leftText);
    }
    setEditTextHint(hintText);
  }

  private void initData() {

  }

  private void initListener() {
    iv_right.setOnClickListener(this);
    tv_right.setOnClickListener(this);
    bt_right.setOnClickListener(this);
  }

  public void setEditTextHint(String strEtHint) {
    et.setHint(strEtHint);
  }

  public String getEtContent() {
    return et.getText().toString();
  }

  public void bigEditText(){
    LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,et.getHeight());
    et.setLayoutParams(params);
  }

  /**
   * 显示左侧文字
   */
  public void setLeftTextView(String strLeftText) {
    tv_left.setVisibility(VISIBLE);
    tv_left.setText(strLeftText);
  }

  //显示默认的右侧默认图片
  private void showDefaultRightImageView() {
    iv_right.setClickable(false);
    iv_right.setVisibility(VISIBLE);
  }

  //显示自定义的右侧图片
  public void setRightImageView(int id) {
    iv_right.setVisibility(VISIBLE);
    iv_right.setImageResource(id);
    iv_right.setLayoutParams(LayoutHelper.getLinearLayoutLayoutParaments(getContext(), 16, 16));
  }

  //显示自定义的右侧图片
  public void setRightImageView(int id,int width, int height) {
    iv_right.setVisibility(VISIBLE);
    iv_right.setImageResource(id);
    iv_right.setLayoutParams(LayoutHelper.getLinearLayoutLayoutParaments(getContext(), width, width));
  }

  //显示右侧图片 密码是否显示 显示
  public void showRightImageViewEyesVisible() {
    iv_right.setVisibility(VISIBLE);
    iv_right.setImageResource(R.drawable.commonresourcelib_visibility_black_24dp);
    LayoutParams layoutParams = (LinearLayout.LayoutParams) LayoutHelper
        .getLinearLayoutLayoutParaments(getContext(), 16, 16);
    layoutParams.setMargins(0, 0, PhoneHelper.dip2px(this.getContext(), 14), 0);
    iv_right.setLayoutParams(layoutParams);
    showEyes = true;
    et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
  }

  //显示右侧图片 密码是否显示 不显示
  public void showRightImageViewEyesVisibleOff() {
    iv_right.setVisibility(VISIBLE);
    iv_right.setImageResource(R.drawable.commonresourcelib_visibility_off_black_24dp);
    LayoutParams layoutParams = (LinearLayout.LayoutParams) LayoutHelper
        .getLinearLayoutLayoutParaments(getContext(), 16, 16);
    layoutParams.setMargins(0, 0, PhoneHelper.dip2px(this.getContext(), 14), 0);
    iv_right.setLayoutParams(layoutParams);
    showEyes = false;
    et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//设置密码不可见
  }

  //显示右侧textView
  public void setRightTextView(String strRightText) {
    showDefaultRightImageView();
    tv_right.setVisibility(VISIBLE);
    tv_right.setText(strRightText);
  }

  //显示右侧buttom
  public void setRightButtom(String strRightButtomText) {

    bt_right.setVisibility(VISIBLE);
    bt_right.setText(strRightButtomText);
  }


  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.mainlib_layout_edit_right_iv) {
      showEyes = !showEyes;
      if (showEyes) {
        showRightImageViewEyesVisible();
      } else {
        showRightImageViewEyesVisibleOff();
      }
    } else if (id == R.id.mainlib_layout_edit_right_tv) {
      if (rightTextViewListener != null) {
        rightTextViewListener.onRightTextViewClick();
      }
    } else if (id == R.id.mainlib_layout_edit_right_bt) {
      if (rightButtomListener != null) {
        rightButtomListener.onRightButtomClick();
      }
    }
  }

  /**
   * 开始倒计时
   */
  public void startCountDown(){
    mTimer.start();
  }



  private CountDownTimer mTimer = new CountDownTimer((long) (timeLess), 1000) {

    @Override
    public void onTick(long millisUntilFinished) {

      int remainTime = (int) (millisUntilFinished / 1000L);
      bt_right.setClickable(false);
      bt_right.setText(remainTime + "");
    }

    @Override
    public void onFinish() {
      bt_right.setClickable(true);
      bt_right.setText("获取验证码");
    }
  };


  public void setRightTextViewListener(RightTextViewListener rightTextViewListener) {
    this.rightTextViewListener = rightTextViewListener;
  }

  public void setRightButtomListener(RightButtomListener rightButtomListener) {
    this.rightButtomListener = rightButtomListener;
  }

  public interface RightImageViewListener {
    void onRightImageClick(boolean showEyes);
  }

  public interface RightTextViewListener {
    void onRightTextViewClick();
  }

  public interface RightButtomListener {
    void onRightButtomClick();
  }

  public void setPasswordDismiss() {
    et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
  }

}
