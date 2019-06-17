package com.test.suite;

public class Employee {
	
	private String name;
	private int id;
	private boolean permanentEmp;
	
	public boolean isPermanentEmp() {
		return permanentEmp;
	}

	public Employee(String name, int id, boolean permanentEmp, Department dept) {
		super();
		this.name = name;
		this.id = id;
		this.permanentEmp = permanentEmp;
		this.dept = dept;
	}

	public void setPermanentEmp(boolean permanentEmp) {
		this.permanentEmp = permanentEmp;
	}

	private Department dept;
	
	
	
	public Employee(String name, int id, Department dept) {
		super();
		this.name = name;
		this.id = id;
		this.dept = dept;
	}





	@Override
	public String toString() {
		return "Employee [name=" + name + ", id=" + id + ", dept=" + dept + "]";
	}





	public Department getDept() {
		return dept;
	}





	public void setDept(Department dept) {
		this.dept = dept;
	}





	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Employee(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Employee() {}
	
}
