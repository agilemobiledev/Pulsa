package com.henggana.pulsa;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActProdukEdit extends Activity {
	Button bt;
	EditText etKode;
	EditText etNominal;
	EditText etHargaServer;
	EditText etHargaAgen;
	Long mRowId;
	Long mIdProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_produk_edit);
		
		bt = (Button)findViewById(R.id.bt_submit);
		etKode = (EditText)findViewById(R.id.et_kode);
		etNominal = (EditText)findViewById(R.id.et_nominal);
		etHargaServer = (EditText)findViewById(R.id.et_harga_server);
		etHargaAgen = (EditText)findViewById(R.id.et_harga_agen);
		
		mRowId = null;
		mIdProvider = null;
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER) && extras.size()==1){
        	 mIdProvider = extras.getLong(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER);
        }
        else if (extras != null) {
            String kode = extras.getString(SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE);
            Long nominal = extras.getLong(SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL);
            Long hargaServer = extras.getLong(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER);
            Long hargaAgen = extras.getLong(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN);
            mRowId = extras.getLong(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID);
            mIdProvider = extras.getLong(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER);

            if (kode != null) {
                etKode.setText(kode);
            }
            if (nominal != null) {
                etNominal.setText(nominal.toString());
            }
            if (hargaServer != null) {
                etHargaServer.setText(hargaServer.toString());
            }
            if (hargaAgen!= null) {
                etHargaAgen.setText(hargaAgen.toString());
            }
        }
		
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mRowId != null){
					String id = mRowId.toString();
					String idProvider = mIdProvider.toString();
					String kode = etKode.getText().toString();
					String nominal = etNominal.getText().toString();
					String hargaServer = etHargaServer.getText().toString();
					String hargaAgen = etHargaAgen.getText().toString();
					new InsertNewRowTask().execute(id, idProvider, kode, nominal, hargaServer, hargaAgen);
				}
				else{
					String idProvider = mIdProvider.toString();
					String kode = etKode.getText().toString();
					String nominal = etNominal.getText().toString();
					String hargaServer = etHargaServer.getText().toString();
					String hargaAgen = etHargaAgen.getText().toString();
					new InsertNewRowTask().execute(idProvider, kode, nominal, hargaServer, hargaAgen);
				}
			}
		});
		
	}
	
	class InsertNewRowTask extends AsyncTask<String, Void, Void> {
		Long result;
		@Override
		protected Void doInBackground(String... params) {
			
			//background task jalan
			
			DataSourceProduk ids = new DataSourceProduk(getApplicationContext());
			ids.open();
			
			Produk produk= new Produk();
			if(params.length == 6){
				produk.setId(Long.parseLong(params[0]));
				produk.setProviderId(Long.parseLong(params[1]));
				produk.setKode(params[2]);
				produk.setNominal(Long.valueOf(params[3]).longValue());
				produk.setHargaServer(Long.valueOf(params[4]).longValue());
				produk.setHargaAgen(Long.valueOf(params[5]).longValue());
				ids.updateRow(produk);
			}
			else{
				produk.setProviderId(Long.parseLong(params[0]));
				produk.setKode(params[1].toString());
				produk.setNominal(Long.parseLong(params[2]));
				produk.setHargaServer(Long.parseLong(params[3]));
				produk.setHargaAgen(Long.parseLong(params[4]));
//				ids.insertRow(produk); //query insert row ke sqlite
				
				result = ids.insertRow(produk);  
			}
			ids.close();
			return null;
			
			//background task selesai
		}

		@Override
		protected void onPostExecute(Void result) {
			finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_server, menu);
		return true;
	}

}
