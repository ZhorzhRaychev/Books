package main;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

public class Book	{

	private String name;
	private String downloadUrl;
	
	public Book(String name, String downloadUrl) {
		this.name = name;
		this.downloadUrl = downloadUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	@Override
	public String toString() {
		return "Book name: " + name + " URL: " + downloadUrl;
	}
	
	@SuppressWarnings("serial")
	public Funnel<Book> getBookFunnel() {
		return new Funnel<Book>() {
			@Override
			public void funnel(Book from, PrimitiveSink into) {
				into
					.putString(name, Charsets.UTF_8)
				    .putString(downloadUrl, Charsets.UTF_8);
				
			}
		};
	}
	
	public int hash(){
		HashFunction hf = Hashing.md5();
		HashCode hc = hf.newHasher()
						.putString(name, Charsets.UTF_8)
			    		.putString(downloadUrl, Charsets.UTF_8)
			    		.hash();
		return hc.asInt();		
	}
}
