package Datenanalyse.Uebung1;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;

import Datenanalyse.Uebung1.Index.LuceneSearch;
import Datenanalyse.Uebung1.Index.PageEntity;
import Datenanalyse.Uebung1.Index.SearchResult;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * 
 * @author Dennis Haegler - s0532338
 * 
 */
public class App {

	/** Clear command for the terminal. */
	final static String CLEAR = "\033[";

	/** Logger from log4j. */
	static Logger logger = Logger.getLogger(App.class);

	/**
	 * Constructs a App (application).
	 */
	public App() {
		
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.setLevel(Level.DEBUG);
		System.out.println("\nSearch Engine by Dennis Haegler\n");
		String crawlStorageFolder = "crawl";
		int numberOfCrawlers = 1;
		String crawlUrl = "http://mysql12.f4.htw-berlin.de/crawl/";
		

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);

		CrawlController crawlController = new CrawlController(config,
				pageFetcher, robotstxtServer);

		crawlController.addSeed(crawlUrl);
		crawlController.start(MyCrawler.class, numberOfCrawlers);
		System.out.println("Finished Crawl " + crawlUrl);

		// LuceneWriter storedPageWriter = new LuceneWriter("index/");
		// LuceneWriter out = new LuceneWriter("crawled/stored");
		if (searchIndex("tokens") && searchIndex("index")
				&& searchIndex("classification")) {
			System.out.println("worked");
		}
	}

	public void run() {
		
	}

	private static boolean searchIndex(String value) {
		boolean result = true;

		LuceneSearch luceneSearch = null;
		SearchResult searchResult = null;
		try {
			luceneSearch = new LuceneSearch("index/");
			searchResult = luceneSearch.search(value);

			System.out.printf("%d Results for '%s'\n",
					searchResult.getResults(), value);
			for (int i = 0; i < searchResult.getPages().size(); i++) {
				PageEntity page = searchResult.getPages().get(i);
				System.out.printf("%d. %.6f: %s\n", i + 1, page.getRelevance(),
						page.getName());
			}
			System.out.println("");
		} catch (IOException e) {
			result = false;
			System.err.println("Error while searching!");
			e.printStackTrace();
		} catch (ParseException e) {
			result = false;
			System.err.println("Error while searching!");
			e.printStackTrace();
		} finally {
			if (luceneSearch != null) {
				try {
					luceneSearch.close();
				} catch (IOException e) {
					result = false;
					System.err.println("Error while searching!");
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
