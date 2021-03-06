package game;

import java.util.concurrent.ThreadLocalRandom;

public class RandomManager {

	private final static double baseValue=10000;
	/**
	 * 
	 * @param rate 百分比 支持到小数点后两位
	 * @return
	 */
	public static boolean randomRate(double rate) {
		int rateInt=(int) (rate*100);
		if(rate<=0)
			return false;
		if(rate>=baseValue)
			return true;
		int randomValue = random().nextInt(rateInt);
		if(randomValue<rate)
			return true;
		return false;
	}
	public static ThreadLocalRandom getRandom() {
		return random();
	}
	
	public static ThreadLocalRandom random() {
		return ThreadLocalRandom.current();
	}
	
}
