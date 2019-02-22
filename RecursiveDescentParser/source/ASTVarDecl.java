
public class ASTVarDecl extends ASTStatement
{
	
	ASTVar var;
	ASTExpression expr;
	
	public ASTVarDecl(ASTVar v)
	{
		var = v;
		expr = null;
	}
	
	public ASTVarDecl(ASTVar v, ASTExpression e)
	{
		var = v;
		expr = e;
	}

	public String toString()
	{
		String ans = "DECLARE ";
		ans += var.toString();
		if (expr == null)
		{
			ans += " AS INTEGER\n";
		}
		else
		{
			ans += " AS INTEGER = ";
			ans += expr.toString();
			ans += "\n";
		}
		return ans;
	}
	
	public void convertToJava()
	{
		printIndent();
		System.out.print("int " + var.toString());
		if (expr == null)
		{
			System.out.print(";\n");
		}
		else
		{
			System.out.print(" = ");
			expr.convertToJava();
			System.out.print(";\n");
		}
		return;
	}
	
}
