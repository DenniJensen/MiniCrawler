package Datenanalyse.Uebung1.PageGraph;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 
 * @author Dennis Haegler - s0532338
 * 
 */
public class PageGraph {

	/** Logger from log4j to log. */
	static Logger log = Logger.getLogger(PageGraph.class);

	/** Array list of page nodes */
	private ArrayList<PageNode> pageNodes;

	/** The delta value for the page rank calculation */
	private final double terminationDelta = 0.04;

	/** The teleportation probability for the page rank calculation */
	private final double teleportationProbability = 0.05;

	/** Decay factor for the page rank calculation */
	private double dampingFactor = 1 - teleportationProbability;

	/**
	 * Constructs an empty PageGraph.
	 */
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
	 * Returns the damping factor.
	 * 
	 * @return the damping factor.
	 */
	public double getDampingFactor() {
		return dampingFactor;
	}

	/**
	 * Sets the damping factor.
	 * 
	 * @param dampingFactor
	 */
	public void setDampingFactor(double dampingFactor) {
		this.dampingFactor = dampingFactor;
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
	 * Returns the page node with the same url from the given page node.
	 * 
	 * @param pageNode
	 *            the page node you look for with the same url.
	 * @return the page node matching with the given page node.
	 */
	public PageNode getPageNode(PageNode pageNode) {
		String webUrl = pageNode.getURL();
		return getPageNode(webUrl);
	}

	/**
	 * Returns the page node with containing url from the given page.
	 * 
	 * @param page
	 *            contains the url you look for the page node.
	 * @return the page node with the url from the given page.
	 */
	public PageNode getPageNode(Page page) {
		String webUrl = page.getWebURL().getURL();
		return getPageNode(webUrl);
	}

	/**
	 * Returns the page node by the given url.
	 * 
	 * @param url
	 *            the url you look for the page node.
	 * @return the page node by the given url.
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
	 * Returns the size of the page graph.
	 * 
	 * @return the size of the page graph.
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
	 * @param url
	 *            an URL witch will be searched in the graph.
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

	/**
	 * Returns if the page is empty. An empty page graph has not any stored page
	 * node in.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return (this.size() == 0);
	}

	/**
	 * Returns a string with information of the page graph. The String includes
	 * all informations of page nodes from the graph.
	 * 
	 * @return string with information of the page nodes in the page graph.
	 */
	@Override
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
	 * the following anchors as links. <b>The anchor will be without a suffix
	 * like html</b>.
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
	 * Returns a string of all page ranks of all page nodes in the page graph.
	 * 
	 * @return a string of all page ranks.
	 */
	public String toStringPageRankOfPageNodes() {
		String string = "";
		for (PageNode pageNode : pageNodes) {
			string += pageNode.getAnchorWithoutSuffix() + ": "
					+ pageNode.getPageRankNew() + "\n";
		}
		return string;
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
				if (this.isPageNode(url)) {
					linkedNode = this.getPageNode(url);
					pageNode.addOutgoingLink(linkedNode);
					linkedNode.addIncomingLink(pageNode);
				}
			}
		}
	}

	/**
	 * Returns the summary of all page nodes.
	 * 
	 * @return the summary of all page nodes.
	 */
	public double getPageRankSumOfAllPageNodes() {
		double sumPagerank = 0;
		for (PageNode pageNode : pageNodes) {
			sumPagerank += pageNode.getPageRankNew();
		}
		return sumPagerank;
	}

	/**
	 * Calculates the page rank in the page graph.
	 * 
	 * @return the calculated page rank.
	 */
	public double calculatePageRanks() {
		double restult = 0;
		this.setInitialPagerank();
		while (!this.hasTerminatingAccuracy()) {
			this.iteratePagerank();
		}
		return restult;
	}

	/**
	 * Sets a Initial page rank. A initial page rank is <b>1.0 / (count of all
	 * page node in the page graph</b>
	 */
	private void setInitialPagerank() {
		double initialPagerank = 1.0 / this.size();
		for (PageNode pageNode : pageNodes) {
			pageNode.setPageRankNew(initialPagerank);
		}
	}

	/**
	 * Iterate to the next step for the page rank calculation.
	 */
	private void iteratePagerank() {
		double x;
		double y;
		for (PageNode pageNode : pageNodes) {
			pageNode.setPageRankOld(pageNode.getPageRankNew());
		}
		for (PageNode pageNode : pageNodes) {
			x = this.getTeleportPagerankValue();
			y = this.getPagesPagerankValue(pageNode);
			pageNode.setPageRankNew(x + y);
		}
	}

	/**
	 * Returns if the terminating value has been arrived. The terminating value
	 * is the limit when the algorithm has to stop.
	 * 
	 * @return
	 */
	private boolean hasTerminatingAccuracy() {
		boolean terminated = false;
		double absSumChangesWithLastIteration = 0;
		for (PageNode pageNode : pageNodes) {
			absSumChangesWithLastIteration += Math.abs(pageNode
					.getPageRankNew() - pageNode.getPageRankOld());
		}
		if (absSumChangesWithLastIteration < this.terminationDelta) {
			terminated = true;
		}
		return terminated;
	}

	/**
	 * 
	 * @param pageNode
	 * @return
	 */
	private double getPagesPagerankValue(PageNode pageNode) {
		double linkedPagerankValue = pageNode.getLinkedPageRankValue();
		double nonLinkedPagesPagerankValue = this
				.getNonLinkedPagesPagerankValue();
		return (1.0 - this.teleportationProbability)
				* (linkedPagerankValue + nonLinkedPagesPagerankValue);
	}

	/**
	 * 
	 * @return
	 */
	private double getNonLinkedPagesPagerankValue() {
		double nonLinkedPagerankValue = 0;
		for (PageNode pageNode : pageNodes) {
			if (!pageNode.hasOutgoingLinks()) {
				nonLinkedPagerankValue += pageNode.getPageRankOld() / size();
			}
		}
		return nonLinkedPagerankValue;
	}

	/**
	 * 
	 * @return
	 */
	private double getTeleportPagerankValue() {
		return (this.teleportationProbability / this.size());
	}
}