package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
	private String firstName;
	private String lastName;
	private Date birthday;

	public Person (
			String firstName, String lastName, String birthday)
	throws ParseException {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = sdf.parse(birthday);
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
