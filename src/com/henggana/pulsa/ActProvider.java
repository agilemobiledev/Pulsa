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

public class ActProvider extends ListActivity {
	private ListView listview;
	private ProgressDialog pd;
	private ArrayList<Provider> itemList;

//	private static final int ACTIVITY_CREATE=0;
//    private static final int ACTIVITY_EDIT=1;
//    
//    private static final int DIALOG_ALERT_DELETE=1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int UPDATE_ID = Menu.FIRST + 1;
    private static final int DELETE_ID = Menu.FIRST + 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_provider);
		// populate view
		listview = (ListView) findViewById(android.R.id.list);
		listview.setEmptyView(findViewById(android.R.id.empty));

		registerForContextMenu(getListView());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// background task untuk query semua row ke sqlite 
		new GetAllRowTask().execute();
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
                createProvider();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        menu.add(0, UPDATE_ID, 0, R.string.menu_update);
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
            	Provider provider = itemList.get((int)info.position);
//              DataSourceProvider ids = new DataSourceProvider(getApplicationContext());
//    			ids.open();
//    			ids.deleteRow(provider);
//    			ids.close();
//    			
//
//
//    			new GetAllRowTask().execute();
            	AlertDialog deleteDialog = AskOption(provider);
            	deleteDialog.show();
            	
//            	if(deleteDialogResult==1){
//            		DataSourceProvider ids = new DataSourceProvider(getApplicationContext());
//        			ids.open();
//        			ids.deleteRow(provider);
//        			ids.close();
//            	}
                return true;
//                break;
            case UPDATE_ID:
//            	Provider data = (Provider)l.getAdapter().getItem(position);
            	info = (AdapterContextMenuInfo) item.getMenuInfo();
            	provider = itemList.get((int)info.position);
                Long mid = provider.getId();
                String nama = provider.getNama();
                String nomorAwalan = provider.getNomorAwalan();
                
                //String a = mid.toString()+nama+nomorAwalan; 
                
                Intent i = new Intent(this, ActProviderEdit.class);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID, mid);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NAMA, nama);
                i.putExtra(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN, nomorAwalan);
                startActivity(i);
                return true;
//                break;
        }
        return super.onContextItemSelected(item);
    }
    
    private AlertDialog AskOption(final Provider provider){
//    	Provider provider;
    	
    	AlertDialog deleteDialog = new AlertDialog.Builder(this)
    	.setTitle("Delete")
    	.setMessage("Really?")
    	
    	.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
    		
    		public void onClick(DialogInterface dialog, int whichButton){
    			DataSourceProvider ids = new DataSourceProvider(getApplicationContext());
     			ids.open();
     			ids.deleteRow(provider);
     			ids.close();
    			dialog.dismiss();
    			new GetAllRowTask().execute();
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
	
	private void createProvider() {
        Intent i = new Intent(this, ActProviderEdit.class);
        startActivity(i);
//        finish();
    }
	
	
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Provider data = (Provider)l.getAdapter().getItem(position);
        
        Long mid = data.getId();
//        String nama = data.getNama();
//        String nomorAwalan = data.getNomorAwalan();
        
        //String a = mid.toString()+nama+nomorAwalan; 
        
        Intent i = new Intent(this, ActProduk.class);
        i.putExtra(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID, mid);
//        i.putExtra(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NAMA, nama);
//        i.putExtra(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN, nomorAwalan);
        startActivity(i);
        
        
        //Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }

	class GetAllRowTask extends AsyncTask<Void, Void, ArrayList<Provider>> {
		
		@Override
		protected void onPreExecute() {
			pd=ProgressDialog.show(ActProvider.this,"","Harap Tunggu",false); 

		}

		@Override
		protected ArrayList<Provider> doInBackground(Void... params) {

			DataSourceProvider ids = new DataSourceProvider(getApplicationContext());
			ids.open();
			itemList = ids.getAllProviders();
			ids.close();

			return itemList;
		}

		@Override
		protected void onPostExecute(ArrayList<Provider> result) {

			// set data ke sqlite, tetep pake ArrayAdapter nggak jadi custom
			// adapter
			pd.dismiss();
			ArrayAdapter<Provider> adapter = new ArrayAdapter<Provider>(getApplicationContext(),
					R.layout.row_layout, result);
//			listview.setAdapter(adapter);
			setListAdapter(adapter);
		}

	}

}
