package com.skyocean.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import java.io.File;

@SuppressLint("NewApi")
public final class CameraUtil {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;





	/**
	 * invoke the system Camera app and capture a image。 you can received the
	 * capture result in {@link Activity。 If
	 * successed,you can use the outputUri to get the image
	 * 
	 * @param activity
	 * @param outputUri
	 *            拍照后图片的存储路径
	 * @param requestCode
	 */
	public static void captureImage(Activity activity, Uri outputUri,
			int requestCode) {
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 调用系统裁减功能，裁减某张指定的图片，并输出到指定的位置
	 * 
	 * @param activity
	 * @param originalFileUri
	 *            原始图片位置
	 * @param outputFileUri
	 *            裁减后图片的输出位置，两个地址最好不一样。如果一样的话，有的手机上面无法保存裁减的结果
	 * @return
	 */
	public static void cropImage(Activity activity, Uri originalFileUri,
			Uri outputFileUri, int requestCode, int aspectX, int aspectY,
			int outputX, int outputY) {
		if (originalFileUri == null) {
			return;
		}
		final Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(originalFileUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);

		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		// 不启用人脸识别
		intent.putExtra("noFaceDetection", false);
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 调用系统图库选择照片 使用 方法从
	 * onActivityResult的data.getData()中解析获得的Uri
	 * 
	 * @param activity
	 * @param requestCode
	 * @return
	 */
	public static void pickImageSimple(Activity activity, int requestCode) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(intent, requestCode);
	}

	/** 获得The data stream for the file */
	public static String getImagePathFromUri(Context context, Uri uri) {
		int SDK_INT = Build.VERSION.SDK_INT;
		int KITKAT = Build.VERSION_CODES.KITKAT;
		//content://media/external/images/media/450900
		Uri u = uri;//content://media/external/images/media/22     //file:///storage/emulated/0/DCIM/Camera/IMG_20150702_094008.jpg
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (DocumentsContract.isDocumentUri(context, uri)) {
				return getImagePathFromUriKitkat(context, uri);
			}
		}
		return getImagePathFromUriSimple(context, uri);

	}

	/**
	 * 4.4以下
	 * 
	 * @param context
	 * @param uri
	 */
	private static String getImagePathFromUriSimple(Context context, Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };//[_data]   //content://media/external/images/media/21   //file:///storage/emulated/0/DCIM/Camera/IMG_20150702_094008.jpg
		if(!uri.toString().contains("content")){
			String path = uri.toString();
			return path.substring(path.indexOf("/storage"));
		}else {
			Cursor cursor = context.getContentResolver().query(uri, proj, null,null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		
	}

	/**
	 * 4.4以上的Document Uri
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	private static String getImagePathFromUriKitkat(Context context, Uri uri) {
		String wholeID = DocumentsContract.getDocumentId(uri);
		if (TextUtils.isEmpty(wholeID) || !wholeID.contains(":")) {
			return null;
		}
		// 获得资源唯一ID
		String id = wholeID.split(":")[1];
		// 定义索引字段
		String[] column = { MediaStore.Images.Media.DATA };
		String sel = MediaStore.Images.Media._ID + "=?";

		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
				new String[] { id }, null);
		int columnIndex = cursor.getColumnIndex(column[0]);

		String filePath = null;
		if (cursor.moveToFirst()) {
			// DATA字段就是本地资源的全路径
			filePath = cursor.getString(columnIndex);
		}
		// 切记要关闭游标
		cursor.close();
		return filePath;
	}

	/**
	 * 调用系统图库选择照片,裁减后返回
	 * ,4.4上无法确定用户是否是在图库里选择的照片，所以不使用该方法，使用pickImageSimple，返回后在调用裁减
	 * 
	 * @param activity
	 * @param filePath
	 *            拍照后图片的存储路径
	 * @param requestCode
	 * @return
	 */
	@Deprecated
	public static void pickImageCrop(Activity activity, Uri outputUri,
			int requestCode, int aspectX, int aspectY, int outputX, int outputY) {
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		Intent intent = new Intent();
		// 根据版本号不同使用不同的Action
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
		} else {
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
		}

		intent.setType("image/*");
		intent.putExtra("crop", "true");
		// 裁剪框比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		// 图片输出大小
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true); // 部分机型没有设置该参数截图会有黑边
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		// 不启用人脸识别
		intent.putExtra("noFaceDetection", false);
		activity.startActivityForResult(intent, requestCode);
	}

	public static interface ScannerResult {
		void onResult(boolean success);
	}

	/**
	 * 扫描某张指定的图片放入系统媒体库
	 */
	public static void scannerImage(Activity activity, final Uri fileUri,
			final ScannerResult scannerResult) {
		if (fileUri == null) {
			if (scannerResult != null) {
				scannerResult.onResult(false);
			}
			return;
		}
		sMediaScannerConnection = new MediaScannerConnection(activity,
				new MediaScannerConnectionClient() {
					public void onMediaScannerConnected() {
						sMediaScannerConnection.scanFile(fileUri.getPath(),
								"image/*");
					}

					public void onScanCompleted(String path, Uri uri) {
						sMediaScannerConnection.disconnect();
						if (scannerResult != null) {
							scannerResult.onResult(uri != null);
						}
					}
				});
		sMediaScannerConnection.connect();
	}

	private static MediaScannerConnection sMediaScannerConnection;

	/**
	 * 查询某张图片有没有被扫描到媒体库
	 * 
	 * @param context
	 * @param filePath
	 * @return 返回这个图片在媒体库的Uri，如果没有扫描到媒体库，则返回null
	 */
	public static Uri isImageFileInMedia(Context context, String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				null,
				MediaStore.Images.Media.DISPLAY_NAME + "='" + file.getName()
						+ "'", null, null);
		Uri uri = null;
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToLast();
			long id = cursor.getLong(0);
			uri = ContentUris.withAppendedId(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
		}
		return uri;
	}

	public static void pickImageSimpleNew(FragmentActivity activity,
			int requestCodePickCropPhoto) {
		Intent intent = new Intent();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		} else {
			intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setAction(Intent.ACTION_GET_CONTENT);
		}
		if (intent.resolveActivity(activity.getPackageManager())!=null) {
			activity.startActivityForResult(intent, requestCodePickCropPhoto);
		}
	}

	/** 获得The data stream for the file */
	public static String getImagePathFromUriNew(Context context, Uri uri) {
		String filePath = "";
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			String wholeID = DocumentsContract.getDocumentId(uri);
			String id = wholeID.split(":")[1];
			String[] column = { MediaStore.Images.Media.DATA };
			String sel = MediaStore.Images.Media._ID + "=?";
			Cursor cursor = context.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
					new String[] { id }, null);
			int columnIndex = cursor.getColumnIndex(column[0]);
			if (cursor.moveToFirst()) {
				filePath = cursor.getString(columnIndex);
			}
			cursor.close();
		} else {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(uri, projection,
					null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			filePath = cursor.getString(column_index);
			cursor.close();
		}
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
		// if (DocumentsContract.isDocumentUri(context, uri)) {
		// return getImagePathFromUriKitkat(context, uri);
		// }
		// }
		// return getImagePathFromUriSimple(context, uri);
		return filePath;
	}

}
