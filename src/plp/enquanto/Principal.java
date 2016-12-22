package plp.enquanto;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import plp.enquanto.parser.EnquantoLexer;
import plp.enquanto.parser.EnquantoParser;
import plp.enquanto.parser.MeuListener;
import static plp.enquanto.linguagem.Linguagem.*;
import static java.util.Arrays.*;

public class Principal {

	private static ParseTree parse(String programa) {
		final ANTLRInputStream input = new ANTLRInputStream(programa);
		final EnquantoLexer lexer = new EnquantoLexer(input);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final EnquantoParser parser = new EnquantoParser(tokens);
		return parser.programa();
	}

	public static void main(String... args) throws IOException {
//		String programa = "para x de 1 ate 6 faca se x <= 2 entao escreva x senaose (x >= 3 e x <=4) entao exiba \"3 ou 4\" senao exiba \"maior que 4\"";
		String programa = "n := leia;\n" +
                "" +
                "se n=0 entao exiba \"zero\"\n" +
                "senaose n = 1 entao exiba \"um\"\n" +
                "senaose n = 2 entao exiba \"dois\"\n" +
                "senaose n = 3 entao exiba \"tres\"\n" +
                "senao exiba \"quatro\"\n";
		final ParseTree tree = parse(programa);
		final ParseTreeWalker walker = new ParseTreeWalker();
		final MeuListener listener = new MeuListener();
		walker.walk(listener, tree);
		Programa p1 = listener.getPrograma();
		// O parser devolve um objeto 'Programa' semelhante ao programa a seguir:
		Programa p2 = new Programa(asList(
				new Atribuicao("x", new Inteiro(10)),                       // x := 10
				new Atribuicao("y", leia),                                  // y := leia
				new Atribuicao("c", new ExpSoma(new Id("x"), new Id("y"))) // c := x + y
//				new Se(
//                        new ExpMenorIgual(new Inteiro(30), new Id("c")),     // se 30 <= c entao
//						new Exiba("1. MAIOR QUE 30"),                           // escreva c
//						new Exiba("3. MENOR QUE 20"),									// senao exiba "menor"
//						new ExpMenorIgual(new Inteiro(20), new Id("c")),
//                        new Exiba("2. MAIOR QUE 20"))
        ));
		p1.execute();
//		p2.execute();
	}
}

/*
x := 10;
y := leia();

c := x + y;

se 30 <= c entao
    exiba "1. MAIOR QUE 30"
senaose 20 <= c
    exiba "2. MAIOR QUE 20"
senao
    exiba "3. MENOR QUE 20"

*/