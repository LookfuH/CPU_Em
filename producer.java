
public class producer implements Runnable {
	private bufferBound res;
	
	public producer(bufferBound res) {
		this.res = res;
	}
	
	@Override
	public void run() {
		//inserts 8 rand ints into the buffer
		int val = 0;
		for(int i = 0; i < 8; i++) {
			val = (int) (Math.random()*99);
			res.insert(val);
			System.out.println("Producer put: " + val + " into the buffer" );
			try {
				Thread.sleep((int)(Math.random()*20));
			} catch(InterruptedException ie){
				
			}
		}	
	}
}
