package com.example.edpp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class Custom_medicen extends BaseAdapter{

	private Context Context;
	String[] b;
	String[] c;
	String[] d;
	String[] e;
	
	
	
	public Custom_medicen(Context applicationContext, String[] b, String[] c, String[] d, String[] e) {
		this.Context=applicationContext;
		this.b=b;
		this.c=c;
		this.d=d;
		this.e=e;
		
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
				gridView=inflator.inflate(R.layout.costom, null);
				
			}
			else
			{
				gridView=(View)convertView;
				
			}
			
			TextView tv1=(TextView)gridView.findViewById(R.id.textView2);
			TextView tv2=(TextView)gridView.findViewById(R.id.textView4);
			TextView tv3=(TextView)gridView.findViewById(R.id.textView6);
			TextView tv4=(TextView)gridView.findViewById(R.id.textView8);
			
			
			tv1.setTextColor(Color.BLACK);
			tv2.setTextColor(Color.BLACK);
			tv3.setTextColor(Color.BLACK);
			tv4.setTextColor(Color.BLACK);
			
			
			
			tv1.setText(b[position]);
			tv2.setText(c[position]);
			tv3.setText(d[position]);
			tv4.setText(e[position]);
			
			
			
			return gridView;
			
		}
		
		
		
	}
