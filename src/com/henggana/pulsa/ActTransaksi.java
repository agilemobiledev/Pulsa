package com.henggana.pulsa;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActTransaksi extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_transaksi);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_transaksi, menu);
		return true;
	}

}
