package comparators;

import java.util.Comparator;

import models.Person;

public class PersonsComparator extends BaseComparator implements Comparator<Person> {

	@Override
	public int compare(Person arg0, Person arg1) {
		return super.compare(arg0.getName(), arg1.getName());
	}
}