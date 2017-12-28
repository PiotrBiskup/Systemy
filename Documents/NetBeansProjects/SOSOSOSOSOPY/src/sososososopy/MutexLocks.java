
package sososososopy;


import java.util.LinkedList;
import java.util.Queue;

public class MutexLocks 
{
   public String name;
	private boolean state;
	private Process lockedProcess;
	private Queue<Process> waitingProcesses;
	
	public MutexLocks(String name) {
		this.name = name;
		this.setLockState(false);
		waitingProcesses = new LinkedList<Process>();
	}
	
	public boolean getState()  {
		return state;
	}
	
	public void setLockState(boolean state)  {
		this.state = state;
	}
	
	public void displayLockedProcess() {
		System.out.println(lockedProcess.get_ID());
	}
	
	public void lock(Process processToLock) {
		if(!getState()) {
			//zamek jest otwarty, proces zamyka zamek i rusza dalej
			setLockState(true);
			this.lockedProcess = processToLock;
		} else {
			//zamek jest zamkniety wiec proces wedruje do kolejki i ustawiany jest jego bit blocked
			waitingProcesses.offer(processToLock);
			processToLock.set_if_lock(1);  // to ustawiamy na true zeby wiadomo bylo ze proces jest zablokowany
			processToLock.set_state(1); // to jest metoda od prcesow i nie wiem jeszcze jak oznaczyc proces czekajacy
			
		}
	}
	
	public void unlock(Process processToUnlock) {
		//odblokowuje zamek i jezli kolejka nie jest pusta to zeruje bit blocked pierwszego oczekujacego procesu.
		if(processToUnlock.get_ID() == this.lockedProcess.get_ID()) {
			this.setLockState(false);
			this.lockedProcess = null;
			if(!waitingProcesses.isEmpty()) 
                        {
				waitingProcesses.peek().set_state(0); //to jest metoda od prcesow i tu zmieniamy procesowi na szczycie kolejki procesow czekajacych stan na ready
				waitingProcesses.poll().set_if_lock(0); // to ustawiamy na false zeby wiadomo bylo ze proces juz nie jest zablokowany (funkcja poll() zwraca gore kolejki procesow czekajacych i usuwa go z niej)
			}
		}
	} 
    
    
}
