package pl.wk.ml.linear_regression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

class LinearRegression {
    private static final int SCALE = 13;
    private static final BigDecimal FIRST_VAR_EXPECTATION = new BigDecimal("1.0").setScale(SCALE);

    private final Double[] parameters;

    LinearRegression(Double[] parameters) {
        this.parameters = Objects.requireNonNull(parameters);
    }

    double calculate(Double[] variablesVector) {
        double result = 0;
        for (int i = 0; i < variablesVector.length; i++) {
            result += variablesVector[i] * parameters[i];
        }
        return result;
    }

    void validateVector(Double[] variablesVector) {
        int parametersLength = parameters.length;
        if (variablesVector.length > parametersLength) {
            throw new IllegalArgumentException("Vector " + Arrays.toString(variablesVector) + " has too many elements. Only " + parametersLength + " elements were expected.");
        }

        if (variablesVector.length < parametersLength) {
            throw new IllegalArgumentException("Vector " + Arrays.toString(variablesVector) + " has too few elements. At least " + parametersLength + " elements were expected.");
        }

        if (!isFirstVectorVariableValid(variablesVector[0])) {
            throw new IllegalArgumentException("First argument of the variables vector should have value " + FIRST_VAR_EXPECTATION.setScale(1) + ". Value " + BigDecimal.valueOf(variablesVector[0]) + " was found instead.");
        }
    }

    boolean isFirstVectorVariableValid(Double firstElement) {
        BigDecimal scaledDownValue = BigDecimal.valueOf(firstElement).setScale(13, RoundingMode.HALF_UP);
        return scaledDownValue.equals(FIRST_VAR_EXPECTATION);
    }
}
