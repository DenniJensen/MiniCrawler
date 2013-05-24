package Datenanalyse.Uebung1;

import java.util.ArrayList;
import java.util.List;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.ParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 
 * @author Dennis Haegler - s0532338
 * 
 */
public class MyCrawler extends WebCrawler {
	private PageGraph pageGraph;

	public MyCrawler() {
		this.pageGraph = new PageGraph();
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
		writePageInformation(page);
		if (pageGraph.isPageNode(page)) {

		} else {
			pageGraph.addPageNode(page);
		}
	}

	public void writePageInformation(Page page) {
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

		if (page.getParseData() instanceof HtmlParseData) {
			ParseData parseData = (ParseData) getHtmlLinkOnly(page);
			page.setParseData(parseData);
			writeHTMLParseDataInformation(page);
		}
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

	private void writeHTMLParseDataInformation(Page page) {
		List<WebURL> links = new ArrayList<WebURL>();
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		String text = htmlParseData.getText();
		String html = htmlParseData.getHtml();
		links = htmlParseData.getOutgoingUrls();
		System.out.println("Text length: " + text.length());
		System.out.println("Html length: " + html.length());
		System.out.println("Number of outgoing links: " + links.size());
	}

	private HtmlParseData getHtmlLinkOnly(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		List<WebURL> links = getHtmlLinkOnly(htmlParseData.getOutgoingUrls());
		htmlParseData.setOutgoingUrls(links);
		return htmlParseData;
	}

}
