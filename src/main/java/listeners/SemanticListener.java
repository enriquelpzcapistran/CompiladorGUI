package listeners;

import antlr.MiniJavaBaseListener;
import antlr.MiniJavaParser;
import controlador.Controller;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class SemanticListener extends MiniJavaBaseListener
{
    private MiniJavaParser parser;
    public SemanticListener(MiniJavaParser parser)
    {
        this.parser = parser;
    }

    @Override public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx)
    {
        String id = ctx.Id().getText();
        if (Controller.classId.contains(id))
            parser.notifyErrorListeners("Clase duplicada : " + id);

        else
            Controller.classId.add(id);
    }

    @Override public void exitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx)
    {
        String id = ctx.Id().getText();

        if (Controller.variables.contains(id))
            parser.notifyErrorListeners("Variable duplicada : " + id);

        else
        {
            Controller.variables.add(id);
            Controller.map.put(id, ctx.declarators().type().getText());
        }

        if (ctx.expression() != null)
        {
            String id1 = ctx.Id().getText();
            String t1 = Controller.map.get(id1);

            String id2 = ctx.expression().reference().getText();
            String t2 = Controller.map.get(id2);

            if (t2 != null)
            {
                if (!t1.contains(t2))
                    parser.notifyErrorListeners("En variable -> "+ id1 +" -> No se puede asignar '" + t2 + "' a '" + t1 + "'");
            }

        }
        Controller.value.add(ctx.Id().getText());
    }

    @Override public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx)
    {
        String id = ctx.Id().getText();

        if (Controller.methodId.contains(id)){
            parser.notifyErrorListeners("Método duplicado : " + id);
        	Controller.methodId.add(id);
        	}
        else
            Controller.methodId.add(id);
    }

    @Override public void exitReference(MiniJavaParser.ReferenceContext ctx)
    {
        List<TerminalNode> ids = ctx.Id();
        for (TerminalNode terminalNode : ids)
        {
            String id = terminalNode.getText();
            if (!Controller.variables.contains(id))
                parser.notifyErrorListeners("Variable indefinida : " + id);
        }
    }

    @Override public void exitVarDeclaration(MiniJavaParser.VarDeclarationContext ctx)
    {

        String id = ctx.Id().getText();
        String posicion = ctx.Id().getSymbol().toString();
        posicion = posicion.substring(ctx.Id().getSymbol().toString().length() - 4);
        Controller.pos.add(posicion.substring(0,3));

        if (Controller.variables.contains(id))
            parser.notifyErrorListeners("Variable duplicada : " + id);
        else
        {
            Controller.variables.add(id);
            Controller.map.put(id, ctx.type().getText());
        }

        if (ctx.expression() != null)
        {
            String id1 = ctx.Id().getText();
            String t1 = Controller.map.get(id1);

            if (ctx.expression().reference() != null)
            {
                String id2 = ctx.expression().reference().getText();
                String t2 = Controller.map.get(id2);

                if (t2 == null)
                    parser.notifyErrorListeners("Variable indefinida : " + id2);

                else
                {
                    if (!t1.contains(t2))
                        parser.notifyErrorListeners("En variable -> "+ id1 +" -> No se puede asignar '" + t2 + "' a '" + t1 + "'");
                }
            }
            Controller.types.add(t1);
        }
        //Controller.value.add(Controller.map.get(ctx.getText()));
        //Controller.value.add(Controller.variables.);
    }

    @Override public void exitGoExp(MiniJavaParser.GoExpContext ctx)
    {
        if (Controller.variables.contains(ctx.reference().getText()) && ctx.expression().reference() != null)
        {
            String t1 = Controller.map.get(ctx.reference().getText());
            String id2 = ctx.expression().reference().getText();
            String t2 = Controller.map.get(id2);

            if (!t1.equals(t2))
                parser.notifyErrorListeners("En variable -> "+ id2 +" -> No se puede asignar '" + t2 + "' a '" + t1 + "'");
        }
        Controller.value.add(Controller.map.get(ctx.reference().getText()));
    }

}

