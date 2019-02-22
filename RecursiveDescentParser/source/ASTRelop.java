
public class ASTRelop extends ASTNode
{
	Token token;
	
	public ASTRelop(Token t)
	{
		token = t;
	}
	
	public String toString()
	{
		return token.string;
	}
	
	//TODO
	public void convertToJava()
	{
		System.out.print(token.string);
		return;
	}
}
