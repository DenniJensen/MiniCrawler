package Datenanalyse.Uebung1.Index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneSearch {
	
	private static final int MAX_SEARCH_RESULTS = 100;
	
	private Directory directory;
	private IndexReader indexReader;
	private Analyzer analyzer;
	private IndexSearcher indexSearcher;
	private QueryParser queryParser;
	
	private String storage;
	
	public LuceneSearch(String storage) throws IOException {
		this.storage = storage;
		
		this.directory = FSDirectory.open(new File(storage));
		this.indexReader = DirectoryReader.open(directory);
		this.analyzer = new StandardAnalyzer(Version.LUCENE_43);
		this.queryParser = new QueryParser(Version.LUCENE_43, "content", analyzer);
		this.indexSearcher = new IndexSearcher(indexReader);
	}
	
	public String getStorage() {
		return storage;
	}
	
	public SearchResult search(String value) throws IOException, ParseException {
		Query query = queryParser.parse(value);
		TopDocs topDocs = indexSearcher.search(query, MAX_SEARCH_RESULTS);
		
		SearchResult searchResult = new SearchResult(value, topDocs.totalHits);
			
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;		
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document document = indexReader.document(scoreDoc.doc);
			searchResult.addPage(new PageEntity(document.get("name"), scoreDoc.score));
		}
		return searchResult;
	}
	
	public void close() throws IOException {
		indexReader.close();
	}
}
