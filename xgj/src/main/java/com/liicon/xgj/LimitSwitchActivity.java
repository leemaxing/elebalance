package com.liicon.xgj;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liicon.xgj.base.BaseActivity;

/**
 * 极限开关
 */
public class LimitSwitchActivity extends BaseActivity {
	private double HD, HJ, HM, HX, JS, L, JX, HS;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_switch);

		setNavigationBar();
		setTitle("极限开关");
        
        View button1 = this.findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				calculate();
			}
		});
        
        Bundle bundle =getIntent().getExtras();
        if(bundle!=null){
        	((EditText)findViewById(R.id.editText1)).setText( bundle.getString("HD"));
        	((EditText)findViewById(R.id.editText2)).setText( bundle.getString("HJ"));
        	((EditText)findViewById(R.id.editText4)).setText( bundle.getString("HX"));
        	((EditText)findViewById(R.id.editText3)).setText( bundle.getString("HM"));
        }
    }

    private void calculate() {
    	EditText edit_HD = (EditText)findViewById(R.id.editText1);
    	EditText edit_HJ = (EditText)findViewById(R.id.editText2);
    	EditText edit_HM = (EditText)findViewById(R.id.editText3);
    	EditText edit_HX = (EditText)findViewById(R.id.editText4);
    	EditText edit_JS = (EditText)findViewById(R.id.editText5);
    	EditText edit_L = (EditText)findViewById(R.id.editText6);
    	EditText edit_JX = (EditText)findViewById(R.id.editText7);
    	EditText edit_HS = (EditText)findViewById(R.id.editText8);
    	
    	//判断输入框是否没填值
    	boolean allHaveDate=true; //是否每个输入数字
    	if(edit_HD.getText().length()== 0){
    		edit_HD.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_HD.setError(null);
    		HD=Double.parseDouble(edit_HD.getText().toString());
    	}
    	if(edit_HJ.getText().length()== 0){
    		edit_HJ.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_HJ.setError(null);
    		HJ=Double.parseDouble(edit_HJ.getText().toString());
    	}
    	if(edit_HM.getText().length()== 0){
    		edit_HM.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_HM.setError(null);
    		HM=Double.parseDouble(edit_HM.getText().toString());
    	}
    	if(edit_HX.getText().length()== 0){
    		edit_HX.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_HX.setError(null);
    		HX=Double.parseDouble(edit_HX.getText().toString());
    	}
    	if(edit_JS.getText().length()== 0){
    		edit_JS.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_JS.setError(null);
    		JS=Double.parseDouble(edit_JS.getText().toString());
    	}
    	if(edit_L.getText().length()== 0){
    		edit_L.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_L.setError(null);
    		L=Double.parseDouble(edit_L.getText().toString());
    	}
    	if(edit_JX.getText().length()== 0){
    		edit_JX.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_JX.setError(getString(R.string.tips_dont_reason));
    		JX=Double.parseDouble(edit_JX.getText().toString());
    	}
    	if(edit_HS.getText().length()== 0){
    		edit_HS.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		}
    	else {
    		edit_HS.setError(null);
    		HS=Double.parseDouble(edit_HS.getText().toString());
    	}
    	String result="";
		if(allHaveDate){
			if(!(JS<HS&&HS<=HM)){
				result = result+"不满足JS<HS≤HM\n";
			}
			if(!(JX<HX)){
				result = result+"不满足JX<HX\n";
			}
			if(!(HM+HD<JS+L)){
				result = result+"不满足HM+HD<JS+L\n";
			}
			if(!(HX+HJ<JX+L)){
				result = result+"不满足HX+HJ<JX+L\n";
			}
			if(result.equals("")){
				result="满足四式要求";
			}
			((TextView)findViewById(R.id.editText9)).setText(result);
		}
		else{
			Toast.makeText(this, "存在空值(已经用红色标出)", Toast.LENGTH_SHORT).show();
		}
	}
    
}
