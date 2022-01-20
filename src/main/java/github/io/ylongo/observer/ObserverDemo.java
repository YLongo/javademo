package github.io.ylongo.observer;

import java.util.Observable;
import java.util.Observer;

public class ObserverDemo {

	public static void main(String[] args) {
		ObserverSubject subject = new ObserverSubject();
		Observer observer = new ConcreteObserver();
		subject.addObserver(observer);
		
		subject.setState(1);
		subject.setState(2);
	}

	/**
	 * 被观察对象
	 */
	public static class ObserverSubject extends Observable {
		
		private Integer state;

		public Integer getState() {
			return state;
		}

		public void setState(Integer state) {
			this.state = state;
			this.setChanged();
			// 如果不通知观察者什么状态变化了，则需要观察者自己去获取变化的对象
//			this.notifyObservers(state);
			this.notifyObservers();
		}
	}

	/**
	 * 观察者
	 */
	public static class ConcreteObserver implements Observer {
		
		@Override
		public void update(Observable o, Object arg) {
//			Integer state = (Integer) arg;
			// 观察者获取变化的对象
			ObserverSubject observerSubject = (ObserverSubject) o;
			Integer state = observerSubject.getState();
			System.out.println("目标对象的状态变成为：" + state);
		}
	}
}
