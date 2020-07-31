package com.kun.common.widge.edittext;

import com.kun.common.BaseApplication;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;


/**
 * Created by Administrator on 2018/9/26.
 * 自定义TextView 可以使用iconfont
 * 使用
 * <com.yongsa.mainlib.widge.TextViewIconFont
 android:id="@+id/test_iconfont"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content"
 android:text="&#xe604;"
 android:textSize="@dimen/space_24"
 />

 代码中 tvic_test.setText("\ue604");
 */

public class EditTextIconFont extends AppCompatEditText {


  public EditTextIconFont(Context context) {
    super(context);
    init(context);
  }

  public EditTextIconFont(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public EditTextIconFont(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    setTypeface(BaseApplication.getIconfont(context));
  }
}
