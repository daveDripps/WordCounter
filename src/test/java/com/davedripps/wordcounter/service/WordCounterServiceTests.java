package com.davedripps.wordcounter.service;

import com.davedripps.wordcounter.dao.WordCounterDao;
import com.davedripps.wordcounter.pojo.Sentence;
import com.davedripps.wordcounter.pojo.WordCount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WordCounterServiceTests {
    @Mock
    private WordCounterDao wordCounterDao;

    @Mock
    private TranslateService translateService;

    @InjectMocks
    private WordCounterService wordCounterService;

    private final static String TEST_WORDS = "the quick brown fox jumped over the lazy dog";

    @Test
    void addWords_isPassedASentence_andReturnsTheSentence(){
        doNothing().when(wordCounterDao).addWords(anyString());

        Sentence result = wordCounterService.addWords(new Sentence("the quick brown fox jumped over the lazy dog"));
        assertThat(result.sentence()).isEqualTo(TEST_WORDS);
    }

    @Test
    void addWords_isPassedASentence_theDaoLayerIsCalledNineTimes(){
        doNothing().when(wordCounterDao).addWords(anyString());

        Sentence result = wordCounterService.addWords(new Sentence("the quick brown fox jumped over the lazy dog"));
        verify(wordCounterDao, times(9)).addWords(anyString());
    }

    @Test
    void getWordCount_isPassedAString_theDaoGetWordCountIsCalled(){
        when(wordCounterDao.getWordCount(anyString())).thenReturn(new WordCount("the", 2));

        assertThat(wordCounterService.getWordCount("the").count()).isEqualTo(2);
        verify(wordCounterDao, times(1)).getWordCount(anyString());
    }
}
