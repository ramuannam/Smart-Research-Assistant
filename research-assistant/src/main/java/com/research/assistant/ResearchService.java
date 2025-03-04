package com.research.assistant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ResearchService {

    //@value inject these respective values from the application.properties
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper; // used to map json to java object (injected by jackson libraray)

    public ResearchService(WebClient.Builder webClientBuilder,ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;

    }


    public String processContent(ResearchRequest request) {

        //Step:1 Build the prompt
        String prompt=buildPrompt(request);

        //Step:2 Query the AI Model API
        //Here make sure in what format the request is being accepted so in Gemini API there is content which is an k:v pair with key as string and value as array, and in that array there is again k:v, parts as key and array as value.so in that array we have text:""
       Map<String, Object> requestBody= Map.of(
               "contents", new Object[]{
                       Map.of("parts", new Object[]{
                               Map.of("text", prompt),
                       })
               }
       );

       //using webclient we are making a post() request to gemini api with request body and in turn it retireves the response and processes it and finally backend can send a response back to the frontend
       String response = webClient.post()
               .uri(geminiApiUrl + geminiApiKey)
               .bodyValue(requestBody)
               .retrieve()
               .bodyToMono(String.class)
               .block();



        //Step:3 Parse the Response
        //Step:4 Return the Response

        return extractTextFromResponse(response);


    }

    private String extractTextFromResponse(String response) {
        try{
            GeminiResponse geminiResponse= objectMapper.readValue(response, GeminiResponse.class);  //mapping the response(JSON) to geminiResponse object.

            if(geminiResponse.getCandidates()!=null && !geminiResponse.getCandidates().isEmpty()){
                GeminiResponse.Candidate firstCandidate=geminiResponse.getCandidates().get(0);
                if(firstCandidate.getContent()!=null && firstCandidate.getContent().getParts()!=null &&
                        !firstCandidate.getContent().getParts().isEmpty()) {
                    return firstCandidate.getContent().getParts().get(0).getText();

                }
            }
            return "No content found in response";

        }catch(Exception e){
            return "Error Parsing:" + e.getMessage();
        }
    }

    private String buildPrompt(ResearchRequest request) {
         StringBuilder prompt= new StringBuilder();

         //so we have diff options to prompt
        switch(request.getOperation()){
            case "summarize":
                prompt.append("Provide a clear and concise summary of the following text in a few sentences, the text is =:\n\n");
                break;
            case "suggest":
                prompt.append("Based on the following content:suggest related topics and further reading.Format the response with clear heading and bullets points. :\n\n");
                break;
            case "AI CTO":
                prompt.append("You are a professional CTO who is very friendly and supportive.\n" +
                        "Your task is to help a developer understand and plan their app idea through a series of questions. Follow these instructions:\n" +
                        "1. Begin by explaining to the developer that you'll be asking them a series of questions to understand their app idea at a high level, and that once you have a clear picture, you'll generate a comprehensive masterplan.md file as a blueprint for their application.\n" +
                        "2. Ask questions one at a time in a conversational manner. Use the developer's previous answers to inform your next questions.\n" +
                        "3. Your primary goal (70% of your focus) is to fully understand what the user is trying to build at a conceptual level. The remaining 30% is dedicated to educating the user about available options and their associated pros and cons.\n" +
                        "4. When discussing technical aspects (e.g., choosing a database or framework), offer high-level alternatives with pros and cons for each approach. Always provide your best suggestion along with a brief explanation of why you recommend it, but keep the discussion conceptual rather than technical.\n" +
                        "5. Be proactive in your questioning. If the user's idea seems to require certain technologies or services (e.g., image storage, real-time updates), ask about these even if the user hasn't mentioned them.\n" +
                        "6. Try to understand the 'why' behind what the user is building. This will help you offer better advice and suggestions.\n" +
                        "7. Ask if the user has any diagrams or wireframes of the app they would like to share or describe to help you better understand their vision.\n" +
                        "8. Remember that developers may provide unorganized thoughts as they brainstorm. Help them crystallize the goal of their app and their requirements through your questions and summaries.\n" +
                        "9. Cover key aspects of app development in your questions, including but not limited to:\n" +
                        "   • Core features and functionality\n" +
                        "   • Target audience\n" +
                        "   • Platform (web, mobile, desktop)\n" +
                        "   • User interface and experience concepts\n" +
                        "   • Data storage and management needs\n" +
                        "   • User authentication and security requirements\n" +
                        "   • Potential third-party integrations\n" +
                        "   •Scalability considerations\n" +
                        "   • Potential technical challenges\n" +
                        "10. After you feel you have a comprehensive understanding of the app idea, inform the user that you'll be generating a masterplan.md file.\n" +
                        "11. Generate the masterplan.md file. This should be a high-level blueprint of the app, including:\n" +
                        "    • App overview and objectives\n" +
                        "    • Target audience\n" +
                        "    • Core features and functionality\n" +
                        "    • High-level technical stack recommendations (without specific code or implementation details)\n" +
                        "    • Conceptual data model\n" +
                        "    • User interface design principles\n" +
                        "    • Security considerations\n" +
                        "    • Development phases or milestones\n" +
                        "    • Potential challenges and solutions\n" +
                        "    • Future expansion possibilities\n" +
                        "\n" +
                        "\n" +
                        "12. Present the masterplan.md to the user and ask for their feedback. Be open to making adjustments based on their input.\n" +
                        "\n" +
                        "Important: Do not generate any code during this conversation. The goal is to understand and plan the app at a high level, focusing on concepts and architecture rather than implementation details.\n" +
                        "\n" +
                        "Remember to maintain a friendly, supportive tone throughout the conversation. Speak plainly and clearly, avoiding unnecessary technical jargon unless the developer seems comfortable with it. Your goal is to help the developer refine and solidify their app idea while providing valuable insights and recommendations at a conceptual level.\n" +
                        "\n" +
                        "Begin the conversation by introducing yourself and asking the developer to describe their app idea.");
                break;

            default:
                throw new IllegalArgumentException("Unknown operation: " + request.getOperation());

        }

        prompt.append(request.getContent());  //finally appending the content that user asks to any one of the cases above.
        return prompt.toString();


    }
}


