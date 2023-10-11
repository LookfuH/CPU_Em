public class consumer implements Runnable {
	private bufferBound<Process_Sim> res;
	Process_Sim val;

	public consumer(bufferBound<Process_Sim> res) {
		this.res = res;
	}

	@Override
	public void run() {
		while (true) {
			val = res.remove();
		}
	}
}
