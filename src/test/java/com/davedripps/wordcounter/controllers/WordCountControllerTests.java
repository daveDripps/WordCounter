package com.davedripps.wordcounter.controllers;

import com.davedripps.wordcounter.pojo.Sentence;
import com.davedripps.wordcounter.pojo.WordCount;
import com.davedripps.wordcounter.service.WordCounterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WordCountControllerTests {
    @Mock
    private WordCounterService wordCounterService;

    @InjectMocks
    private WordCountController wordCountController;

    @Test
    void insertWords_whenMethodIsCalled_thenTheCorrectServiceMethodIsCalled(){
        Sentence testSentence = new Sentence("hello world");

        when(wordCounterService.addWords(any(Sentence.class))).thenReturn(testSentence);

        wordCountController.insertWords(testSentence);
        verify(wordCounterService, times(1)).addWords(testSentence);
    }

    @Test
    void wordCount_whenMethodIsCalled_thenTheCorrectServiceMethodIsCalled(){
        WordCount testWordCount = new WordCount("hello", 1);

        when(wordCounterService.getWordCount(anyString())).thenReturn(testWordCount);

        wordCountController.wordCount("hello");
        verify(wordCounterService, times(1)).getWordCount("hello");
    }

}
