package cl.bcs.application.file.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author Narveider
 *
 */

public class UtilesSelenium {
	private static final Logger LOGGER = Log4jFactory.getLogger(UtilesSelenium.class);

	/**
	 * 
	 * @param tag
	 * @param xpath
	 */
	public static void moveToElement(WebDriver D, String tag, String xpath) {
		try {
			WebElement webElement = D.findElement(By.xpath("//" + tag + "[text()='" + xpath + "']"));
			Coordinates coordinates = ((Locatable) webElement).getCoordinates();
			coordinates.inViewPort();
			waitForLoad(D);
		} catch (Exception ex) {
			LOGGER.error(ex);
		}
	}

	public static void scrollDownBytext(WebDriver D, String tag, String xpath) {
		WebElement element = D.findElement(By.xpath("//" + tag + "[text()='" + xpath + "']"));
		((JavascriptExecutor) D).executeScript("arguments[0].scrollIntoView();", element);
		waitForLoad(D);
	}

	/**
	 * 
	 * @param tag
	 * @param xpath
	 * @throws Exception
	 */
	public static void scrollDown(WebDriver D, String tag, String xpath) {
		WebElement element = D.findElement(By.xpath("//" + tag + "[@class='" + xpath + "']"));
		((JavascriptExecutor) D).executeScript("arguments[0].scrollIntoView();", element);
		waitForLoad(D);
	}

	/**
	 * 
	 * @return
	 */
	public static String getDateFormatt() {
		Date date = new Date();
		SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
		return parser.format(date);
	}

	/**
	 * 
	 * @param element
	 */
	public static void moveByElement(WebElement element) {
		try {
			Coordinates cordinates = ((Locatable) element).getCoordinates();
			cordinates.inViewPort();
		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

	/**
	 * 
	 * @param listaIonic
	 * @param tagName
	 * @param key
	 * @return
	 */
	public static WebElement getElementoFromIonicItem(List<WebElement> listaIonic, String tagName, String key) {
		WebElement elementoEncontrado = null;
		for (WebElement elemento : listaIonic) {
			List<WebElement> divElementos = elemento.findElements(By.tagName(tagName));
			for (WebElement elementos : divElementos) {
				if (key.equals(elementos.getText())) {
					elementoEncontrado = elementos;
				}
			}
		}
		return elementoEncontrado;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */

	public static WebElement findElementById(WebDriver D, By id) {
		return D.findElement(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static List<WebElement> findElementsById(WebDriver D, By id) {
		return D.findElements(id);
	}

	/**
	 * 
	 * @param tag
	 * @return
	 */
	public static List<WebElement> findElementsByTag(WebDriver D, By tag) {
		return D.findElements(tag);
	}

	/**
	 * 
	 * @param tag
	 * @return
	 */

	public static List<WebElement> getElementsByClassName(WebDriver D, String tag) {
		return D.findElements(By.className(tag));
	}

	/**
	 * 
	 * @param findElements
	 * @param tag
	 * @return
	 */
	public static WebElement getWebElementByTag(List<WebElement> findElements, String tag) {
		WebElement elementoWeb = null;
		for (int i = 0; i < findElements.size(); i++) {
			WebElement elemento = findElements.get(i).findElement(By.className(tag));
			if (elemento.isDisplayed()) {
				elementoWeb = elemento;
			}
		}
		return elementoWeb;
	}

	/**
	 * 
	 * @param D
	 * @param by
	 * @return
	 */
	public static WebElement findElement(WebDriver D, By by) {
		try {
			waitForLoad(D, 2000);
			return D.findElement(by);
		} catch (NoSuchElementException no) {
			LOGGER.error(no.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param D
	 */
	public static void waitForLoad(WebDriver D) {
		ExpectedCondition<Boolean> expectation = expectedCondition();
		try {
			Thread.sleep(Integer.parseInt(Resource.getProperty("select.config.time")));

			WebDriverWait wait = new WebDriverWait(D, 30);
			wait.until(expectation);
		} catch (Exception error) {
			LOGGER.error(error);
			if (error instanceof WebDriverException) {
				D.switchTo().defaultContent();
				waitForLoad(D);
			}
		}
	}
	public static void waitForLoadMid(WebDriver D) {
		ExpectedCondition<Boolean> expectation = expectedCondition();
		try {
			Thread.sleep(Integer.parseInt(Resource.getProperty("select.config.time.mid")));

			WebDriverWait wait = new WebDriverWait(D, 30);
			wait.until(expectation);
		} catch (Exception error) {
			LOGGER.error(error);
			if (error instanceof WebDriverException) {
				D.switchTo().defaultContent();
				waitForLoad(D);
			}
		}
	}
	public static void waitForLoadLong(WebDriver D) {
		ExpectedCondition<Boolean> expectation = expectedCondition();
		try {
			Thread.sleep(Integer.parseInt(Resource.getProperty("select.config.time.long")));

			WebDriverWait wait = new WebDriverWait(D, 30);
			wait.until(expectation);
		} catch (Exception error) {
			LOGGER.error(error);
			if (error instanceof WebDriverException) {
				D.switchTo().defaultContent();
				waitForLoad(D);
			}
		}
	}

	/**
	 * 
	 * @param D
	 */
	public void refresh(WebDriver D) {
		if (D != null) {
			D.navigate().refresh();
			waitForLoad(D);
		}
	}

	/**
	 * 
	 * @param D
	 * @param time
	 */
	public static void waitForLoad(WebDriver D, int time) {
		ExpectedCondition<Boolean> expectation = expectedCondition();
		try {
			Thread.sleep(time);

			WebDriverWait wait = new WebDriverWait(D, 30);
			wait.until(expectation);
		} catch (Exception error) {
			LOGGER.error(error);
			if (error instanceof WebDriverException) {
				D.switchTo().defaultContent();
				waitForLoad(D);
			}
		}
	}

	/**
	 * 
	 * @param D
	 */
	public static void openPage(WebDriver D) {
		String URL = Resource.getProperty("url.optimus");
		LOGGER.debug("Redireccionando a URL --> " + URL);
		try {
			D.get(URL);
		} catch (Exception e) {
			LOGGER.error("Error al Redireccionar la URL, " + e.getStackTrace());
		}
	}

	/**
	 * 
	 * @return
	 */
	private static ExpectedCondition<Boolean> expectedCondition() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return expectation;
	}
	
	/**
	 * 
	 * @param D
	 */
	public void destroyDriver(WebDriver D) {
			D.quit();
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String validaData(String data) {
		if (data == null) {
			LOGGER.error("Campo Nulo");
			return null;
		}
		if (data.trim().isEmpty()) {
			LOGGER.error("Campo Vacio");
			return null;
		}
		return data;
		
	}
	/**
	 * 
	 * @param elementBig
	 * @param xpathInput
	 * @return
	 */
	public static WebElement findInputNumber(String elementBig,String xpathInput,WebDriver webDriver) {
		UtilesSelenium.findElement(webDriver, By.xpath(elementBig))
		.click();
		new WebDriverWait((WebDriver) webDriver, 10).until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(xpathInput)));
		return UtilesSelenium.findElement(webDriver, By.xpath(xpathInput));
	}
	/**
	 * 
	 * @param element
	 * @return
	 */
	public static WebElement findInputText(String element,WebDriver webDriver) {
		new WebDriverWait((WebDriver) webDriver, 10).until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath(element)));
		return UtilesSelenium.findElement(webDriver, By.xpath(element));
	}
}
