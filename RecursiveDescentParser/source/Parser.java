import java.util.ArrayList;

public class Parser extends Object
{

	private Chario chario;
	private Scanner scanner;
	private Token token;
	

	public Parser(Chario c, Scanner s)
	{
		chario = c;
		scanner = s;
		this.token = scanner.nextToken();
	}

	public void reset()
	{
		scanner.reset();
		this.token = scanner.nextToken();
	}

	private void accept(int expected, String errorMessage)
	{
		if (this.token.code != expected)
			fatalError(errorMessage);
		this.token = scanner.nextToken();
	}

	private void fatalError(String errorMessage)
	{
		chario.putError(errorMessage);
		throw new RuntimeException("Fatal error");
	}

	public void parse()
	{
		ASTProgram program = statement_list();
		//System.out.println(program);
		program.convertToJava();
	}

	/*---------------------------------------------------------------
	  statement_list ::= statement { statement }
	----------------------------------------------------------------*/
	private ASTProgram statement_list()
	{
		ArrayList<ASTStatement> programList = new ArrayList<ASTStatement>();
		ASTStatement first = statement();
		programList.add(first);
		while(this.token.code != Token.EOF)
		{
			if (this.token.code == Token.ENDIF || this.token.code == Token.UNTIL)
			{
				return new ASTProgram(programList);
			}
			ASTStatement curr = statement();
			programList.add(curr);
		}
		accept(Token.EOF, "extra symbols after the logical end of the program");
		return new ASTProgram(programList);
	}

	/*---------------------------------------------------------------
	  statement ->  var-decl
					print-statement
	          		if-statement 
		   			loop-statement
		   			assign-statement
	----------------------------------------------------------------*/
	private ASTStatement statement()
	{
		ASTStatement ans = null;
		if (this.token.code == Token.DECLARE)
		{
			ans = var_decl();
		}
		else if (this.token.code == Token.PRINT)
		{
			ans = print_statement();
		}
		else if (this.token.code == Token.IF)
		{
			ans = if_statement();
		}
		else if (this.token.code == Token.LOOP)
		{
			ans = loop_statement();
		}
		else if (this.token.code == Token.LET)
		{
			ans = assign_statement();
		}
		else
		{
			System.out.println(this.token.code);
			fatalError("Invalid statement");
		}
		return ans;
	}

	/*---------------------------------------------------------------
	  var-decl ::= DECLARE var AS type [= expression]
	----------------------------------------------------------------*/
	private ASTVarDecl var_decl()
	{
		accept(Token.DECLARE, "'DECLARE' expected");
		ASTVar v = new ASTVar(this.token);
		accept(Token.VAR, "VARIABLE expected");
		accept(Token.AS, "'AS' expected");
		// Language only has one type
		accept(Token.INTEGER, "'INTEGER' type expected");
		// Two return cases based on the grammar
		if (this.token.code == Token.ASSIGN)
		{
			accept(Token.ASSIGN, "'=' expected");
			ASTExpression e = expression();
			return new ASTVarDecl(v, e);
		}
		else
		{
			return new ASTVarDecl(v);
		}
	}

	/*---------------------------------------------------------------
	  print-statement ::= PRINT expression
	----------------------------------------------------------------*/
	private ASTPrint print_statement()
	{
		accept(Token.PRINT, "'PRINT' expected");
		ASTExpression e = expression();
		return new ASTPrint(e);
	}

	/*---------------------------------------------------------------------------
	  if-statement ::= IF condition THEN statement_list ENDIF
	-----------------------------------------------------------------------------*/
	private ASTIf if_statement()
	{
		accept(Token.IF, "'IF' expected");
		ASTCondition ifCondition = condition();
		accept(Token.THEN, "'THEN' expected");
		ASTProgram ifBody = statement_list();
		accept(Token.ENDIF, "'ENDIF' expected");
		return new ASTIf(ifCondition, ifBody);
	}
	
	/*-------------------------------------------------------------------
	  loop-statement ::= LOOP statement_list UNTIL condition
	---------------------------------------------------------------------*/
	private ASTLoop loop_statement()
	{
		accept(Token.LOOP, "'LOOP' expected");
		ASTProgram loopBody = statement_list();
		accept(Token.UNTIL, "'UNTIL' expected");
		ASTCondition loopCondition = condition();
		return new ASTLoop(loopBody, loopCondition);
	}

	/*---------------------------------------------------------------
	  assign-statement ::= LET var = expression
	----------------------------------------------------------------*/
	private ASTAssign assign_statement()
	{
		accept(Token.LET, "'LET' expected");
		ASTVar assnVar = new ASTVar(this.token);
		accept(Token.VAR, "VARIABLE expected");
		accept(Token.ASSIGN, "'=' expected");
		ASTExpression assnExpression = expression();
		return new ASTAssign(assnExpression, assnVar);
	}
	
	/*---------------------------------------------------------------
	  condition ::= expression relop expression
	----------------------------------------------------------------*/
	private ASTCondition condition()
	{
		ASTExpression a = expression();
		ASTRelop op = relop();
		ASTExpression b = expression();
		return new ASTCondition(a, op, b);
	}
	
	/*-----------------------------------------------------------------------------------
	  expression ::= simple-expression | simple-expression ( + | - | * | / ) expression 
	------------------------------------------------------------------------------------*/
	private ASTExpression expression()
	{
		ASTSimple simple = simple_expression();
		Token temp;
		// Switch case to handle 
		switch(this.token.code)
		{
		case Token.PLUS:
			temp = this.token;
			accept(Token.PLUS, "'+' expected");
			break;
		case Token.MINUS:
			temp = this.token;
			accept(Token.MINUS, "'-' expcted");
			break;
		case Token.TIMES:
			temp = this.token;
			accept(Token.TIMES, "'*' expected");
			break;
		case Token.DIV:
			temp = this.token;
			accept(Token.DIV, "'/' expected");
			break;
		case Token.MOD:
			temp = this.token;
			accept(Token.MOD, "'%' expected");
			break;
		default:
			return simple;
		}
		ASTExpression expr = expression();
		return new ASTMathExpr(simple, expr, temp);
	}

	/*---------------------------------------------------------------
	  simple-expression ::= var | number
	----------------------------------------------------------------*/
	private ASTSimple simple_expression()
	{
		ASTSimple ans = null;
		if (this.token.code == Token.VAR)
		{
			ans = new ASTVar(this.token);
			accept(Token.VAR, "VARIABLE expected");
		}
		else
		{
			ans = new ASTNum(this.token);
			accept(Token.NUMBER, "NUMBER expected");
		}
		return ans;
	}
	
	/*---------------------------------------------------------------
	  relop ::= < [>|=] | > [ = ] | = =
	----------------------------------------------------------------*/
	private ASTRelop relop()
	{
		ASTRelop ans = new ASTRelop(this.token);
		switch(this.token.code)
		{
		case Token.NOTEQ:
			accept(Token.NOTEQ, "'<>' expected");
			break;
		case Token.EQ:
			accept(Token.EQ, "'==' expected");
			break;
		case Token.LT:
			accept(Token.LT, "'<' expected");
			break;
		case Token.LTE:
			accept(Token.LTE, "'<= expected");
			break;
		case Token.GT:
			accept(Token.GT, "'>' expected");
			break;
		case Token.GTE:
			accept(Token.GTE, "'>=' expected");
			break;
		default:
			fatalError("Invalid relop");
		}
		return ans;
	}
	
	

}
