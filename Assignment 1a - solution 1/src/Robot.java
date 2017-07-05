/*
 * the robot used to carry bicycle between belt and inspector. initial position
 * of robot's arm is located at belt
 */
public class Robot extends BicycleHandlingThread {
	// the belt from which the robot fetches or return the bicycles
	protected Belt belt;

	/*
	 * create a robot to get or return a bike from the belt
	 */
	public Robot(Belt belt) {
		this.belt = belt;
	}

	/**
	 * Take a bicycle from segment 3 of the belt to inspector
	 * 
	 * @return the bicycle in segment 3
	 */
	public Bicycle fetchBicycle() {
		Bicycle temp = null;
		try {
			temp = belt.get(2, " sended to inspector");
			sleep(Params.ROBOT_MOVE_TIME);
		} catch (NoBicycleException e) {
			terminate(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * Put a bicycle on the segment 3 of belt.
	 * 
	 * @param bicycle
	 *            the bicycle to put onto the belt.
	 */
	public void returnBicycle(Bicycle bicycle) {
		try {
			sleep(Params.ROBOT_MOVE_TIME);
			String message = bicycle + " sended back to belt";
			belt.put(bicycle, 2, message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
