package main;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Callable;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Downloader implements Callable<DownloadResult>{

	private Book book;
	
	public Downloader(Book book) {
		this.book = book;
	}

	@Override
	public DownloadResult call() throws Exception {
	
		Connection connect = Jsoup.connect(book.getDownloadUrl());
		Document bookPage = null;
		try {
			bookPage = connect.get();
		} catch (IOException e) {
			System.out.println("Error execution Get Request to " + book.getDownloadUrl());
			e.printStackTrace();
		}
		Elements downloadLinks = bookPage.select("span.download-links > a");
		Element element = downloadLinks.get(0);
		String downloadUrl = element.attr("href").replace(" ", "%20");
		
		URL url = null;
		try {
			url = new URL(downloadUrl);
		} catch (MalformedURLException e) {
			System.out.println("The URL is not well formatted " + book.getDownloadUrl());
			e.printStackTrace();
		}
		long bytesDownloaded = 0;
		try (InputStream in = url.openStream();) {
			bytesDownloaded = Files.copy(in, Paths.get("/home/zhorzh/Downloads/Books" + book.getName()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.out.println("Error while downloading book from url " + book.getDownloadUrl());
			e.printStackTrace();
		}
		
		return new DownloadResult(book, bytesDownloaded);
	}

}
