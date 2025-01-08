package beans;

public class User {
	private String id;
	private String password;
	private String name;
	private int age;
	private String phone;
	
	public User(String id, String passwd, String name, int age, String phone) {
		super();
		this.id = id;
		this.password = passwd;
		this.name = name;
		this.age = age;
		this.phone = phone;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passwd) {
		this.password = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	
}
