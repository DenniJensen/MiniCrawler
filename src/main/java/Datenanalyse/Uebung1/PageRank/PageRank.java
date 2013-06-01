package Datenanalyse.Uebung1.PageRank;

import org.apache.log4j.Logger;

import Datenanalyse.Uebung1.PageGraph.PageGraph;
import Datenanalyse.Uebung1.PageGraph.PageNode;

public class PageRank {
	/** Page graph full of crawled pages */
	private PageGraph pageGraph;
	
	/** The delta value for the page rank calculation */
	private final double terminationDelta = 0.04;

	/** The teleportation probability for the page rank calculation */
	private final double teleportationProbability = 0.05;

	/** Decay factor for the page rank calculation */
	private final double dampingFactor = 1 - teleportationProbability;
	
	/** Log4j logger */
	static Logger logger = Logger.getLogger(PageRank.class);

	public PageRank(PageGraph pageGraph) {
		this.pageGraph = pageGraph;
	}

	public void calculatePageRanks() {
		System.out.println("Start PageRank Calculation");
		this.setInitialPagerank();
		int iterationCounter = 1;
		while (!this.hasTerminatingAccuracy()) {
			this.printPageRankSum();
			this.iteratePagerank();
			System.out.println("PageRank iteration " + iterationCounter);
			iterationCounter++;
		}
		logger.info("PageRank successfull calculated:");
		//System.out.println("PageRank successfull calculated:");
		//System.out.println("--------------------------------");
		this.printPageRankResults();

	}

	private void printPageRankResults() {
		int size = pageGraph.size();
		PageNode pageNode;
		for (int i = 0; i < size; i++) {
			pageNode = pageGraph.getPageNode(i);
			System.out.println(pageNode.getPageRankNew());
		}
	}

	private boolean hasTerminatingAccuracy() {
		double absSumChangesWithLastIteration = 0;
		double prOld;
		double prNew;
		boolean terminated = false;
		int size = pageGraph.size();
		PageNode pageNode;
		for (int i = 0; i < size; i++) {
			pageNode = pageGraph.getPageNode(i);
			prOld = pageNode.getPageRankOld();
			prNew = pageNode.getPageRankNew();
			absSumChangesWithLastIteration += Math.abs(prNew - prOld);
			
		}
		if (absSumChangesWithLastIteration < this.terminationDelta) {
			terminated = true;
		}
		return terminated;
	}

	private void setInitialPagerank() {
		int size = pageGraph.size();
		double initialPagerank = 1.0 / size;
		PageNode pageNode;
		for (int i = 0; i < size; i++) {
			pageNode = pageGraph.getPageNode(i);
			pageNode.setPageRankNew(initialPagerank);
			pageNode.setPageRankOld(0.0);
		}
		logger.debug("Setted initial page rank value: " + initialPagerank);
	}

	private void iteratePagerank() {
		int size = pageGraph.size();
		double x = teleportationProbability / size;
		double y;
		PageNode pageNode;
		for (int i = 0; i < size; i++) {
			pageNode = pageGraph.getPageNode(i);
			y = this.getPagesPagerankValue(pageNode);
			pageNode.setPageRankOld(pageNode.getPageRankNew());
			pageNode.setPageRankNew(x + y);
		}
	}

	private double getPagesPagerankValue(PageNode pageNode) {
		double linkedPagerankValue = this.getLinkedPagerankValue(pageNode);
		double nonLinkedPagesPagerankValue = this
				.getNonLinkedPagesPagerankValue(pageNode);
		double result = dampingFactor * (linkedPagerankValue + nonLinkedPagesPagerankValue);
		return result;
	}

	private double getLinkedPagerankValue(PageNode pageNode) {
		double linkedPrValue = 0;
		int size = pageGraph.size();
		PageNode otherNode;
		for (int i = 0; i < size; i++) {
			otherNode = pageGraph.getPageNode(i);
			if (otherNode.isOutgoingLink(pageNode)) {
				linkedPrValue += otherNode.getPageRankOld()
						/ (double) otherNode.getNumberOutgoingLinks();
			}
		}
		// System.out.println("linkedPagerankValue: " + linkedPagerankValue);
		return linkedPrValue;
	}

	private double getNonLinkedPagesPagerankValue(PageNode pageNode) {
		double noLinkedPrValue = 0;
		int size = pageGraph.size();
		PageNode otherNode;
		for (int i = 0; i < size; i++) {
			otherNode = pageGraph.getPageNode(i);
			if (otherNode.hasOutgoingLinks()) {
				noLinkedPrValue += otherNode.getPageRankOld()
						/ (double) size;
			}
		}
		// System.out.println("nonLinkedPagerankValue: " +
		// nonLinkedPagerankValue);
		return noLinkedPrValue;
	}

	private void printPageRankSum() {
		double sumPagerank = 0;
		int size = pageGraph.size();
		PageNode pageNode;
		for (int i = 0; i < size; i++) {
			pageNode = pageGraph.getPageNode(i);
			sumPagerank += pageNode.getPageRankNew();
		}
		System.out.println("Summe Pagerank:" + sumPagerank);
	}
}
