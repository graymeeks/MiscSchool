
public class ASTAssign extends ASTStatement
{
	ASTExpression expr;
	ASTVar var;
	
	public ASTAssign(ASTExpression e, ASTVar v)
	{
		expr = e;
		var = v;
	}
	
	public String toString()
	{
		String ans = "LET " + var.toString() + " = " + expr.toString() + "\n"; 
		return ans;
	}
	
	public void convertToJava()
	{
		printIndent();
		var.convertToJava();
		System.out.print(" = ");
		expr.convertToJava();
		System.out.print(";\n");
		return;
	}
}
