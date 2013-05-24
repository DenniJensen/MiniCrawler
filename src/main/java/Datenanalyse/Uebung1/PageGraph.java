package Datenanalyse.Uebung1;

import java.util.ArrayList;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 
 * @author Dennis Haegler - s0532338
 *
 */
public class PageGraph {
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
		return isPageNode(url);
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
		return isPageNode(url);
	}

	/**
	 * Returns true if the URL will be found in the graph, otherwise the URL
	 * will be not found it returns false.
	 * 
	 * 
	 * @param url an URL witch will be searched in the graph.
	 * @return true if the given URL will be found in the graph.
	 */
	public boolean isPageNode(String url) {
		for (PageNode pageNode : pageNodes) {
			if (pageNode.getURL().equals(url)) {
				return true;
			}
		}
		return false;
	}
}
