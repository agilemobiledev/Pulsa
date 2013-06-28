package com.henggana.pulsa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSourceProduk {

	private SQLiteDatabase db;
	private SqliteHelper dbHelper;

//	private static String[] allColoumns = { SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID,
//			SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, 
//			SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, 
//			SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, SqliteHelper.SQLITE_TABLE_PRODUK_COL_CREATED_AT, 
//			SqliteHelper.SQLITE_TABLE_PRODUK_COL_UPDATED_AT};
	
	private static String[] allColoumns = { SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID,
		SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, 
		SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, 
		SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN};

	public DataSourceProduk(Context context) {
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
	 * Query untuk semua row yang ada di table produk
	 * 
	 * @return array list yang berisi object Produk
	 */
	public ArrayList<Produk> getAllProduk() {

		ArrayList<Produk> produkList = new ArrayList<Produk>();
		Produk produk = new Produk();
		Cursor cursor = db.query(SqliteHelper.SQLITE_TABLE_PRODUK, allColoumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			// convert ke array list
			produk.setId(cursor.getLong(0));
			produk.setProviderId(cursor.getLong(1));
			produk.setKode(cursor.getString(2));
			produk.setNominal(cursor.getLong(3));
			produk.setHargaServer(cursor.getLong(4));
			produk.setHargaAgen(cursor.getLong(5));
//			produk.setCreatedAt(cursor.getString(6));
//			produk.setUpdatedAt(cursor.getString(7));
			produkList.add(produk);
			produk = new Produk();
			cursor.moveToNext();
		}

		return produkList;
	}
	
	/**
	 * Query untuk semua row yang ada di table produk
	 * 
	 * @return array list yang berisi object Produk
	 */
	public ArrayList<Produk> getAllProdukByIdProvider(long id_provider) {

		ArrayList<Produk> produkList = new ArrayList<Produk>();
		Produk produk = new Produk();
		Cursor cursor = db.query(SqliteHelper.SQLITE_TABLE_PRODUK, 
				allColoumns, SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER + '=' + id_provider, 
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			// convert ke array list
			produk.setId(cursor.getLong(0));
			produk.setProviderId(cursor.getLong(1));
			produk.setKode(cursor.getString(2));
			produk.setNominal(cursor.getLong(3));
			produk.setHargaServer(cursor.getLong(4));
			produk.setHargaAgen(cursor.getLong(5));
//			produk.setCreatedAt(cursor.getString(6));
//			produk.setUpdatedAt(cursor.getString(7));
			produkList.add(produk);
			produk = new Produk();
			cursor.moveToNext();
		}

		return produkList;
	}

	/**
	 * Query untuk insert row baru ke table items
	 * 
	 * @param item
	 *            object Item sebagai representasi row yang akan di query
	 */
	public Long insertRow(Produk produk) {
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, produk.getProviderId());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, produk.getKode());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, produk.getNominal());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, produk.getHargaServer());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, produk.getHargaAgen());
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, 1);
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, "S3");
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, 10);
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, 11);
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, 15);
		// set the format to sql date time
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		Date date = new Date();
//		
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_CREATED_AT, dateFormat.format(date));
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_UPDATED_AT, dateFormat.format(date));
////		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_CREATED_AT, produk.getCreatedAt());
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_UPDATED_AT, produk.getUpdatedAt());

		return db.insertOrThrow(SqliteHelper.SQLITE_TABLE_PRODUK, null, values);
	}
	
	public void updateRow(Produk produk) {
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, produk.getProviderId());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_KODE, produk.getKode());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_NOMINAL, produk.getNominal());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, produk.getHargaServer());
		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, produk.getHargaAgen());
		
		// set the format to sql date time
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		Date date = new Date();
		
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_UPDATED_AT, dateFormat.format(date));		
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_CREATED_AT, produk.getCreatedAt());
//		values.put(SqliteHelper.SQLITE_TABLE_PRODUK_COL_UPDATED_AT, produk.getUpdatedAt());
		

		db.update(SqliteHelper.SQLITE_TABLE_PRODUK, values, SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID + '=' + produk.getId()  , null);
	}
	
	public void deleteRow(Produk produk){
		db.delete(SqliteHelper.SQLITE_TABLE_PRODUK, SqliteHelper.SQLITE_TABLE_PRODUK_COL_ID + '=' + produk.getId()  , null);
	}

}
