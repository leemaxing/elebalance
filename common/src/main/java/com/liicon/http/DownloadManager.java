package com.liicon.http;

import java.io.File;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.DefaultHttpRedirectHandler;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.liicon.AppCore;
import com.liicon.application.BasicAppHelper;
import com.liicon.manager.FileManager;
import com.liicon.ui.uikit.NotificationUtils;
import com.liicon.ui.uikit.ToastUtils;
import com.liicon.utils.app.NetworkUtils;
import com.liicon.utils.lang.StringUtils;
import com.liicon.utils.log.Level;

/**
 * Apk下载工具类
 * @author King
 * @version 1.0
 * @created 2013.09.29
 */
public class DownloadManager {
	private final String actionShowDownload = BasicAppHelper.get().getPackageName()+".intent.action.download";
	
	private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_START = 1;
    private static final int DOWN_UPDATE = 2;
    private static final int DOWN_INSTALL = 3;
	private static final int DOWN_NOT_OPERATE_FILE = 4;
    private static final int DOWN_NOT_NETWORK = 5;
    private static final int DOWN_HTTP_ERROR = 6;
    
	private static DownloadManager mDownloadManager = new DownloadManager();
	
	private Context mContext;
	//下载对话框
	private Dialog downloadDialog;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    //进度值
    private int progress;
	//Apk下载url
	private String apkUrl = "";
	//下载包保存路径
    private String savePath = "";
	//下载文件大小
	private String apkFileSize = "0MB";
	//已下载文件大小
	private String tmpFileSize = "0MB";
	
	//下载完成后，保存的文件名
	private String apkName = "";

	//下载时显示的标题
	private String title = "";
	//下载时显示的标题
	private int iconResId;
	
	private HttpHandler<File> mHttpHandler;
    
	@SuppressLint("HandlerLeak")
	private final Handler mHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
		    case DOWN_START:
		        mProgress.setProgress(0);
		        mProgressText.setText("正在准备...");
		        break;
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				mProgressText.setText(tmpFileSize + "/" + apkFileSize);
				break;
			case DOWN_INSTALL:
				downloadDialog.dismiss();
				String apkFilePath = (String) msg.obj;
				installApk(new File(apkFilePath));
				break;
			case DOWN_NOSDCARD:
				downloadDialog.dismiss();
				ToastUtils.show(mContext, "无法下载Apk文件，请检查SD卡是否挂载", Toast.LENGTH_LONG);
				break;
			case DOWN_NOT_OPERATE_FILE:
				downloadDialog.dismiss();
				ToastUtils.show(mContext, "无法下载Apk文件，无法执行本地文件操作", Toast.LENGTH_LONG);
				break;
			case DOWN_NOT_NETWORK:
				downloadDialog.dismiss();
				ToastUtils.show(mContext, "无法下载Apk文件，没有网络连接", Toast.LENGTH_LONG);
				break;
			case DOWN_HTTP_ERROR:
				
				if (mContext != null && isValidContext(mContext)&& downloadDialog != null) {
					downloadDialog.dismiss();
				}
				
			    //downloadDialog.dismiss();
			    String text = StringUtils.toString(msg.obj);
			    ToastUtils.show(mContext, text, Toast.LENGTH_LONG);
			    break;
			}
    	};
    };
    
	public void dismiss() {
		if (mContext != null && isValidContext(mContext)
				&& downloadDialog != null) {
			downloadDialog.dismiss();
		}
		downloadDialog = null;
	}

	private boolean isValidContext(Context c) {
		Activity a = (Activity) c;
		if (a.isFinishing()) {
			return false;
		} else {
			return true;
		}
	}
    
    /**
     * 获取下载工具类实例
     * @param title 下载时显示的标题
     * @return
     */
    public static DownloadManager getUpdateManager(Context ctx, int iconResId) {
		return getUpdateManager(ctx, iconResId, "正在下载", "Apk" + File.separator);
    }
    /**
     * 获取下载工具类实例
     * @param title 下载时显示的标题
     * @param saveDir 本地保存路径（为缓存目录下文件夹名，前面不需带“/”）
     * @return
     */
	public static DownloadManager getUpdateManager(Context ctx, int iconResId, String title, String saveDir) {
	    if (mDownloadManager.mHttpHandler != null) {
	        mDownloadManager.mHttpHandler.cancel();
	    }
		mDownloadManager.mContext = ctx;
		mDownloadManager.title = title;
		mDownloadManager.savePath = FileManager.getCacheDirByUsable(ctx.getPackageName(), saveDir);
		mDownloadManager.iconResId = iconResId;
		System.out.println("hbh--mDownloadManager.savePath:"+mDownloadManager.savePath);
		return mDownloadManager;
	}
	
	/**
	 * 下载文件
     * @param apkUrl APK文件下载URL
     * @param fileName 下载保存的文件名（不需带后缀名）
	 */
	public void download(String apkUrl, String fileName){
		this.apkUrl = apkUrl;
		this.apkName = fileName+".apk";
		
		showDialog();
		downloadApk();
	}
	/**
	 * 显示下载对话框
	 */
	private void showDialog() {
		final Builder builder = new Builder(mContext);
		builder.setTitle(title);
		
		LinearLayout layout = new LinearLayout(mContext);
		layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		layout.setPadding(5, 0, 5, 0);
		layout.setOrientation(LinearLayout.VERTICAL);

		mProgress = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
		mProgress.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		
		mProgressText = new TextView(mContext);
		mProgressText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		mProgressText.setGravity(Gravity.RIGHT);
		mProgressText.setMinimumHeight(30);
		
		layout.addView(mProgress);
		layout.addView(mProgressText);
		
		builder.setView(layout);
		builder.setPositiveButton("取消", new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
		        if (mDownloadManager.mHttpHandler != null) {
		            mDownloadManager.mHttpHandler.cancel();
		        }
			}
		});
		builder.setNegativeButton("后台运行", new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				downloadDialog.dismiss();
				
				final int notifiId = new Random().nextInt(10000);
				
				// 注册广播
				mContext.registerReceiver(new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						showDialog();
						mContext.unregisterReceiver(this);
					}
				}, new IntentFilter(actionShowDownload));
				
				// 显示通知栏消息
				new NotificationUtils(mContext).putBroadcast(new Intent(actionShowDownload),
				        "版本更新", "正在下载最新版本...", iconResId, true, notifiId);
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
		        if (mDownloadManager.mHttpHandler != null) {
		            mDownloadManager.mHttpHandler.cancel();
		        }
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.setCancelable(false);
		downloadDialog.show();
	}
	
	/**
	 * 执行下载
	 */
	private void downloadApk(){
	    File apkFile = getApkSaveFile();
	    if (apkFile == null) {
	        return;
	    }
	    
		// 回掉函数
        RequestCallBack<File> callback = new RequestCallBack<File>() {
            @Override
            public void onStart() {
                mHandler.sendEmptyMessage(DOWN_START);
            }
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                // 当前下载文件大小、进度值
                tmpFileSize = StringUtils.formatFileSize(current);
                if (total != -1) {
                    apkFileSize = StringUtils.formatFileSize(total);
                    progress = (int) (((float) current / (float) total) * 100);
                } else {
                    apkFileSize = "未知";
                    progress = (int) (((float) current / 1024 / 1024 / 20) * 100);
                }
                // 更新进度
                mHandler.sendEmptyMessage(DOWN_UPDATE);
            }
            @Override
            public void onCancelled() {
            }
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                File file = responseInfo.result;
                if (file != null && file.exists()) {
                    File apkFile = getApkSaveFile();
                    file.renameTo(apkFile);
                    
                    Message message = mHandler.obtainMessage(DOWN_INSTALL, apkFile.getAbsolutePath());
                    mHandler.sendMessage(message);
                }
            }
            @Override
            public void onFailure(HttpException error, String msg) {
                Message message = mHandler.obtainMessage(DOWN_HTTP_ERROR, msg);
                mHandler.sendMessage(message);

                AppCore.log(Level.ERROR, error.getExceptionCode(), msg, error);
            }
        };

        boolean autoResume = true;  // 是否续传
        boolean autoRename = false;  // 是否重命名
        HttpManager.get().configHttpRedirectHandler(new DefaultHttpRedirectHandler() {
            @Override
            public HttpRequestBase getDirectRequest(HttpResponse response) {
                return super.getDirectRequest(response);
            }
        });
        
        mHttpHandler = HttpManager.get().download(HttpMethod.GET,
                apkUrl, apkFile.getAbsolutePath()+".tmp",
                null, autoResume, autoRename, callback);
	}
	
	/**
	 * 获取APK文件保存路径
	 * @return 返回空，则不需下载
	 */
	private File getApkSaveFile() {
        // apk保存完整路径
	    File apkFile = null;
        
        //判断是否挂载了SD卡
        String storageState = Environment.getExternalStorageState();        
        if(Environment.MEDIA_MOUNTED.equals(storageState)){
            File file = new File(savePath);
            if(!file.exists()){
                file.mkdirs();
            }
            apkFile = new File(savePath, apkName);
        } else {  //没有挂载SD卡，无法下载文件
            mHandler.sendEmptyMessage(DOWN_NOSDCARD);
            return null;
        }
        
        //是否已下载更新文件
        if(apkFile.exists()){  // 文件已存在
            Message message = mHandler.obtainMessage(DOWN_INSTALL, apkFile.getAbsolutePath());
            mHandler.sendMessage(message);
            return null;
        }
        
        // 判断网络是否连接
        if (!NetworkUtils.isConnected(mContext)) {  // 没有网络
            mHandler.sendEmptyMessage(DOWN_NOT_NETWORK);
            return null;
        }
        
        // 判断URL是否存在
        if (TextUtils.isEmpty(apkUrl)) {  // 找不到文件
            // 找不到文件
            mHandler.sendEmptyMessage(DOWN_NOT_OPERATE_FILE);
            return null;
        }
        
        return apkFile;
	}
	
	/**
    * 安装apk
    * @param apkFile
    */
	private void installApk(File apkFile){
        if (apkFile == null || !apkFile.exists()) {
            return;
        }
		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= 28) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri	contentUri = FileProvider.getUriForFile(mContext, "com.liicon.gisbase.provider" , apkFile);
//			Uri contentUri = FileProvider.getUriForFile(mContext.getApplicationContext(),
//					BuildConfig.APPLICATION_ID+".fileProvider", apkFile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()), "application/vnd.android.package-archive");
//			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		mContext.startActivity(intent);
	}
	
}
