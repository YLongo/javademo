package github.io.ylongo.desiginpattern.command;

public class CommandDemo {

	public static void main(String[] args) {

		Invoker invoker = new Invoker();

		Command commandA = new CommandA();
		invoker.setCommand(commandA);
		invoker.execute();
		
		CommandB commandB = new CommandB();
		invoker.setCommand(commandB);
		invoker.execute();
	}
	
	public interface Command {
		
		public void execute();
	}
	
	public static class CommandA implements Command {
		
		@Override
		public void execute() {
			System.out.println("commandA执行逻辑");	
		}
	}

	public static class CommandB implements Command {

		@Override
		public void execute() {
			System.out.println("commandB执行逻辑");
		}
	}
	
	public static class Invoker {
		
		private Command command;

		public void execute() {
			command.execute();
		}
		
		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}
	}
}
