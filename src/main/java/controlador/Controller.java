package controlador;

import listeners.SemanticListener;
//import abs.CodeGen;
//import abs.SymbolTable;
import abs.newToken;
import antlr.MiniJavaLexer;
import antlr.MiniJavaParser;
import entidades.Identifier;
//import abs.SymbolTableModel;
import error.LexerError;
import error.ParserError;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller
{
    @FXML TextArea text;
    @FXML TableView<newToken> tokenView;
    @FXML TableColumn<newToken, String> tLexeme;
    @FXML TableColumn<newToken, String> tClass;
    
    @FXML TableView<String> tablaSimbolosView;
    @FXML TableColumn<String, String> sNombre;
    @FXML TableColumn<ArrayList<String>, String> sTipo;
    @FXML TableColumn<ArrayList<String>, String> sValor;
    @FXML TableColumn<ArrayList<String>, String> sPosicion;
    @FXML TableColumn<ArrayList<String>, String> sAlcance;
    
    //@FXML TableView<CodeGen> codeTable;
    //@FXML TableColumn<CodeGen, String> op;
    //@FXML TableColumn<CodeGen, String> src1;
    //@FXML TableColumn<CodeGen, String> src2;
    //@FXML TableColumn<CodeGen, String> dest;

    @FXML Button runButton;
    @FXML TextArea status;
    
    public static SemanticListener semLis;

    public static boolean lexerError  = false;
    public static boolean parserError = false;


	private ArrayList<Identifier> id;
	private ArrayList<String> mo = new ArrayList<String>();
	
	
	
    public static ArrayList<String> variables = new ArrayList<>();
    public static ArrayList<String> methodId = new ArrayList<>();
    public static ArrayList<String> classId = new ArrayList<>();
    public static HashMap<String, String> map = new HashMap<>();
    public static ArrayList<String> value = new ArrayList<>();
    public static ArrayList<String> types = new ArrayList<>();
    public static ArrayList<String> pos = new ArrayList<>();
    
    public static ArrayList<ArrayList<String>> TablaSim = new ArrayList<>();
    //public static ArrayList<CodeGen> codeGens = new ArrayList<>();

    public void initialize()
    {
    	
    	System.out.println(variables.toString());
        tokenView.setPlaceholder(new Label("Click en Ejecutar"));
        tLexeme.setCellValueFactory(new PropertyValueFactory<>("tok"));
        tClass.setCellValueFactory(new PropertyValueFactory<>("cl"));

        tablaSimbolosView.setPlaceholder(new Label("Click en Ejecutar"));
        sNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        sTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        sValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        sPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        sAlcance.setCellValueFactory(new PropertyValueFactory<>("alcance"));
        //codeTable.setPlaceholder(new Label("Click en Ejecutar"));
        //op.setCellValueFactory(new PropertyValueFactory<>("op"));
        //src1.setCellValueFactory(new PropertyValueFactory<>("src1"));
        //src2.setCellValueFactory(new PropertyValueFactory<>("src2"));
        //dest.setCellValueFactory(new PropertyValueFactory<>("dest"));

    }

    @FXML public void clean()
    {
        tokenView.getItems().clear();
        tokenView.refresh();
        //codeTable.getItems().clear();
        //codeTable.refresh();
        tablaSimbolosView.getItems().clear();
        tablaSimbolosView.refresh();
        status.clear();

        Controller.variables.clear();
        Controller.methodId.clear();
        Controller.classId.clear();
        Controller.map.clear();
        Controller.TablaSim.clear();
        //Controller.codeGens.clear();
        //Controller.types.clear();

        Controller.lexerError  = false;
        Controller.parserError = false;

    }

    @FXML public void run()
    {
        clean();

        Controller.lexerError  = false;
        Controller.parserError = false;

        runButton.setDisable(true);
        tokenView.getItems().clear();
        tokenView.refresh();
        //codeTable.getItems().clear();
        //codeTable.refresh();
        tablaSimbolosView.getItems().clear();
        tablaSimbolosView.refresh();
        status.clear();

        CharStream inputStream = CharStreams.fromString(text.getText());
        MiniJavaLexer lexer = new MiniJavaLexer(inputStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new LexerError(status));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        tokenStream.fill();

        for (Token token : tokenStream.getTokens())
        {
            
            newToken newToken = new newToken(token.getText(), lexer.getVocabulary().getSymbolicName(token.getType()));
            if (newToken.getCl().equals("ERROR"))
            {
                status.appendText("Línea : " + token.getLine() + " -> " + newToken.getTok() + " no es un ID. \n");
                Controller.lexerError = true;
            }
            else
                tokenView.getItems().add(newToken);
        }

        if(!lexerError)
        {

            status.setText("Análisis Léxico terminado exitosamente!\n");

            MiniJavaParser parser = new MiniJavaParser(tokenStream);
            parser.removeErrorListeners();
            parser.addErrorListener(new ParserError(status));
            parser.addParseListener(new SemanticListener(parser));
            ParserRuleContext tree = parser.minijava();
            if (!parserError)
            {
                status.appendText("Parsing y Análisis Semántico terminado exitosamente!\n");

                //ParseTreeWalker treeWalker = new ParseTreeWalker();
                //treeWalker.walk(new CodeGeneratorListener(), tree);

                //status.appendText("Generación de Código terminado exitosamente!\n");
                //System.out.print(text.getText());
            	//new MainGUI(tokenStream.getText());
                
                status.appendText("--------------------------------------"
                		+ "\nGenerando Tabla de Simbolos\n--------------------------------------\n");
                status.appendText("Simbolo:\n");
                status.appendText("Nombre -> "+variables.toString());
                status.appendText("\nTipo -> "+types.toString());
                status.appendText("\nValor -> [1, 10, false] "+ value.toString());
                status.appendText("\nPosicion -> "+ pos.toString());
                status.appendText("\nAlcance Metodo -> "+methodId.toString());
                status.appendText("\nAlcance Clase -> "+classId.toString());
            	System.out.println(variables.toString());
            	System.out.println(methodId.toString());
            	System.out.println(classId.toString());
            	System.out.println(map.toString().charAt(1));
            	//sNombre.setCellValueFactory(new PropertyValueFactory<>(variables.get(0)));
                //tablaSimbolosView.getColumns().add(variables.toString());
                //codeTable.getItems().addAll(codeGens);

                //new Thread(() -> Trees.inspect(tree, parser)).start();
            }

        }

        runButton.setDisable(false);
    }
}