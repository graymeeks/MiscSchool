
public class ASTLoop extends ASTStatement 
{
	ASTProgram body;
	ASTCondition condition;
	
	public ASTLoop(ASTProgram b, ASTCondition c)
	{
		body = b;
		condition = c;
	}
	
	public String toString()
	{
		String ans = "LOOP\n" + body.toString() + "UNTIL " + condition.toString() + "\n";
		return ans;
	}
	
	//TODO: CONVERT TO JAVA
	public void convertToJava()
	{
		printIndent();
		System.out.print("do\n");
		printIndent();
		System.out.print("{\n");
		tabLevel += 4;
		body.convertToJava();
		tabLevel -= 4;
		printIndent();
		System.out.print("}\n");
		printIndent();
		System.out.print("while ");
		condition.convertToJava();
		System.out.print(";\n");
		return;
	}
}
