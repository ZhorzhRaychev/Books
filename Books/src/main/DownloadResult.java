package main;

public class DownloadResult {

	private Book book;
	private long downloadedBytes;
	
	public DownloadResult(Book book, long downloadedBytes) {
		this.book = book;
		this.downloadedBytes = downloadedBytes;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public long getDownloadedBytes() {
		return downloadedBytes;
	}
	public void setDownloadedBytes(long downloadedBytes) {
		this.downloadedBytes = downloadedBytes;
	}
	
	@Override
	public String toString(){
		return "NAME: " +  book.getName() + " SIZE: " + toMgb(downloadedBytes);
	}
	
	private String toMgb(long bytes) {
	    int unit =  1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = "KMGTPE".charAt(exp-1)+"";
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
