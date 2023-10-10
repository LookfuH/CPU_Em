
import java.math.BigInteger;

public class consumer implements Runnable {
	private bufferBound res;
	int val = 0;
	
	public consumer(bufferBound res) {
		this.res = res;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < 4; i++) {
			//grabs from buffer and calc fib
			val = (int) res.remove();
			
			System.out.println("Consumer: " + val + " The fib is " + fib(val));
			try {
				Thread.sleep((int)(Math.random()*20));
			} catch(InterruptedException ie){
				
			}
		}	
	}
	
	
//fib seq using BigInts
	public static BigInteger fib(int n) {
        if (n <= 0) {
            return BigInteger.ZERO;
        } else if (n == 1) {
            return BigInteger.ONE;
        } else {
            BigInteger first = BigInteger.ZERO;
            BigInteger second = BigInteger.ONE;
            BigInteger next = BigInteger.ZERO;
            
            for (int i = 2; i <= n; i++) {
                next = first.add(second);
                first = second;
                second = next;
            }
            
            return next;
        }
    }
}


