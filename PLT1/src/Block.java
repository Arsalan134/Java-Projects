
import java.util.Arrays;
import java.util.List;

public class Block extends Node implements Statement {
    public final List<Statement> stmnt;

    public Block(List<Statement> statements) {
        this.stmnt = statements;
    }
    
    public Block(Statement statements) {
        this(Arrays.asList(statements));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Block) {
            Block that = (Block)object;
            return this.stmnt.equals(that.stmnt);
        } else {
            return false;
        }
    }

  
    
}