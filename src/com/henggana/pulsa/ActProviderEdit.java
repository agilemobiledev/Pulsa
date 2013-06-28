package com.henggana.pulsa;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActProviderEdit extends Activity {
	Button bt;
	EditText etNama;
	EditText etNomorAwalan;
	Long mRowId;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_provider_edit);
		
		bt = (Button)findViewById(R.id.bt_submit);
		etNama = (EditText)findViewById(R.id.et_nama);
		etNomorAwalan = (EditText)findViewById(R.id.et_nomor_awalan);
		
		mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NAMA);
            String body = extras.getString(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN);
            mRowId = extras.getLong(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID);

            if (title != null) {
                etNama.setText(title);
            }
            if (body != null) {
                etNomorAwalan.setText(body);
            }
        }
		
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				ArrayList<String> data = new ArrayList<String>();
//				if(mRowId != null){
//					data.add(mRowId.toString());
//				}
//				data.add(etNama.getText().toString());
//				data.add(etNomorAwalan.getText().toString());
				
				if(mRowId != null){
					String id = mRowId.toString();
					String nama = etNama.getText().toString();
					String nomorAwalan = etNomorAwalan.getText().toString();
					new InsertNewRowTask().execute(id, nama, nomorAwalan);
				}
				else{
					String nama = etNama.getText().toString();
					String nomorAwalan = etNomorAwalan.getText().toString();
					new InsertNewRowTask().execute(nama, nomorAwalan);
				}
				
				//new InsertNewRowTask().execute(data);
			}
		});
		
	}
	
	class InsertNewRowTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			
			//background task jalan
			
			DataSourceProvider ids = new DataSourceProvider(getApplicationContext());
			ids.open();
			
//			passed = params[0];
//			
//			Provider provider= new Provider();
//			
//			provider.setNama(passed.get(0).toString());
//			provider.setNomorAwalan(passed.get(1).toString());
//			ids.insertRow(provider); //query insert row ke sqlite
//			ids.close();
			
			Provider provider= new Provider();
			if(params.length == 3){
				provider.setId(Long.parseLong(params[0]));
				provider.setNama(params[1].toString());
				provider.setNomorAwalan(params[2].toString());
				ids.updateRow(provider);
			}
			else{
				provider.setNama(params[0].toString());
				provider.setNomorAwalan(params[1].toString());
				ids.insertRow(provider); //query insert row ke sqlite
			}
			
			
			ids.close();

			return null;
			
			//background task selesai
		}

		@Override
		protected void onPostExecute(Void result) {
			
			//launch activity berikutnya
			
			finish();
			Intent i = new Intent(ActProviderEdit.this, ActProvider.class);
			startActivity(i);
		}

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_server, menu);
		return true;
	}

}
