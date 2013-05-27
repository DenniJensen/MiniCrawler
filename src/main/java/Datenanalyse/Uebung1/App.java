package Datenanalyse.Uebung1;

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
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//TODO graph
		//TODO page rank
		System.out.println("\nSearch Engine by Dennis Haegler\n");
		String crawlStorageFolder = "crawl";
		int numberOfCrawlers = 1;
		String crawlUrl = "http://mysql12.f4.htw-berlin.de/crawl/";

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);
		
		crawlController.addSeed(crawlUrl);
		crawlController.start(MyCrawler.class, numberOfCrawlers);
		System.out.println("Finished Crawl\t"+ crawlUrl);
		System.out.println("Result from Crawl\t"+ crawlUrl);
	}
}
