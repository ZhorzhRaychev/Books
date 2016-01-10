package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		System.out.println("Starting Getting Books!");
		
		ArrayList<Book> books = new ArrayList<>();
		String url = "http://www.allitebooks.com/programming/java/";
		while(url != null){
			Connection connection = Jsoup.connect(url);
			Document page = connection.get();
			Elements titles = page.select(".entry-title > a");
			for (Element element : titles) {
				books.add(new Book(element.text(), element.attr("href")));
//				System.out.println("Title: " + element.text() + " link: " + element.attr("href"));
			}
			
			Elements currentPageSpans = page.select("span.current + a");
			if(!currentPageSpans.isEmpty()){
				Element currentPageSpan = currentPageSpans.get(0);
				System.out.println(currentPageSpan.attr("href"));
				url = currentPageSpan.attr("href");
			} else {
				url = null;
			}
		}
		
		ExecutorService threadPool = Executors.newFixedThreadPool(6);
		ArrayList<Future<DownloadResult> > results = new ArrayList<>();
		for (Book book : books) {
			Future<DownloadResult> future = threadPool.submit(new Downloader(book));
			results.add(future);
		}
		
		boolean stillDownloading = true;
		while(stillDownloading) {
			stillDownloading = false;
			Iterator<Future<DownloadResult>> iterator = results.iterator();
			while(iterator.hasNext()){
				Future<DownloadResult> result = iterator.next();
				if(result.isDone()) {
					System.out.println("Finished: " + result.get());
					iterator.remove();
				} else {
					stillDownloading = true;
				}
			}
			Thread.sleep(30_000);
		}
		
		System.out.println("Finished all");
		
		threadPool.shutdown();
		threadPool.awaitTermination(5, TimeUnit.MINUTES);
	}
	
}
