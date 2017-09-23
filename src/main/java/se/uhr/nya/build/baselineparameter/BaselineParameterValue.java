package se.uhr.nya.build.baselineparameter;

import java.util.Arrays;
import java.util.List;

import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.Whitelisted;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.export.Exported;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.ParameterValue;
import hudson.util.VariableResolver;

public class BaselineParameterValue extends ParameterValue {

	private static final long serialVersionUID = 1L;
	@Exported(visibility = 4)
	public final String baseline;
	private final List<Product> products;

	@DataBoundConstructor
	public BaselineParameterValue(String name, String baseline, List<Product> products) {
		this(name, baseline, products, null);
	}

	public BaselineParameterValue(String name, String baseline, List<Product> products, String description) {
		super(name, description);
		this.baseline = baseline;
		this.products = products;
	}

	@Whitelisted
	public List<Product> getProducts() {
		return products;
	}
	
	@Whitelisted
	public String getBaseline() {
		return baseline;
	}
	
	@Override
	public Object getValue() {
		return this;
	}

	@Override
	public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
		env.put(name, baseline);
	}

	@Override
	public VariableResolver<String> createVariableResolver(AbstractBuild<?, ?> build) {
		return new VariableResolver<String>() {
			public String resolve(String name) {
				return BaselineParameterValue.this.name.equals(name) ? baseline : null;
			}
		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((baseline == null) ? 0 : baseline.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaselineParameterValue other = (BaselineParameterValue) obj;
		if (baseline == null) {
			if (other.baseline != null)
				return false;
		} else if (!baseline.equals(other.baseline))
			return false;
		return true;
	}

}
