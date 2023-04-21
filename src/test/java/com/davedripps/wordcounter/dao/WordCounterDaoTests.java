package com.davedripps.wordcounter.dao;

import com.davedripps.wordcounter.service.TranslateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class WordCounterDaoTests {

    private WordCounterDao wordCounterDao;

    @BeforeEach
    void init(){
        wordCounterDao = new WordCounterDao(new TranslateService());
    }

    @Test
    void addWords_isPassedAWord_aCountOfOneIsReturned() {
        wordCounterDao.addWords("word");
        assertThat(wordCounterDao.getWordCount("word").count()).isEqualTo(1);

    }

    @Test
    void addWords_isPassedNoWords_zeroIsReturned() {
        wordCounterDao.addWords();
        assertThat(wordCounterDao.getWordCount("word").count()).isZero();
    }

    @Test
    void addWords_isPassedMultipleWords_allAreFound() {
        wordCounterDao.addWords("one", "two", "three");
        assertThat(wordCounterDao.getWordCount("one").count()).isEqualTo(1);
        assertThat(wordCounterDao.getWordCount("two").count()).isEqualTo(1);
        assertThat(wordCounterDao.getWordCount("three").count()).isEqualTo(1);
    }

    @Test
    void wordCount_isPassedWordThatIsPresentTwice_andReturnsTwo(){
        wordCounterDao.addWords("dupe", "dupe");
        assertThat(wordCounterDao.getWordCount("dupe").count()).isEqualTo(2);
    }

    @Test
    void wordCount_isPassedWordThatIsNotPresent_andReturnsZero(){
        assertThat(wordCounterDao.getWordCount("new").count()).isZero();
    }

    @Test
    void wordCount_isPassedANonAlphaNumbericWord_andReturnsZero(){
        wordCounterDao.addWords("c@t");
        assertThat(wordCounterDao.getWordCount("c@t").count()).isZero();
    }

    @Test
    void wordCount_isPassedAValidWord_theTranslateServiceIsCalled(){
        TranslateService translateServiceMock = Mockito.mock(TranslateService.class);
        wordCounterDao = new WordCounterDao(translateServiceMock);
        when(translateServiceMock.translate(anyString())).thenReturn("translatedWord");

        wordCounterDao.addWords("word");

        verify(translateServiceMock, times(1)).translate("word");
        assertThat(wordCounterDao.getWordCount("translatedWord").count()).isEqualTo(1);
    }
}
