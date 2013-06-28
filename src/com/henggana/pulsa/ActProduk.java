package com.henggana.pulsa;

import java.util.ArrayList;

//import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.app.Dialog;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import com.henggana.pulsa.Halaman2.GetAllRowTask;
//import android.widget.Toast;

public class ActProduk extends ListActivity {
	private ListView listview;
	private ProgressDialog pd;
	private ArrayList<Produk> itemList;
	

//	private static final int ACTIVITY_CREATE=0;
//    private static final int ACTIVITY_EDIT=1;
//    
//    private static final int DIALOG_ALERT_DELETE=1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int UPDATE_ID = Menu.FIRST + 1;
    private static final int DELETE_ID = Menu.FIRST + 2;
    
    Long providerId;
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_provider);
		// populate view
		
		providerId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.providerId = extras.getLong(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID);

        }
		
		listview = (ListView) findViewById(android.R.id.list);
		listview.setEmptyView(findViewById(android.R.id.empty));

		registerForContextMenu(getListView());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// background task untuk query semua row ke sqlite
		
		new GetAllRowTaskByIdProvider().execute();
	};
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_add);
        return true;
    }
	
	@Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
            	
                createProduk();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Delete");
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
//        menu.add(0, UPDATE_ID, 0, R.string.menu_update);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
            	
//            	ListAdapter info = (ListAdapter) item.getMenuInfo();
            	
//            	Provider provider = itemList.get(Integer.parseInt(Long.toString(info.id)));
            	
//                Provider provider = new Provider();
//                provider.setId(info.id);
//            	Toast.makeText(this, Long.toString(info.position), Toast.LENGTH_LONG).show();
//            	Toast.makeText(this, provider.getNama(), Toast.LENGTH_LONG).show();
//    			finish();
//    			startActivity(getIntent());
            	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            	Produk produk = itemList.get((int)info.position);

            	AlertDialog deleteDialog = AskOption(produk);
            	deleteDialog.show();
            	
                return true;
//                break;
            case UPDATE_ID:
//            	Provider data = (Provider)l.getAdapter().getItem(position);
            	info = (AdapterContextMenuInfo) item.getMenuInfo();
            	produk = itemList.get((int)info.position);
                Long mid = produk.getId();
                String kode = produk.getKode();
                Long nominal = produk.getNominal();
                Long hargaServer = produk.getHargaServer();
                Long hargaAgen = produk.getHargaAgen();
                String created_at = produk.getCreatedAt();
                String updated_at = produk.getUpdatedAt();
                
                //String a = mid.toString()+nama+nomorAwalan; 
                
                Intent i = new Intent(this, ActProviderEdit.class);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID, mid);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, mid);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, kode);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, nominal);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, hargaServer);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, hargaAgen);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_CREATED_AT, created_at);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_UPDATED_AT, updated_at);
                startActivity(i);
                return true;
//                break;
        }
        return super.onContextItemSelected(item);
    }
    
    private AlertDialog AskOption(final Produk produk){
//    	Provider provider;
    	
    	AlertDialog deleteDialog = new AlertDialog.Builder(this)
    	.setTitle("Delete")
    	.setMessage("Really?")
    	
    	.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
    		
    		public void onClick(DialogInterface dialog, int whichButton){
    			DataSourceProduk ids = new DataSourceProduk(getApplicationContext());
     			ids.open();
     			ids.deleteRow(produk);
     			ids.close();
    			dialog.dismiss();
    			new GetAllRowTaskByIdProvider().execute();
    		}
    	})
    	
    	.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int whichButton){
    			dialog.dismiss();
    		}
    	})
    	.create();
    	
    	return deleteDialog;
    }
	
	private void createProduk() {
        Intent i = new Intent(this, ActProdukEdit.class);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, this.providerId);
        startActivity(i);
    }
	
	
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Produk data = (Produk)l.getAdapter().getItem(position);
        
        Long mid = data.getId();
        Long mIdProvider = data.getProviderId();
        String kode = data.getKode();
        Long nominal = data.getNominal();
        Long hargaServer = data.getHargaServer();
        Long hargaAgen = data.getHargaAgen();
        
        Intent i = new Intent(this, ActProdukEdit.class);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID, mid);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, mIdProvider);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, kode);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, nominal);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, hargaServer);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, hargaAgen);
        startActivity(i);
        
    }

	class GetAllRowTask extends AsyncTask<Void, Void, ArrayList<Produk>> {
		
		@Override
		protected void onPreExecute() {
			pd=ProgressDialog.show(ActProduk.this,"","Harap Tunggu",false); 

		}

		@Override
		protected ArrayList<Produk> doInBackground(Void... params) {

			DataSourceProduk ids = new DataSourceProduk(getApplicationContext());
			ids.open();
			itemList = ids.getAllProduk();
			ids.close();

			return itemList;
		}

		@Override
		protected void onPostExecute(ArrayList<Produk> result) {

			// set data ke sqlite, tetep pake ArrayAdapter nggak jadi custom
			// adapter
			pd.dismiss();
			ArrayAdapter<Produk> adapter = new ArrayAdapter<Produk>(getApplicationContext(),
					R.layout.row_layout, result);
//			listview.setAdapter(adapter);
			setListAdapter(adapter);
		}

	}
	
	
	class GetAllRowTaskByIdProvider extends AsyncTask<Void, Void, ArrayList<Produk>> {
	
		@Override
		protected void onPreExecute() {
			pd=ProgressDialog.show(ActProduk.this,"","Harap Tunggu",false); 

		}

		@Override
		protected ArrayList<Produk> doInBackground(Void... params) {
			
			long providerId = 0;
	        Bundle extras = getIntent().getExtras();
	        if (extras != null) {
	            providerId = extras.getLong(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID);

	        }
			
			DataSourceProduk ids = new DataSourceProduk(getApplicationContext());
			ids.open();
			itemList = ids.getAllProdukByIdProvider(providerId);
			ids.close();

			return itemList;
		}

		@Override
		protected void onPostExecute(ArrayList<Produk> result) {

			// set data ke sqlite, tetep pake ArrayAdapter nggak jadi custom
			// adapter
			
			pd.dismiss();
			ArrayAdapter<Produk> adapter = new ArrayAdapter<Produk>(getApplicationContext(),
					R.layout.row_layout, result);
//			listview.setAdapter(adapter);
			setListAdapter(adapter);
		}

	}

}
