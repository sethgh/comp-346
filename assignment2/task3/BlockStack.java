package task3;

import common.CustomException;

class BlockStack
{
	public static final int MAX_SIZE = 28;
	public static final int DEFAULT_SIZE = 6;
	private int iSize = DEFAULT_SIZE;
	private int iTop  = 3;
	public int stackAccessCounter = 0;
	private char acStack[] = new char[] {'a', 'b', 'c', 'd', '$', '$'};

	public BlockStack()
	{
	}

	public BlockStack(final int piSize)
	{
        if(piSize != DEFAULT_SIZE)
		{
			this.acStack = new char[piSize];
			for(int i = 0; i < piSize - 2; i++)
				this.acStack[i] = (char)('a' + i);
			this.acStack[piSize - 2] = this.acStack[piSize - 1] = '$';
			this.iTop = piSize - 3;
            this.iSize = piSize;
		}
	}

	public char pick() throws CustomException
	{
		this.stackAccessCounter++;
		if (this.isEmpty()==false) {
			return this.acStack[this.iTop];
		} else {
			throw new CustomException("Error. Stack is empty.");
		}
		
	}

	public char getAt(final int piPosition) throws CustomException
	{
		this.stackAccessCounter++;
		if (piPosition<=this.iSize) {
			return this.acStack[piPosition];
		} else {
			throw new CustomException("There is no value at the selected position.");
		}
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

	public void push(final char pcBlock) throws CustomException
	{
		this.stackAccessCounter++;
		if (this.iTop<this.iSize) {
			if (this.isEmpty()) {
				this.acStack[++this.iTop]='a';
			} else {
				this.acStack[++this.iTop] = pcBlock;
			}
		} else {
			throw new CustomException("Stack Error");
		}
	}

	public char pop() throws CustomException
	{
		if (this.isEmpty()==false) {
			this.stackAccessCounter++;
			char cBlock = this.acStack[this.iTop];
			this.acStack[this.iTop--] = '$';
			return cBlock;
		} else {
			throw new CustomException("Error. Stack is empty.");
		}

	}
	
	public boolean isEmpty()
	{
		return (this.iTop == -1);
	}
	
}
