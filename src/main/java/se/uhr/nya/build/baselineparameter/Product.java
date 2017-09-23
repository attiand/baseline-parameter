package se.uhr.nya.build.baselineparameter;

import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.Whitelisted;

public class Product {

	private final String name;

	private final String version;

	private Product(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}

	public static Product of(String name, String version) {
		return new Product(name, version);
	}
	
	@Whitelisted
	public String getName() {
		return name;
	}

	@Whitelisted
	public String getVersion() {
		return version;
	}
}
