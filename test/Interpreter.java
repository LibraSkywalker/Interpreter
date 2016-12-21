import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import simpl.interpreter.InitialState;
import simpl.interpreter.RuntimeError;
import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.Expr;
import simpl.typing.DefaultTypeEnv;
import simpl.typing.TypeError;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class Interpreter {

    private String filename;

    public Interpreter(String filename) {
        this.filename = filename;
    }

    @Test
    public void run() throws IOException{
        System.out.println(filename);
        try (InputStream inp = new FileInputStream(filename)) {
            Parser parser = new Parser(inp);
            java_cup.runtime.Symbol parseTree = parser.parse();
            Expr program = (Expr) parseTree.value;
            System.out.println(program);
            System.out.println(program.typecheck(new DefaultTypeEnv()).t);
            System.out.println(program.eval(new InitialState()));
        }
        catch (SyntaxError e) {
            System.out.println("syntax error");
        }
        catch (TypeError e) {
            System.out.println("type error");
            e.printStackTrace();
            fail();
        }
        catch (RuntimeError e) {
            System.out.println("runtime error");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        Collection<Object[]> params = new ArrayList<>();
        for (File f : new File("doc/examples/").listFiles()) {
            if (f.isFile() && f.getName().endsWith(".spl")) {
                params.add(new Object[] { "doc/examples/" + f.getName()});
            }
        }
        return params;
    }
}
