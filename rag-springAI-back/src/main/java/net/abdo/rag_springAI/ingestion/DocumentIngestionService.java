package net.abdo.rag_springAI.ingestion;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentIngestionService implements CommandLineRunner {

    private final VectorStore vectorStore;

    @Value("classpath:/pdf/Cooking-Basics.pdf")
    private Resource resource;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting to read pdf then split it to chunks");
        // Read PDF
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        // Spliting into chunks
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> documentList = textSplitter.split(tikaDocumentReader.read()); // tikaDocumentReader.read() => return list of document
        System.out.println("📄 Total Chunks: " + documentList.size());
        // Store data to vectorStore
        vectorStore.add(documentList);
    }
}
