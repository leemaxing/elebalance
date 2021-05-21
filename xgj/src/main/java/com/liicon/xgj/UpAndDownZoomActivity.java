package com.liicon.xgj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.liicon.xgj.base.BaseActivity;

import java.text.DecimalFormat;

public class UpAndDownZoomActivity extends BaseActivity {
	private double V, h1, l1, a, b, c, P, d, e, f, l2, h2, A, L, P1, P2, P3,
			P4, P5, P6, P7, P8, P_l1_h1, B, C, D, E, F, G1, G2, G3;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_up_and_down_zoom);
		((EditText) findViewById(R.id.editText30)).requestFocus();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null && bundle.containsKey("averageSpeed")) {
			((EditText) findViewById(R.id.editText30)).setText(bundle
					.getString("averageSpeed"));
		}

		View button1 = this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				calculate();
			}
		});
		View button2 = this.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openNewActivity();
			}
		});

	}

	private void openNewActivity() {

		Intent i = new Intent(this, LimitSwitchActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("HD", ((EditText) findViewById(R.id.editText2))
				.getText().toString());
		bundle.putString("HJ", ((EditText) findViewById(R.id.editText19))
				.getText().toString());
		bundle.putString("HX", ((EditText) findViewById(R.id.editText21))
				.getText().toString());
		bundle.putString("HM", ((EditText) findViewById(R.id.editText1))
				.getText().toString());
		i.putExtras(bundle);
		this.startActivity(i);
	}

	private void calculate() {
		// 用于输入
		EditText edit_h1 = (EditText) findViewById(R.id.editText1);
		EditText edit_l1 = (EditText) findViewById(R.id.editText2);
		EditText edit_a = (EditText) findViewById(R.id.editText13);
		EditText edit_b = (EditText) findViewById(R.id.editText14);
		EditText edit_c = (EditText) findViewById(R.id.editText15);
		EditText edit_P = (EditText) findViewById(R.id.editText3);
		EditText edit_P1 = (EditText) findViewById(R.id.editText5);
		EditText edit_P2 = (EditText) findViewById(R.id.editText6);
		EditText edit_P3 = (EditText) findViewById(R.id.editText7);
		EditText edit_P4 = (EditText) findViewById(R.id.editText8);
		EditText edit_d = (EditText) findViewById(R.id.editText16);
		EditText edit_e = (EditText) findViewById(R.id.editText17);
		EditText edit_f = (EditText) findViewById(R.id.editText18);
		EditText edit_l2 = (EditText) findViewById(R.id.editText19);
		EditText edit_h2 = (EditText) findViewById(R.id.editText21);
		EditText edit_A = (EditText) findViewById(R.id.editText20);
		EditText edit_L = (EditText) findViewById(R.id.editText31);
		EditText edit_P5 = (EditText) findViewById(R.id.editText26);
		EditText edit_P6 = (EditText) findViewById(R.id.editText27);
		EditText edit_P7 = (EditText) findViewById(R.id.editText28);
		EditText edit_V = (EditText) findViewById(R.id.editText30);
		RadioButton radiobutton1 = (RadioButton) findViewById(R.id.radioButton1);

		DecimalFormat dcmFmt = new DecimalFormat("0.000");

		// 判断输入框是否没填值
		String result = "";
		boolean isAllOK = true;
		boolean allHaveDate = true; // 是否每个输入数字
		if (edit_h1.getText().length() == 0) {
			edit_h1.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_h1.setBackgroundResource(R.color.color4);
			h1 = Double.parseDouble(edit_h1.getText().toString());
		}
		if (edit_l1.getText().length() == 0) {
			edit_l1.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_l1.setBackgroundResource(R.color.color4);
			l1 = Double.parseDouble(edit_l1.getText().toString());
		}
		if (edit_a.getText().length() == 0) {
			edit_a.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_a.setBackgroundResource(R.color.color4);
			a = Double.parseDouble(edit_a.getText().toString());
		}
		if (edit_b.getText().length() == 0) {
			edit_b.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_b.setBackgroundResource(R.color.color4);
			b = Double.parseDouble(edit_b.getText().toString());
		}
		if (edit_c.getText().length() == 0) {
			edit_c.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_c.setBackgroundResource(R.color.color4);
			c = Double.parseDouble(edit_c.getText().toString());
		}
		if (edit_P.getText().length() == 0) {
			edit_P.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P.setBackgroundResource(R.color.color4);
			P = Double.parseDouble(edit_P.getText().toString());
		}
		if (edit_P1.getText().length() == 0) {
			edit_P1.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P1.setBackgroundResource(R.color.color4);
			P1 = Double.parseDouble(edit_P1.getText().toString());
		}
		if (edit_P2.getText().length() == 0) {
			edit_P2.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P2.setBackgroundResource(R.color.color4);
			P2 = Double.parseDouble(edit_P2.getText().toString());
		}
		if (edit_P3.getText().length() == 0) {
			edit_P3.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P3.setBackgroundResource(R.color.color4);
			P3 = Double.parseDouble(edit_P3.getText().toString());
		}
		if (edit_P4.getText().length() == 0) {
			edit_P4.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P4.setBackgroundResource(R.color.color4);
			P4 = Double.parseDouble(edit_P4.getText().toString());
		}
		if (edit_d.getText().length() == 0) {
			edit_d.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_d.setBackgroundResource(R.color.color4);
			d = Double.parseDouble(edit_d.getText().toString());
		}
		if (edit_e.getText().length() == 0) {
			edit_e.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_e.setBackgroundResource(R.color.color4);
			e = Double.parseDouble(edit_e.getText().toString());
		}
		if (edit_f.getText().length() == 0) {
			edit_f.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_f.setBackgroundResource(R.color.color4);
			f = Double.parseDouble(edit_f.getText().toString());
		}
		if (edit_l2.getText().length() == 0) {
			edit_l2.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_l2.setBackgroundResource(R.color.color4);
			l2 = Double.parseDouble(edit_l2.getText().toString());
		}
		if (edit_h2.getText().length() == 0) {
			allHaveDate = false;
			edit_h2.setBackgroundResource(R.color.color3);
		} else {
			edit_h2.setBackgroundResource(R.color.color4);
			h2 = Double.parseDouble(edit_h2.getText().toString());
		}
		if (edit_A.getText().length() == 0) {
			edit_A.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_A.setBackgroundResource(R.color.color4);
			A = Double.parseDouble(edit_A.getText().toString());
		}
		if (edit_L.getText().length() == 0) {
			edit_L.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_L.setBackgroundResource(R.color.color4);
			L = Double.parseDouble(edit_L.getText().toString());
		}
		if (edit_P5.getText().length() == 0) {
			edit_P5.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P5.setBackgroundResource(R.color.color4);
			P5 = Double.parseDouble(edit_P5.getText().toString());
		}
		if (edit_P6.getText().length() == 0) {
			edit_P6.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P6.setBackgroundResource(R.color.color4);
			P6 = Double.parseDouble(edit_P6.getText().toString());
		}
		if (edit_P7.getText().length() == 0) {
			edit_P7.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_P7.setBackgroundResource(R.color.color4);
			P7 = Double.parseDouble(edit_P7.getText().toString());
		}
		/*
		 * if(edit_P8.getText().length()== 0) {
		 * edit_P8.setBackgroundResource(R.color.color3); allHaveDate=false; }
		 * else { edit_P8.setBackgroundResource(R.color.color4);
		 * P8=Double.parseDouble(edit_P8.getText().toString()); }
		 */
		if (edit_V.getText().length() == 0) {
			edit_V.setBackgroundResource(R.color.color3);
			allHaveDate = false;
		} else {
			edit_V.setBackgroundResource(R.color.color4);
			V = Double.parseDouble(edit_V.getText().toString());
		}

		// 用于输出
		EditText edit_P_h1_l1 = (EditText) findViewById(R.id.editText4);
		EditText edit_B = (EditText) findViewById(R.id.editText9);
		EditText edit_C = (EditText) findViewById(R.id.editText10);
		EditText edit_D = (EditText) findViewById(R.id.editText11);
		EditText edit_E = (EditText) findViewById(R.id.editText12);
		EditText edit_G1 = (EditText) findViewById(R.id.editText22);
		EditText edit_G2 = (EditText) findViewById(R.id.editText23);
		EditText edit_G3 = (EditText) findViewById(R.id.editText24);
		EditText edit_F = (EditText) findViewById(R.id.editText25);

		if (allHaveDate) {
			double T = 0.035 * V * V;
			if (radiobutton1.isChecked()) {
				if (V <= 4) {
					T = 0.035 * V * V / 2;
					if (T < 0.25)
						T = 0.25;
				} else {
					T = 0.035 * V * V / 3;
					if (T < 0.28)
						T = 0.28;
				}
			}
			P_l1_h1 = P - l1 - h1;
			P_l1_h1 = Double.parseDouble(dcmFmt.format(P_l1_h1));
			edit_P_h1_l1.setText(String.valueOf(P_l1_h1));

			B = P1 - l1 - h1;
			B = Double.parseDouble(dcmFmt.format(B));
			if (B < 0.1 + T) {
				edit_B.setBackgroundResource(R.color.color3);
				isAllOK = false;
			} else {
				edit_B.setBackgroundResource(R.color.color4);

			}
			edit_B.setText(String.valueOf(B));
			C = P2 - l1 - h1;
			C = Double.parseDouble(dcmFmt.format(C));
			if (C < 1.0 + T) {
				edit_C.setBackgroundResource(R.color.color3);
				isAllOK = false;
			} else {
				edit_C.setBackgroundResource(R.color.color4);

			}
			edit_C.setText(String.valueOf(C));
			D = P3 - l1 - h1;
			D = Double.parseDouble(dcmFmt.format(D));
			if (D < 0.3 + T) {
				edit_D.setBackgroundResource(R.color.color3);
				isAllOK = false;
			} else {
				edit_D.setBackgroundResource(R.color.color4);

			}
			edit_D.setText(String.valueOf(D));
			E = P4 - l1 - h1;
			E = Double.parseDouble(dcmFmt.format(E));
			if (E < 0.1 + T) {
				edit_E.setBackgroundResource(R.color.color3);
				isAllOK = false;
			} else {
				edit_E.setBackgroundResource(R.color.color4);

			}
			edit_E.setText(String.valueOf(E));
			double t;
			if (a > b) {
				t = a;
				a = b;
				b = t;
			}
			if (a > c) {
				t = a;
				a = c;
				c = t;
			}
			if (b > c) {
				t = b;
				b = c;
				c = t;
			}

			if (a < 0.5 || b < 0.6 || c < 0.8) {
				edit_a.setBackgroundResource(R.color.color3);
				edit_b.setBackgroundResource(R.color.color3);
				edit_c.setBackgroundResource(R.color.color3);
				isAllOK = false;
				result = result + "轿顶空间应大于0.5*0.6*0.8\n";
			} else {
				edit_a.setBackgroundResource(R.color.color4);
				edit_b.setBackgroundResource(R.color.color4);
				edit_c.setBackgroundResource(R.color.color4);
			}
			if (A >= 0.1 + T) {
				edit_A.setBackgroundResource(R.color.color4);
			} else {
				edit_A.setBackgroundResource(R.color.color3);
				isAllOK = false;
			}
			if (d > e) {
				t = d;
				d = e;
				e = t;
			}
			if (d > f) {
				t = d;
				d = f;
				f = t;
			}
			if (e > f) {
				t = e;
				e = f;
				f = t;
			}
			if (d < 0.5 || e < 0.6 || f < 1.0) {
				edit_d.setBackgroundResource(R.color.color3);
				edit_e.setBackgroundResource(R.color.color3);
				edit_f.setBackgroundResource(R.color.color3);
				isAllOK = false;
				result = result + "底坑空间应大于0.5*0.6*1.0\n";
			} else {
				edit_d.setBackgroundResource(R.color.color4);
				edit_e.setBackgroundResource(R.color.color4);
				edit_f.setBackgroundResource(R.color.color4);
			}
			G1 = P5 - l2 - h2;
			G2 = P6 - l2 - h2;
			G3 = P7 - l2 - h2;
			F = P8 - l2 - h2;
			G1 = Double.parseDouble(dcmFmt.format(G1));
			G2 = Double.parseDouble(dcmFmt.format(G2));
			G3 = Double.parseDouble(dcmFmt.format(G3));
			F = Double.parseDouble(dcmFmt.format(F));
			edit_G1.setText(String.valueOf(G1));
			edit_G2.setText(String.valueOf(G2));
			edit_G3.setText(String.valueOf(G3));
			edit_F.setText(String.valueOf(F));
			if (F >= 0.3) {
				edit_F.setBackgroundResource(R.color.color4);
			} else {
				edit_F.setBackgroundResource(R.color.color3);
				isAllOK = false;
			}
			double y = 1.143 * L - 0.071;// 允许值

			if (A <= 0.15 && L <= 0.15) {
				if (G1 >= 0.5) {
					edit_G1.setBackgroundResource(R.color.color4);
				} else {
					edit_G1.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
				if (G2 >= 0.1) {
					edit_G2.setBackgroundResource(R.color.color4);
				} else {
					edit_G2.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
			} else if (A <= 0.15 && (L > 0.15 && L <= 0.5)) {
				if (G1 >= 0.5) {
					edit_G1.setBackgroundResource(R.color.color4);
				} else {
					edit_G1.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
				if (G2 >= 0.1) {
					edit_G2.setBackgroundResource(R.color.color4);
				} else {
					edit_G2.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
				if (G3 >= y) {
					edit_G3.setBackgroundResource(R.color.color4);
				} else {
					edit_G3.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
			} else if (A > 0.15 && L <= 0.15) {
				if (G1 >= 0.5) {
					edit_G1.setBackgroundResource(R.color.color4);
				} else {
					edit_G1.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
				if (G2 >= 0.1) {
					edit_G2.setBackgroundResource(R.color.color4);
				} else {
					edit_G2.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
			} else if (A > 0.15 && (L > 0.15 && L <= 0.5)) {
				if (G1 >= 0.5) {
					edit_G1.setBackgroundResource(R.color.color4);
				} else {
					edit_G1.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}

				if (G3 >= y) {
					edit_G3.setBackgroundResource(R.color.color4);
				} else {
					edit_G3.setBackgroundResource(R.color.color3);
					isAllOK = false;
				}
			}
			if (!isAllOK) {
				result = result + "存在数据不符合要求";
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "数据符合要求", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "存在空值(已经用红色标出)", Toast.LENGTH_SHORT).show();
		}

	}

}
