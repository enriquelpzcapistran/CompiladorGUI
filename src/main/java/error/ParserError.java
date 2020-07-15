package error;

import controlador.Controller;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ParserError extends BaseErrorListener
{
    private TextArea textArea;
    public ParserError(TextArea textArea)
    {
        this.textArea = textArea;
    }

    @Override public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
    	int linea = line-1;
        Platform.runLater(() -> textArea.appendText("Linea : " + linea + " -> " + msg + " \n"));
        Controller.parserError = true;
    }
}
