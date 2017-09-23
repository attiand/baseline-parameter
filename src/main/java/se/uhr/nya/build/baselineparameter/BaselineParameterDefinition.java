package se.uhr.nya.build.baselineparameter;

import java.util.Arrays;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.SimpleParameterDefinition;
import net.sf.json.JSONObject;

public class BaselineParameterDefinition extends SimpleParameterDefinition {

	private static final long serialVersionUID = 1L;

	private List<Product> products = Arrays.asList(Product.of("legacy", "1.0.0"), Product.of("hubble", "1.0.1"));
	private List<String> baselines = Arrays.asList("1.0.0", "1.1.0", "1.1.2");
	private String baseline;
	
	@DataBoundConstructor
	public BaselineParameterDefinition(String name, String description) {
		super(name, description);
	}

	@Extension
	public static class DescriptorImpl extends ParameterDescriptor {
		@Override
		public String getDisplayName() {
			return "Baseline Parameter";
		}

		@Override
		public String getHelpFile() {
			return "/plugin/baseline-parameter/help.html";
		}
	}

	@Override
	public ParameterValue createValue(String value) {
		return new BaselineParameterValue(getName(), value, products, getDescription());
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		//BaselineParameterValue value = req.bindJSON(BaselineParameterValue.class, jo);
		return new BaselineParameterValue(getName(), jo.getString("value"), products, getDescription());
	}
}
