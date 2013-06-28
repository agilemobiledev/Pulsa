package com.henggana.pulsa;

public class Produk {

	private long id;
	private long providerId;
	private String kode;
	private long nominal;
	private long hargaServer;
	private long hargaAgen;
	private String createdAt;
	private String updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getProviderId() {
		return providerId;
	}

	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public Long getNominal() {
		return nominal;
	}

	public void setNominal(Long nominal) {
		this.nominal = nominal;
	}
	
	public Long getHargaServer() {
		return hargaServer;
	}

	public void setHargaServer(Long hargaServer) {
		this.hargaServer = hargaServer;
	}
	
	public Long getHargaAgen() {
		return hargaAgen;
	}

	public void setHargaAgen(Long hargaAgen) {
		this.hargaAgen = hargaAgen;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	// override method toString untuk menampilkan string di list view sesuai
	// dengan keinginan

	@Override
	public String toString() {

		String stringAppearInListView = this.providerId+", "+this.kode+", "+this.nominal+", "+this.hargaServer;
		return stringAppearInListView;
	}
}
