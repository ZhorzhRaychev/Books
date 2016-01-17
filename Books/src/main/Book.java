package main;

public class Book {

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
	
}
//comment
//this is the fix
