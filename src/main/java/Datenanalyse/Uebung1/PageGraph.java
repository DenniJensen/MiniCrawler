package Datenanalyse.Uebung1;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 
 * @author Dennis Haegler - s0532338
 * 
 */
public class PageGraph {

	static Logger log = Logger.getLogger(PageGraph.class);

	/** Array list of page nodes */
	private ArrayList<PageNode> pageNodes;

	public PageGraph() {
		pageNodes = new ArrayList<PageNode>();
	}

	/**
	 * Sets a list of page nodes as new array list of page nodes.
	 * 
	 * @param pageNodes
	 *            the new list of page nodes for the graph.
	 */
	public void setPageNode(ArrayList<PageNode> pageNodes) {
		this.pageNodes = pageNodes;
	}

	/**
	 * Adds a page node to the array list of page nodes.
	 * 
	 * @param pageNode
	 *            the page node to add to the list.
	 */
	public void addPageNode(PageNode pageNode) {
		pageNodes.add(pageNode);
	}

	/**
	 * Adds a page as page node to the array list of page nodes.
	 * 
	 * @param pageNode
	 *            the page node to add to the list.
	 */
	public void addPageNode(Page page) {
		PageNode pN = new PageNode(page);
		pageNodes.add(pN);
	}

	/**
	 * Adds a page as page node to the page graph in case, that the page is not
	 * already a page node in the page graph.
	 * 
	 * @param page
	 */
	public void addNoneExcistingPageNode(Page page) {
		if (this.isPageNode(page)) {

		} else {
			this.addPageNode(page);
		}
	}

	/**
	 * Adds a page node to the page graph if the page note is not already a page
	 * node in the page graph.
	 * 
	 * @param pageNode
	 */
	public void addNoneExcistingPageNode(PageNode pageNode) {
		if (this.isPageNode(pageNode)) {

		} else {
			this.addPageNode(pageNode);
		}
	}

	/**
	 * Removes the given page node from the list.
	 * 
	 * @param pageNode
	 *            the page node to remove from the array list of page nodes.
	 */
	public void removePageNode(PageNode pageNode) {
		pageNodes.remove(pageNode);
	}

	/**
	 * Returns the page node on the given index.
	 * 
	 * @param index
	 *            the index where page node is on the array list of page nodes.
	 * @return the page node on the given index.
	 */
	public PageNode getPageNode(int index) {
		return pageNodes.get(index);
	}

	/**
	 * 
	 * @param pageNode
	 * @return
	 */
	public PageNode getPageNode(PageNode pageNode) {
		String webUrl = pageNode.getURL();
		return getPageNode(webUrl);
	}

	/**
	 * 
	 * @param page
	 * @return
	 */
	public PageNode getPageNode(Page page) {
		String webUrl = page.getWebURL().getURL();
		return getPageNode(webUrl);
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public PageNode getPageNode(String url) {
		for (PageNode pageNode : pageNodes) {
			if (pageNode.isPageNode(url)) {
				return pageNode;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public int size() {
		return pageNodes.size();
	}

	/**
	 * Returns true if the given page node is in the list of page nodes from the
	 * graph,otherwise the url will be not found it returns false. If the URL of
	 * the given page node will be found in the list of page nodes in the list
	 * of the graph, it will be detected as true.
	 * 
	 * @param pageNode
	 *            the page node witch will be found or not in the list.
	 * @return True if the url of the pageNode will be found in the graph
	 *         otherwise false.
	 */
	public boolean isPageNode(PageNode pageNode) {
		String url = pageNode.getURL();
		return existAsPageNode(url);
	}

	/**
	 * Returns true if the URL of the given page will be found in the graph,
	 * otherwise the URL will be not found it returns false.
	 * 
	 * @param page
	 *            the page witch will be searched in the graph.
	 * @return True if the URL of will be found in the graph, otherwise false.
	 */
	public boolean isPageNode(Page page) {
		String url = page.getWebURL().getURL();
		return existAsPageNode(url);
	}

	/**
	 * Returns true if the URL will be found in the graph, otherwise the URL
	 * will be not found it returns false.
	 * 
	 * 
	 * @param url
	 *            an URL witch will be searched in the graph.
	 * @return true if the given URL will be found in the graph.
	 */
	public boolean existAsPageNode(String url) {
		for (PageNode pageNode : pageNodes) {
			if (pageNode.getURL().equals(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a string with information of the page graph. The String includes
	 * all informations of page nodes from the graph.
	 * 
	 * @return string with information of the page nodes in the page graph.
	 */
	public String toString() {
		String result = "";
		for (PageNode pageNode : pageNodes) {
			result += "\n" + pageNode.toString() + "\n";
		}
		return result;
	}

	/**
	 * Returns the string of a link path of the page nodes in the page graph. A
	 * link path is row of anchor. The first anchor is the anchor which contains
	 * the following anchors.
	 * 
	 * @return the string of a link path, beginning with head page.
	 */
	public String toStringLinkPath() {
		String result = "";
		for (PageNode pageNode : pageNodes) {
			result += pageNode.toStringLinkPath() + "\n";
		}
		return result;
	}
	
	/**
	 * Returns the string of a link path of the page nodes in the page graph. A
	 * link path is row of anchor. The first anchor is the anchor which contains
	 * the following anchors as links. <b>The anchor will be without a suffix like 
	 * html</b>.
	 * 
	 * @return the string of a link path, beginning with head page.
	 */
	public String toStringLinkPathWithoutSuffix() {
		String result = "";
		for (PageNode pageNode : pageNodes) {
			result += pageNode.toStringLinkPathWithoutSuffix() + "\n";
		}
		return result;
	}

	/**
	 * Linking all page nodes with each other if they are linked.
	 */
	public void linkPageNodes() {
		String url = "";
		int numberLinks;
		PageNode linkedNode;
		for (PageNode pageNode : pageNodes) {
			numberLinks = pageNode.getNumberOutgoingLinksFromHeadPage();
			for (int i = 0; i < numberLinks; i++) {
				url = pageNode.getOutgoingLinkUrlFromHeadPage(i);
				if (this.existAsPageNode(url)) {
					linkedNode = this.getPageNode(url);
					pageNode.addOutgoingLink(linkedNode);
					linkedNode.addIncomingLink(pageNode);
				} 
			}
		}
	}
}
