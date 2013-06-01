package Datenanalyse.Uebung1.Index;

public class PageEntity {
	
	private String name;
	private float relevance;
	
	public PageEntity(String name, float relevance) {
		this.name = name;
		this.relevance = relevance;
	}
	
	public String getName() {
		return name;
	}
	
	public float getRelevance() {
		return relevance;
	}
}
