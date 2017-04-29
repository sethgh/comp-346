package task1;

class BlockStack
{
	public static final int MAX_SIZE = 28;
	public static final int DEFAULT_SIZE = 6;
	public int iSize = DEFAULT_SIZE;
	public int iTop  = 3;
	
	public int stackAccessCounter = 0;
	public char acStack[] = new char[] {'a', 'b', 'c', 'd', '$', '$'};

	public BlockStack()
	{
	}

	public BlockStack(final int piSize)
	{


        if(piSize != DEFAULT_SIZE)
		{
			this.acStack = new char[piSize];
			for(int i = 0; i < piSize - 2; i++) {
				this.acStack[i] = (char)('a' + i);
			}
			this.acStack[piSize - 2] = this.acStack[piSize - 1] = '$';
			this.iTop = piSize - 3;
            this.iSize = piSize;
		}
	}

	public char pick()
	{
		this.stackAccessCounter++;
		return this.acStack[this.iTop];
	}

	public char getAt(final int piPosition)
	{
		this.stackAccessCounter++;
		return this.acStack[piPosition];
	}
	
	public int getITop() 
	{
		return this.iTop;
	}
	
	public int getISize() 
	{
		return this.iSize;
	}
	
	public int getAccessCounter() {
		return this.stackAccessCounter;
	}

	public void push(final char pcBlock)
	{
		this.stackAccessCounter++;
		this.acStack[++this.iTop] = pcBlock;
	}

	public char pop()
	{
		this.stackAccessCounter++;
		char cBlock = this.acStack[this.iTop];
		this.acStack[this.iTop--] = '$';
		return cBlock;
	}
	
	public boolean isEmpty()
	{
		return (this.iTop == -1);
	}
}

