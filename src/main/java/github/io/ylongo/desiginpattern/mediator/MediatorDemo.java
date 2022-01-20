package github.io.ylongo.desiginpattern.mediator;

public class MediatorDemo {

	public static void main(String[] args) {

		Mediator mediator = new Mediator();

		ModuleA moduleA = new ModuleA(mediator);
		ModuleB moduleB = new ModuleB(mediator);
		ModuleC moduleC = new ModuleC(mediator);
		
		moduleA.execute();
		moduleB.execute();
		moduleC.execute();
	}
	
	public static class Mediator {
		
		private ModuleA moduleA;
		
		private ModuleB moduleB;
		
		private ModuleC moduleC;

		public ModuleA getModuleA() {
			return moduleA;
		}

		public void setModuleA(ModuleA moduleA) {
			this.moduleA = moduleA;
		}

		public ModuleB getModuleB() {
			return moduleB;
		}

		public void setModuleB(ModuleB moduleB) {
			this.moduleB = moduleB;
		}

		public ModuleC getModuleC() {
			return moduleC;
		}

		public void setModuleC(ModuleC moduleC) {
			this.moduleC = moduleC;
		}
		
		public void moduleAInvoke() {
			moduleB.execute("mediator moduleB");
			moduleC.execute("mediator moduleC");
		}

		public void moduleBInvoke() {
			moduleA.execute("mediator moduleA");
			moduleC.execute("mediator moduleC");
		}

		public void moduleCInvoke() {
			moduleA.execute("mediator moduleA");
			moduleB.execute("mediator moduleB");
		}
	}
	
	public static class ModuleA {
		
		private final Mediator mediator;

		public ModuleA(Mediator mediator) {
			this.mediator = mediator;
			mediator.setModuleA(this);
		}

		public void execute() {
			mediator.moduleAInvoke();
		}
		
		public void execute(String invoker) {
			System.out.println(invoker + "调用模块A的功能");
		}
	}

	public static class ModuleB {

		private final Mediator mediator;

		public ModuleB(Mediator mediator) {
			this.mediator = mediator;
			mediator.setModuleB(this);
		}

		public void execute() {
			mediator.moduleBInvoke();
		}
		
		public void execute(String invoker) {
			System.out.println(invoker + "调用模块B的功能");
		}
	}

	public static class ModuleC {

		private final Mediator mediator;

		public ModuleC(Mediator mediator) {
			this.mediator = mediator;
			mediator.setModuleC(this);
		}

		public void execute() {
			mediator.moduleCInvoke();
		}

		public void execute(String invoker) {
			System.out.println(invoker + "调用模块C的功能");
		}
	}
}
