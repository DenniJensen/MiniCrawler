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
		initNewPageNode(page);
		initOutgoingLinks(); // TODO right Init of links
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
	 * Returns the number of outgoing links
	 */
	public int getNumberOutgoingLinks() {
		return outgoingLinks.size();
	}

	/**
	 * Returns the number of incoming links
	 */
	public int getNumberIncomingLinks() {
		return outgoingLinks.size();
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
		return getWebUrlsFromHeadPage(headPage).size();
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
	public boolean containsInOutgoingLinks(Page page) {
		String webUrl = page.getWebURL().getURL();
		return containsInOutgoingLinks(webUrl);
	}

	/**
	 * 
	 * @param pageNode
	 * @return
	 */
	public boolean containsInOutgoingLinks(PageNode pageNode) {
		String webUrl = pageNode.getURL();
		return containsInOutgoingLinks(webUrl);
	}

	/**
	 * 
	 * @param webUrl
	 * @return
	 */
	public boolean containsInOutgoingLinks(String webUrl) {
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
		String resultString = "";
		int docid = getDocid();
		String url = getURL();
		String domain = getDomain();
		String path = getPath();
		String subDomain = getSubDomain();
		String parentUrl = getParentUrl();
		String anchor = getAnchor();

		resultString += ("Docid: " + docid + "\n");
		resultString += ("URL: " + url + "\n");
		resultString += ("Domain: '" + domain + "'\n");
		resultString += ("Sub-domain: '" + subDomain + "'\n");
		resultString += ("Path: '" + path + "'\n");
		resultString += ("Parent page: " + parentUrl + "\n");
		resultString += ("Anchor text: " + anchor + "\n");
		resultString += ("Number of outgoing links: "
				+ getNumberOutgoingLinks() + "\n");
		resultString += ("Number of incoming links: " + getNumberIncomingLinks());
		return resultString;
	}

	/**
	 * Returns a link path string of the anchors of page node and links. Begins
	 * by the anchor of the page node and will be continued by the anchors of
	 * the links
	 * 
	 * @return
	 */
	public String toStringLinkPath() {
		String resultString = getAnchor();
		for (PageNode pN : outgoingLinks) {
			resultString += pN.getAnchor() + " ";
		}
		return resultString;
	}

	/**
	 * 
	 */
	private void initOutgoingLinks() {
		List<WebURL> links = getWebUrlsFromHeadPage(headPage);
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
	private List<WebURL> getWebUrlsFromHeadPage(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		return htmlParseData.getOutgoingUrls();
	}

	/**
	 * Initialize an empty page node by a given page.
	 * 
	 * @param page the new page head for the page node.
	 */
	protected void initNewPageNode(Page page) {
		this.headPage = page;
		this.outgoingLinks = new ArrayList<PageNode>();
		this.incomingLinks = new ArrayList<PageNode>();
	}

	// TODO calculate page Rank
}
