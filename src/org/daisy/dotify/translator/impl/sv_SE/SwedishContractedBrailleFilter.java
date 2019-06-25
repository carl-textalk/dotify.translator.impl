package org.daisy.dotify.translator.impl.sv_SE;

import org.daisy.dotify.common.text.StringFilter;

import java.io.IOException;
import java.net.URL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class SwedishContractedBrailleFilter implements StringFilter {

    private HashMap<String, String> contractedBrailleMap;
    private static final String CONTRACTED_BRAILLE_TABLE_PATH = "sv_SE-single-character-contracted-words.xml";

    public static final String CAPITAL_CHAR_MARKER = "\\u2820";
    public static final String SOFT_HYPHEN = "\\u2820";

    /**
     * Todo: add support for different grades of contraction.
     *      For now only single character contracted words is supported
     */
    public SwedishContractedBrailleFilter() {
        this.contractedBrailleMap = new HashMap<>();
        this.loadTable();
    }

    /**
     * Search and replace with contracted braille.
     * First split the string on space character and go through all words and look for a match in the contractin table.
     *
     * @param str - The string that should be filterd
     * @return the filtered string
     */
    @Override
    public String filter(String str) {

        String[] words = str.split("\\s");
        // Handle Edge case with no words.
        if (words.length == 0) {
            return str;
        }
        // Strip string from
        Pattern pattern = Pattern.compile(CAPITAL_CHAR_MARKER + "*([\\p{javaUpperCase}\\p{javaLowerCase}" + SOFT_HYPHEN + "]+)");
        StringBuilder sb = new StringBuilder();
        String key, replace;

        for (String word: words) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                key = matcher.group(1).toLowerCase();
                if (this.contractedBrailleMap.containsKey(key)) {
                    replace = this.contractedBrailleMap.get(key);
                    word = word.substring(0, matcher.start(1)) + replace + word.substring(matcher.end(1));
                }
            }
            sb.append(word);
            sb.append(" ");
        }
        str = sb.toString().trim();
        return str;
    }

    /**
     * Loads a table using the Properties class.
     */
    private void loadTable() {
        URL tableURL = this.getClass().getResource(CONTRACTED_BRAILLE_TABLE_PATH);
        Properties props = new Properties();
        try {
            props.loadFromXML(tableURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Set<?> keys = props.keySet();
        for (Iterator<?> it = keys.iterator(); it.hasNext(); ) {
            String key = (String) it.next();
            contractedBrailleMap.put(key, props.getProperty(key));
        }
    }
}