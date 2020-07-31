package com.kun.common.fragment;

import com.kun.common.mvp.BaseActivityFragmentController;
import com.kun.common.mvp.constract.BaseViewContract;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Administrator on 2018/9/5.
 * BaseHCFFragment
 */

public class BaseHCFFragment extends Fragment implements View.OnClickListener, BaseViewContract {
  public BaseActivityFragmentController baseActivityFragmentController;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    this.getActivity().getBaseContext();
    baseActivityFragmentController = new BaseActivityFragmentController(this.getActivity(), this);
    dealScreenOritation();
    initView();
    initData();
    initListener();
    return baseActivityFragmentController.getBaseView();
  }

  public <T extends View> T findViewById(int id) {
    return baseActivityFragmentController.getBaseView().findViewById(id);
  }

  public void initView() {
  }

  public void initData() {
  }

  public void initListener() {

  }

  @Override
  public void onClick(View v) {

  }


  @Override
  public void transparent() {
    baseActivityFragmentController.transparent();
  }

  @Override
  public void showToastShot(String message) {
    baseActivityFragmentController.showToastShot(message);
  }

  @Override
  public void showToastLong(String message) {
    baseActivityFragmentController.showToastLongDebug(message);
  }

  @Override
  public void showToastLongDebug(String message) {
    baseActivityFragmentController.showToastLongDebug(message);
  }

  @Override
  public void showError(int type, String message) {
    baseActivityFragmentController.showError(type, message);
  }

  @Override
  public void clickError() {

  }

  @Override
  public void showCustomError(int layoutId) {
    baseActivityFragmentController.showCustomError(layoutId);
  }

  @Override
  public void showHintDialog(String message) {
    baseActivityFragmentController.showHintDialog(message);
  }

  @Override
  public void showHintDialog(String message, View.OnClickListener onClickListener) {
    baseActivityFragmentController.showHintDialog(message, onClickListener);
  }

  @Override
  public void showHintDialog(String title, String message, View.OnClickListener onClickListener) {
    baseActivityFragmentController.showHintDialog(title, message, onClickListener);
  }

  @Override
  public void showHintDialog(String title, String message, View.OnClickListener onClickListener, long timeAutoDismiss) {
    baseActivityFragmentController.showHintDialog(title, message, onClickListener);
  }

  @Override
  public void hideHintDialog() {
    baseActivityFragmentController.hideHintDialog();
  }

  @Override
  public void showWaitingDialog() {
    baseActivityFragmentController.showWaitingDialog();
  }

  @Override
  public void hindWaitingDialog() {
    baseActivityFragmentController.hindWaitingDialog();
  }

  @Override
  public void toActivity(Class activityClass) {
    baseActivityFragmentController.toActivity(activityClass);
  }

  @Override
  public void msg(String message, int code) {

  }

  @Override
  public void dealScreenOritation() {
    baseActivityFragmentController.dealScreenOritation();
  }
}
