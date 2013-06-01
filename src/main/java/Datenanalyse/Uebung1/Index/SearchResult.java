package Datenanalyse.Uebung1.Index;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
	
	private String search;
	private int results;
	private List<PageEntity> pages;
	
	public SearchResult(String search, int results) {
		this.search = search;
		this.results = results;
		this.pages = new ArrayList<PageEntity>(results);
	}
	
	public String getSearch() {
		return search;
	}
	
	public int getResults() {
		return results;
	}
	
	public List<PageEntity> getPages() {
		return pages;
	}
	
	public void addPage(PageEntity page) {
		this.pages.add(page);
	}
}