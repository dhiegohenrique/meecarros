package comparators;

import java.util.Comparator;

import models.Color;

public class ColorsComparator extends BaseComparator implements Comparator<Color> {

	@Override
	public int compare(Color o1, Color o2) {
		return super.compare(o1.getName(), o2.getName());
	}
}