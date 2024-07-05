package challenge.backend.forohub.api.forohub_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataAnswerUpdate;
import challenge.backend.forohub.api.forohub_backend.domain.answer.DataSavedAnswer;
import challenge.backend.forohub.api.forohub_backend.domain.services.AnswerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin
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

    @PutMapping("/solution/{id}")
    @Transactional
    public ResponseEntity answerSolution(@PathVariable @Valid Long id){
        var answer = answerService.solutionAnswer(id);

        return ResponseEntity.ok(answer);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity answerDelete(@PathVariable @Valid Long id){
        var answer = answerService.deleteAnswer(id);

        return ResponseEntity.ok(answer);
    }

    @PutMapping
    @Transactional
    public ResponseEntity answerUpdate(@RequestBody @Valid DataAnswerUpdate dataAnswerUpdate){
        var answer = answerService.updateAnswer(dataAnswerUpdate);

        return ResponseEntity.ok(new DataSavedAnswer(answer));
    }
}
