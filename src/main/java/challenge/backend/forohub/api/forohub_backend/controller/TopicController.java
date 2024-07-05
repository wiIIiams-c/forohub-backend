package challenge.backend.forohub.api.forohub_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.backend.forohub.api.forohub_backend.domain.services.TopicService;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataSavedTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataTopic;
import challenge.backend.forohub.api.forohub_backend.domain.topic.DataTopicList;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin
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
    public ResponseEntity topicNew(@RequestBody @Valid DataTopic dataNewTopic){
        var responseNewTopic = topicService.addNewTopic(dataNewTopic);

        return ResponseEntity.ok(responseNewTopic);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity topicUpdate(@PathVariable Long id, @RequestBody @Valid DataTopic dataUpdateTopic){
        var updatedTopic = topicService.updateTopic(id, dataUpdateTopic);

        return ResponseEntity.ok(new DataSavedTopic(updatedTopic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity topicDelete(@PathVariable @Valid Long id){
        var deletedTopic = topicService.deleteTopic(id);

        return ResponseEntity.ok(deletedTopic);
    }

    @GetMapping
    public ResponseEntity<Page<DataTopicList>> topicAll(@PageableDefault(size = 10, sort = "creationDate", direction = Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(topicService.listTopic(pageable));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity tipicDetail(@PathVariable @Valid Long id){
        return ResponseEntity.ok(topicService.detailTopic(id));
    }

    @PutMapping("/close/{id}")
    @Transactional
    public ResponseEntity topicClose(@PathVariable @Valid Long id){
        var closedTopic = topicService.closeTopic(id);

        return ResponseEntity.ok(closedTopic);
    }
}
