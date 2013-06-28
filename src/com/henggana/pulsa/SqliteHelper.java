	package com.henggana.pulsa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

	private static final String SQLITE_DATABASE = "pulsa.db";
	
	/*
	 * PROVIDER CONSTANTA
	 */
	public static final String SQLITE_TABLE_PROVIDER = "provider";
	public static final String SQLITE_TABLE_PROVIDER_COL_ID = "_id";
	public static final String SQLITE_TABLE_PROVIDER_COL_NAMA = "nama";
	public static final String SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN = "nomor_awalan";
	
	/*
	 * PRODUK CONSTANTA
	 */
	public static final String SQLITE_TABLE_PRODUK = "produk";
	public static final String SQLITE_TABLE_PRODUK_COL_ID = "_id";
	public static final String SQLITE_TABLE_PRODUK_COL_ID_PROVIDER = "id_provider";
	public static final String SQLITE_TABLE_PRODUK_COL_KODE = "kode";
	public static final String SQLITE_TABLE_PRODUK_COL_NOMINAL = "nominal";
	public static final String SQLITE_TABLE_PRODUK_COL_HARGA_SERVER = "harga_server";
	public static final String SQLITE_TABLE_PRODUK_COL_HARGA_AGEN = "harga_agen";
	public static final String SQLITE_TABLE_PRODUK_COL_CREATED_AT = "created_at";
	public static final String SQLITE_TABLE_PRODUK_COL_UPDATED_AT = "updated_at";

	/*
	 * CREATE TABLE PROVIDER
	 */
	private static final String CREATE_TABLE_PROVIDER = String.format(
			"create table %s (%s integer not null primary key autoincrement, %s text, %s text);", SQLITE_TABLE_PROVIDER,
			SQLITE_TABLE_PROVIDER_COL_ID, SQLITE_TABLE_PROVIDER_COL_NAMA, SQLITE_TABLE_PROVIDER_COL_NOMOR_AWALAN);

	/*
	 * CREATE TABLE PROVIDER
	 */
//	private static final String CREATE_TABLE_PRODUK = String.format(
//			"create table %s (%s integer not null primary key autoincrement, %s integer not null, %s text, %s integer, %s integer, %s integer, %s date, %s date);",
//			SQLITE_TABLE_PRODUK, SQLITE_TABLE_PRODUK_COL_ID, SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, SQLITE_TABLE_PRODUK_COL_KODE, SQLITE_TABLE_PRODUK_COL_NOMINAL,
//			SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, SQLITE_TABLE_PRODUK_COL_HARGA_AGEN, SQLITE_TABLE_PRODUK_COL_CREATED_AT, SQLITE_TABLE_PRODUK_COL_UPDATED_AT
//			);
	
	private static final String CREATE_TABLE_PRODUK = String.format(
			"create table %s (%s integer not null primary key autoincrement, %s integer, %s text, %s integer, %s integer, %s integer);",
			SQLITE_TABLE_PRODUK, SQLITE_TABLE_PRODUK_COL_ID, SQLITE_TABLE_PRODUK_COL_ID_PROVIDER, SQLITE_TABLE_PRODUK_COL_KODE, SQLITE_TABLE_PRODUK_COL_NOMINAL,
			SQLITE_TABLE_PRODUK_COL_HARGA_SERVER, SQLITE_TABLE_PRODUK_COL_HARGA_AGEN
			);
	
	public SqliteHelper(Context context) {
		super(context, SQLITE_DATABASE, null, 1);

	}
	
	
	
	String dummyProviderSql =
//            "INSERT or replace INTO provider (nama, nomor_awalan) VALUES('Simpati','0812'),('Indosat','0856');" ;
			"INSERT or replace INTO provider (nama, nomor_awalan)" +
			"select 'Simpati' as nama, '0812'as 'nomor_awalan'" +
			"union select 'Indosat', '0856'";
	
	String dummyProdukSql =
			"INSERT or replace INTO produk (id_provider, kode, nominal, harga_server, harga_agen)" +
			"select 1 as id_provider, 'I10' as kode, 10000 as nominal, 10900 as harga_server, 11500 as harga_agen " +
			"union select 2, 'S20', 20000, 20800, 21500";
                

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_TABLE_PROVIDER);
		db.execSQL(CREATE_TABLE_PRODUK);
		db.execSQL(dummyProviderSql);
		db.execSQL(dummyProdukSql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
