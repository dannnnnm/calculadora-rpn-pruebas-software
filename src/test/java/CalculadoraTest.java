import org.example.stack.RPNStack;
import org.example.stack.exceptions.PopOnEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

//Alumnos: Vicente Marquez
//         Daniel Aguayo


public class CalculadoraTest {

    RPNCalculator rpnCalculator;
    RPNStack stack;

    @BeforeEach
    void setup(){
        rpnCalculator = new RPNCalculator();
        stack = new RPNStack();
    }

    @AfterEach
    void teardown(){
        rpnCalculator = null;
        stack=null;
    }


    //Test agregados para revisar tamaño de números

    @Test
    void allowOperationsOnAlmostBigNumbers(){
        //Permite operaciones con numeros cercanos a los grandes siempre que el resultado sea menor al límite de 10 cifras
        String expression = "99999999 43 +";
        Assertions.assertDoesNotThrow(()->rpnCalculator.calculate(expression));
    }

    @Test
    void throwErrorOnBigResult(){
        //Si el resultado es más grande de 10 cifras, lanza error
        String expression= "99999999999 2 *";
        InvalidOperatorException exception = Assertions.assertThrows(InvalidOperatorException.class,
                ()->rpnCalculator.calculate(expression),
                "number too big");

        Assertions.assertTrue(exception.getMessage().contains("too big"));
    }

    @Test
    void considersDecimalNumberUpToLimit(){
        //Considera números decimales hasta un límite de seis dígitos decimales
        String expression="0.000001 2.5 +";
        CalcResult result=rpnCalculator.calculate(expression);

        Assertions.assertEquals(2.500001, result.inner());
    }

    @Test
    void discardsDigitsAfterLimit(){
        //descarta dígitos decimales después del sexto.
        String expression="0.2100001 2.5 +";
        CalcResult result=rpnCalculator.calculate(expression);

        Assertions.assertEquals(2.71, result.inner());
    }

    //Fin de tests nuevos


    @Test
    void simpleAdd(){
        String expression = "5 3 +";
        CalcResult result = rpnCalculator.calculate(expression);

        Assertions.assertEquals(result.inner(), 8);
    }

    @Test
    void simpleSubstract(){
        String expression = "10 4 -";
        CalcResult result = rpnCalculator.calculate(expression);

        Assertions.assertEquals(result.inner(), 6);
    }

    @Test
    void simpleMultiplication(){
        String expression = "6 2 *";
        CalcResult result = rpnCalculator.calculate(expression);

        Assertions.assertEquals(result.inner(),12);
    }

    @Test
    void simpleDivision(){
        String expression = "9 3 /";
        CalcResult result = rpnCalculator.calculate(expression);

        Assertions.assertEquals(result.inner(),3);
    }

    @Test
    void intResultDivision(){
        String expression = "8 3 /";
        CalcResult result = rpnCalculator.calculate(expression);

        Assertions.assertEquals(rpnCalculator.inner(), 2);
    }

    @Test
    void floatResultDivision(){
        String expression = "9.0 2 /";
        CalcResult result = rpnCalculator.calculate(expression);

        Assertions.assertEquals(rpnCalculator.inner(), 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"4 33 +", "5.5 3.1 -", "4.1 5 *", "4 0 +"})
    void randomOperationsVerification(String expression){
        CalcResult result = rpnCalculator.calculate(expression);
        CalcResult manualResult = manualCalculatorHelper();
        Assertions.assertEquals(manualResult.inner(), result.inner());
    }

    Result manualCalculatorHelper(String expression){
        //Calcular manualmente según la expresión!
        return null;
    }


    @Test
    void divisionByZero(){
        String expression = "4 0 /";
        DivideByZeroException exception = Assertions.assertThrows(DivideByZeroException.class,
                                            ()->rpnCalculator.calculate(expression),
                                            "Cannot divide by zero");

        Assertions.assertTrue(exception.getMessage().contains("by zero"));
    }

    @Test
    void invalidOperator(){
        // Profesor: considerar para "caracter extraño" Y "operación incompleta"
        String badExpression = "8 3 $";
        InvalidOperatorException exception = Assertions.assertThrows(InvalidOperatorException.class,
                                                ()->rpnCalculator.calculate(badExpression),
                                                "Bad operator");

        Assertions.assertTrue(exception.getMessage().contains("operator"));
    }



    @Test
    void checkTiming(){
        String expression = "12 3.5 + 7 2 - * 10 2.5 / 4 + 6 1.5 - * 8 4 / + 5.5 -";
        long startTime = System.currentTimeMillis();

        CalcResult result = rpnCalculator.calculate(expression);
        long stopTime = System.currentTimeMillis();
        long secondsElapsed = (stopTime-startTime)/1000;
        Assertions.assertTrue(secondsElapsed<5);
    }




    @Test
    void pushToStackIncreasesLength(){
        stack.push(3);

        Assertions.assertEquals(stack.length(),1);
    }

    @Test
    void randomPushesToStackIncreaseLength(){
        int amountOfPushes = new Random().nextInt(1, 30);
        for (int i = 0; i < amountOfPushes; i++) {
            stack.push(i);
        }
        Assertions.assertEquals(stack.length(), amountOfPushes);
    }

    @Test
    void popRetrievesPushedData(){
        int value=3;
        stack.push(value);
        Assertions.assertEquals(stack.pop(),value);
    }

    @Test
    void popReducesLength(){
        stack.push(56);
        stack.push(56);
        stack.pop();
        Assertions.assertEquals(stack.length(),1);
    }

    @Test
    void randomPushesAndPopsToStackIncreaseLength(){
        int amountOfPushes = new Random().nextInt(1, 30);
        int amountOfPops = new Random().nextInt(0,amountOfPushes);
        for (int i = 0; i < amountOfPushes; i++) {
            stack.push(i);
        }
        for (int i = 0; i < amountOfPops; i++) {
            stack.pop();
        }
        Assertions.assertEquals(stack.length(), amountOfPushes-amountOfPops);
    }

    @Test
    void poppingOnEmptyThrowsException(){
        PopOnEmptyException exception = Assertions.assertThrows(PopOnEmptyException.class, stack.pop(), "empty stack");

        Assertions.assertTrue(exception.getMessage().contains("empty stack"));
    }
}
