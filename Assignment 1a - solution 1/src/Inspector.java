/*
 * a place performing thorough examination for tagged bicycles to decide whether
 * the bicycle is defective
 */
public class Inspector extends BicycleHandlingThread {
	// the belt might contain bicycles needing to be inspected
	protected Belt belt;
	// the robot used to transport bicycles for inspection
	protected Robot robot;

	/*
	 * create a inspector to examine the bicycle fetched by the robot from belt
	 */
	public Inspector(Belt belt) {
		super();
		this.belt = belt;
		robot = new Robot(belt);
		robot.start();
	}

	/*
	 * Loop indefinitely trying to get bicycle which is needed more inspection
	 * from the quality control belt
	 */
	@Override
	public void run() {
		Bicycle targetBicycle;

		while (!isInterrupted()) {
			try {
				// check whether there is a bicycle needed to be examined
				while (belt.sensor()) {
					targetBicycle = robot.fetchBicycle();

					// double check whether it sends the tagged bicycle to
					// inspector
					if (targetBicycle.isTagged()) {
						// if the bicycle is not defective, removing the tag of
						// bicycle
						if (!targetBicycle.isDefective()) {
							targetBicycle.setNotTagged();
						}
						targetBicycle.setInspected();
						sleep(Params.INSPECT_TIME);

						robot.returnBicycle(targetBicycle);
					} else {
						String message =
								"An untagged bicycle is sending to inspector";
						throw new DefKnownException(message);
					}
				}
			} catch (DefKnownException e) {
				terminate(e);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
		System.out.println("Inspector terminated");
	}
}
