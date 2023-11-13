/*
 * @author Diego Toledo Luque
 * Double Degree in Mathematics and Computer Science
 * Analysis and Design of Algorithms
 * @date 13/11/2023
 */

public class OptimalSpeed {
	
	public OptimalSpeed() {
		/*
		 * TODO
		 * Constructor of the class
		 */
	}
	
	private int tyreDegradation (int speed, int fuel, int orgDegradation) {
		/*
		 * TODO
		 * Here goes the statistical model that measures degradation
		 * We can assume that degradation is not bigger than 100%
		*/
		return 0;
	}
	
	private int fuelConsumption (int speed, int orgFuel) {
		/*
		 * TODO
		 * Here goes the statistical model that measures fuel consumption
		 * We can assume that fuelConsumption is not bigger than fuel originally available
		*/
		return 0;
	}
	
	private int maxSpeed (int fuel, int degradation) {
		/*
		 * TODO
		 * Here goes the statistical model that measures max possible speed
		*/
		return 0;
	}
	
	public int[] optSpeed (int laps, int initial_fuel, int initial_degradation, int lap_length) {
		
		/*
		 * @param takes the number of laps left, the initial fuel available in kg and the initial degradation in % as well as lap length
		 * @return integer list for optimal speed in each lap
		 */
		
		double[][][] Time = new double[laps][initial_fuel + 1][101];
		int[][][] Speed = new int[laps][initial_fuel + 1][101];
		
		//First Step: Filling base cases
		
		for (int j = 1; j <= initial_fuel; j++) {
			for (int k = initial_degradation; k < 100; k++) {
				int max_speed = maxSpeed(j,k);
				Time[0][j][k] = lap_length / max_speed;
				Speed[0][j][k] = max_speed;
			}
		}
		
		for (int i = 0; i < laps; i++) {
			for (int k = initial_degradation; k < 100; k++) {
				Time[i][0][k] = Double.POSITIVE_INFINITY;
				Speed[i][0][k] = 0;
			}
		}
		
		for (int i = 0; i < laps; i++) {
			for (int j = 0; j <= initial_fuel; j++) {
				Time[i][j][100] = Double.POSITIVE_INFINITY;
				Speed[i][j][100] = 0;
			}
		}
		
		//Second Step: Bellman's Equation
		
		for (int i = 1; i < laps; i++) {
			for (int j = 1; j <= initial_fuel; j++) {
				for (int k = initial_degradation; k < 100; k++) {
					double min_time = Double.POSITIVE_INFINITY;
					int opt_speed = 0;
					for (int s = 1; s <= maxSpeed(j,k); s++) {
						double time = Time[i-1][j-fuelConsumption(s,j)][tyreDegradation(s,j,k)] + lap_length / s;
						if (time < min_time) {
							min_time = time; 
							opt_speed = s;
						}
					}
					Time[i][j][k] = min_time;
					Speed[i][j][k] = opt_speed;
				}
			}
		}
		
		//Third Step: Reconstruct Solution
		
		int[] optimalSpeed = new int [laps];		 
		int optSpeed = Speed[laps - 1][initial_fuel][initial_degradation];
		int fuel = initial_fuel;
		int degradation = initial_degradation;
		optimalSpeed[0] = optSpeed;
		
		for (int i = 1; i < laps; i++) {
			degradation = tyreDegradation(optSpeed, fuel, degradation);
			fuel = fuel - fuelConsumption(optSpeed,fuel);
			optSpeed = Speed[laps - i - 1][fuel][degradation];			 
			optimalSpeed[laps - i - 1] = optSpeed;
		}
		
		return optimalSpeed;
		
	}

}
