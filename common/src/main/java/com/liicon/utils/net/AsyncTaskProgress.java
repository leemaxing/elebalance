package com.liicon.utils.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 异步操作-基类（继承自AsyncTask）
 * 
 * <p>
 * 会自动弹出关闭ProgressDialog
 * </p>
 * 
 * @param <T> 用来传参的数据类型
 * 
 * @date 2013.06.01
 */
public abstract class AsyncTaskProgress<T> extends AsyncTask<String, String, T> {
	public final int KEY_STATUST = 101;
	public final int KEY_MSG = 102;
	
	
	/** 关闭Dialog */
	protected final String DIALOG_CLOSE = "close";
	/** 进度提示Dialog */
	protected final ProgressDialog proDialog;

	/**
	 * 构造方法
	 * @param context 当前上下文
	 */
	public AsyncTaskProgress(Context context) {
		super();

		this.proDialog = new ProgressDialog(context);
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		
		this.publishProgress(DIALOG_CLOSE);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		this.proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		this.proDialog.setTitle("提示");
		this.proDialog.setMessage("正在发送请求...");
		this.proDialog.setIndeterminate(false);
		this.proDialog.show();
	}
	
	/**
	 * 用来提示更新进度
	 */
	@Override
	protected void onProgressUpdate(final String... values) {
		super.onProgressUpdate(values);
		
		if (values != null && values.length > 0) {
			String msg = values[0];
			if (msg == DIALOG_CLOSE) {
				this.proDialog.cancel();
			} else {
				this.proDialog.setMessage(msg);
			}
		}
	}
	
}
