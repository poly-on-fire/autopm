package pull.dbinit;

import pull.repo.ProductCategoryRepo;

public class InitializeProductCategory {
	public void go() {
		/*
		 * Poor Man's workflow: commenting this out once it has run one time, so that I don't inadvertently run it again
		 * 
		 * If this was some kind of real data done frequently, more reasonable measures would be appropriate.
		 */
		ProductCategoryRepo pcr = new ProductCategoryRepo();
		// pcr.add(name);

//		pcr.add("Not Product Specific");
//		pcr.add("Nu Skin Body Care");
//		pcr.add("Nu Skin Color Cosmetics");
//		pcr.add("Nu Skin Epoch Essential Oils");
//		pcr.add("Nu Skin Face Care");
//		pcr.add("Nu Skin Spa Systems");
//		pcr.add("Nu Skin Treatments");
//		pcr.add("Nu Skin AgeLOC Me");
//		pcr.add("Nu Skin AgeLoc");
//		pcr.add("Pharmanex AgeLOC");
//		pcr.add("Pharmanex BioPhotonic Scanner");
//		pcr.add("Pharmanex Nutritionals");
//		pcr.add("Pharmanex Targeted Solutions");
//		pcr.add("Pharmanex Weight Management");
//		pcr.add("Pharmanex AgeLOC Youth");
		
	}

}
