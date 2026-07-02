package stepdefinitions;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;

public class CasekaroSteps {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @Before
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @Given("I navigate to the Casekaro website")
    public void i_navigate_to_the_casekaro_website() {
        page.navigate("https://casekaro.com/"); // [cite: 1, 3]
    }

    @When("I click on {string} from the Top Navigation Menu")
    public void i_click_on_from_the_top_navigation_menu(String menuName) {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(menuName).setExact(true)).first().click(); // [cite: 4]
    }



    @Then("I validate that other brands are not visible in the search results")
    public void i_validate_that_other_brands_are_not_visible_in_the_search_results() {
        // Wait a brief moment to ensure the grid has finished filtering after typing "Apple"
        page.waitForTimeout(1500);

        // Target the specific model blocks (.ck-mn), filter by brand text, and check how many are currently visible
        int visibleSamsungCount = page.locator(".ck-mn")
                .filter(new Locator.FilterOptions().setHasText("Samsung"))
                .and(page.locator(":visible"))
                .count();

        int visibleOnePlusCount = page.locator(".ck-mn")
                .filter(new Locator.FilterOptions().setHasText("OnePlus"))
                .and(page.locator(":visible"))
                .count();

        // Assert that the count of visible Samsung and OnePlus blocks is exactly 0
        Assert.assertEquals("Negative Validation Failed: Samsung models are still visible", 0, visibleSamsungCount);
        Assert.assertEquals("Negative Validation Failed: OnePlus models are still visible", 0, visibleOnePlusCount);
    }



    @When("I click {string} on the First Product Card")
    public void i_click_on_the_first_product_card(String buttonText) {
        // [cite: 8]
        page.locator(".product-card").first().locator("button:has-text('" + buttonText + "')").click();
    }

    @When("I add the Hard, Soft, and Glass material variants to the cart")
    public void i_add_the_material_variants_to_the_cart() {
        // [cite: 9, 13]
        String[] materials = {"Hard", "Soft", "Glass"}; // [cite: 10, 11, 12]

        for (String material : materials) {
            // Select material variant
            page.getByText(material, new Page.GetByTextOptions().setExact(true)).click();

            // Click Add to Cart
            page.locator("button[name='add']").click();

            // Wait for cart drawer/confirmation to appear, then close it to add the next item
            page.waitForSelector(".cart-drawer");
            page.locator(".cart-drawer .close-button").click();
        }
    }

    @When("I open the cart")
    public void i_open_the_cart() {
        // [cite: 14]
        page.locator("a[href='/cart']").first().click();
    }



    @When("I search for {string} in the phone cases by model search box")
    public void i_search_for_in_the_phone_cases_by_model_search_box(String brand) {
        // Find the input that has 'model' in the placeholder AND is currently visible on screen
        Locator searchBox = page.locator("input[placeholder*='model' i]:visible").first();

        // Scroll to the "Phone cases by model" section
        searchBox.scrollIntoViewIfNeeded();

        // Click and fill
        searchBox.click();
        searchBox.fill(brand);

        // Wait a brief moment for search results to update
        page.waitForTimeout(2000);
    }

    @When("I search for {string} and select the exact match from the dropdown")
    public void i_search_for_and_select_the_exact_match_from_the_dropdown(String model) {
        // Again, explicitly target the visible search box
        Locator searchBox = page.locator("input[placeholder*='model' i]:visible").first();

        // Clear previous text and fill the new model
        searchBox.clear();
        searchBox.fill(model);

        // Wait for the dropdown results container to be visible
        page.waitForSelector(".predictive-search, .search-dropdown-results, [id^='predictive-search'], ul.search-results");

        // Select exact match, avoiding "Max"
        page.locator("a")
                .filter(new Locator.FilterOptions().setHasText("iPhone 16 Pro"))
                .filter(new Locator.FilterOptions().setHasNotText("Max"))
                .first().click();
    }



    @Then("I validate that all {int} items are added to the cart")
    public void i_validate_that_all_items_are_added_to_the_cart(Integer expectedCount) {
        // [cite: 15]
        page.waitForSelector(".cart-item");
        int actualCount = page.locator(".cart-item").count();
        Assert.assertEquals("Cart does not have the expected number of items", expectedCount.intValue(), actualCount);
    }

    @Then("I print the price, material, and link of all items in the console")
    public void i_print_the_details_of_all_items_in_the_console() {
        // [cite: 16]
        Locator cartItems = page.locator(".cart-item");
        int count = cartItems.count();

        System.out.println("---- Cart Item Details ----");
        for (int i = 0; i < count; i++) {
            Locator item = cartItems.nth(i);

            String material = item.locator(".item-material").innerText(); // [cite: 17]
            String price = item.locator(".item-price").innerText(); // [cite: 18]
            String link = item.locator("a.item-link").getAttribute("href"); // [cite: 19]

            System.out.println("Item " + (i + 1) + ":");
            System.out.println("Material: " + material);
            System.out.println("Price: " + price);
            System.out.println("Link: https://casekaro.com" + link);
            System.out.println("---------------------------");
        }
    }

    @After
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}