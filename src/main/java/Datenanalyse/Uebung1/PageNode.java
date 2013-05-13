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

	public PageNode(Page page) {
		this.headPage = page;
		initLinks();
	}

	public PageNode(WebURL pageURL) {
		this.headPage = new Page(pageURL);
		//initLinks();
	}
	
	public void setPageNodeLinks(List<PageNode> links) {
		this.outgoingLinks = links;
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

	/*
	 * public String toString() { String resultString = headPage + ":"; if
	 * (hasOutgoingLinks()) { for (String link : links) { resultString += "," +
	 * link; } } return resultString; }
	 */
}
