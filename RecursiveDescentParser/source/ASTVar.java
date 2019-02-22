
public class ASTVar extends ASTSimple
{
	Token token;
	
	public ASTVar(Token t)
	{
		token = t;
	}
	
	public String toString()
	{
		return token.string.toLowerCase();
	}
	
	public void convertToJava()
	{
		System.out.print(token.string.toLowerCase());
		return;
	}
}
