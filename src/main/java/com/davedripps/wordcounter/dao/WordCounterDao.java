package com.davedripps.wordcounter.dao;

import com.davedripps.wordcounter.pojo.WordCount;
import com.davedripps.wordcounter.service.TranslateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WordCounterDao {

    TranslateService translateService;

    @Autowired
    public WordCounterDao(TranslateService translateService){
        this.translateService = translateService;
    }
    private Map<String, Integer> wordCount = new ConcurrentHashMap<>();

    public void addWords(String ... words){
        if (words.length > 0) {
            for (String word : words) {
                if (StringUtils.isAlphanumeric(word)) {
                    this.addWord(translateService.translate(word));
                }
            }
        }

    }

    private void addWord(String word){
        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
    }

    public WordCount getWordCount(String word){
        int count = wordCount.getOrDefault(word, 0);
        return new WordCount(word, count);
    }

}
