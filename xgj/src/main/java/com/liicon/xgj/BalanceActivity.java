package com.liicon.xgj;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.liicon.xgj.base.BaseActivity;

/**
 * 平衡系数
 */
public class BalanceActivity extends BaseActivity {
	private double a, b, c, d;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);

		setNavigationBar();
		setTitle("平衡系数");

		View button1 = this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculate();
			}
		});
	}

	private void calculate() {
		EditText edit_1 = (EditText) findViewById(R.id.editText1);
		EditText edit_2 = (EditText) findViewById(R.id.editText2);
		EditText edit_3 = (EditText) findViewById(R.id.editText3);
		EditText edit_4 = (EditText) findViewById(R.id.editText4);

		// 判断输入框是否没填值

		boolean allHaveDate = true; // 是否每个输入数字
		if (edit_1.getText().length() == 0) {
			edit_1.setError(getString(R.string.tips_dont_null));
			allHaveDate = false;
		} else {
			edit_1.setError(null);
			a = Double.parseDouble(edit_1.getText().toString());
		}
		if (edit_2.getText().length() == 0) {
			edit_2.setError(getString(R.string.tips_dont_null));
			allHaveDate = false;
		} else {
			edit_2.setError(null);
			b = Double.parseDouble(edit_2.getText().toString());
		}
		if (edit_3.getText().length() == 0) {
			edit_3.setError(getString(R.string.tips_dont_null));
			allHaveDate = false;
		} else {
			edit_3.setError(null);
			c = Double.parseDouble(edit_3.getText().toString());
		}
		if (edit_4.getText().length() == 0) {
			edit_4.setError(getString(R.string.tips_dont_null));
			allHaveDate = false;
		} else {
			edit_4.setError(null);
			d = Double.parseDouble(edit_4.getText().toString());
		}
		if (allHaveDate) {
			if ((a >= c && b <= d) || (a <= c && b >= d)) {
				Toast.makeText(this, "存在交点", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "不存在交点", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "存在空值(已经用红色标出)", Toast.LENGTH_SHORT).show();
		}
	}
}
