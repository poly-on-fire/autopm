package pull.domain;

public class ProductCategory {
	private String name;

	public ProductCategory() {
		super();
	}

	public ProductCategory(String name) {
		super();
		this.name = name;
	}
	

	@Override
	public String toString() {
		return "ProductCategory [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
