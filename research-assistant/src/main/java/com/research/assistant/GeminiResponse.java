package com.research.assistant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)  //so when gemini response gives other than these class properties it jus ignores them rather giving an errors.
public class GeminiResponse {  //class representing nested JSON response from the gemini api
    //make sure to add the same name property names that matches with the response from the Gemini API.
    private List<Candidate> candidates;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {
        private Content content;

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }




    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private List<Part> parts;

        public List<Part> getParts() {  // ✅ Explicit getter
            return parts;
        }

        public void setParts(List<Part> parts) {  // ✅ Explicit setter
            this.parts = parts;
        }
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Part {
        private String text;

        public String getText() {  // ✅ Explicit getter
            return text;
        }

        public void setText(String text) {  // ✅ Explicit setter
            this.text = text;
        }
    }

}
