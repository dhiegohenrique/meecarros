package comparators;

import java.text.Collator;

import org.apache.commons.lang3.StringUtils;

public class BaseComparator {

	private Collator collator;
	
	public BaseComparator() {
		this.collator = Collator.getInstance();
		this.collator.setStrength(Collator.PRIMARY);
	}
	
	protected int compare(String arg0, String arg1) {
		return this.collator.compare(this.getValue(arg0), this.getValue(arg1));
	}
	
	private String getValue(String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		
		return value;
	}
}