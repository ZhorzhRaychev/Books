package main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String[] args) throws IOException {

		Book book = new Book("Learning HBase", "http://www.allitebooks.com/learning-hbase/");
		//404 on URLs..
		//http://www.allitebooks.com/learning-hbase/  ---  http://file.allitebooks.com/20150930/Learning HBase.pdf
		//http://www.allitebooks.com/java-how-to-program-10th-edition/  --- http://file.allitebooks.com/20150930/Java How to Program, 10th Edition.pdf
		//http://www.allitebooks.com/java-how-to-program-9th-edition/   --- http://file.allitebooks.com/20150929/Java How to Program, 9th edition.pdf
		//http://www.allitebooks.com/java-hibernate-cookbook/  --- http://file.allitebooks.com/20150921/Java Hibernate Cookbook.pdf
		//http://www.allitebooks.com/getting-started-with-hazelcast/ --- http://file.allitebooks.com/20150703/Getting Started with Hazelcast.pdf
		//http://www.allitebooks.com/just-hibernate/ --- http://file.allitebooks.com/20150509/Just Hibernate.pdf
		//http://www.allitebooks.com/beginning-hibernate-3rd-edition/ --- http://file.allitebooks.com/20150509/Beginning Hibernate, 3rd Edition.pdf
		//http://www.allitebooks.com/beginning-hibernate-2nd-edition/ --- http://file.allitebooks.com/20150508/Beginning Hibernate, 2nd Edition.pdf
		//http://www.allitebooks.com/spring-persistence-with-hibernate/ --- http://file.allitebooks.com/20150508/Spring Persistence with Hibernate.pdf
		//http://www.allitebooks.com/sams-teach-yourself-java-in-24-hours-6th-edition/ --- http://file.allitebooks.com/20150508/Sams Teach Yourself Java in 24 Hours, 6th Edition.pdf
		
		
		
		
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
		
//		long bytesDownloaded = 0;
//		try (InputStream in = url.openStream();) {
//			bytesDownloaded = Files.copy(in, Paths.get("/home/zhorzh/Downloads/" + book.getName()), StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException e) {
//			url.ge
//			System.out.println("Error while downloading book from url " + book.getDownloadUrl());
//			e.printStackTrace();
//		}
		
		URLConnection connection = url.openConnection();
		InputStream is = null;
		try {
			connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
			connection.setRequestProperty("Accept","*/*");
		    is = connection.getInputStream();
		} catch (IOException ioe) {
		    if (connection instanceof HttpURLConnection) {
		        HttpURLConnection httpConn = (HttpURLConnection) connection;
		        int statusCode = httpConn.getResponseCode();
		        if (statusCode != 200) {
		            is = httpConn.getErrorStream();
		        }
		    }
		}
		Files.copy(is, Paths.get("/home/zhorzh/Downloads/" + book.getName()), StandardCopyOption.REPLACE_EXISTING);
	}
}
