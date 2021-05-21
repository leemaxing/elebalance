package com.liicon.xgj;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.liicon.xgj.base.BaseActivity;

import java.text.DecimalFormat;

/**
 * 顶部空间
 */
public class UpActivity extends BaseActivity {
	Bundle bundle;
	private double V, h1,l1,a,b,c,P,P1,P2,P3,P4,P_l1_h1,B,C,D,E;
	Drawable drawable;
	Drawable drawable2;
	DecimalFormat dcmFmt = new DecimalFormat("0.000");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);

        setNavigationBar();
        setTitle("顶部空间");

        //设置参考值
        ((EditText)findViewById(R.id.editText30)).addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {	
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calCankao();
			}
        	
        });
        ((RadioButton)findViewById(R.id.radioButton1)).setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				calCankao();
			}  	
        });
        ((EditText)findViewById(R.id.editText5)).addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calB();
			}
        });
        ((EditText)findViewById(R.id.editText6)).addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calC();
			}
        });
        ((EditText)findViewById(R.id.editText7)).addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calD();
			}
        });
        ((EditText)findViewById(R.id.editText8)).addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calE();
			}
        });
        ((EditText)findViewById(R.id.editText3)).addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calP();
			}
        });
        ((EditText)findViewById(R.id.editText1)).addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calB();
				calC();
				calD();
				calE();
				calP();
			}
        });
        ((EditText)findViewById(R.id.editText2)).addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				calB();
				calC();
				calD();
				calE();
				calP();
			}
        });
        //设置额定速度
       
        ((EditText)findViewById(R.id.editText30)).requestFocus();
 
        drawable = ((EditText)findViewById(R.id.editText30)).getBackground();
        drawable2 = ((EditText)findViewById(R.id.editText5)).getBackground();
        bundle =getIntent().getExtras();
        if(bundle!=null&&bundle.containsKey("averageSpeed")){
        ((EditText)findViewById(R.id.editText30)).setText(bundle.getString("averageSpeed"));
        calCankao();
        }
        View button1 = this.findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				calculate();
			}
			});
        View button2 = this.findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				openNewActivity();
			}
			});
    }
    protected void calB(){
    	if(((EditText)findViewById(R.id.editText5)).getText().length()!=0 &&((EditText)findViewById(R.id.editText2)).getText().length()!=0&&((EditText)findViewById(R.id.editText1)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText5)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText2)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText1)).getText().toString());
        	((EditText)findViewById(R.id.editText9)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calC(){
    	if(((EditText)findViewById(R.id.editText6)).getText().length()!=0 &&((EditText)findViewById(R.id.editText2)).getText().length()!=0&&((EditText)findViewById(R.id.editText1)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText6)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText2)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText1)).getText().toString());
        	((EditText)findViewById(R.id.editText10)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calD(){
    	if(((EditText)findViewById(R.id.editText7)).getText().length()!=0 &&((EditText)findViewById(R.id.editText2)).getText().length()!=0&&((EditText)findViewById(R.id.editText1)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText7)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText2)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText1)).getText().toString());
        	((EditText)findViewById(R.id.editText11)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calE(){
    	if(((EditText)findViewById(R.id.editText8)).getText().length()!=0 &&((EditText)findViewById(R.id.editText2)).getText().length()!=0&&((EditText)findViewById(R.id.editText1)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText8)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText2)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText1)).getText().toString());
        	((EditText)findViewById(R.id.editText12)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calP(){
    	if(((EditText)findViewById(R.id.editText3)).getText().length()!=0 &&((EditText)findViewById(R.id.editText2)).getText().length()!=0&&((EditText)findViewById(R.id.editText1)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText3)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText2)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText1)).getText().toString());
        	((EditText)findViewById(R.id.editText4)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calCankao()
    {
    	if(((EditText)findViewById(R.id.editText30)).getText().length() !=0){
			V=Double.parseDouble(((EditText)findViewById(R.id.editText30)).getText().toString());
			double T = 0.035*V*V;
    		if(((RadioButton)findViewById(R.id.radioButton1)).isChecked()){
    			if(V<=4){
    				T=0.035*V*V/2;
    				if(T<0.25) T=0.25;
    			}
    			else{
    				T=0.035*V*V/3;
    				if(T<0.28) T=0.28;
    			}
    		}
			
			((EditText)findViewById(R.id.editText21)).setText(dcmFmt.format(T+0.1));
			((EditText)findViewById(R.id.editText22)).setText(dcmFmt.format(T+1.0));
			((EditText)findViewById(R.id.editText23)).setText(dcmFmt.format(T+0.3));
			((EditText)findViewById(R.id.editText24)).setText(dcmFmt.format(T+0.1));
		}
    }
    protected void openNewActivity() {
		
    	Intent i = new Intent(this, DownActivity.class);
    	bundle = new Bundle();
    	bundle.putString("HD",((EditText)findViewById(R.id.editText2)).getText().toString());
    	bundle.putString("HM",((EditText)findViewById(R.id.editText1)).getText().toString());
    	bundle.putString("averageSpeed",((EditText)findViewById(R.id.editText30)).getText().toString());
    	bundle.putBoolean("isCutDesign", ((RadioButton)findViewById(R.id.radioButton1)).isChecked());
    	i.putExtras(bundle);
		this.startActivity(i);
		
	}

	protected void calculate() {

    	//用于输入
    	EditText edit_h1 = (EditText)findViewById(R.id.editText1);
    	EditText edit_l1 = (EditText)findViewById(R.id.editText2);
    	EditText edit_a = (EditText)findViewById(R.id.editText13);
    	EditText edit_b = (EditText)findViewById(R.id.editText14);
    	EditText edit_c = (EditText)findViewById(R.id.editText15);
    	EditText edit_P = (EditText)findViewById(R.id.editText3);
    	EditText edit_P1 = (EditText)findViewById(R.id.editText5);
    	EditText edit_P2 = (EditText)findViewById(R.id.editText6);
    	EditText edit_P3 = (EditText)findViewById(R.id.editText7);
    	EditText edit_P4 = (EditText)findViewById(R.id.editText8);
    	
    	EditText edit_V = (EditText)findViewById(R.id.editText30);
    	RadioButton radiobutton1 = (RadioButton)findViewById(R.id.radioButton1);
    	
    	
    	
    	//判断输入框是否没填值
    	String result="";
    	boolean isAllOK=true;
    	boolean allHaveDate=true; //是否每个输入数字
    	if(edit_h1.getText().length()== 0){
    		edit_h1.setError(getString(R.string.tips_dont_null)); 
    		allHaveDate=false;
    		h1=0;
    		}
    	else {
    		edit_h1.setBackgroundDrawable(drawable);
    		h1=Double.parseDouble(edit_h1.getText().toString());
    	}
    	if(edit_l1.getText().length()== 0)  {
    		edit_l1.setError(getString(R.string.tips_dont_null)); 
    		allHaveDate=false;
    		l1=0;
    	}
    	else {
    		edit_l1.setBackgroundDrawable(drawable);
    		l1=Double.parseDouble(edit_l1.getText().toString());
    		
    	}
    	
    	
    	if(edit_a.getText().length()== 0)  {
    		//edit_a.setError(getString(R.string.tip_dont_null)); 
    		//allHaveDate=false;
    		a=0;
    	}
    	else {
    		//edit_a.setBackgroundDrawable(drawable);
    		a=Double.parseDouble(edit_a.getText().toString());
    	}
    	if(edit_b.getText().length()== 0)  {
    		//edit_b.setError(getString(R.string.tip_dont_null)); 
    		//allHaveDate=false;
    		b=0;
    	}
    	else {
    		//edit_b.setBackgroundDrawable(drawable);
    		b=Double.parseDouble(edit_b.getText().toString());
    	}
    	if(edit_c.getText().length()== 0)  {
    		//edit_c.setError(getString(R.string.tip_dont_null));
    		//allHaveDate=false;
    		c=0;
    	}
    	else{
    		//edit_c.setBackgroundDrawable(drawable);
    		c=Double.parseDouble(edit_c.getText().toString());
    	}
    	if(edit_P.getText().length()== 0)  {
    		//edit_P.setError(getString(R.string.tip_dont_null)); 
    		//allHaveDate=false;
    		P=0;
    	}
    	else {
    		//edit_P.setBackgroundDrawable(drawable);
    		P=Double.parseDouble(edit_P.getText().toString());
    	}
    	if(edit_P1.getText().length()== 0)  {
    		//edit_P1.setError(getString(R.string.tip_dont_null)); 
    		//allHaveDate=false;
    		P1=0;
    	}
    	else {
    		//edit_P1.setBackgroundDrawable(drawable);
    		P1=Double.parseDouble(edit_P1.getText().toString());
    	}
    	if(edit_P2.getText().length()== 0)  {
    		//edit_P2.setError(getString(R.string.tip_dont_null)); 
    		//allHaveDate=false;
    		P2=0;
    	}
    	else {
    		//edit_P2.setBackgroundDrawable(drawable);
    		P2=Double.parseDouble(edit_P2.getText().toString());
    	}
    	if(edit_P3.getText().length()== 0) {
    		//edit_P3.setError(getString(R.string.tip_dont_null)); 
    		//allHaveDate=false;
    		P3=0;
    	}
    	else {
    		//edit_P3.setBackgroundDrawable(drawable);
    		P3=Double.parseDouble(edit_P3.getText().toString());
    	}
    	if(edit_P4.getText().length()== 0)  {
    		//edit_P4.setError(getString(R.string.tip_dont_null));
    		//allHaveDate=false;
    		P4=0;
    	}
    	else {
    		//edit_P4.setBackgroundDrawable(drawable);
    		P4=Double.parseDouble(edit_P4.getText().toString());
    	}
    	
    	if(edit_V.getText().length()== 0) {
    		edit_V.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    		V=0;
    	}
    	else {
    		edit_V.setBackgroundDrawable(drawable);
    		V=Double.parseDouble(edit_V.getText().toString());
    	}
    	
    	//用于输出
    	EditText edit_P_h1_l1 = (EditText)findViewById(R.id.editText4);
    	EditText edit_B = (EditText)findViewById(R.id.editText9);
    	EditText edit_C = (EditText)findViewById(R.id.editText10);
    	EditText edit_D = (EditText)findViewById(R.id.editText11);
    	EditText edit_E = (EditText)findViewById(R.id.editText12);
    	
    	if(allHaveDate){
    		double T = 0.035*V*V;
    		if(radiobutton1.isChecked()){
    			if(V<=4){
    				T=0.035*V*V/2;
    				if(T<0.25) T=0.25;
    			}
    			else{
    				T=0.035*V*V/3;
    				if(T<0.28) T=0.28;
    			}
    		}
    		if(edit_P.getText().length()!= 0){
    			P_l1_h1=P-l1-h1;
        		P_l1_h1=Double.parseDouble(dcmFmt.format(P_l1_h1));
        		edit_P_h1_l1.setText(String.valueOf(P_l1_h1));
    		}
    		if(edit_P1.getText().length()!= 0){
    			B=P1-l1-h1;
        		B=Double.parseDouble(dcmFmt.format(B));
        		if(B<0.1+T){
        			edit_B.setError(getString(R.string.tips_dont_reason));
        			isAllOK=false;
        		}
        		else{
        			edit_B.setBackgroundDrawable(drawable2);
        			
        		}
        		edit_B.setText(String.valueOf(B));
    		}
    		if(edit_P2.getText().length()!= 0){
    			C=P2-l1-h1;
        		C=Double.parseDouble(dcmFmt.format(C));
        		if(C<1.0+T){
        			edit_C.setError(getString(R.string.tips_dont_reason));
        			isAllOK=false;
        		}
        		else{
        			edit_C.setBackgroundDrawable(drawable2);
        			
        		}
        		edit_C.setText(String.valueOf(C));
    		}
    		if(edit_P3.getText().length()!= 0){
    			D=P3-l1-h1;
        		D=Double.parseDouble(dcmFmt.format(D));
        		if(D<0.3+T){
        			edit_D.setError(getString(R.string.tips_dont_reason));
        			isAllOK=false;
        		}
        		else{
        			edit_D.setBackgroundDrawable(drawable2);
        			
        		}
        		edit_D.setText(String.valueOf(D));
    		}
    		if(edit_P4.getText().length()!= 0){
    			E=P4-l1-h1;
        		E=Double.parseDouble(dcmFmt.format(E));
        		if(E<0.1+T){
        			edit_E.setError(getString(R.string.tips_dont_reason));
        			isAllOK=false;
        		}
        		else{
        			edit_E.setBackgroundDrawable(drawable2);
        			
        		}
        		edit_E.setText(String.valueOf(E));
    		}
    		
    		
    		if(edit_a.getText().length()!= 0 || edit_b.getText().length()!= 0 ||edit_c.getText().length()!= 0){
    		double t;
    		if(a>b){
    			t=a;
    			a=b;
    			b=t;
    			}
    		 if(a>c){
    			 t=a;
    			 a=c;
    			 c=t;
    			 }
    		 if(b>c)
    		 {
    			 t=b;
    			 b=c;
    			 c=t;
    		 }
    		 
    		 if(a<0.5||b<0.6||c<0.8){
    			 edit_a.setError(getString(R.string.tips_dont_reason));
    			 edit_b.setError(getString(R.string.tips_dont_reason));
    			 edit_c.setError(getString(R.string.tips_dont_reason));
    			 isAllOK=false;
    			 result=result+"轿顶空间应大于0.5*0.6*0.8\n";
    		 }
    		 else{
    			 edit_a.setBackgroundDrawable(drawable);
    			 edit_b.setBackgroundDrawable(drawable);
    			 edit_c.setBackgroundDrawable(drawable);
    		 }
    		}
    		 if(!isAllOK){
    			 result=result+"存在数据不符合要求";
    			 Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    		 }
    		 else{
    			 Toast.makeText(this, "数据符合要求", Toast.LENGTH_SHORT).show();
    		 }
    	}
    	else{
    		Toast.makeText(this, "存在空值(已经用红色标出)", Toast.LENGTH_SHORT).show();
    	}
		
	}
    
}
