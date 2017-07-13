/**
 * 	 Diese Testklasse enthält die wichtigsten Testfälle, mit denen Sie überprüfen können,
 *   ob der von Ihnen generierte Parser die Morsenachrichten gemäß den Anforderungen korrekt
 *   parst.
 *   
 *   @author Annika Wagner
 */

package MorseParser;

import static org.junit.Assert.*;
// Hier muss der von Ihnen generierte Code importiert werden
import MorseParser.MorseParser;
import MorseParser.ParseException;
import MorseParser.TokenMgrError;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;

/*
 * Das Zeichen S im Wort SOS muss das erste S sein, dass in der Nachricht vorkommt, d.h. 
 * vor SOS darf es noch kein S gegeben haben
 */
public class MorseParserTest_erstes_S {
	
	private MorseParser parser;

	/**
	 * Methode zur Initialisierung des Parsers
	 * @param str
	 */
	private void setUp(String str){
		StringReader sr = new StringReader(str);
		Reader r = new BufferedReader(sr);
		try {
		parser.ReInit(r);
		} catch (NullPointerException e){
			parser = new MorseParser(r);
		}
	}
	
	
	/**
	 * Ab hier kommen die Morsenachrichten, die als syntaktisch korrekt erkannt werden sollen,
	 * weil sie vor dem Wort SOS keine weiteren S enthalten.
	 */
	
	@Test
	public void test_SOS_Als_Wort() {
	
		setUp("...#___#...##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}

	@Test
	public void test_SOS_am_Ende_ohne_weitere_S() {
	
		setUp("_#.#_..#._.##...#___#...##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}
	
	@Test
	public void test_SOS_am_Anfang_ohne_weitere_S() {
	
		setUp("...#___#...##..#_.#._##.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}
	
	@Test
	public void test_S_nach_SOS_innerhalb_Wort() {
	
		setUp("...#___#...##_._#_.##_.#...#.#__##_.#.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}

	@Test
	public void test_S_nach_SOS_am_Wortanfang() {
	
		setUp("...#___#...##_._#_.##_.##...#.#__##_.#.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}
	
	
	/**
	 * Ab hier kommen die Morsenachrichten, die nicht syntaktisch korrekt sind, d.h. 
	 * vor dem Wort SOS mindestens ein S bereits enthalten
	 */
	
	@Test(expected = ParseException.class)
	public void test_SOS_in_der_Mitte_mit_weiteren_S_am_Wortanfang() throws ParseException {
	
		setUp("...#..##...#___#...##__..#_.#._##.##;");
		
		parser.Input();
	
	}
	
	@Test(expected = ParseException.class)
	public void test_SOS_in_der_Mitte_mit_weiteren_S_in_der_Wortmitte() throws ParseException {
	
		setUp("__..#...#__##...#___#...##_._#..#_.#._##.##;");
		
		parser.Input();
	
	}
	
	@Test(expected = ParseException.class)
	public void test_SOS_am_Ende_mit_weiteren_S_in_der_Wortmitte() throws ParseException {
	
		setUp("..#_.#._##_.#...#_.##...#___#...##;");
		
		parser.Input();
			
	}
	
	@Test(expected = ParseException.class)
	public void test_SOS_am_Ende_mit_weiteren_S_am_Wortanfang() throws ParseException {
	
		setUp("...#_.#._##..##...#___#...##;");
		
		parser.Input();
			
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_komplett_anderes_Wort() {
		
		setUp("_..#_._#._.##;");
		
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
	}

}
