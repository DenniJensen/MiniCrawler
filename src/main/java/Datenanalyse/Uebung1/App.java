package Datenanalyse.Uebung1;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.setLevel(Level.DEBUG);
		System.out.println("\nSearch Engine by Dennis Haegler\n");
		String crawlStorageFolder = "crawl";
		int numberOfCrawlers = 1;
		//String crawlUrl = "http://mysql12.f4.htw-berlin.de/crawl/";
		String crawlUrl = "http://www.openframeworks.cc/";

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		
		CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);
		
			
		crawlController.addSeed(crawlUrl);
		crawlController.start(MyCrawler.class, numberOfCrawlers);
		System.out.println("Finished Crawl "+ crawlUrl);
	}
}
