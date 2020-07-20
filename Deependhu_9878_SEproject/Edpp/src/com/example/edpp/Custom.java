package com.example.edpp;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Custom extends BaseAdapter{

	private Context Context;
	String[] b;
	String[] c;
	String[] d;
	String[] e;
	String[] f;
	
	
	public Custom(Context applicationContext, String[] b, String[] c, String[] d, String[] e, String[] f) {
		this.Context=applicationContext;
		this.b=b;
		this.c=c;
		this.d=d;
		this.e=e;
		this.f=f;
			
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		if(convertView==null)
		{
			gridView=new View(Context);
			gridView=inflator.inflate(R.layout.activity_custom, null);
			
		}
		else
		{
			gridView=(View)convertView;
			
		}
		
		TextView tv1=(TextView)gridView.findViewById(R.id.textView1);
		TextView tv2=(TextView)gridView.findViewById(R.id.textView2);
		TextView tv3=(TextView)gridView.findViewById(R.id.textView3);
		TextView tv4=(TextView)gridView.findViewById(R.id.textView4);
		TextView tv5=(TextView)gridView.findViewById(R.id.textView5);
		
		
		tv1.setTextColor(Color.BLACK);
		tv2.setTextColor(Color.BLACK);
		tv3.setTextColor(Color.BLACK);
		tv4.setTextColor(Color.BLACK);
		tv5.setTextColor(Color.BLACK);
		
		
		tv1.setText(b[position]);
		tv2.setText(c[position]);
		tv3.setText(d[position]);
		tv4.setText(e[position]);
		tv5.setText(f[position]);
		
		
		return gridView;
		
	}
	
	
	
}
