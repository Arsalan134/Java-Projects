
import java.util.Arrays;
import java.util.List;

public class CallFunction extends Node implements Expression {
	
	
    public final String calling_function_name;
    public final List<Expression> list_of_expressions;

    public CallFunction(String functionName, List<Expression> arguments) {
        this.calling_function_name = functionName;
        this.list_of_expressions = arguments;
    }
    
    public CallFunction(String functionName, Expression arguments) {
        this(functionName, Arrays.asList(arguments));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CallFunction) {
            CallFunction that = (CallFunction)obj;
            return
                    this.calling_function_name.equals(that.calling_function_name) &&
                    this.list_of_expressions.equals(that.list_of_expressions);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return calling_function_name + "( )";
    }
}