package com.davedripps.wordcounter.service;

import com.davedripps.wordcounter.dao.WordCounterDao;
import com.davedripps.wordcounter.pojo.Sentence;
import com.davedripps.wordcounter.pojo.WordCount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WordCounterService {

    @Autowired
    WordCounterDao wordCounterDao;

    public Sentence addWords (Sentence sentence) {
        List<String> wordList = Arrays.asList(sentence.sentence().split("\\s+"));

        for (String word : wordList){
            if (StringUtils.isAlphanumeric(word)){
                wordCounterDao.addWords(word);
            }
        }

        return sentence;
    }

    public WordCount getWordCount(String word){
        return wordCounterDao.getWordCount(word);
    }
}
