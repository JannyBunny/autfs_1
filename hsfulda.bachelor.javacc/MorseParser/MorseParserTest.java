/**
 * 	 Diese Testklasse enthält die wichtigsten Testfälle, mit denen Sie überprüfen können,
 *   ob der von Ihnen generierte Parser die Morsenachrichten gemäß den Anforderungen korrekt
 *   parst.
 *
 *   Sie basiert auf JUnit 4.11
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



public class MorseParserTest {
	
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
	 * weil sie das Wort SOS enthalten.
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
	public void test_SOS_am_Ende() {
	
		setUp("_#.#_..#._.##...#___#...##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}

	@Test
	public void test_SOS_am_Anfang() {
	
		setUp("...#___#...##..#_.#._##.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}
	
	@Test
	public void test_SOS_in_der_Mitte() {
	
		setUp("__..#..##...#___#...##..#_.#._##.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			fail(p.getMessage());	
		}	
	}
	
	@Test
	public void test_S_stotternd() {
		
		setUp(".#..##...#___#...##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				fail(p.getMessage());	
			}
	}
	
	@Test
	public void test_beginnt_mit_Pause() {
		
		setUp("##...#___#...##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				fail(p.getMessage());	
			}
	}
	
	/**
	 * Ab hier kommen die Morsenachrichten, die nicht syntaktisch korrekt sind, d.h. nicht
	 * das Wort SOS enthalten.
	 */
	
	@Test(expected = TokenMgrError.class)
	public void test_komplett_anderes_Wort() {
		
		setUp("_..#_._#._.##;");
		
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_SOS_ohne_Wortende() {
		
		setUp("...#___#...#;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("SOS als Wort nicht beendet");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_SOS_als_Praefix_eines_Wortes() {
		
		setUp("...#___#...#._.#.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("SOS kein eigenes Wort, sondern Teil eines Wortes");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_SOS_als_Suffix_eines_Wortes() {
		
		setUp(".._#.#...#___#...##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("SOS kein eigenes Wort, sondern Teil eines Wortes");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_fehlende_Pause() {
		
		setUp("...___...;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("Die Zeichen nicht durch # getrennt");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_S_kommt_doppelt_vor() {
		
		setUp("...#...#___#...##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("SOS nicht als eigenes Wort");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_O_kommt_doppelt_vor() {
		
		setUp("...#___#___#...##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("Wort SOOS enthält O doppelt");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_zuviele_Pausen() {
		
		setUp(".#.#.##_#_#_##.#.#.##;");
		try {
		  parser.Input();
		} catch (ParseException p) {
			;
		}	
		//fail("Zu einem Zeichen gehörende Signale nicht durch # trennen");
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_S_O_S_nicht_hintereinander() {
		
		setUp("_##...##__##___##_.##...##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				;
			}	
		//fail("S, O, S über mehrere Worte verteilt");
		
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_OSS_ist_falsche_Reihenfolge() {
		
		setUp("___#...#...##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				;
			}	
		//fail("OOS hat falsche Reihenfolge");
		
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_SSO_ist_falsche_Reihenfolge() {
		
		setUp("...#...#___##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				;
			}	
		//fail("SS0 hat falsche Reihenfolge");
		
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_beginnende_Pause_muss_Wort_sein() {
		
		setUp("#...#___#...##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				;
			}	
		//fail("Wort SOS darf nicht mit Pause beginnen");
		
	}
	
	@Test(expected = TokenMgrError.class)
	public void test_ungerade_Anzahl_Pause() {
		
		setUp("_.###...#___#...##;");
		try {
			  parser.Input();
			} catch (ParseException p) {
				;
			}	
		//fail("Wort SOS darf nicht mit Pause beginnen");
		
	}
}
