package Datenanalyse.Uebung1;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * A node of page is a page with outgoing and incoming pages.
 * 
 * @author Dennis Haegler - s0532338
 * 
 */
public class PageNode {
	/** The page of the page node */
	private Page headPage;

	/** List of outgoing links from the page */
	private List<PageNode> outgoingLinks;

	/** List of incoming links to the page */
	private List<PageNode> incomingLinks;

	/**
	 * Constructs a page node from a given page. The given page will be a page
	 * node and could have outgoing links witch will be initialized and saved in
	 * a list of outgoing links.
	 * 
	 * @param page
	 */
	public PageNode(Page page) {
		this.headPage = page;
		this.outgoingLinks = new ArrayList<PageNode>();
		this.incomingLinks = new ArrayList<PageNode>();
		initOutgoingLinks();
	}

	/**
	 * Constructs a page node from a given page. The given page will be a page
	 * node and could have outgoing links witch will be initialized and saved in
	 * a list of outgoing links.
	 * 
	 * @param pageURL
	 */
	public PageNode(WebURL pageURL) {
		this.headPage = new Page(pageURL);
		// initLinks();
	}

	/**
	 * Returns the number of outgoing links
	 */
	public int getNumberOutgoingLinks() {
		return outgoingLinks.size();
	}

	/**
	 * Returns the url from the page node. 
	 */
	public String getURL() {
		return headPage.getWebURL().getURL();
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public boolean isPageNode(Page page) {
		return isPageNode(page.getWebURL().getURL());
	}
	
	/**
	 * 
	 * @param webUrl
	 * @return
	 */
	public boolean isPageNode(String webUrl) {
		String headPageUrl = this.headPage.getWebURL().getURL();
		boolean isPageNode = false;
		if (headPageUrl.equals(webUrl)) {
			isPageNode = true;
		} else {
			isPageNode = false;
		}
		return isPageNode;
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public boolean containsInOutgoingLinks(Page page) {
		String compareUrl = page.getWebURL().getURL();
		String linkUrl = "";
		for (PageNode link : this.outgoingLinks) {
			compareUrl = link.getURL();
			if (compareUrl.equals(linkUrl)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param pageNode
	 * @return
	 */
	public boolean containsInOutgoingLinks(PageNode pageNode) {
		String compareUrl = pageNode.getURL();
		String linkUrl = "";
		for (PageNode link : outgoingLinks) {
			linkUrl = link.getURL();
			if (compareUrl.equals(linkUrl)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasOutgoingLinks() {
		boolean hasLinks = false;
		if (this.outgoingLinks.size() > 0) {
			hasLinks = true;
		}
		return hasLinks;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasIncomingLinks() {
		boolean hasLinks = false;
		if (this.incomingLinks.size() > 0) {
			hasLinks = true;
		}
		return hasLinks;
	}

	/**
	 * 
	 * @param links
	 * @return
	 */
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

	// TODO check
	public String toString() {
		String resultString = "";
		resultString += this.headPage.getWebURL().getURL() + ": ";
		for (PageNode pN : outgoingLinks) {
			resultString += pN.headPage.getWebURL().getAnchor() + " ";
		}
		resultString += "\n\t links: " + getNumberOutgoingLinks();
		return resultString;
	}

	private void initSublinks() {
	}

	// TODO go for check
	/**
	 * 
	 */
	private void initOutgoingLinks() {
		List<WebURL> links = getOutGoingLinks(headPage);
		links = pickHTML(links);
		List<PageNode> pageNodes = new ArrayList<PageNode>();
		for (WebURL link : links) {
			PageNode newNode = new PageNode(link);
			pageNodes.add(newNode);
		}
		this.outgoingLinks = pageNodes;
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	private List<WebURL> getOutGoingLinks(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		return htmlParseData.getOutgoingUrls();
	}

	// TODO calculate page Rank
}
