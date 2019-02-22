public class ASTCondition extends ASTNode
{
	ASTExpression expr1;
	ASTRelop op;
	ASTExpression expr2;
	
	public ASTCondition(ASTExpression x, ASTRelop r, ASTExpression y)
	{
		expr1 = x;
		op = r;
		expr2 = y;
	}
	
	public String toString()
	{
		String ans = expr1.toString() + " " + op.toString() + " " + expr2.toString();
		return ans;
	}
	
	// TODO: CONVERT TO JAVA
	public void convertToJava()
	{
		String operator;
		if(op.toString().equals("<>"))
		{
			operator = "!=";
		}
		else
		{
			operator = op.toString();
		}
		System.out.print("(");
		expr1.convertToJava();
		System.out.print(" " + operator + " ");
		expr2.convertToJava();
		System.out.print(")");
		return;
	}
}
