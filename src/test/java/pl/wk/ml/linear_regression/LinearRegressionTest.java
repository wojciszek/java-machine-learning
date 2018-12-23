package pl.wk.ml.linear_regression;

import org.assertj.core.data.Percentage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinearRegressionTest {

    private LinearRegression linearRegression;

    @BeforeMethod
    public void setUp() {
        linearRegression = new LinearRegression(new Double[]{1.0, 2.0, 3.0});
    }

    @Test
    public void shouldCalculateValueFromVector() {
        assertThat(linearRegression.calculate(new Double[]{2.0, 1.0, 3.0})).isCloseTo(13.0, Percentage.withPercentage(0.001));
    }

    @DataProvider
    Object[][] shouldValidateFirstElementDataProvider() {
        return new Object[][]{
                {1.1, false},
                {2.0, false},
                {1.0000000000001, false},
                {1.000000000000001, true},
                {1.0, true},
        };
    }

    @Test(dataProvider = "shouldValidateFirstElementDataProvider")
    public void shouldValidateFirstElement(Double firstElement, boolean isValidExpectation) {
        assertThat(linearRegression.isFirstVectorVariableValid(firstElement)).isEqualTo(isValidExpectation);
    }


    @Test
    public void shouldThrowExceptionForTooManyVectorElements() {
        // given:
        Double[] variablesVector = {1.0, 1.0, 3.0, 4.0};

        // when:
        assertThatThrownBy(() -> linearRegression.validateVector(variablesVector))
                .hasMessage("Vector [1.0, 1.0, 3.0, 4.0] has too many elements. Only 3 elements were expected.")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionForTooFewVectorElements() {
        // given:
        Double[] variablesVector = {1.0, 2.0};

        // when:
        assertThatThrownBy(() -> linearRegression.validateVector(variablesVector))
                .hasMessage("Vector [1.0, 2.0] has too few elements. At least 3 elements were expected.")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowExceptionWhenFirstArgumentIsNotValid() {
        // given:
        LinearRegression linearRegression = new LinearRegression(new Double[]{1.0, 1.0});

        // then:
        assertThatThrownBy(() -> linearRegression.validateVector(new Double[]{1.001, 1.0}))
                .hasMessage("First argument of the variables vector should have value 1.0. Value 1.001 was found instead.")
                .isInstanceOf(IllegalArgumentException.class);
    }
}