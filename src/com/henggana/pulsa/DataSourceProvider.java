package com.henggana.pulsa;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSourceProvider {

	private SQLiteDatabase db;
	private SqliteHelper dbHelper;

	private static String[] allColoumns = { SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID,
			SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NAMA, SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN };

	public DataSourceProvider(Context context) {
		dbHelper = new SqliteHelper(context);
	}
	
	/**
	 * Mendapatkan akses ke database untuk melakukan query
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * Tutup akses ke database, selalu panggil method ini setelah melakukan
	 * query
	 */
	public void close() {
		if (db.isOpen()) {
			db.close();
		}
	}

	/**
	 * Query untuk semua row yang ada di table items
	 * 
	 * @return array list yang berisi object Item
	 */
	public ArrayList<Provider> getAllProviders() {

		ArrayList<Provider> itemList = new ArrayList<Provider>();
		Provider item = new Provider();
		Cursor cursor = db.query(SqliteHelper.SQLITE_TABLE_PROVIDER, allColoumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			// convert ke array list
			item.setId(cursor.getLong(0));
			item.setNama(cursor.getString(1));
			item.setNomorAwalan(cursor.getString(2));
			itemList.add(item);
			item = new Provider();
			cursor.moveToNext();
		}

		return itemList;
	}

	/**
	 * Query untuk insert row baru ke table items
	 * 
	 * @param item
	 *            object Item sebagai representasi row yang akan di query
	 */
	public void insertRow(Provider item) {
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NAMA, item.getNama());
		values.put(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN, item.getNomorAwalan());

		db.insert(SqliteHelper.SQLITE_TABLE_PROVIDER, null, values);
	}
	
	public void updateRow(Provider item) {
		ContentValues values = new ContentValues();
//		values.put(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID, item.getId());
		values.put(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NAMA, item.getNama());
		values.put(SqliteHelper.SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN, item.getNomorAwalan());

		db.update(SqliteHelper.SQLITE_TABLE_PROVIDER, values, SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID + '=' + item.getId()  , null);

	}
	
	public void deleteRow(Provider item){
		db.delete(SqliteHelper.SQLITE_TABLE_PROVIDER, SqliteHelper.SQLITE_TABLE_PROVIDER_COL_ID + '=' + item.getId()  , null);
	}

}
