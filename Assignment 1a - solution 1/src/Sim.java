/**
 * The driver of the simulation
 */

public class Sim {
	/**
	 * Create all components and start all of the threads.
	 */
	public static void main(String[] args) {

		Belt belt = new Belt();
		Producer producer = new Producer(belt);
		Consumer consumer = new Consumer(belt);
		BeltMover mover = new BeltMover(belt);
		Inspector inspector = new Inspector(belt);

		consumer.start();
		producer.start();
		mover.start();
		inspector.start();

		while (consumer.isAlive() && producer.isAlive() && mover.isAlive()
				&& inspector.isAlive())
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				BicycleHandlingThread.terminate(e);
			}

		// interrupt other threads
		consumer.interrupt();
		producer.interrupt();
		mover.interrupt();
		inspector.interrupt();

		System.out.println("Sim terminating");
		System.out.println(BicycleHandlingThread.getTerminateException());
		System.exit(0);
	}
}
