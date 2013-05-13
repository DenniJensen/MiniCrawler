package Datenanalyse.Uebung1;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Dennis Haegler - s0532338
 * 
 */
public class PageNode {
	private Page headPage;
	private List<WebURL> links;

	public PageNode(Page page, List<WebURL> links) {
		this.headPage = page;
		this.links = links;
	}

	public int getCountOfLinks() {
		return links.size();
	}

	public String getURL() {
		return headPage.getWebURL().getURL();
	}

	public boolean isLinkedWithPage(Page page) {
		String nodeUrl = page.getWebURL().getURL();
		String compareUrl = "";
		for (WebURL link : this.links) {
			compareUrl = link.getURL();
			if (nodeUrl.equals(compareUrl)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLinkedWithPageNode(PageNode pageNode) {
		return false; //TODO links with Object
	}

	public boolean hasOutgoingLinks() {
		boolean hasLinks = false;
		if (this.links.size() > 0) {
			hasLinks = true;
		}
		return hasLinks;
	}

	/*public String toString() {
		String resultString = headPage + ":";
		if (hasOutgoingLinks()) {
			for (String link : links) {
				resultString += "," + link;
			}
		}
		return resultString;
	}*/
}
