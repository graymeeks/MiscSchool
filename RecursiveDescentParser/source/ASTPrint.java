public class ASTPrint extends ASTStatement
{
	ASTExpression expr;
	
	public ASTPrint(ASTExpression e)
	{
		expr = e;
	}
	
	public String toString()
	{
		String ans = "PRINT ";
		ans += expr.toString();
		ans += "\n"; //NEW
		return ans;
	}
	
	//TODO: CONVERT TO JAVA
	public void convertToJava()
	{
		printIndent();
		System.out.print("System.out.println(");
		expr.convertToJava();
		System.out.print(");\n");
		return;
	}
}
