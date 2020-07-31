//package com.kun.common.tools.permission;
//
//import java.util.List;
//
//import com.kun.common.R;
//import com.yanzhenjie.permission.Action;
//import com.yanzhenjie.permission.AndPermission;
//import com.yanzhenjie.permission.runtime.Permission;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.text.TextUtils;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//
///**
// * Created by Administrator on 2018/12/18.
// * 权限申请类
// * 使用
// * 1、PermisionHelper helper_permision=new PermisionHelper(activity,自定义的permission成功返回监听);
// *
// * 2、helper_permision.getPermision(Permission.CAMERA);
// *
// * 3、 自定义回调监听实现
// *  public void permissionSuccess(int requestCode, @NonNull List<String> grantPermissions) {
// * toCaptureActivity();
// * }
// * 4、权限
// *   CALENDAR（日历）
// READ_CALENDAR
// WRITE_CALENDAR
// CAMERA（相机）
// CAMERA
// CONTACTS（联系人）
// READ_CONTACTS
// WRITE_CONTACTS
// GET_ACCOUNTS
// LOCATION（位置）
// ACCESS_FINE_LOCATION
// ACCESS_COARSE_LOCATION
// MICROPHONE（麦克风）
// RECORD_AUDIO
// PHONE（手机）
// READ_PHONE_STATE
// CALL_PHONE
// READ_CALL_LOG
// WRITE_CALL_LOG
// ADD_VOICEMAIL
// USE_SIP
// PROCESS_OUTGOING_CALLS
// SENSORS（传感器）
// BODY_SENSORS
// SMS（短信）
// SEND_SMS
// RECEIVE_SMS
// READ_SMS
// RECEIVE_WAP_PUSH
// RECEIVE_MMS
// STORAGE（存储卡）
// READ_EXTERNAL_STORAGE
// WRITE_EXTERNAL_STORAGE
// ---------------------
// 作者：严振杰
// 来源：CSDN
// 原文：https://blog.csdn.net/yanzhenjie1003/article/details/52503533
// 版权声明：本文为博主原创文章，转载请附上博文链接！
// */
//
//public class PermisionHelper {
//  private static final int REQUEST_CODE_SETTING = 300;
//
//  private PermisionSuccessListener permisionSuccessListener;
//
//  private Activity activity;
//
//  public PermisionHelper(Activity activity, PermisionSuccessListener permisionSuccessListener) {
//    this.activity = activity;
//    this.permisionSuccessListener = permisionSuccessListener;
//  }
//
//  public void getPermision(String... permissionsArray) {
//    AndPermission.with(activity)
//        .runtime()
//        .permission(permissionsArray)
//        .rationale(new RuntimeRationale())
//        .onDenied(new Action<List<String>>() {
//          @Override
//          public void onAction(List<String> permissions) {
//            Toast.makeText(activity,"获取权限失败",Toast.LENGTH_SHORT).show();
//            if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
//              showSettingDialog(activity, permissions);
//            }
//          }
//        })
//        .onGranted(new Action<List<String>>() {
//          @Override
//          public void onAction(List<String> data) {
//            if(permisionSuccessListener!=null){
//              permisionSuccessListener.permissionSuccess(0,data);
//            }
//          }
//        })
//        .start();
//  }
//
//
//  public interface PermisionSuccessListener {
//    void permissionSuccess(int requestCode, @NonNull List<String> grantPermissions);
//  }
//
//
//  /**
//   * Display setting dialog.
//   */
//  public void showSettingDialog(Context context, final List<String> permissions) {
//    List<String> permissionNames = Permission.transformText(context, permissions);
//    String message = context.getString(R.string.mainlib_permission_always_failed,
//        TextUtils.join("\n", permissionNames));
//
//    new AlertDialog.Builder(context).setCancelable(false)
//        .setTitle(R.string.mainlib_tip)
//        .setMessage(message)
//        .setPositiveButton(R.string.mainlib_setting, new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialog, int which) {
//            setPermission();
//          }
//        })
//        .setNegativeButton(R.string.mainlib_cancle, new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialog, int which) {
//          }
//        })
//        .show();
//  }
//
//  /**
//   * Set permissions.
//   */
//  private void setPermission() {
//    AndPermission.with(activity).runtime().setting().start(REQUEST_CODE_SETTING);
//  }
//
//
//}
