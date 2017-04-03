package common;
import java.util.Random;
public class BaseThread extends Thread
{
	public static int siNextTID = 1;
	protected int iTID;
	private static int siTurn = 1;

	public BaseThread()
	{
		setTID();
	}

	public BaseThread(ThreadGroup poGroup, String pstrName)
	{
		super(poGroup, pstrName);
		setTID();
	}

	public BaseThread(final int piTID)
	{
		this.iTID = piTID;
	}

	public final int getTID()
	{
		return this.iTID;
	}

	private final void setTID()
	{
		this.iTID = siNextTID++;
	}

	public static synchronized final void setInitialTurn(int piInitTurn)
	{
		siTurn = piInitTurn;
	}

	protected synchronized void phase1()
	{
		System.out.println(this.getClass().getName() + " thread [TID=" + this.iTID + "] starts PHASE I.");

		System.out.println
		(
			"Some stats info in the PHASE I:\n" +
			"    iTID = " + this.iTID +
			", siNextTID = " + siNextTID +
			", siTurn = " + siTurn +
			".\n    Their \"checksum\": " + (siNextTID * 100 + this.iTID * 10 + siTurn)
		);

		System.out.println(this.getClass().getName() + " thread [TID=" + this.iTID + "] finishes PHASE I.");
	}

	protected synchronized void phase2()
	{
		System.out.println(this.getClass().getName() + " thread [TID=" + this.iTID + "] starts PHASE II.");

		System.out.println
		(
			"Some stats info in the PHASE II:\n" +
			"    iTID = " + this.iTID +
			", siNextTID = " + siNextTID +
			", siTurn = " + siTurn +
			".\n    Their \"checksum\": " + (siNextTID * 100 + this.iTID * 10 + siTurn)
		);

		System.out.println(this.getClass().getName() + " thread [TID=" + this.iTID + "] finishes PHASE II.");
	}

	public synchronized boolean turnTestAndSet(boolean pcIncreasingOrder)
	{
		if(siTurn == this.iTID)
		{
			if(pcIncreasingOrder == true)
				siTurn++;
			else
				siTurn--;
			return true;
		}

		return false;
	}

	public synchronized boolean turnTestAndSet()
	{
		return turnTestAndSet(true);
	}
}
