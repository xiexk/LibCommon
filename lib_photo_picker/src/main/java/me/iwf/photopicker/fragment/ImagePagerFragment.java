package me.iwf.photopicker.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.app.ud361.cropper.CropImagePopWindow;
import com.app.ud361.cropper.CropImagePopWindow.CropImagePopWindowInterface;
import com.bumptech.glide.Glide;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.R;
import me.iwf.photopicker.adapter.PhotoPagerAdapter;
import me.iwf.photopicker.utils.SdCardHelper;

/**
 * Image Pager Fragment
 * Created by donglua on 15/6/21.
 */
public class ImagePagerFragment extends Fragment {

  public final static String ARG_PATH = "PATHS";

  public final static String ARG_CURRENT_ITEM = "ARG_CURRENT_ITEM";

  public final static String SHOW_CROP_IMAGE_BUTTON = "SHOW_CROP_IMAGE_BUTTON";//显示裁减按钮

  public static final int PHOTO_REQUEST_CUT = 2;//裁剪结束

  private ArrayList<String> paths;

  private ViewPager mViewPager;

  private PhotoPagerAdapter mPagerAdapter;

  private Button bt_crop, bt_save;

  private int currentItem = 0;

  public Uri uritempFile;

  private boolean showCropImage = false;//是否显示裁减按钮

  private boolean fixed_proportion;//裁减固定比例

  public static int fixed_x = 16, fixed_y = 9;//裁剪固定比例 x方向 y方向

  private CropImagePopWindow cropImagePopWindow;

  public static ImagePagerFragment newInstance(List<String> paths, int currentItem, boolean showCropImage) {
    ImagePagerFragment f = new ImagePagerFragment();
    try {
      Bundle args = new Bundle();
      args.putStringArray(ARG_PATH, paths.toArray(new String[paths.size()]));
      args.putInt(ARG_CURRENT_ITEM, currentItem);
      args.putBoolean(SHOW_CROP_IMAGE_BUTTON, showCropImage);
      f.setArguments(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

  public static ImagePagerFragment newInstance(List<String> paths, int currentItem) {
    return newInstance(paths, currentItem, false);
  }

  /**
   * 设置裁剪比例
   */
  public void setCutProportion(int fixed_x, int fixed_y) {
    this.fixed_proportion = true;
    this.fixed_x = fixed_x;
    this.fixed_y = fixed_y;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (getActivity() instanceof PhotoPickerActivity) {
      PhotoPickerActivity photoPickerActivity = (PhotoPickerActivity) getActivity();
      photoPickerActivity.updateTitleDoneItem();
    }
  }

  public void setPhotos(List<String> paths, int currentItem) {
    this.paths.clear();
    this.paths.addAll(paths);
    this.currentItem = currentItem;

    mViewPager.setCurrentItem(currentItem);
    mViewPager.getAdapter().notifyDataSetChanged();
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    paths = new ArrayList<>();

    Bundle bundle = getArguments();

    if (bundle != null) {
      String[] pathArr = bundle.getStringArray(ARG_PATH);
      paths.clear();
      if (pathArr != null) {

        paths = new ArrayList<>(Arrays.asList(pathArr));
      }

      currentItem = bundle.getInt(ARG_CURRENT_ITEM);
      showCropImage = bundle.getBoolean(SHOW_CROP_IMAGE_BUTTON, false);
    }

    mPagerAdapter = new PhotoPagerAdapter(Glide.with(this), paths);
    mPagerAdapter.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {

          final ImageView imageView = (ImageView) v;

          AlertDialog.Builder normalDialog =
              new AlertDialog.Builder(getActivity());
          normalDialog.setMessage("是否保存图片？");
          normalDialog.setPositiveButton("确定",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                  SdCardHelper.saveBitmapToCameraPath(image);
                  Toast.makeText(getContext(), "保存图片成功", Toast.LENGTH_LONG).show();
                }
              });
          normalDialog.setNegativeButton("取消",
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //...To-do
                }
              });
          // 显示对话框
          normalDialog.show();

        return false;
      }
    });
  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.__picker_picker_fragment_image_pager, container, false);

    mViewPager = rootView.findViewById(R.id.vp_photos);
    mViewPager.setAdapter(mPagerAdapter);
    mViewPager.setCurrentItem(currentItem);
    mViewPager.setOffscreenPageLimit(5);

    bt_crop = rootView.findViewById(R.id.bt_image_crop);
    bt_crop.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        String currentPath = paths.get(getCurrentItem());
        cropImagePopWindow = new CropImagePopWindow(getActivity(), currentPath, fixed_proportion, fixed_x, fixed_y);
        cropImagePopWindow.setCropImagePopWindowInterface(new CropImagePopWindowInterface() {
          @Override
          public void cropImageBack(String imageUrl) {
            if (getActivity() instanceof PhotoPickerActivity) {
              ((PhotoPickerActivity) getActivity()).cropImageBack(imageUrl);
            }
          }
        });
        cropImagePopWindow.show();
      }
    });
    if (!showCropImage) {
      bt_crop.setVisibility(View.GONE);
    }

    return rootView;
  }


  public ViewPager getViewPager() {
    return mViewPager;
  }


  public ArrayList<String> getPaths() {
    return paths;
  }


  public ArrayList<String> getCurrentPath() {
    ArrayList<String> list = new ArrayList<>();
    int position = mViewPager.getCurrentItem();
    if (paths != null && paths.size() > position) {
      list.add(paths.get(position));
    }
    return list;
  }


  public int getCurrentItem() {
    return mViewPager.getCurrentItem();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    paths.clear();
    paths = null;

    if (mViewPager != null) {
      mViewPager.setAdapter(null);
    }
  }


  /**
   * @param uri
   * 裁剪
   */
  public void crop(Uri uri) {

    // 裁剪图片意图
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//7.0权限
    intent.setDataAndType(uri, "image/*");
    intent.putExtra("crop", "true");
//    // 裁剪框的比例，1：1  //不设置 可以自由选择裁减框大小
    if (fixed_proportion) {
      intent.putExtra("aspectX", fixed_x);
      intent.putExtra("aspectY", fixed_y);
    }

//    // 裁剪后输出图片的尺寸大小
//    intent.putExtra("outputX", 250);
//    intent.putExtra("outputY", 250);

    intent.putExtra("outputFormat", "JPEG");// 图片格式
    intent.putExtra("noFaceDetection", true);// 取消人脸识别
    intent.putExtra("return-data", false);
    // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    getActivity().startActivityForResult(intent, PHOTO_REQUEST_CUT);
  }
}
