package com.liicon.xgj;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.liicon.utils.StringUtils;
import com.liicon.xgj.base.BaseActivity;

/**
 * 限速器
 */
public class XianSuQiActivity extends BaseActivity {

	private double A = 0;
	private double C = 0;
	private double E = 0;
	private double F = 0;
	private double G = 0;
	private double V = 0;
	private Spinner s1;
	private Spinner s2;
	private int unit = 1;
	private int AQQ_FS = 0;
	private int BH_ZZ = 0;
	Drawable drawable;
	Drawable drawable2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xian_su_qi);

		setNavigationBar();
		setTitle("限速器");

		drawable = ((EditText) findViewById(R.id.editText9)).getBackground();
		drawable2 = ((EditText) findViewById(R.id.editText1)).getBackground();

		/**
		 * 设置第一个下拉框
		 */
		s1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.saft, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adapter);
		s1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// showToast("Spinner1: position=" + arg2 + " id=" + arg3);
				AQQ_FS = arg2;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		/**
		 * 设置第二个下拉框
		 */
		s2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.protect, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(adapter2);
		s2.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				BH_ZZ = arg2;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		/**
		 * 设置计算按钮
		 */
		View buttonStart1 = (View) findViewById(R.id.buttonStart1);
		buttonStart1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculate();
			}
		});
		View button1 = (View) findViewById(R.id.button1);

		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openNewActivity();
			}
		});
	}

	private void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private void openNewActivity() {
		Intent i = new Intent(this, UpActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("averageSpeed",
				((EditText) findViewById(R.id.editText9)).getText().toString());
		i.putExtras(bundle);
		this.startActivity(i);
	}

	private void calculate() {
		/**
		 * 获取输入框的值
		 */
		EditText deit1 = (EditText) findViewById(R.id.editText1);
		EditText deit3 = (EditText) findViewById(R.id.editText3);
		EditText deit5 = (EditText) findViewById(R.id.editText5);
		EditText deit6 = (EditText) findViewById(R.id.editText6);
		EditText deit7 = (EditText) findViewById(R.id.editText7);
		EditText deit9 = (EditText) findViewById(R.id.editText9);
		RadioButton radiobutton1 = (RadioButton) findViewById(R.id.radio0);

    	A = StringUtils.toDouble(deit1.getText().toString(), 0);
    	C = StringUtils.toDouble(deit3.getText().toString(), 0);
    	E = StringUtils.toDouble(deit5.getText().toString(), 0);
    	F = StringUtils.toDouble(deit6.getText().toString(), 0);
    	G = StringUtils.toDouble(deit7.getText().toString(), 0);
    	V = StringUtils.toDouble(deit9.getText().toString(), 0);
		unit = radiobutton1.isChecked() ? 1 : 60;

		/**
		 * 判断是否有空值
		 */
		boolean allHaveDate = true; // 是否每个输入数字
		if (A == 0) {
			deit1.setError("不能为空");
			allHaveDate = false;
		}
		/*
		 * if(B==0) { deit2.setBackgroundResource(R.color.color1);
		 * allHaveDate=false; } else { deit2.setBackgroundDrawable(drawable2);
		 * 
		 * }
		 */
		if (C == 0) {
			deit3.setError("不能为空");
			allHaveDate = false;
		}
		/*
		 * if(D==0){ deit4.setBackgroundDrawable(drawable2); allHaveDate=false;
		 * } else{ deit4.setBackgroundResource(R.color.color2); }
		 */
		if (E == 0) {
			deit5.setError("不能为空");
			allHaveDate = false;
		}

		if (BH_ZZ == 2 && F == 0) {
			deit6.setError("不能为空");
			allHaveDate = false;
		}

		if (G == 0) {
			if (BH_ZZ == 3) {
			} else {
				deit7.setError("不能为空");
				allHaveDate = false;
			}
		}
		/*
		 * if(H==0){ deit8.setBackgroundDrawable(drawable2); allHaveDate=false;
		 * } else{ deit8.setBackgroundResource(R.color.color1); }
		 */
		if (V == 0) {
			deit9.setError("不能为空");
			allHaveDate = false;
		}

		/**
		 * 判断逻辑
		 */
		boolean allOK = true;
		if (allHaveDate == true) {
			A /= unit;
			C /= unit;
			E /= unit;
			F /= unit;
			G /= unit;
			V /= unit;
			switch (AQQ_FS) {
			case 0:
				// 轿厢侧限速器
				if (E > 1.15 * V && E < 0.8) {
				} else {
					deit5.setError("数据不合理");
					allOK = false;
				}

				break;
			case 1:
				// 轿厢侧限速器
				if (E > 1.15 * V && E < 1.0) {
				} else {
					deit5.setError("数据不合理");
					allOK = false;
				}

				break;
			case 2:
				// 轿厢侧限速器
				if (V <= 1.0) {
					if (E > 1.15 * V && E < 1.5) {
					} else {
						deit5.setError("数据不合理");
						allOK = false;
					}
				} else {
					if (E > 1.15 * V && E < 1.25 * V + 0.25 / V) {
					} else {
						deit5.setError("数据不合理");
						allOK = false;
					}
				}

				break;
			}
			switch (BH_ZZ) {
			case 0:
				switch (AQQ_FS) {
				case 0:
					if (G > 1.15 * V && G < 0.8) {
					} else {
						deit7.setError("数据不合理");
						allOK = false;
					}
					break;
				case 1:
					if (G > 1.15 * V && G < 1.0) {
					} else {
						deit7.setError("数据不合理");
						allOK = false;
					}
					break;
				case 2:
					if (G > 1.15 * V && G < 1.5) {
					} else {
						deit7.setError("数据不合理");
						allOK = false;
					}
					break;
				}

				break;
			case 1:
				if (G > 1.15 * V && G < 1.1 * E) {
				} else {
					deit7.setError("数据不合理");
					allOK = false;
				}
				break;
			case 2:
				if (G > E && G < 1.1 * E) {
				} else {
					deit7.setError("数据不合理");
					allOK = false;
				}
				break;
			case 3:
				G = E;
				break;
			}
			// 轿厢侧电器开关
			if (V <= 1) {
				// 电器下
				if (A <= E) {
				} else {
					deit1.setError("数据不合理");
					allOK = false;
				}
				// 电器上
				if (C <= G) {
				} else {
					deit3.setError("不能为空");
					allOK = false;
				}

			} else {
				// 电器下
				if (A < E) {
				} else {
					deit1.setError("不能为空");
					allOK = false;
				}
				// 电器上

				if (C < G) {
				} else {
					deit3.setError("不能为空");
					allOK = false;
				}

			}
			// 对重侧限速器
			if (BH_ZZ == 2) {
				if (F > E && F <= 1.1 * E) {
				} else {
					deit6.setError("不能为空");
					allOK = false;
				}
			}
		}

		if (allOK && allHaveDate) {
			showToast("数据符合规范");
		} else {
			if (!allHaveDate) {
				showToast("存在空(已经用红色标出)");
			} else {
				showToast("数据不符合规范(已经用红色标出)");
			}
		}
	}
}
