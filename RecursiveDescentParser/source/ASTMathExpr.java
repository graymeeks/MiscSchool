
public class ASTMathExpr extends ASTExpression
{
	ASTSimple simple;
	ASTExpression expression;
	Token token;
	
	public ASTMathExpr(ASTSimple s, ASTExpression e, Token t)
	{
		simple = s;
		expression = e;
		token = t;
	}
	
	public String toString()
	{
		String ans = simple.toString() + " " + token.string + " " + expression.toString();
		return ans;
	}
	
	public void convertToJava()
	{
		simple.convertToJava();
		System.out.print(" " + token.string + " ");
		expression.convertToJava();
		return;
	}
}
