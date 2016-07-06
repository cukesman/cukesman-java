package cukesman.jbehave.step;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class GrocerySteps {

    @Given("I go to the bakery")
    public void givenIGoToTheBakery() {
    }

    @When("I buy $count croissant")
    public void whenIBuyCroissant(int count) {
    }

    @Then("I fail to pay")
    public void thenIFailToPay() {
        throw new IllegalStateException("Failing step.");
    }

}
