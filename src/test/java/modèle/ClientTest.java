package modèle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
	private Client c;

	@Before
	public void setUp() throws Exception {
		c = new Client("Dupont", "Jean", "123 rue Exemple", "75000", "Paris", "0123456789", "jean.dupont@example.com", MoyenPaiement.CarteCrédits, false);
	}

	@After
	public void tearDown() throws Exception {
		c = null;
	}

	@Test
	public void testNom() {
		assertEquals("Dupont", c.getNom());
	}

	@Test
	public void testPrénom() {
		assertEquals("Jean", c.getPrénom());
	}

	@Test
	public void testAdresse() {
		assertEquals("123 rue Exemple", c.getAdresse());
	}

	@Test
	public void testCodePostal() {
		assertEquals("75000", c.getCodePostal());
	}

	@Test
	public void testVille() {
		assertEquals("Paris", c.getVille());
	}

	@Test
	public void testTéléphone() {
		assertEquals("0123456789", c.getTéléphone());
	}

	@Test
	public void testMail() {
		assertEquals("jean.dupont@example.com", c.getMail());
	}
	
	@Test
	public void testMoyenPaiement() {
		assertEquals(MoyenPaiement.CarteCrédits, c.getMoyenDePaiement());
	}
	
	@Test
	public void testAbonnementNewsletter() {
		assertEquals(false, c.getAbonnementNewsletter());
	}

}
