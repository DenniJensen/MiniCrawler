package Datenanalyse.Uebung1;

import java.util.ArrayList;
import java.util.List;
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

	/** Crawler stores pages in the page graph */
	private PageGraph pageGraph;

	/** List of crawled pages. Stored from the web crawler. */
	private ArrayList<Page> crawledPages;

	/**
	 * Initialize the list of the crawled pages and the page graph.
	 */
	public void onStart() {
		this.pageGraph = new PageGraph();
		crawledPages = new ArrayList<Page>();
	}

	/**
	 * Specifies whether the given url should be crawled or not (based on your
	 * crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return href.startsWith("http://mysql12.f4.htw-berlin.de/crawl/d")
				&& href.endsWith(".html");
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
		crawledPages.add(page);
	}

	/**
	 * Runs before the crawler terminates. Handle stored list of pages.
	 */
	public void onBeforeExit() {
		System.out.println("crawled Pages: " + crawledPages.size());
		for (Page page : crawledPages) {
			pageGraph.addNoneExcistingPageNode(page);
		}
		pageGraph.linkPageNodes();
		System.out.println("Page graph size: " + pageGraph.size());
		String pageInfo = toString();
		String path = toStringLinkPath();
		String pathWithoutSuffix = toStringLinkPathWithoutSuffix();
		System.out.println(pageInfo);
		System.out.println(pathWithoutSuffix);
		
		FileWriter crawledPages = new FileWriter("cawled_page_informations.txt");
		crawledPages.write(pageInfo);
		FileWriter linkPath = new FileWriter("path_listing.txt");
		linkPath.write(path);
		linkPath.write(pathWithoutSuffix);
		crawledPages.closeFile();
		linkPath.closeFile();
	}

	/**
	 * Returns a string of the page graph.
	 * 
	 * @return a string of the page graph.
	 */
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
	 * the following anchors as links. <b>The anchor will be without a suffix like 
	 * html</b>.
	 * @return
	 */
	public String toStringLinkPathWithoutSuffix() {
		return pageGraph.toStringLinkPathWithoutSuffix();
	}

	/**
	 * Writes all information of a visited page on the terminal.
	 * 
	 * @param page a page crawled by crawler4j.
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
			ParseData parseData = (ParseData) getHtmlLinkOnly(page);
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

	public List<WebURL> getHtmlLinkOnly(List<WebURL> links) {
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

	private HtmlParseData getHtmlLinkOnly(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		List<WebURL> links = getHtmlLinkOnly(htmlParseData.getOutgoingUrls());
		htmlParseData.setOutgoingUrls(links);
		return htmlParseData;
	}
}
