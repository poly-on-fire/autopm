package pull.domain;

public class TopicType {
	private String name;
	private boolean hasLeadCapturePage = false;
	private boolean hasSalesPage = false;
	private boolean hasOptionalVslPage = false;

	public TopicType() {
		super();
	}

	public TopicType(String name,  boolean hasLeadCapturePage, boolean hasSalesPage,
			boolean hasOptionalVslPage) {
		super();
		this.name = name;
		this.hasLeadCapturePage = hasLeadCapturePage;
		this.hasSalesPage = hasSalesPage;
		this.hasOptionalVslPage = hasOptionalVslPage;
	}

	public boolean isHasLeadCapturePage() {
		return hasLeadCapturePage;
	}

	public void setHasLeadCapturePage(boolean hasLeadCapturePage) {
		this.hasLeadCapturePage = hasLeadCapturePage;
	}

	public boolean isHasSalesPage() {
		return hasSalesPage;
	}

	public void setHasSalesPage(boolean hasSalesPage) {
		this.hasSalesPage = hasSalesPage;
	}

	public boolean isHasOptionalVslPage() {
		return hasOptionalVslPage;
	}

	public void setHasOptionalVslPage(boolean hasOptionalVslPage) {
		this.hasOptionalVslPage = hasOptionalVslPage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
