package challenge.backend.forohub.api.forohub_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.backend.forohub.api.forohub_backend.domain.services.TopicService;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataNewTopic;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private TopicService topicService;
    
    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity newTopic(@RequestBody @Valid DataNewTopic dataNewTopic){
        var responseNewTopic = topicService.addNewTopic(dataNewTopic);

        return ResponseEntity.ok(responseNewTopic);
    }
}
