package task4;

import common.*;

public class BlockManager
{
	private static BlockStack soStack = new BlockStack();
	private static final int NUM_PROBERS = 4;
	private static int siThreadSteps = 5;
	private static Semaphore mutex = new Semaphore(1);
	private static Semaphore s1 = new Semaphore(-9);

	public static void main(String[] argv)
	{
		try
		{
			System.out.println("Main thread starts executing.");
			System.out.println("Initial value of top = " + soStack.getITop() + ".");
			System.out.println("Initial value of stack top = " + soStack.pick() + ".");
			System.out.println("Main thread will now fork several threads.");

			AcquireBlock ab1 = new AcquireBlock();
			AcquireBlock ab2 = new AcquireBlock();
			AcquireBlock ab3 = new AcquireBlock();

			System.out.println("main(): Three AcquireBlock threads have been created.");

			ReleaseBlock rb1 = new ReleaseBlock();
			ReleaseBlock rb2 = new ReleaseBlock();
			ReleaseBlock rb3 = new ReleaseBlock();

			System.out.println("main(): Three ReleaseBlock threads have been created.");

			CharStackProber	aStackProbers[] = new CharStackProber[NUM_PROBERS];

			for(int i = 0; i < NUM_PROBERS; i++)
				aStackProbers[i] = new CharStackProber();

			System.out.println("main(): CharStackProber threads have been created: " + NUM_PROBERS);

			ab1.start();
			aStackProbers[0].start();
			rb1.start();
			aStackProbers[1].start();
			ab2.start();
			aStackProbers[2].start();
			rb2.start();
			ab3.start();
			aStackProbers[3].start();
			rb3.start();

			System.out.println("main(): All the threads are ready.");

			ab1.join();
			ab2.join();
			ab3.join();

			rb1.join();
			rb2.join();
			rb3.join();

			for(int i = 0; i < NUM_PROBERS; i++)
				aStackProbers[i].join();

			System.out.println("System terminates normally.");
			System.out.println("Final value of top = " + soStack.getITop() + ".");
			System.out.println("Final value of stack top = " + soStack.pick() + ".");
			System.out.println("Final value of stack top-1 = " + soStack.getAt(soStack.getITop() - 1) + ".");
			System.out.println("Stack access count = " + soStack.getAccessCounter());

			System.exit(0);
		}
		catch(InterruptedException e)
		{
			System.err.println("Caught InterruptedException (internal error): " + e.getMessage());
			e.printStackTrace(System.err);
		}
		catch(Exception e)
		{
			reportException(e);
		}
		finally
		{
			System.exit(1);
		}
	}

	static class AcquireBlock extends BaseThread
	{
		private char cCopy;

		public void run()
		{
			mutex.P();
			System.out.println("AcquireBlock thread [TID=" + this.iTID + "] starts executing.");
			phase1();
			try
			{
				System.out.println("AcquireBlock thread [TID=" + this.iTID + "] requests Ms block.");

				this.cCopy = soStack.pop();

				System.out.println
				(
					"AcquireBlock thread [TID=" + this.iTID + "] has obtained Ms block " + this.cCopy +
					" from position " + (soStack.getITop() + 1) + "."
				);


				System.out.println
				(
					"Acq[TID=" + this.iTID + "]: Current value of top = " +
					soStack.getITop() + "."
				);

				System.out.println
				(
					"Acq[TID=" + this.iTID + "]: Current value of stack top = " +
					soStack.pick() + "."
				);
			}
			catch (CustomException e) 
			{
				reportException(e);
			}
			catch(Exception e)
			{
				reportException(e);
				System.exit(1);
			}
			finally {
				mutex.V();
			}

			s1.V();
			s1.P();
			phase2();
			s1.V();

			System.out.println("AcquireBlock thread [TID=" + this.iTID + "] terminates.");
		}
	}

	static class ReleaseBlock extends BaseThread
	{
		private char cBlock = 'a';

		public void run()
		{
			mutex.P();
			System.out.println("ReleaseBlock thread [TID=" + this.iTID + "] starts executing.");
			phase1();
			try
			{
				if(soStack.isEmpty() == false)
					this.cBlock = (char)(soStack.pick() + 1);

				System.out.println
				(
					"ReleaseBlock thread [TID=" + this.iTID + "] returns Ms block " + this.cBlock +
					" to position " + (soStack.getITop() + 1) + "."
				);

				soStack.push(this.cBlock);

				System.out.println
				(
					"Rel[TID=" + this.iTID + "]: Current value of top = " +
					soStack.getITop() + "."
				);

				System.out.println
				(
					"Rel[TID=" + this.iTID + "]: Current value of stack top = " +
					soStack.pick() + "."
				);
			}
			catch (CustomException e) 
			{
				reportException(e);
			}
			catch(Exception e)
			{
				reportException(e);
				System.exit(1);
			}
			finally {
				mutex.V();
			}
			s1.V();
			s1.P();
			phase2();
			s1.V();
			System.out.println("ReleaseBlock thread [TID=" + this.iTID + "] terminates.");
		}
	}

	static class CharStackProber extends BaseThread
	{
		public void run()
		{
			mutex.P();
			phase1();
			try
			{
				for(int i = 0; i < siThreadSteps; i++)
				{
					System.out.print("Stack Prober [TID=" + this.iTID + "]: Stack state: ");
					for(int s = 0; s < soStack.getISize(); s++)
						System.out.print
						(
							(s == BlockManager.soStack.getITop() ? "(" : "[") +
							BlockManager.soStack.getAt(s) +
							(s == BlockManager.soStack.getITop() ? ")" : "]")
						);
					System.out.println(".");
				}
			}
			catch(Exception e)
			{
				reportException(e);
				System.exit(1);
			}
			finally {
				mutex.V();
			}
			s1.V();
			s1.P();
			phase2();
			s1.V();
		}
	}

	private static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}
