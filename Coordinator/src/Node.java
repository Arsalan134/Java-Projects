import java.util.Date;

enum Vote {
	ok, election, coordinator
};

public class Node extends Thread {

	private int ID; // Unique id of a node
	private boolean isMaster = false; // boolean that tell if this node is a
										 // master
	private boolean recievedOK = false; // boolean that tell if it recieved ok
	private boolean electionStarted = false;
	private int timeoutInSeconds = 3; // time to wait until response comes
	private int masterID;
	private Date deadline; // it a time when stop waiting and make decision.

	public Node(int id) {
		this.ID = id;
	}

	// Starts election process
	void startElection() {
		deadline = new Date(new Date().getTime() + timeoutInSeconds * 1000);
		System.out.println("Node: " + ID + " started election" + ". Deadline is " + deadline);
		for (int i = 1 + ID; i < Main.N; i++) {
			Main.nodes.get(i).sendVote(ID, Vote.election); // Sends election
															 // vote to all
															 // nodes
		}
	}

	void sendVote(int from, Vote message) {
		System.out.println("Node: " + ID + " recieved " + message + " from node: " + from);

		switch (message) {
		case election:

			try {
				Thread.sleep(100); // waits 1 second

				System.out.println("Node: " + ID + " send " + Vote.ok + " to node: " + from);
				Main.nodes.get(from).sendVote(ID, Vote.ok);

				if (!electionStarted) {
					electionStarted = true;
					Thread.sleep(100);
					startElection();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			break;
		case ok:
			recievedOK = true;
			break;
		case coordinator:
			masterID = from;
			break;
		default:
			break;
		}
	}

	// Get id of a node
	public int getID() {
		return ID;
	}

	// returns boolean telling that is it a master
	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	@Override
	public void run() {
		while (true) {

			try {
				Thread.sleep(100);
				if (!Main.electionStarted) {
					Main.electionMutex.acquire(); // critical region that's why
													 // used mutex
					Main.electionStarted = true;
					Main.electionMutex.release();
					startElection();
				}

				if (new Date().after(deadline)) {
					deadline = null;
					System.out.println("Node: " + ID + " is deadline");
					if (!recievedOK) {
						System.out.println("Node: " + ID + " is a coordinator!"); // Node
																					 // declares
																					 // itself
																					 // as
																					 // a
																					 // coordinator
						for (Node node : Main.nodes) {
							if (node == this) // skip if it is itself
								continue;
							node.sendVote(ID, Vote.coordinator);
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}
}
