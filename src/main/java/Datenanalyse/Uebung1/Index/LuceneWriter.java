package Datenanalyse.Uebung1.Index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneWriter {
	
	private Directory directory;
	private IndexWriterConfig indexWriterConfig;
	private IndexWriter indexWriter;
	private Analyzer analyzer;
	
	private String storage;
	
	public LuceneWriter(String storage) throws IOException {
		this.storage = storage;
		
		this.directory = FSDirectory.open(new File(storage));
		this.analyzer = new StandardAnalyzer(Version.LUCENE_43);
		this.indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		this.indexWriter = new IndexWriter(directory, indexWriterConfig);
	}
	
	public String getStorage() {
		return storage;
	}
	
	public void write(String pageName, String content) throws IOException {
		Document document = new Document();
		document.add(new StringField("name",  pageName, Field.Store.YES));
		document.add(new TextField("content", content, Field.Store.NO));
		indexWriter.addDocument(document);
	}
	
	public void close() throws IOException {
		this.indexWriter.close();
	}
}
