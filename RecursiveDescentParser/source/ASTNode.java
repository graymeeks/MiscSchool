public class ASTNode 
{
	protected static int tabLevel = 0;
	
	// Placeholder toString method
	public String toString()
	{
		return "INVALID TOSTRING CALL";
	}
	
	// Placeholder convertToJava method
	public void convertToJava()
	{
		System.out.println("INVALID CONVERT TO JAVA CALL");
		return;
	}
	
	public void printIndent()
	{
		for (int i=0; i<tabLevel; i++)
		{
			System.out.print(" ");
		}
	}
}
