package challenge.backend.forohub.api.forohub_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.services.AnswerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/answers")
public class AnswerController {
    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity answerNew(@RequestBody @Valid DataAnswer dataAnswer){
        var answer = answerService.addNewAnswer(dataAnswer);

        return ResponseEntity.ok(answer);
    }
}
