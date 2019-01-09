package com.welian.utils;

import org.sean.framework.text.WordFilter;
import org.sean.framework.util.StringUtil;

public class CharUtil {

    public static String wordToXX(String content) {
        int size = 0;
        WordFilter.FilterResult result;
        do {
            size++;
            result = WordFilter.filterWords(content);
            if (!StringUtil.isEmpty(result.blackWords)) {
                String[] strings = result.blackWords.split(",");
                //如果有敏感词，将敏感词替换成XXX...
                for (String s : strings) {
                    if (content.contains(s)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0, count = s.length(); i < count; i++) {
                            stringBuilder.append("X");
                        }
                        content = content.replace(s, stringBuilder.toString());
                    }
                }
            }
        } while (result.hasBlackWord);
        System.out.println(content);
        return content;

    }
}
