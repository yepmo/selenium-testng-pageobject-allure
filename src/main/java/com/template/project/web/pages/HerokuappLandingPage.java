package com.template.project.web.pages;

import static com.template.project.common.Logger.logInfo;
import static com.template.project.web.utils.SeleniumUtils.assertElementPresent;
import static com.template.project.web.utils.SeleniumUtils.clickElement;
import static com.template.project.web.utils.SeleniumUtils.getElementText;
import static com.template.project.web.utils.Waiters.waitUntilElementVisible;

import com.template.project.web.locators.HerukuappLandingPageLocator;

public class HerokuappLandingPage extends HerukuappLandingPageLocator {

  public void navigateToSubPage(String subPageName) {
    final String subPageLink = "//li/a[contains(text(),'" + subPageName + "')]";
    assertElementPresent(subPageLink);
    clickElement(subPageLink);
  }

  public void verifyIfPageHeaderIsDisplayed() {
    waitUntilElementVisible(PAGE_HEADER);
    logInfo("Page header:" + getElementText(PAGE_HEADER) + " is displayed");
    waitUntilElementVisible(PAGE_SUB_HEADER);
    logInfo("Sub header:" + getElementText(PAGE_SUB_HEADER) + " is displayed");
  }
}
