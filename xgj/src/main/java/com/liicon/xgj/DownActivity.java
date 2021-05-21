package com.liicon.xgj;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.liicon.xgj.base.BaseActivity;

import java.text.DecimalFormat;

/**
 * 底坑空间
 */
public class DownActivity extends BaseActivity {
	Bundle bundle;
	private double V,d,e,f,l2,h2,A,L,P5,P6,P7,P8,F,G1,G2,G3;
	DecimalFormat dcmFmt = new DecimalFormat("0.000");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);

        setNavigationBar();
        setTitle("底坑空间");

        ((EditText)findViewById(R.id.editText30)).requestFocus();
        
        //设置参考值
        ((EditText)findViewById(R.id.editText31)).addTextChangedListener(new TextWatcher(){
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
        	
        }
        );
        ((EditText)findViewById(R.id.editText20)).addTextChangedListener(new TextWatcher(){
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
        ((EditText)findViewById(R.id.editText26)).addTextChangedListener(new TextWatcher(){

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
				calG1();
			}
        	
        }
        );
        ((EditText)findViewById(R.id.editText27)).addTextChangedListener(new TextWatcher(){

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
				calG2();
			}
        	
        }
        );
        ((EditText)findViewById(R.id.editText28)).addTextChangedListener(new TextWatcher(){

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
				calG3();
			}
        	
        }
        );
        ((EditText)findViewById(R.id.editText29)).addTextChangedListener(new TextWatcher(){

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
				calF();
			}
        	
        }
        );
        ((EditText)findViewById(R.id.editText19)).addTextChangedListener(new TextWatcher(){

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
				calG1();
				calG2();
				calG3();
				calF();
			}
        	
        }
        );
        ((EditText)findViewById(R.id.editText21)).addTextChangedListener(new TextWatcher(){

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
				calG1();
				calG2();
				calG3();
				calF();
			}
        	
        }
        );
        //额定速度
        bundle =getIntent().getExtras();
        if(bundle!=null&&bundle.containsKey("averageSpeed")){
        ((EditText)findViewById(R.id.editText30)).setText(bundle.getString("averageSpeed"));
        calCankao();
        }
        
        //是否减行程设计
        if(bundle!=null&&bundle.containsKey("isCutDesign")){
        	((RadioButton)findViewById(R.id.radioButton1)).setChecked(bundle.getBoolean("isCutDesign"));
        	calCankao();
        }
        
        
        //事件处理
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

    protected void calG1(){
    	if(((EditText)findViewById(R.id.editText26)).getText().length()!=0 &&((EditText)findViewById(R.id.editText19)).getText().length()!=0&&((EditText)findViewById(R.id.editText21)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText26)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText19)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText21)).getText().toString());
        	((EditText)findViewById(R.id.editText22)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calG2(){
    	if(((EditText)findViewById(R.id.editText27)).getText().length()!=0 &&((EditText)findViewById(R.id.editText19)).getText().length()!=0&&((EditText)findViewById(R.id.editText21)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText27)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText19)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText21)).getText().toString());
        	((EditText)findViewById(R.id.editText23)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calG3(){
    	if(((EditText)findViewById(R.id.editText28)).getText().length()!=0 &&((EditText)findViewById(R.id.editText19)).getText().length()!=0&&((EditText)findViewById(R.id.editText21)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText28)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText19)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText21)).getText().toString());
        	((EditText)findViewById(R.id.editText24)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }
    protected void calF(){
    	if(((EditText)findViewById(R.id.editText29)).getText().length()!=0 &&((EditText)findViewById(R.id.editText19)).getText().length()!=0&&((EditText)findViewById(R.id.editText21)).getText().length()!=0){
    		double temp_p =Double.parseDouble(((EditText)findViewById(R.id.editText29)).getText().toString());
        	double temp_l1=Double.parseDouble(((EditText)findViewById(R.id.editText19)).getText().toString());
        	double temp_h1=Double.parseDouble(((EditText)findViewById(R.id.editText21)).getText().toString());
        	((EditText)findViewById(R.id.editText25)).setText(dcmFmt.format(temp_p-temp_l1-temp_h1));
    	}
    	
    }

	private void calCankao() {
		if(((EditText)findViewById(R.id.editText31)).getText().length()!=0 &&((EditText)findViewById(R.id.editText20)).getText().length()!=0){
			double temp_A = Double.parseDouble(((EditText)findViewById(R.id.editText20)).getText().toString());
			double temp_L = Double.parseDouble(((EditText)findViewById(R.id.editText31)).getText().toString());
			double tempG1=0;
			double tempG2=0;
			double tempG3=0;
			double tempF=0.3;
			double y=1.143*temp_L-0.071;
			if(temp_A<=0.15 &&temp_L<=0.15){
				tempG1=0.5;
				tempG2=0.1;
			}
			if(temp_A<=0.15 &&(temp_L>0.15&&temp_L<=0.5)){
				tempG1=0.5;
				tempG2=0.1;
				tempG3=y;
			}
			if(temp_A>0.15 &&temp_L<=0.15){
				tempG1=0.5;
				tempG2=0.1;
			}
			if(temp_A>0.15 &&(temp_L>0.15&&temp_L<=0.5)){
				tempG1=0.5;
				tempG3=y;
			}
			if(temp_L>0.5){
				tempG1=0;
				tempG2=0;
				tempG3=0;
			}
			if(tempG1!=0){
				((EditText)findViewById(R.id.editText41)).setText(dcmFmt.format(tempG1));
			}
			else{
				((EditText)findViewById(R.id.editText41)).setText("");
			}
			if(tempG2!=0){
				((EditText)findViewById(R.id.editText42)).setText(dcmFmt.format(tempG2));
			}
			else{
				((EditText)findViewById(R.id.editText42)).setText("");
			}
			if(tempG3!=0){
				((EditText)findViewById(R.id.editText43)).setText(dcmFmt.format(tempG3));
			}
			else{
				((EditText)findViewById(R.id.editText43)).setText("");
			}
			((EditText)findViewById(R.id.editText44)).setText(dcmFmt.format(tempF));
		}
	}

	protected void openNewActivity() {
    	Intent i = new Intent(this, com.liicon.xgj.LimitSwitchActivity.class);
    	if(null==bundle)bundle=new Bundle();
    	bundle.putString("HJ",((EditText)findViewById(R.id.editText19)).getText().toString());
    	bundle.putString("HX",((EditText)findViewById(R.id.editText21)).getText().toString());
    	i.putExtras(bundle);
		this.startActivity(i);
		
	}

	protected void calculate() {
    	//用于输入
    	EditText edit_d = (EditText)findViewById(R.id.editText16);
    	EditText edit_e = (EditText)findViewById(R.id.editText17);
    	EditText edit_f = (EditText)findViewById(R.id.editText18);
    	EditText edit_l2 = (EditText)findViewById(R.id.editText19);
    	EditText edit_h2 = (EditText)findViewById(R.id.editText21);
    	EditText edit_A = (EditText)findViewById(R.id.editText20);
    	EditText edit_L = (EditText)findViewById(R.id.editText31);
    	EditText edit_P5 = (EditText)findViewById(R.id.editText26);
    	EditText edit_P6 = (EditText)findViewById(R.id.editText27);
    	EditText edit_P7 = (EditText)findViewById(R.id.editText28);
    	EditText edit_P8 = (EditText)findViewById(R.id.editText29);
    	EditText edit_V = (EditText)findViewById(R.id.editText30);
    	
    	RadioButton radiobutton1 = (RadioButton)findViewById(R.id.radioButton1);
    	
    	//判断输入框是否没填值
    	String result="";
    	boolean isAllOK=true;
    	boolean allHaveDate=true; //是否每个输入数字
    	
    	
    	if(edit_d.getText().length()!= 0)  {
    		
    		d=Double.parseDouble(edit_d.getText().toString());
    	}
    	else{
    		d=0;
    	}
    	
    	if(edit_e.getText().length()!= 0)  {
    		e=Double.parseDouble(edit_e.getText().toString());
    	}
    	else{
    		e=0;
    	}
    	
    	if(edit_f.getText().length()!= 0)  {
    		
    		f=Double.parseDouble(edit_f.getText().toString());
    	}
    	else{
    		f=0;
    	}
    	
    	if(edit_l2.getText().length()== 0){
    		edit_l2.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    	}
    	else {
    		l2=Double.parseDouble(edit_l2.getText().toString());
    	}
    	if(edit_h2.getText().length()== 0)  {
    		allHaveDate=false;
    		edit_h2.setError(getString(R.string.tips_dont_null));
    	}
    	else {
    		h2=Double.parseDouble(edit_h2.getText().toString());
    	}
    	if(edit_A.getText().length()!= 0)  {
    		
    		A=Double.parseDouble(edit_A.getText().toString());
    	}
    	else{
    		A=0;
    	}
    	
    	if(edit_L.getText().length()!= 0)  {
    		L=Double.parseDouble(edit_L.getText().toString());
    	}
    	else{
    		allHaveDate=false;
    		edit_L.setError(getString(R.string.tips_dont_null));
    		L=0;
    	}
    	
    	if(edit_P5.getText().length()!= 0) {
    	
    		P5=Double.parseDouble(edit_P5.getText().toString());
    	}
    	else{
    		P5=0;
    	}
    	
    	if(edit_P6.getText().length()!= 0)  {
    		
    		P6=Double.parseDouble(edit_P6.getText().toString());
    	}
    	else{
    		P6=0;
    	}
    	
    	if(edit_P7.getText().length()!= 0)  {
    		
    		P7=Double.parseDouble(edit_P7.getText().toString());
    	}else{
    		P7=0;
    	}
    	
    	
    	if(edit_V.getText().length()== 0) {
    		edit_V.setError(getString(R.string.tips_dont_null));
    		allHaveDate=false;
    	}
    	else {
    		V=Double.parseDouble(edit_V.getText().toString());
    	}
    	
    	//用于输出
    	
    	EditText edit_G1 = (EditText)findViewById(R.id.editText22);
    	EditText edit_G2 = (EditText)findViewById(R.id.editText23);
    	EditText edit_G3 = (EditText)findViewById(R.id.editText24);
    	EditText edit_F = (EditText)findViewById(R.id.editText25);
    	
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
    	
    		if(A>=0.1+T){
    		}
    		else{
    			edit_A.setError(getString(R.string.tips_dont_reason));
    			isAllOK=false;
    		}
    		if(edit_d.getText().length() !=0 || edit_e.getText().length() !=0 ||edit_f.getText().length() !=0){
    		double t;
    		if(d>e){
    			t=d;
    			d=e;
    			e=t;
    			}
    		 if(d>f){
    			 t=d;
    			 d=f;
    			 f=t;
    			 }
    		 if(e>f)
    		 {
    			 t=e;
    			 e=f;
    			 f=t;
    		 }
    		 if(d<0.5||e<0.6||f<1.0){
    			 edit_d.setError(getString(R.string.tips_dont_reason));
    			 edit_e.setError(getString(R.string.tips_dont_reason));
    			 edit_f.setError(getString(R.string.tips_dont_reason));
    			 isAllOK=false;
    			 result=result+"底坑空间应大于0.5*0.6*1.0\n";
    		 }
    		 else{
    			 edit_d.setError(null);
    			 edit_e.setError(null);
    			 edit_f.setError(null);
    		 }
    		}
    		 G1=P5-l2-h2;
    		 G2=P6-l2-h2;
    		 G3=P7-l2-h2;
    		 F=P8-l2-h2;
    		 G1=Double.parseDouble(dcmFmt.format(G1));
    		 G2=Double.parseDouble(dcmFmt.format(G2));
    		 G3=Double.parseDouble(dcmFmt.format(G3));
    		 F=Double.parseDouble(dcmFmt.format(F));
    		 if(edit_P5.getText().length()!=0)
    			 edit_G1.setText(String.valueOf(G1));
    		 if(edit_P6.getText().length()!=0)
    			 edit_G2.setText(String.valueOf(G2));
    		 if(edit_P7.getText().length()!=0)
    			 edit_G3.setText(String.valueOf(G3));
    		 if(edit_P8.getText().length()!=0)
    			 edit_F.setText(String.valueOf(F));
    		 if(F>=0.3){
    			 if(edit_P8.getText().length()!=0)
    				 edit_F.setError(null);
    		 }
    		 else{
    			 if(edit_P8.getText().length()!=0){
    				 edit_F.setError(getString(R.string.tips_dont_reason));
    				 isAllOK=false;
    			 }
    		 }
    		 double y=1.143*L-0.071;//允许值
    		 
    		 if(A<=0.15 &&L<=0.15){
    			 if(G1>=0.5){
    				 if(edit_P5.getText().length()!=0)
    					 edit_G1.setError(null);
    			 }
    			 else{
    				 if(edit_P5.getText().length()!=0)
    				 {
    					 edit_G1.setError(getString(R.string.tips_dont_reason));
    					 isAllOK=false;
    				 }
    				 
    			 }
    			 if(G2>=0.1){
    				 if(edit_P6.getText().length()!=0)
    					 edit_G2.setError(null);
    			 }
    			 else{
    				 if(edit_P6.getText().length()!=0){
    					 edit_G2.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				 
    			 }
    		 }
    		 else if(A<=0.15 &&(L>0.15 && L<=0.5)){
    			 if(G1>=0.5){
    				 if(edit_P5.getText().length()!=0)
    				 edit_G1.setError(null);
    			 }
    			 else{
    				 if(edit_P5.getText().length()!=0){
    					 edit_G1.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				
    			 }
    			 if(G2>=0.1){
    				 if(edit_P6.getText().length()!=0)
    				 edit_G2.setError(null);
    			 }
    			 else{
    				 if(edit_P6.getText().length()!=0){
    					 edit_G2.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				 
    			 }
    			 if(G3>=y){
    				 if(edit_P7.getText().length()!=0)
    				 edit_G3.setError(null);
    			 }
    			 else{
    				 if(edit_P7.getText().length()!=0){
    					 edit_G3.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				 
    			 }
    		 }
    		 else if(A>0.15 &&L<=0.15){
    			 if(G1>=0.5){
    				 if(edit_P5.getText().length()!=0)
    				 edit_G1.setError(null);
    			 }
    			 else{
    				 if(edit_P5.getText().length()!=0){
    				 edit_G1.setError(getString(R.string.tips_dont_reason));
    				 isAllOK=false;
    				 }
    			 }
    			 if(G2>=0.1){
    				 if(edit_P6.getText().length()!=0)
    				 edit_G2.setError(null);
    			 }
    			 else{
    				 if(edit_P6.getText().length()!=0){
    					 edit_G2.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				 
    			 }
    		 }else if(A>0.15 &&(L>0.15 &&L<=0.5)){
    			 if(G1>=0.5){
    				 if(edit_P5.getText().length()!=0) {
    					 edit_G1.setError(null);
    				 }
    			 }
    			 else{
    				 if(edit_P5.getText().length()!=0){
    					 edit_G1.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				
    			 }
    			
    			 if(G3>=y){
    				 if(edit_P7.getText().length()!=0)
    					 edit_G3.setError(null);
    			 }
    			 else{
    				 if(edit_P7.getText().length()!=0){
    					 edit_G3.setError(getString(R.string.tips_dont_reason));
        				 isAllOK=false;
    				 }
    				 
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
