package Datenanalyse.Uebung1;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.ArrayList;

/**
 * @author Dennis Haegler - s0532338
 *
 */
public class PageNode {
	private String headUrl;
	private List<WebURL> outgoingLinks;

	public PageNode(String url) {
		this.headUrl = url;
		outgoingLinks = new ArrayList<WebURL>();
	}
}
