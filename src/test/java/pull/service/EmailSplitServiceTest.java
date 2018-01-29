package pull.service;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import pull.service.EmailSplitService;

public class EmailSplitServiceTest {

	@Test
	public void test() {
		String testString = "Belma Baca <belmabaca@hotmail.com>, Linda Friss <yankeelmf21@yahoo.com>, Carol Schulte <carolschulte@hotmail.com>, Lisa Hakenewerth <lisangelfire@gmail.com>, Pam Gurecky <pamkoepke@consolidated.net>, Tina Reid <sweettsmedibles@gmail.com>, Lisa Mitchan <tlttm@hotmail.com>, Adys Mirabela <docsweightloss@gmail.com>, Linda Lindsey <glinda.2@att.net>, Violet Cearley <vcearley@yahoo.com>, Charlotte Rose <RoseIQ@gmail.com>, Ann Watters <annwatters@austin.rr.com>, Cheri Willard <cheriwillarddesigns03@yahoo.com>, Gail Schiavone <gailschiavone@aol.com>, Shirley Cage <scage@gvtc.com>, Cheryl Lambert <cklambert610@gmail.com>, Rhonda Nunez <rlnunez28@yahoo.com>, Pamela Kahn <pkahn@genoaint.com>, Ricci Neer <riccineer@gmail.com>, Jayne Wright <jwkangenwater@gmail.com>, Lisa Braswell <barefoottexgal@hotmail.com>, Darleen McDaniel <darleen4942@sbcglobal.net>, Nona Chaille-Bertschy <alphabiotics@gmail.com>, Norma Petrosewicz <norma@petrolaw.biz>, danielle martin <ddfgm2@gmail.com>, Laurie Burt Goodsell <laurierg615@gmail.com>, Barbara Engle <bengle@solomonwealth.com>, Cheryl Ann Morse <cherylquinn55@gmail.com>, Brenda Hollingsworth Knoll <brendasop@yahoo.com>, Becky Lindsey <lindseywaco@aol.com>, Constance Milton Dias <cfdias55@gmail.com>, Rebecca Chang <rkchang@aol.com>, Terry Deckard <tedeckard.cpa@gmail.com>, Jay Comeaux <jcomeaux250@gmail.com>, Jennifer Lehmann <jenniferlehmann@yahoo.com>, \"Mary Hebert, MD\" <mhebert9193@live.com>, Becky Flores <rev.rebecca222@gmail.com>, Claudia Salazar <claudia_salazar97@yahoo.com>, Julie Mcdowell <juliemcdowell1966@gmail.com>, Laurie Labrousse <laurielab@hotmail.com>, Donie Torrance <ohmskincare@gmail.com>, bobby cervantez <bob.cervantez@gmail.com>, Kristi Thigpen <inspireyourbestself@gmail.com>, Chelsey Hickman <chelseykayee122@yahoo.com>, \"P Michael (magic Mike) Wyatt\" <1michaelwyatt@gmail.com>";
		Map<String, String> emailMap= EmailSplitService.splitCanonicalEmailList(testString);
		assertEquals(45,emailMap.size());
		assertEquals("belmabaca@hotmail.com", emailMap.get("Belma Baca"));
		assertEquals("tlttm@hotmail.com", emailMap.get("Lisa Mitchan"));
	}

}
