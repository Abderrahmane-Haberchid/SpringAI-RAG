package net.abdo.rag_springAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatController {

    private final OllamaChatModel ollamaChatModel;
    private final VectorStore vectorStore;

    public ChatController(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
        this.ollamaChatModel = ollamaChatModel;
        this.vectorStore = vectorStore;
    }

    @PostMapping
    public String chat(@RequestBody String prompt){
         String rawResponse = ChatClient.builder(ollamaChatModel)
                                    .build()
                                    .prompt()
                                    .advisors(new QuestionAnswerAdvisor(vectorStore))
                                    .user(prompt)
                                    .call()
                                    .content();
        if (rawResponse == null)
            throw new IllegalStateException("No data available");

        return rawResponse.replaceAll("(?s)<think>.*?</think>", "").trim();
    }
}
