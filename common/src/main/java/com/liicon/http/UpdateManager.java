package com.liicon.http;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liicon.application.BasicAppHelper;

import com.liicon.json.Json;
import com.liicon.json.JsonArray;
import com.liicon.json.JsonObject;

import com.liicon.volley.request.JsonRequest;

import cn.trinea.android.common.util.ShellUtils;
import cn.trinea.android.common.util.StringUtils;

/**
 * 应用程序更新工具包
 * @version  v0.1  king  2013.09.26  实现应用程序更新功能
 * @version  v0.1  king  2014.12.22  数据解析类改用接口
 */
public class UpdateManager {
    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;
    
    public static boolean existUploadReport=false;
    
    public static interface OnUpdateListener {
        void parseResponse(JsonObject response);
        int getVersionCode();
        String getDownloadUrl();
        String getUpdateLog();
        String getVersionName();
        boolean checkUpdate(int currentVersionCode, int newVersionCode);
    }
	
	private Context mContext;
	//通知对话框
	private Dialog noticeDialog;
	//'已经是最新' 或者 '无法获取最新版本' 的对话框
	private Dialog latestOrFailDialog;
    //查询动画
    private ProgressDialog mProDialog;
	
	private String curVersionName = "";
	private int curVersionCode;
	private OnUpdateListener mOnUpdate;
	private boolean isMustUpdate;

	/**
	 * 更新提示信息
	 */
	private String updateMsg;
	/**
	 * Apk下载url
	 */
	private String apkUrl = "";
	
	public UpdateManager(OnUpdateListener update) {
	    mOnUpdate = update;

	}
	
	/**
	 * 检查App更新
	 * @param context
	 * @param iconResId ICON的资源ID
	 * @param url 获取版本信息的URL
	 * @param isShowMsg 是否显示“正在检测”提示
	 */
	public void checkAppUpdate(Context context, final int iconResId, final String url,
							   final boolean isShowMsg){
		this.mContext = context;
		getCurrentVersion();
		if(isShowMsg){
			if(mProDialog == null){
				Handler handler=new Handler();
				handler.post(new Runnable(){

					@Override
					public void run() {
						mProDialog = ProgressDialog.show(mContext, null, "正在检测，请稍后...", true, true);						
					}
				});
				
				
				
			}else if(mProDialog.isShowing() || (latestOrFailDialog!=null && latestOrFailDialog.isShowing()))
				return;
		}

		// 加载数据
		BasicAppHelper.get().loadNoCache(new JsonRequest<JsonObject>(url, null, new Response.Listener<JsonObject>() {
			@Override
			public void onResponse(JsonObject response) {
				// 关闭进度条
				closeProgressDialog();
				
				// 处理结果
				try {
					mOnUpdate.parseResponse(response);
				} catch (Exception e) { }
				// 显示检测结果
				if (mOnUpdate != null) {
					if (mOnUpdate.checkUpdate(curVersionCode, mOnUpdate.getVersionCode())) {
						
						if(existUploadReport){							
							showUploadReportDialog(iconResId);
							return;
						}
						JsonArray mes = response.getJSONArray("tData");
						String message = "";
						String version = "";
						if (!mes.isEmpty(0)){
							message = (String) mes.getJSONArray(0).get(0);
							version = (String) mes.getJSONArray(1).get(0);
						}
						isMustUpdate = checIsMustUpdate(version);
						toUpdate(iconResId,message);
					} else if (isShowMsg) {
						showLatestOrFailDialog(DIALOG_TYPE_LATEST);
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				// 处理结果
				if (isShowMsg) {
					showLatestOrFailDialog(DIALOG_TYPE_FAIL);
				}
				// 关闭进度条
				closeProgressDialog();
			}
		}));
	}
	
	private void toUpdate(final int iconResId,String message){
		String mss = message.replace("#必须更新#","");
		apkUrl = mOnUpdate.getDownloadUrl();
		updateMsg = "\n" + mOnUpdate.getUpdateLog() + "\n"
					+ "\n当前版本：" + curVersionName + "\n"
				+ "\n更新功能：" + message + "\n";
//		检查是否需要强制更新
		isMustUpdate = message.indexOf("#必须更新#") != -1;
		showNoticeDialog(iconResId);
	}
	/**
	 * 关闭并释放释放进度条对话框
	 */
	private void closeProgressDialog() {
		// 关闭并释放释放进度条对话框
		if (mProDialog != null) {
			try {
				mProDialog.dismiss();
			} catch (Exception e) { }
			mProDialog = null;
		}
	}
	
	/**
	 * 显示'已经是最新'或者'无法获取版本信息'对话框
	 */
	private void showLatestOrFailDialog(int dialogType) {
		if (latestOrFailDialog != null) {
			//关闭并释放之前的对话框
			latestOrFailDialog.dismiss();
			latestOrFailDialog = null;
		}
		Builder builder = new Builder(mContext);
		builder.setTitle("系统提示");
		if (dialogType == DIALOG_TYPE_LATEST) {
			builder.setMessage("您当前已经是最新版本");
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			builder.setMessage("无法获取版本更新信息");
		}
		builder.setPositiveButton("确定", null);
		latestOrFailDialog = builder.create();
		latestOrFailDialog.show();
		
		
	}

	/**
	 * 获取当前客户端版本信息
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	curVersionName = info.versionName;
        	curVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * 显示版本更新通知对话框
	 */
	private void showNoticeDialog(final int iconResId){

		Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				// 保存的文件名
				String apkName = mContext.getPackageName()+"_"+mOnUpdate.getVersionName();
				DownloadManager.getUpdateManager(mContext, iconResId, "正在下载新版本", "Update").download(apkUrl, apkName);
			}
		});
		if (!isMustUpdate){
			builder.setNegativeButton("以后再说", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
		}
		builder.setCancelable(!isMustUpdate);
		noticeDialog = builder.create();
		if (mContext instanceof Activity && !((Activity)mContext).isFinishing()) {
			noticeDialog.show();
		}
	}


    boolean checIsMustUpdate(String serverVersion){

		boolean mustUpdate = false;

		if (!StringUtils.isEmpty(serverVersion))
		{
			String[] serStrings = serverVersion.split("\\.");
			String[] loacaolStrings = curVersionName.split("\\.");
			mustUpdate = serStrings[0].compareTo(loacaolStrings[0]) == 1;
		}
        return mustUpdate;
    }

	/**
	 * 显示上传报告对话框
	 */
	private void showUploadReportDialog(final int iconResId){
		Builder builder = new Builder(mContext);
		builder.setTitle("版本更新");
		builder.setMessage("存在未上传的报告,版本更新前,请先登录上传");//
		builder.setPositiveButton("登录上传", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
//		builder.setNegativeButton("更新版本", new OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//			
//				toUpdate(iconResId);
//				dialog.dismiss();
//			}
//		});
		noticeDialog = builder.create();
		noticeDialog.show();
		if (mContext instanceof Activity && !((Activity)mContext).isFinishing()) {
			noticeDialog.show();
		}
	}
}
