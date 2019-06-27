package org.daisy.dotify.translator.impl.sv_SE;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.net.URL;

public class SwedishContractedBrailleFilterTest {

    private final SwedishContractedBrailleFilter filter;

    public SwedishContractedBrailleFilterTest() {

        filter = new SwedishContractedBrailleFilter();
    }

    @Test
    public void testFilter() {
        String text = "jag gillar att dansa men inte att sjunga";
        String filteredString = this.filter.filter(text);
        assertEquals("j gillar a dansa mê ü a sjunga", filteredString);
    }

    @Test
    public void testFilterWithFirstCharacterCapitalMarker() {
        String text = "⠠Test ⠠Testson ⠠Ett livs berättelse om saker som berör många";
        String filteredString = this.filter.filter(text);
        assertEquals("⠠Test ⠠Testson ⠠§ livs berättelse om saker s berör mg", filteredString);
    }

    @Test
    public void testFilterWithEmptyString() {
        String text = "";
        String filteredString = this.filter.filter(text);
        assertEquals("", filteredString);
    }

    @Test
    public void testFilterWithOnlySpace() {
        String text = " ";
        String filteredString = this.filter.filter(text);
        assertEquals(" ", filteredString);

        // Test multiple spaces
        text = "   ";
        filteredString = this.filter.filter(text);
        assertEquals("   ", filteredString);
    }

    @Test
    public void testFilterWithWordCapitalMarker() {
        String test = "⠠⠠JAG börjar att sjunga";
        String filteredString = this.filter.filter(test);
        assertEquals("⠠⠠j bjr a sjunga", filteredString);
    }

    @Test
    public void testFilterWithDoubleBackSlashCharacter() {
        String test = "Kan du ge mig den där";
        String filteredString = this.filter.filter(test);
        assertEquals("k du ge mig \\\\ d", filteredString);
    }

    @Test
    public void testFilterWhenSequentialCapitalWordsExists() {
        String test = "⠠⠠⠠JAG SAKNAR HONOM⠱";
        String filteredString = this.filter.filter(test);
        assertEquals("⠠⠠⠠j SAKNAR oo⠱", filteredString);
    }

    @Test
    public void testFilterWhenStringContainsSoftHyphen() {
        String test = "den här texten inne\u00ADhåller ord med och ut\u00ADan soft hyphen";
        String filteredString = this.filter.filter(test);
        System.out.println(filteredString);
        assertEquals("\\\\ här texten inne\u00ADhåller ord î c u soft hyphen", filteredString);
    }
}
