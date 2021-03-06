package se.uhr.nya.build.baselineparameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.Job;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.SimpleParameterDefinition;
import hudson.util.ListBoxModel;
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
		
		public ListBoxModel doFillValueItems(@AncestorInPath Job job, @QueryParameter String param) {
			ParametersDefinitionProperty prop = (ParametersDefinitionProperty) job
					.getProperty(ParametersDefinitionProperty.class);
			ListBoxModel items = new ListBoxModel();

			if (prop != null) {
				ParameterDefinition def = prop.getParameterDefinition(param);
				if (def instanceof BaselineParameterDefinition) {
					BaselineParameterDefinition paramDef = (BaselineParameterDefinition) def;

					List<String> tmp = new ArrayList<>(paramDef.baselines);
					Collections.reverse(tmp);
					
					for (String bl : tmp) {
						items.add(bl);
					}
				}
			}

			return items;
		}
	}

	@Override
	public ParameterValue createValue(String value) {
		return new BaselineParameterValue(getName(), value, products, getDescription());
	}

	@Override
	public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
		return new BaselineParameterValue(getName(), jo.getString("value"), products, getDescription());
	}
}
