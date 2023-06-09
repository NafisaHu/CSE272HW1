/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.luence.app;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.spans.SpanMultiTermQueryWrapper;
import org.apache.lucene.queries.spans.SpanQuery;
import org.apache.lucene.queries.spans.SpanTermQuery;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.search.similarities.BooleanSimilarity;


import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * refer to <a href="https://www.lucenetutorial.com/lucene-in-5-minutes.html"/>
 * A more comprehensive implementation, please check <a href="http://www.java2s.com/example/java-api/org/apache/lucene/search/indexsearcher/setsimilarity-1-1.html"/>
 */
public class App {

    private static CustomTFIDFSimilarity similarity = new CustomTFIDFSimilarity();

    // used to be title, medline_ui, desc
    // not is id, title, desc
    private static void addDoc(IndexWriter w,String medline_uis, String title, String descr) throws IOException {
        Document doc = new Document();
        doc.add(new StringField("medline_uis", medline_uis, Field.Store.YES));
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new TextField("description", descr, Field.Store.YES));
        w.addDocument(doc);
    }

    public static String removeStopWords(String line){
        String x = line;
        x = " " + x + " ";

        x = x.replaceAll(" with ", " ");
        x = x.replaceAll(" and ", " ");
        x = x.replaceAll(" the ", " ");
        x = x.replaceAll(" of ", " ");
        x = x.replaceAll(" for ", " ");
        x = x.replaceAll(" old ", " ");
        x = x.replaceAll(" a ", " ");
        x = x.replaceAll(" an ", " ");
        x = x.replaceAll(" is ", " ");
        x = x.replaceAll(" review ", " ");
        x = x.replaceAll(" which ", " ");
        x = x.replaceAll(" are ", " ");
        x = x.replaceAll(" top ", " ");
        x = x.replaceAll(" <title> ", " ");
        x = x.replaceAll(" <desc> ", " ");
        x = x.replaceAll(" desc ", " ");
        x = x.replaceAll(" TREATMENT ", " ");
        x = x.replaceAll(" DIAGNOSIS ", " ");
        x = x.replaceAll(" when", " ");
        x = x.replaceAll(" without ", " ");
        x = x.replaceAll(" effects ", " ");
        x = x.replaceAll(" yo ", " ");
        x = x.replaceAll(" year ", " ");
        x = x.replaceAll(" W ", " ");
        x = x.replaceAll(" yo ", " ");
        x = x.replaceAll(" on ", " ");
        x = x.replaceAll(" use ", " ");
        x = x.replaceAll(" in ", " ");
        x = x.replaceAll(" \\d+ ", " ");
        x = x.replaceAll(" male ", " ");
        x = x.replaceAll(" female ", " ");
        x = x.replaceAll(" woman ", " ");
        x = x.replaceAll(" old ", " ");
        x = x.replaceAll(" review ", " ");
        x = x.replaceAll(" article ", " ");
        x = x.replaceAll(" f ", " ");
        x = x.replaceAll(" adult ", " ");
        x = x.replaceAll(" out ", " ");
        x = x.replaceAll(" Are", " ");
        x = x.replaceAll(" there ", " ");
        x = x.replaceAll(",", " ");
        x = x.replaceAll(";", " ");
        x = x.replaceAll("/", " ");
        x = x.replaceAll(":", " ");
        x = x.replaceAll("-", " ");
        x = x.replaceAll("\\?", " ");
        x = x.replaceAll("\\(", " ");
        x = x.replaceAll("\\)", " ");
        x = x.replaceAll("\\*", " ");
        x = x.replaceAll("\\.", " ");


        x = x.replaceAll(" does ", "");
        x = x.replaceAll(" months ", "");
        x = x.replaceAll(" to ", "");
        x = x.replaceAll(" AND ", "");
        x = x.replaceAll(" chronic ", "");
        x = x.replaceAll(" lower ", "");
        x = x.replaceAll(" pt ", "");
        x = x.replaceAll(" fe ", "");
        x = x.replaceAll(" heat ", "");
        x = x.replaceAll(" s/p ", "");
        x = x.replaceAll(" AS ", "");
        x = x.replaceAll(" possible ", "");
        x = x.replaceAll(" wom ", "");
        x = x.replaceAll(" asi ", "");
        x = x.replaceAll(" asian ", "");
        x = x.replaceAll(" treated ", "");
        x = x.replaceAll(" to ", "");
        x = x.replaceAll(" pain ", "");
        x = x.replaceAll(" test ", "");
        x = x.replaceAll(" best ", "");
        x = x.replaceAll(" illness ", "");
        x = x.replaceAll(" while ", "");
        x = x.replaceAll(" whether ", "");
        x = x.replaceAll(" recommened ", "");
        x = x.replaceAll(" breakthrough ", "");
        x = x.replaceAll(" never ", "");
        x = x.replaceAll(" treated ", "");
        x = x.replaceAll(" advanced ", "");

        return x;
    }

    static Directory index(StandardAnalyzer analyzer)  throws IOException {

        //Directory index = new NIOFSDirectory(Paths.get("/Users/nafisahussain/Desktop/CSE272"));
        Directory index = new NIOFSDirectory(Paths.get("<your file index location>"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setSimilarity(similarity);
        try (IndexWriter w = new IndexWriter(index, config)) {
            try {
                File myObj = new File ("/Users/nafisahussain/Desktop/CSE272/ohsumed.88-91");
                Scanner myReader = new Scanner(myObj);

                String documentInfo[] = new String[3];
                boolean documentIDFound = false;
                boolean documentTitleFound = false;
                boolean documentDescriptionFound = false;
                boolean currentDocumentWork = false;

                while (myReader.hasNextLine()){
                    String data = myReader.nextLine();
                    if (documentIDFound){
                        if (currentDocumentWork){
                            documentInfo[2] = "";
                            // id, title, description
                            addDoc(w, documentInfo[0], documentInfo[1], documentInfo[2]);
                        }
                        documentInfo[0] = data;
                        documentIDFound = false;
                        currentDocumentWork = true;
                    }
                    if(documentTitleFound){
                        documentInfo[1] = data;
                        documentTitleFound = false;
                    }
                    if (documentDescriptionFound){
                        documentInfo[2] = data;
                        documentDescriptionFound = false;
                        currentDocumentWork = false;
                        addDoc(w, documentInfo[0], documentInfo[1], documentInfo[2]);
                    }
                    if (data.contains(".U")){
                        documentIDFound = true;
                    }
                    if (data.contains(".T")){
                        documentTitleFound = true;
                    }
                    if (data.contains(".W")){
                        documentDescriptionFound = true;
                    }
                }
                myReader.close();
            } catch (FileNotFoundException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            return index;
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        String filePathQ = "/Users/nafisahussain/Desktop/CSE272/query.ohsu.1-63";

        File f = new File("log1.txt");
        if (f.createNewFile()){
            System.out.println("File created "+ f.getName());
        } else {
            System.out.println("File exists already");
        }
        f = new File("log2.txt");
        if (f.createNewFile()){
            System.out.println("File created "+ f.getName());
        } else {
            System.out.println("File exists already");
        }
        f = new File("log3.txt");
        if (f.createNewFile()){
            System.out.println("File created "+ f.getName());
        } else {
            System.out.println("File exists already");
        }
        f = new File("log4.txt");
        if (f.createNewFile()){
            System.out.println("File created "+ f.getName());
        } else {
            System.out.println("File exists already");
        }
        f = new File("log5.txt");
        if (f.createNewFile()){
            System.out.println("File created "+ f.getName());
        } else {
            System.out.println("File exists already");
        }

        FileWriter file1 = new FileWriter("log1.txt");
        FileWriter file2 = new FileWriter("log2.txt");
        FileWriter file3 = new FileWriter("log3.txt");
        FileWriter file4 = new FileWriter("log4.txt");
        FileWriter file5 = new FileWriter("log5.txt");

        String searchString = "<title>";
        List<String> queries = new ArrayList<>();
        String num = "";
        List<String> ohsuNum = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePathQ))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+"); // Split line by whitespace characters
                if (line.startsWith("<num>")){
                    num = line.replaceAll("<num> Number: ", "");
                    ohsuNum.add(num);
                }
                if (line.startsWith("<title>")) {
                    int startIndex = line.indexOf(searchString);
                    if (startIndex != -1) {
                        String inter = removeStopWords(line); //title line

                        String title = inter; //title line

                        // read in the number
                        String nextLine1 = reader.readLine(); //number

                        // add tildas to the description section
                        String desc = reader.readLine(); // description
                        desc = removeStopWords(desc);
                        String desctag = " <desc>"; // description tag
                        String top = reader.readLine(); //top tag
                        if ( desc != null) {
                            queries.add(title + " " + desc);
                        }
                    }
                }

            }
        }catch(IOException e){
            e.printStackTrace();
        }

        for(String q : queries){
            System.out.println(q);
        }

        Directory index = index(analyzer);
        int hitsPerPage = 50;
        String rankType = "";
        int numQ = 0;

        for (String s: queries) {

            s = s.trim();
            s = s + "~";
            s = s.replaceAll(" ", "~ ");
            s = s.replaceAll(" ~", "");
            System.out.println(s);

            Query q = new QueryParser("title", analyzer).parse(QueryParser.escape(s));
            QueryParser q2 = new QueryParser("<default field>", analyzer);
            String newStr = "description:" + s + " OR title:" + s + " OR medline_uis:" + s;

            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);

            for (int iter = 0; iter < 5; iter++) {
                if (iter == 0) {
                    searcher.setSimilarity(new BM25Similarity());  // maybe change to just similairt in the ()
                    rankType = "BM25Similarity";
                }if (iter == 1) {
                    searcher.setSimilarity(new BooleanSimilarity());
                    rankType = "BooleanSimilarity";
                }if (iter == 2) {
                    searcher.setSimilarity(new ClassicSimilarity());
                    rankType = "TFIDFSimilarity";
                }if (iter == 3) {
                    searcher.setSimilarity(new TFIDFSimilarity() {
                        @Override
                        public float tf(float freq) {
                            // return 0;
                            return (float) Math.sqrt(freq);
                        }

                        @Override
                        public float idf(long docFreq, long docCount) {
                            return 1;
                        }

                        @Override
                        public float lengthNorm(int length) {
                            return (1 /((float) Math.sqrt(length)));
                        }
                    });
                    rankType = "TFSimilarity";
                } if (iter == 4){
                    searcher.setSimilarity(new TFIDFSimilarity() {
                        @Override
                        public float tf(float freq) {
                            return 1;
                        }

                        @Override
                        public float idf(long docFreq, long docCount) {
                            return (1/((float) Math.log(docCount/ docFreq)));
                        }

                        @Override
                        public float lengthNorm(int length) {
                            return (1/((float) Math.sqrt(length)));
                        }
                    });
                    rankType = "MySimilarity";
                }

                TopDocs docs = searcher.search(q2.parse(newStr), hitsPerPage);
                System.out.println("Total hits: " + docs.totalHits);
                ScoreDoc[] hits = docs.scoreDocs;


                System.out.println("Found " + hits.length + " hits.");

                for (int i = 0; i < hits.length; ++i) {
                    int docId = hits[i].doc;
                    Document d = searcher.getIndexReader().document(docId);
                    //System.out.println(ohsuNum.get(numQ) + " " + "Q0 " + d.get("medline_uis") + " " + (i + 1) + " " + hits[i].score + " " +  rankType + " " + "\t" + d.get("title"));
                    System.out.println(ohsuNum.get(numQ) + " " + "Q0 " + d.get("medline_uis") + " " + (i + 1) + " " + hits[i].score + " " +  rankType);
                    if (rankType.equals("BM25Similarity")){
                        file1.write(ohsuNum.get(numQ) + " Q0 " + d.get("medline_uis") + " " + (i+1) + " " + hits[i].score + " " + rankType + "\n");
                    }
                    if (rankType.equals("BooleanSimilarity")){
                        file2.write(ohsuNum.get(numQ) + " Q0 " + d.get("medline_uis") + " " + (i+1) + " " + hits[i].score + " " + rankType + "\n");
                    }
                    if (rankType.equals("TFIDFSimilarity")){
                        file3.write(ohsuNum.get(numQ) + " Q0 " + d.get("medline_uis") + " " + (i+1) + " " + hits[i].score + " " + rankType + "\n");
                    }
                    if (rankType.equals("TFSimilarity")){
                        file4.write(ohsuNum.get(numQ) + " Q0 " + d.get("medline_uis") + " " + (i+1) + " " + hits[i].score + " " + rankType + "\n");
                    }
                    if (rankType.equals("MySimilarity")){
                        file5.write(ohsuNum.get(numQ) + " Q0 " + d.get("medline_uis") + " " + (i+1) + " " + hits[i].score + " " + rankType + "\n");
                    }

                }

            }
            numQ += 1;
        }
        file1.close();
        file2.close();
        file3.close();
        file4.close();
        file5.close();


            /*
            for(String s: queries){
                s = s.trim();
                s = s + "~";
                s = s.replaceAll(" ", "~ ");
                s = s.replaceAll(" ~", "");
                System.out.println(s);

                Query q = new QueryParser("title", analyzer).parse(QueryParser.escape(s));
                QueryParser q2 = new QueryParser("<default field>", analyzer);
                String newStr = "description:" + s + " OR title:" + s + " OR medline_uis:" + s;

                //IndexReader reader = DirectoryReader.open(index);
                //IndexSearcher searcher = new IndexSearcher(reader);

                TopDocs docs = searcher.search(q2.parse(newStr), hitsPerPage);
                System.out.println("Total hits: " + docs.totalHits);
                ScoreDoc[] hits = docs.scoreDocs;


                System.out.println("Found " + hits.length + " hits.");

                for (int i = 0; i < hits.length; ++i) {
                    int docId = hits[i].doc;
                    Document d = searcher.getIndexReader().document(docId);
                    //System.out.println(ohsuNum.get(numQ) + " " + "Q0 " + d.get("medline_uis") + " " + (i + 1) + " " + hits[i].score + " " +  rankType + " " + "\t" + d.get("title"));
                }
            }
            numQ =+ 1;
            */

        }

    }

