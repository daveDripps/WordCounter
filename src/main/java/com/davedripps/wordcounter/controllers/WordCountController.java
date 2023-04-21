package com.davedripps.wordcounter.controllers;

import com.davedripps.wordcounter.pojo.Sentence;
import com.davedripps.wordcounter.pojo.WordCount;
import com.davedripps.wordcounter.service.WordCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordCount")
public class WordCountController {

    private WordCounterService wordCounterService;

    @Autowired
    public WordCountController(WordCounterService wordCounterService){
        this.wordCounterService = wordCounterService;
    }

    @PostMapping("/")
    public ResponseEntity<Sentence> insertWords(@RequestBody  Sentence sentence){
        wordCounterService.addWords(sentence);
        return ResponseEntity.ok(sentence);
    }

    @GetMapping("/{word}")
    ResponseEntity<WordCount> wordCount(@PathVariable("word") String word){
        return ResponseEntity.ok(wordCounterService.getWordCount(word));
    }
}
