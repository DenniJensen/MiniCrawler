package Datenanalyse.Uebung1.PageGraph;

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
	
	/** The page rank of the page node */
	private double pageRankNew;
	
	/** The old page rank of the page node */
	private double pageRankOld;

	/**
	 * Constructs a page node from a given page. The given page will be a page
	 * node and could have outgoing links witch will be initialized and saved in
	 * a list of outgoing links.
	 * 
	 * @param page
	 */
	public PageNode(Page page) {
		initNewPageNode(page);
	}

	/**
	 * Constructs a page node from a given page. The given page will be a page
	 * node and could have outgoing links witch will be initialized and saved in
	 * a list of outgoing links.
	 * 
	 * @param pageURL
	 */
	public PageNode(WebURL pageURL) {
		initNewPageNode(new Page(pageURL));
	}
	
	/**
	 * Returns the page rank of the page node.
	 * 
	 * @return
	 */
	public double getPageRankOld() {
		return pageRankOld;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getPageRankNew() {
		return pageRankNew;
	}
	
	/**
	 * Sets the page rank.
	 * 
	 * @param value
	 */
	public void setPageRankNew(double value) {
		this.pageRankNew = value;
	}
	
	public void setPageRankOld(double value) {
		this.pageRankOld = value;
	}

	/**
	 * Returns the number of outgoing links
	 */
	public int getNumberOutgoingLinks() {
		return outgoingLinks.size();
	}

	/**
	 * Returns the number of incoming links
	 */
	public int getNumberIncomingLinks() {
		return incomingLinks.size();
	}

	/**
	 * Returns the number of outgoing links stored from crawler4j in the html
	 * parser of the page head from the page node. The number has not to be
	 * identical same to the size of out going links in the page not, in reason
	 * that the out going links of the page not could be not initialized.
	 * 
	 * @return
	 */
	public int getNumberOutgoingLinksFromHeadPage() {
		return getWebUrlsFromHeadPage().size();
	}

	/**
	 * Returns the url from the page node.
	 */
	public String getURL() {
		return headPage.getWebURL().getURL();
	}

	/**
	 * 
	 * @return
	 */
	public String getDomain() {
		return headPage.getWebURL().getDomain();
	}

	/**
	 * 
	 * @return
	 */
	public int getDocid() {
		return headPage.getWebURL().getDocid();
	}

	/**
	 * 
	 * @return
	 */
	public String getParentUrl() {
		return headPage.getWebURL().getParentUrl();
	}

	/**
	 * 
	 * @return
	 */
	public String getSubDomain() {
		return headPage.getWebURL().getSubDomain();
	}

	/**
	 * 
	 * @return
	 */
	public String getPath() {
		return headPage.getWebURL().getPath();
	}

	/**
	 * 
	 * @return
	 */
	public String getAnchor() {
		return headPage.getWebURL().getAnchor();
	}

	/**
	 * 
	 * @return
	 */
	public String getAnchorWithoutSuffix() {
		return getAnchor().split(".html")[0];
	}
	
	/**
	 * 
	 * @return
	 */
	public String getText() {
		HtmlParseData htmlParseData = (HtmlParseData) headPage.getParseData();
		return htmlParseData.getText();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getHtml() {
		HtmlParseData htmlParseData = (HtmlParseData) headPage.getParseData();
		return htmlParseData.getHtml();
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public PageNode getOutgoingLink(int index) {
		return outgoingLinks.get(index);
	}

	/**
	 * 
	 * @param pageNode
	 */
	public void addOutgoingLink(PageNode pageNode) {
		outgoingLinks.add(pageNode);
	}

	/**
	 * 
	 * @param pageNode
	 */
	public void addIncomingLink(PageNode pageNode) {
		incomingLinks.add(pageNode);
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public boolean isPageNode(Page page) {
		String webUrl = page.getWebURL().getURL();
		return isPageNode(webUrl);
	}

	/**
	 * 
	 * @param webUrl
	 * @return
	 */
	public boolean isPageNode(String webUrl) {
		String headPageUrl = this.headPage.getWebURL().getURL().toLowerCase();
		webUrl = webUrl.toLowerCase();
		return (headPageUrl.equals(webUrl));
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public boolean isOutgoingLinks(Page page) {
		String webUrl = page.getWebURL().getURL();
		return isOutgoingLink(webUrl);
	}

	/**
	 * 
	 * @param pageNode
	 * @return
	 */
	public boolean isOutgoingLink(PageNode pageNode) {
		String webUrl = pageNode.getURL();
		return isOutgoingLink(webUrl);
	}

	/**
	 * 
	 * @param webUrl
	 * @return
	 */
	public boolean isOutgoingLink(String webUrl) {
		for (PageNode link : outgoingLinks) {
			if (link.isPageNode(webUrl)) {
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
	 * @param page
	 * @return
	 */
	public boolean isIncomingLink(Page page) {
		String webUrl = page.getWebURL().getURL();
		return isIncomingLink(webUrl);
	}

	/**
	 * 
	 * @param pageNode
	 * @return
	 */
	public boolean isIncomingLink(PageNode pageNode) {
		String webUrl = pageNode.getURL();
		return isOutgoingLink(webUrl);
	}

	/**
	 * 
	 * @param webUrl
	 * @return
	 */
	public boolean isIncomingLink(String webUrl) {
		for (PageNode link : incomingLinks) {
			if (link.isPageNode(webUrl)) {
				return true;
			}
		}
		return false;
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

	@Override
	public String toString() {
		String string = "";
		int docid = getDocid();
		String url = getURL();
		String domain = getDomain();
		String path = getPath();
		String subDomain = getSubDomain();
		String parentUrl = getParentUrl();
		String anchor = getAnchor();
		string += ("Docid: " + docid + "\n");
		string += ("URL: " + url + "\nf");
		string += ("Domain: '" + domain + "'\n");
		string += ("Sub-domain: '" + subDomain + "'\n");
		string += ("Path: '" + path + "'\n");
		string += ("Parent page: " + parentUrl + "\n");
		string += ("Anchor text: " + anchor + "\n");
		string += toStringHtmlParseInformation();
		string += ("Number of outgoing links: " + getNumberOutgoingLinks() + "\n");
		string += ("Number of incoming links: " + getNumberIncomingLinks());
		return string;
	}

	/**
	 * 
	 * @return a row of anchors, beginning with start and followed by the links.
	 */
	public String toStringLinkPath() {
		String resultString = "";
		if (getAnchor() == null) {
			resultString += getURL();
		} else {
			resultString += getAnchor() + ": ";
			for (PageNode pN : outgoingLinks) {
				resultString += pN.getAnchor() + " ";
			}
		}
		return resultString;
	}

	/**
	 * 
	 * @return a row of anchors, beginning with start and followed by the links.
	 */
	public String toStringLinkPathWithoutSuffix() {
		String resultString = "";
		if (getAnchor() == null) {
			resultString += getURL();
		} else {
			resultString += getAnchorWithoutSuffix() + ": ";
			for (PageNode pN : outgoingLinks) {
				resultString += pN.getAnchorWithoutSuffix() + " ";
			}
		}
		return resultString;
	}

	/**
	 * 
	 * @return
	 */
	public String toStringHtmlParseInformation() {
		String string = "";
		if (headPage.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) headPage
					.getParseData();
			String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			string += "Text length: " + text.length() + "\n";
			string += "Html length: " + html.length() + "\n";
		}
		return string;
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	private List<WebURL> getWebUrlsFromHeadPage() {
		HtmlParseData htmlParseData = (HtmlParseData) headPage.getParseData();
		return htmlParseData.getOutgoingUrls();
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getOutgoingLinkUrlFromHeadPage(int index) {
		return this.getWebUrlsFromHeadPage().get(index).getURL();
	}
	
	/**
	 * 
	 * @return
	 */
	public double getLinkedPageRankValue() {
		double linkedPagerankValue = 0;
		for (PageNode linkedNode : incomingLinks) {
				linkedPagerankValue += linkedNode.pageRankOld
						/ linkedNode.getNumberOutgoingLinks();
		}
		return linkedPagerankValue;
	}

	/**
	 * Initialize an empty page node by a given page.
	 * 
	 * @param page
	 *            the new page head for the page node.
	 */
	protected void initNewPageNode(Page page) {
		this.headPage = page;
		this.outgoingLinks = new ArrayList<PageNode>();
		this.incomingLinks = new ArrayList<PageNode>();
		this.pageRankNew = 0;
		this.pageRankOld = 0;
	}
}
