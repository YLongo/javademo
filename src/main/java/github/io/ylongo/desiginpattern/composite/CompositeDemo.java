package github.io.ylongo.desiginpattern.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeDemo {

	public static void main(String[] args) {
		Department dept1 = new Department("部门1");
		Department dept2 = new Department("部门2");
		Department dept3 = new Department("部门3");

		Department subDept1 = new Department("一级部门1");
		subDept1.getChildren().add(dept1);
		subDept1.getChildren().add(dept2);
		
		Department subDept2 = new Department("一级部门2");
		subDept2.getChildren().add(dept3);

		Department pDept = new Department("父部门");
		pDept.getChildren().add(subDept1);
		pDept.getChildren().add(subDept2);
		
		pDept.remove();
	}
	
	private static class Department {
		
		
		private String name;
		
		private List<Department> children = new ArrayList<>();

		public Department(String name) {
			this.name = name;
		}

		public void remove() {
			
			if (children.size() > 0) {
				for (Department child : children) {
					child.remove();
				}
			}
			System.out.println("部门[" + name + "]被删除");
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Department> getChildren() {
			return children;
		}

		public void setChildren(List<Department> children) {
			this.children = children;
		}
	}
}
