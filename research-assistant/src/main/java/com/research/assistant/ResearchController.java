package com.research.assistant;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins="*")    //annotation allows a Spring Boot application to accept requests from any origin (i.e., any domain).
//@AllArgsConstructor   //The @AllArgsConstructor annotation generates a constructor with parameters for all fields in the class.
public class ResearchController {
    private final ResearchService researchService;
    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }


    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest request){  //ResponseEntity<> is a class used to represent the entire HTTP response (status code, Headers, body et..)It provides fine-grained control over the HTTP response, making it a powerful tool for building RESTful APIs.
        String result = researchService.processContent(request);
        return ResponseEntity.ok(result);

    }


}


//@RequestBody annotation is used to bind the body of an HTTP request to a Java object. It is commonly used in RESTful APIs to handle data sent in the request body, typically in JSON or XML format. The @RequestBody annotation tells Spring to deserialize the incoming request body into a Java object.