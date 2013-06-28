package com.henggana.pulsa;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.ContextMenu;
//import android.view.ContextMenu.ContextMenuInfo;
//import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	String[] items = {"Transaksi", "Pelanggan", "Produk", "Server"};
	
	TextView selection;
	
//	private ArrayAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_list_view);
		setListAdapter(new ArrayAdapter<String>(this,
					R.layout.row_layout,
					items
				)
		);
		
		this.deleteDatabase("pulsa.db");	
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	/*@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");  
        menu.add(0, v.getId(), 0, "Action 1");  
        menu.add(0, v.getId(), 0, "Action 2"); 
    }*/
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if("Transaksi".equals(items[position])){
			Intent i = new Intent(this, ActTransaksi.class);
			startActivity(i);
		}
		else if("Pelanggan".equals(items[position])){
			Intent i = new Intent(this, ActProduk.class);
			startActivity(i);
		}
		else if("Produk".equals(items[position])){
			Intent i = new Intent(this, ActProvider.class);
			startActivity(i);
		}
		else if("Server".equals(items[position])){
			Intent i = new Intent(this, ActServer.class);
			startActivity(i);
		}
		
	}

}
