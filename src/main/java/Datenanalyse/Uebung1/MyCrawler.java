package Datenanalyse.Uebung1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import Datenanalyse.Uebung1.Index.LuceneWriter;
import Datenanalyse.Uebung1.PageGraph.PageGraph;
import Datenanalyse.Uebung1.PageGraph.PageNode;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Modified Crawler from WebCrawler from the crawler4j library. My Crawler
 * contains a page graph to store the crawled pages.
 * 
 * @author Dennis Haegler - s0532338
 */
public class MyCrawler extends WebCrawler {

	/** Logger to log informations */
	static Logger logger = Logger.getLogger(MyCrawler.class);

	/** Crawler stores pages in the page graph */
	private PageGraph pageGraph;

	/** A lucene writer */
	private LuceneWriter lucenewriter;

	/**
	 * Initialize the list of the crawled pages and the page graph.
	 */
	@Override
	public void onStart() {
		this.pageGraph = new PageGraph();
		try {
			this.lucenewriter = new LuceneWriter("index");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}

	}

	/**
	 * Specifies whether the given url should be crawled or not (based on your
	 * crawling logic). Change the logic by change the <code>shouldVisit</code>.
	 * The crawler on visit pages if this methode returns true.
	 * 
	 * @return true if the page should be visited otherwise false.
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String crawlUrl = "http://mysql12.f4.htw-berlin.de/crawl/";
		
		String href = url.getURL().toLowerCase();
		return href.startsWith(crawlUrl) && href.endsWith(".html");
	}

	/**
	 * Handle to content of a visited page.
	 * 
	 * @param page
	 *            the page object that is just fetched and parsed.
	 */
	@Override
	public void visit(Page page) {
		System.out.println(toStringPageInformation(page));
		//logger.info(toStringPageInformation(page));
		storeCrawledPageAsFile(page.getWebURL().getAnchor(), page);
		//pageGraph.addNoneExcistingPageNode(page);
	}

	/**
	 * Runs before the crawler terminates. Handle stored list of pages.
	 */
	@Override
	public void onBeforeExit() {
		storePageGraphElementsAsFiles();
		if (!this.pageGraph.isEmpty()) {
			this.pageGraph.linkPageNodes();
			String storedPages = toString();
			String linkPathWithoutSuffix = toStringLinkPathWithoutSuffix();
			storeFile("cawled_page_informations.txt", storedPages);
			storeFile("path_listing.txt", linkPathWithoutSuffix);
			
			logger.info("CRAWLED\n" + storedPages);
			logger.info("LINK PATH DETECTED:\n" + linkPathWithoutSuffix);
			
			System.out.print("PageGraph size: " + pageGraph.size() + "\n");
			pageGraph.calculatePageRanks();
			System.out.println(pageGraph.toStringPageRankOfPageNodes());

			storePageGraphLucen();
			try {
				this.lucenewriter.close();
			}
			catch (IOException e) {
			}
			
			//PageRank pageRank = new PageRank(pageGraph);
			//pageRank.calculatePageRanks();
		}
	}

	/**
	 * Returns a string of the page graph.
	 * 
	 * @return a string of the page graph.
	 */
	@Override
	public String toString() {
		return pageGraph.toString();
	}

	/**
	 * Returns the string of a link path of the page nodes in the page graph. A
	 * link path is row of anchor. The first anchor is the anchor which contains
	 * the following anchors.
	 * 
	 * @return the string of a link path, beginning with head page.
	 */
	public String toStringLinkPath() {
		return pageGraph.toStringLinkPath();
	}

	/**
	 * Returns the string of a link path of the page nodes in the page graph. A
	 * link path is row of anchor. The first anchor is the anchor which contains
	 * the following anchors as links. <b>The anchor will be without a suffix
	 * like html</b>.
	 * 
	 * @return
	 */
	public String toStringLinkPathWithoutSuffix() {
		return pageGraph.toStringLinkPathWithoutSuffix();
	}

	/**
	 * Writes all information of a visited page on the terminal.
	 * 
	 * @param page
	 *            a page crawled by crawler4j.
	 */
	public String toStringPageInformation(Page page) {
		String string = "";
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		String domain = page.getWebURL().getDomain();
		String path = page.getWebURL().getPath();
		String subDomain = page.getWebURL().getSubDomain();
		String parentUrl = page.getWebURL().getParentUrl();
		String anchor = page.getWebURL().getAnchor();

		string += "Docid: " + docid + "\n";
		string += "URL: " + url + "\n";
		string += "Domain: '" + domain + "'\n";
		string += "Sub-domain: '" + subDomain + "'\n";
		string += "Path: '" + path + "'\n";
		string += "Parent page: " + parentUrl + "\n";
		string += "Anchor text: " + anchor + "\n";

		if (page.getParseData() instanceof HtmlParseData) {
			ParseData parseData = getHtmlLinkOnly(page);
			page.setParseData(parseData);
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			List<WebURL> links = htmlParseData.getOutgoingUrls();
			string += "Text length: " + text.length() + "\n";
			string += "Html length: " + html.length() + "\n";
			string += "Number of outgoing links: " + links.size() + "\n\n";
		}
		return string;
	}

	/**
	 * Stores a crawled page as a file. The content of the file belongs on the
	 * suffix of the filename. If the suffix is ".txt", the content will be an
	 * clean text. Otherwise the content will be HTML data.
	 * 
	 * @param filename
	 *            path of the file.
	 * @param pageNode
	 *            delivers the content of the file.
	 */
	private void storeStoredPageAsFile(String filename, PageNode pageNode) {
		if (filename.endsWith(".txt")) {
			storeFile("crawled/stored/" + filename, pageNode.getText());
		} else {
			storeFile("crawled/stored/" + filename, pageNode.getHtml());
		}
	}

	/**
	 * Stores a crawled page as a file. The content of the file belongs on the
	 * suffix of the filename. If the suffix is ".txt", the content will be an
	 * clean text. Otherwise the content will be HTML data.
	 * 
	 * @param filename
	 *            path of the file.
	 * @param page
	 *            delivers the content for the file.
	 */
	private void storeCrawledPageAsFile(String filename, Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		if (filename.endsWith(".txt")) {
			storeFile("crawled/crawler4j/" + filename, htmlParseData.getText());
		} else {
			storeFile("crawled/crawler4j/" + filename, htmlParseData.getHtml());
		}
	}

	/**
	 * Stores a file with a content.
	 * 
	 * @param filename
	 *            path of the file.
	 * @param content
	 *            text in the file.
	 */
	private void storeFile(String filename, String content) {
		FileWriter fw = new FileWriter(filename);
		fw.write(content);
		fw.closeFile();
	}

	/**
	 * Returns links with html suffix only from the links of a
	 * <code>ParsData</code> of a <code>Page</code>
	 * 
	 * @param page
	 *            a crawler4j Page.
	 * @return links with html suffix only.
	 */
	private HtmlParseData getHtmlLinkOnly(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		List<WebURL> links = getHtmlLinkOnly(htmlParseData.getOutgoingUrls());
		htmlParseData.setOutgoingUrls(links);
		return htmlParseData;
	}

	/**
	 * Returns the html links from the given list of WebURL links.
	 * 
	 * @param links
	 *            list of links.
	 * @return html suffix only links.
	 */
	private List<WebURL> getHtmlLinkOnly(List<WebURL> links) {
		List<WebURL> result = new ArrayList<WebURL>();
		boolean isHTMLEnding = false;
		for (WebURL url : links) {
			isHTMLEnding = url.getURL().endsWith(".html");
			if (isHTMLEnding) {
				result.add(url);
			}
		}
		return result;
	}

	/**
	 * Stores files of all stored <code>PageNodes</code> in the
	 * <code>PageGrap</code>.
	 */
	private void storePageGraphElementsAsFiles() {
		int graphSize = this.pageGraph.size();
		PageNode pageNode;
		String filename;
		for (int i = 0; i < graphSize; i++) {
			pageNode = this.pageGraph.getPageNode(i);
			filename = pageNode.getAnchor();
			storeStoredPageAsFile(filename, pageNode);
		}
	}
	
	private void storePageGraphLucen() {
		int size = pageGraph.size();
		String pageName;
		String content;
		for (int i = 0; i < size; i++) {
			pageName = pageGraph.getPageNode(i).getAnchor();
			content = pageGraph.getPageNode(i).getText();
			try {
				this.lucenewriter.write(pageName, content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
	}
}