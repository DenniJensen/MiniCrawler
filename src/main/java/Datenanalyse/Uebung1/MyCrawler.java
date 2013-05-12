package Datenanalyse.Uebung1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	
	
	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        return href.startsWith("http://mysql12.f4.htw-berlin.de/crawl/d");
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
		List<WebURL> links = new ArrayList<>();
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			links = htmlParseData.getOutgoingUrls();
			System.out.println("Text length: " + text.length());
			System.out.println("Html length: " + html.length());
			System.out.println("Number of outgoing links: " + links.size());
		}
		Header[] responseHeaders = page.getFetchResponseHeaders();
		if (responseHeaders != null) {
			System.out.println("Response headers:");
			for (Header header : responseHeaders) {
				System.out.println("\t" + header.getName() + ": "
						+ header.getValue());
			}
		}
		System.out.println("=============");
	}

	/**
	 * This function is called once the header of a page is fetched. It can be
	 * overwritten by sub-classes to perform custom logic for different status
	 * codes. For example, 404 pages can be logged, etc.
	 * 
	 * @param webUrl
	 * @param statusCode
	 * @param statusDescription
	 */
	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode,
			String statusDescription) {
		// Do nothing by default
		// Sub-classed can override this to add their custom functionality
	}

	/**
	 * This function is called if the content of a url could not be fetched.
	 * 
	 * @param webUrl
	 */
	@Override
	protected void onContentFetchError(WebURL webUrl) {
		// Do nothing by default
		// Sub-classed can override this to add their custom functionality
	}

	/**
	 * This function is called if there has been an error in parsing the
	 * content.
	 * 
	 * @param webUrl
	 */
	@Override
	protected void onParseError(WebURL webUrl) {
		// Do nothing by default
		// Sub-classed can override this to add their custom functionality
	}
}
