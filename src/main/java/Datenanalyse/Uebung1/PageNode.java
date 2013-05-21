package Datenanalyse.Uebung1;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Dennis Haegler - s0532338
 * 
 */
public class PageNode {
	private Page headPage;
	private List<PageNode> outgoingLinks;
	private List<PageNode> incomingLinks;

	public PageNode(Page page) {
		this.headPage = page;
		this.outgoingLinks = new ArrayList<PageNode>();
		this.incomingLinks = new ArrayList<PageNode>();
		initLinks();
	}

	public PageNode(WebURL pageURL) {
		this.headPage = new Page(pageURL);
		// initLinks();
	}

	public int getCountOutgoingLinks() {
		return outgoingLinks.size();
	}

	public String getURL() {
		return headPage.getWebURL().getURL();
	}

	public PageNode getPageNode(int index) {
		return this.outgoingLinks.get(index);
	}

	public boolean hasPageAsNode(Page page) {
		String headPageUrl = this.headPage.getWebURL().getURL();
		String compareUrl = page.getWebURL().getURL();
		boolean isPageNode = false;
		if (headPageUrl.equals(compareUrl)) {
			isPageNode = true;
		} else {
			isPageNode = false;
		}
		return isPageNode;
	}

	public boolean isLinkedWithPage(Page page) {
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

	public boolean isLinkedWithPageNode(PageNode pageNode) {
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

	public boolean hasOutgoingLinks() {
		boolean hasLinks = false;
		if (this.outgoingLinks.size() > 0) {
			hasLinks = true;
		}
		return hasLinks;
	}

	public boolean hasIncomingLinks() {
		boolean hasLinks = false;
		if (this.incomingLinks.size() > 0) {
			hasLinks = true;
		}
		return hasLinks;
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

	// TODO check
	@Override
	public String toString() {
		String resultString = "";
		resultString += this.headPage.getWebURL().getURL();
		for (PageNode pN : outgoingLinks) {
			resultString += "\n" + pN.headPage.getWebURL().getAnchor() + ":"
					+ "links: ";
		}
		resultString += "\n\t links: " + getCountOutgoingLinks();
		return resultString;
	}

	private void initSublinks() {
	}

	// TODO go for check
	private void initLinks() {
		List<WebURL> links = getOutGoingLinks(headPage);
		links = pickHTML(links);
		List<PageNode> pageNodes = new ArrayList<PageNode>();
		for (WebURL link : links) {
			PageNode newNode = new PageNode(link);
			pageNodes.add(newNode);
		}
		this.outgoingLinks = pageNodes;
	}

	private List<WebURL> getOutGoingLinks(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		return htmlParseData.getOutgoingUrls();
	}
	
	//TODO calculate page Rank
}
