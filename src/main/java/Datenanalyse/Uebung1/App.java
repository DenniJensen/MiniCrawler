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

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//TODO graph
		//TODO page rank
		String crawlStorageFolder = "crawl";
		int numberOfCrawlers = 1;

		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController crawlController = new CrawlController(config, pageFetcher, robotstxtServer);
		
		MyCrawler myCrawler = new MyCrawler();

		crawlController.addSeed("http://mysql12.f4.htw-berlin.de/crawl/");
		crawlController.start(MyCrawler.class, numberOfCrawlers);
		System.out.println(myCrawler.toString());
		
		
	}
}
