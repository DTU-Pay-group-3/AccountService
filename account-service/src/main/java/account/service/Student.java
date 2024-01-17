package account.service;

import java.io.Serializable;

public class Student implements Serializable{
	
	private static final long serialVersionUID = 9023222981284806610L;
	private String name;
	private String id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Student)) {
			return false;
		}
		var c = (Student) o;
		return name != null && name.equals(c.getName()) && 
				id != null && id.equals(c.getId()) ||
				name == null && c.getName() == null &&
				id == null && c.getId() == null;
	}
	
	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}
	
	@Override
	public String toString() {
		return String.format("Student name %s, id %s", name, id);
	}
}
