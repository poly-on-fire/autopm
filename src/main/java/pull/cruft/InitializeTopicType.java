package pull.cruft;

import pull.repo.TopicTypeRepo;

//	TODO: Comment - one of several dev-oriented cruft pieces that are not being used but were so handy that I wanted to leave as a how-to
public class InitializeTopicType {
	public void go() {
		/*
		 * Poor Man's workflow: commenting this out once it has run one time, so that I don't inadvertently run it again
		 * 
		 * If this was some kind of real data done frequently, more reasonable measures would be appropriate.
		 */
		TopicTypeRepo ctr = new TopicTypeRepo();
		// ctr.add(name, hover, hasLeadCapturePage, hasSalesPage,
		// hasOptionalVslPage);
		
		if(true){
			throw new RuntimeException("HAVE YOU INCORPORATED THE MANUAL CHANGES MADE ON THE fb SITE?");
		}
//		ctr.add("OTG style Distributor Startup",
//				"Use this when you want to take a business first Nathan Ricks style approach, such as promoting a 4 for 3 startup package.",
//				true, true, false);
//		ctr.add("Kuchel style product enthusiast startup",
//				"Use this when you want to take a Jan Kuchel approach, supporting people's desire to have the best product, rather than business first.",
//				true, true, false);
//		ctr.add("'London Mum' styled stocking vendor:",
//				"Use this when you want to immitate the wildly successful young mothers in London who stock and sell small items at retail, such as toothpaste.",
//				true, true, false);
//		ctr.add("'Just Met You' - follow up",
//				"Use this when you want to send a series of personalized emails, all written the day after from templates, but sent out over a longer period of time",
//				false, false, true);
//		ctr.add("ADR Retention",
//				"Use this when you want to remind people on an ADR about the benefits they are receiving.", false,
//				false, true);
//		ctr.add("General Preferred Customer Retention",
//				"Use this when you want to write a retention series, but NOT one that is focused on a single specific product ADR",
//				false, false, true);
//		ctr.add("Distributor Downline",
//				"Use this when you want to dispatch a monthly note to your downline. As if they weren't already over-communicated to..",
//				false, false, true);
//		ctr.add("Onboarding",
//				"Use this for your initial recruits, to give them the support they need, and keep them on the straight and narrow.",
//				false, false, true);
//		ctr.add("School of Charm",
//				"Use this for special corrective communication series, such as explaining to a sub-group when a popular approach is over-bearing, and how it should be refined.",
//				false, false, true);
	}

}
