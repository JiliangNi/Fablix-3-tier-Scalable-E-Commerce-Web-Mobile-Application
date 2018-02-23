
public class star {
	public String name;
	public int year;
	public star() {}
	public star(String name,int year) {
		this.name = name;
		this.year = year;
	}
	public String toQuery() {
		return String.format("insert into stars value(%s,%s,%d)","",this.name,this.year );
	}
}
