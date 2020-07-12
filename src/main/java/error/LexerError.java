package error;

import controlador.Controller;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class LexerError extends BaseErrorListener
{
    private TextArea textArea;
    public LexerError(TextArea textArea)
    {
        this.textArea = textArea;
    }

    @Override public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
    {
        Platform.runLater(() -> textArea.appendText("Linea : " + line + " -> " + msg + "\n"));
        Controller.lexerError = true;
    }
}
