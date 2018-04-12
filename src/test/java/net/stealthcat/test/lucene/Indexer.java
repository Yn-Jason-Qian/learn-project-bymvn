package net.stealthcat.test.lucene;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogDocMergePolicy;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockFactory;
import org.apache.lucene.store.SimpleFSLockFactory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by qianyang on 18-1-11.
 */
public class Indexer {
    private IndexWriter indexWriter;

    public Indexer(String path){
        try {
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, new SimpleAnalyzer());
            LogDocMergePolicy mergePolicy = new LogDocMergePolicy();
            mergePolicy.setUseCompoundFile(true);
            config.setMergePolicy(mergePolicy);
            FSDirectory directory = FSDirectory.open(new File(path));
            directory.setLockFactory(new SimpleFSLockFactory());
            if (IndexWriter.isLocked(directory)) {
                System.out.println(String.format("Path %s has been locked.", path));
                IndexWriter.unlock(directory);
            }
            indexWriter = new IndexWriter(directory, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startDebug() {
        try {
            indexWriter.setInfoStream(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Document document) {
        try {
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Collection<Document> documents) {
        try {
            indexWriter.addDocuments(documents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAndCommit(Document document) {
        try {
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            commit();
        }
    }

    public void writeAndCommit(Collection<Document> documents) {
        try {
            indexWriter.addDocuments(documents);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            commit();
        }
    }

    public void commit() {
        try {
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            indexWriter = null;
        }
    }

    private static Random random = new Random();
    public static Document doc() {
        Document document = new Document();
        document.add(new Field("id", Integer.toString(random.nextInt()), Field.Store.YES, Field.Index.ANALYZED));
        return document;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        Indexer indexer = new Indexer(path);
//        System.out.println(indexer.indexWriter.numDocs());

        addDocuments();
    }

    private static String path = "/home/qianyang/temp/lucene";
    public static void addDocuments() {
        for (int i = 0; i < 30; i++) {
            String path = "/home/qianyang/temp/lucene";
            Indexer indexer = new Indexer(path);
            indexer.startDebug();
            List<Document> docs = Lists.newArrayList();
            for (int j = 0; j < 100; j++) {
                docs.add(doc());
            }
            indexer.write(docs);
            indexer.close();
        }
    }

}
