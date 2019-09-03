package io.github.sskorol.reflection.testcases;

public class LoginPage {

    @Find(by = By.CSS, locator = "[data-qa=username]", name = "username input")
    private String inputUsername;
}
