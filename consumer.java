public class consumer implements Runnable {
	private bufferBound<Process_Sim> res;
	Process_Sim val;

	public consumer(bufferBound<Process_Sim> res) {
		this.res = res;
	}

	@Override
	public void run() {
		for(int i = 0; i < 4; i++) {
			//grabs from buffer and calc fib
			val = res.remove();

			try {
				Thread.sleep((int)(Math.random()*20));
			} catch(InterruptedException ie){

			}
		}
	}
}
