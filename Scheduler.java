public class Scheduler implements Runnable {
	private bufferBound res;
	Process_Sim val;

	public Scheduler(bufferBound res) {
		this.res = res;
	}

	@Override
	public void run() {
		while (true) {
			val = res.remove();
		}
	}
}
