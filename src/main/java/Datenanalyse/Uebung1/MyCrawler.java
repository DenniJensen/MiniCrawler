package Datenanalyse.Uebung1;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	public MyCrawler() {
	}

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return href.matches("http://mysql12.f4.htw-berlin.de/crawl/");
	}

	/**
	 * Classes that extends WebCrawler can overwrite this function to process
	 * the content of the fetched and parsed page.
	 * 
	 * @param page
	 *            the page object that is just fetched and parsed.
	 */
	@Override
	public void visit(Page page) {
		
		PageNode pageNode = new PageNode(page);
		// Do nothing by default
		// Sub-classed can override this to add their custom functionality
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		String domain = page.getWebURL().getDomain();
		String path = page.getWebURL().getPath();
		String subDomain = page.getWebURL().getSubDomain();
		String parentUrl = page.getWebURL().getParentUrl();
		String anchor = page.getWebURL().getAnchor();

		System.out.println("Docid: " + docid);
		System.out.println("URL: " + url);
		System.out.println("Domain: '" + domain + "'");
		System.out.println("Sub-domain: '" + subDomain + "'");
		System.out.println("Path: '" + path + "'");
		System.out.println("Parent page: " + parentUrl);
		System.out.println("Anchor text: " + anchor);
		List<WebURL> links = new ArrayList<WebURL>();

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			links = pickHTML(htmlParseData.getOutgoingUrls());
			System.out.println("Text length: " + text.length());
			System.out.println("Html length: " + html.length());
			System.out.println("Number of outgoing links: " + links.size());
		}
		
		System.out.println("PageNodeURL: '" + pageNode.getURL() + "'");
		System.out.println("PageNodeLinks: '" + pageNode.getCountOutgoingLinks() + "'");
		
		pageNode = pageNode.getPageNode(5);
		System.out.println("PageNodeURL2: '" + pageNode.getURL() + "'");
		System.out.println("PageNodeLinks2: " + pageNode.getCountOutgoingLinks());
	}
	
	public void buildPageGraph(Page page) {
		PageNode pageNode = new PageNode(page);
		List<PageNode> linksOfPage = new ArrayList<PageNode>();
		int otherNodes = pageNode.getCountOutgoingLinks();
		for (int i = 0; i < otherNodes; i++) {
			linksOfPage.add((pageNode.getPageNode(i)));
		}
	}

	public List<WebURL> pickHTML(List<WebURL> links) {
		List<WebURL> result = new ArrayList<WebURL>();
		boolean endOnHTMLTag = false;
		for (WebURL url : links) {
			endOnHTMLTag = url.getURL().endsWith(".html");
			if (endOnHTMLTag) {
				result.add(url);
			}
		}
		return result;
	}
	
	private List<WebURL> getOutGoingLinks(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		return htmlParseData.getOutgoingUrls();
	}
}
