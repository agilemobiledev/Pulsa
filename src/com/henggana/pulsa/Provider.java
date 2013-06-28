package com.henggana.pulsa;

public class Provider {

	private long id;
	private String nama;
	private String nomorAwalan;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNomorAwalan() {
		return nomorAwalan;
	}

	public void setNomorAwalan(String nomorAwalan) {
		this.nomorAwalan = nomorAwalan;
	}

	// override method toString untuk menampilkan string di list view sesuai
	// dengan keinginan

	@Override
	public String toString() {

		String stringAppearInListView = this.nama;
		return stringAppearInListView;
	}
}
