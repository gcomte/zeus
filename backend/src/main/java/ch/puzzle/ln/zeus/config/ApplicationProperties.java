package ch.puzzle.ln.zeus.config;

import ch.puzzle.ln.zeus.domain.DailyOpeningHours;
import ch.puzzle.ln.zeus.domain.enums.MacaroonClass;
import liquibase.util.StringUtils;
import org.lightningj.lnd.wrapper.MacaroonContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

/**
 * Properties specific to Zeus.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationProperties.class);

    private String memoPrefix;
    private String currencyTicker;
    private Double taxMultiplier;

    private Lnd lnd = new Lnd();
    private Bitcoin bitcoin = new Bitcoin();
    private Mail mail = new Mail();
    private Twitter twitter = new Twitter();
    private Shop shop = new Shop();

    public String getMemoPrefix() {
        return memoPrefix;
    }

    public void setMemoPrefix(String memoPrefix) {
        this.memoPrefix = memoPrefix;
    }

    public String getCurrencyTicker() {
        return currencyTicker;
    }

    public void setCurrencyTicker(String currencyTicker) {
        this.currencyTicker = currencyTicker;
    }

    public Double getTaxMultiplier() {
        return taxMultiplier;
    }

    public void setTaxMultiplier(Double taxMultiplier) {
        this.taxMultiplier = taxMultiplier;
    }

    public Lnd getLnd() {
        return lnd;
    }

    public Bitcoin getBitcoin() {
        return bitcoin;
    }

    public Mail getMail() {
        return mail;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public Shop getShop() {
        return shop;
    }

    public static class Lnd {

        private String host;
        private int port;
        private String certPath;
        private String adminMacaroonHex;
        private String invoiceMacaroonHex;
        private String readonlyMacaroonHex;
        private Long invoiceExpirySeconds;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getCertPath() {
            return certPath;
        }

        public void setCertPath(String certPath) {
            this.certPath = certPath;
        }

        public String getAdminMacaroonHex() {
            return adminMacaroonHex;
        }

        public MacaroonContext getAdminMacaroonContext() {
            return () -> adminMacaroonHex;
        }

        public void setAdminMacaroonHex(String adminMacaroonHex) {
            this.adminMacaroonHex = adminMacaroonHex;
        }

        public String getInvoiceMacaroonHex() {
            return invoiceMacaroonHex;
        }

        public MacaroonContext getInvoiceMacaroonContext() {
            return () -> invoiceMacaroonHex;
        }

        public void setInvoiceMacaroonHex(String invoiceMacaroonHex) {
            this.invoiceMacaroonHex = invoiceMacaroonHex;
        }

        public String getReadonlyMacaroonHex() {
            return readonlyMacaroonHex;
        }

        public MacaroonContext getReadonlyMacaroonContext() {
            return () -> readonlyMacaroonHex;
        }

        public void setReadonlyMacaroonHex(String readonlyMacaroonHex) {
            this.readonlyMacaroonHex = readonlyMacaroonHex;
        }

        public List<MacaroonClass> getClassesOfProvidedMacaroon () {
            List<MacaroonClass> macaroonClasses = new ArrayList<>();

            if (!StringUtils.isEmpty(this.readonlyMacaroonHex)) {
                macaroonClasses.add(MacaroonClass.READONLY);
            }

            if (!StringUtils.isEmpty(this.invoiceMacaroonHex)) {
                macaroonClasses.add(MacaroonClass.INVOICE);
            }

            if (!StringUtils.isEmpty(this.adminMacaroonHex)) {
                macaroonClasses.add(MacaroonClass.ADMIN);
            }

            return macaroonClasses;
        }

        public Long getInvoiceExpirySeconds() {
            return invoiceExpirySeconds;
        }

        public void setInvoiceExpirySeconds(Long invoiceExpirySeconds) {
            this.invoiceExpirySeconds = invoiceExpirySeconds;
        }
    }

    public static class Bitcoin {

        private String tickerUrl;
        private String tickerBackupUrl;
        private Integer connectTimeout;
        private Integer readTimeout;
        private String restUrl;

        public String getTickerUrl() {
            return tickerUrl;
        }

        public URI getTickerUri() throws URISyntaxException {
            return new URI(tickerUrl);
        }

        public void setTickerUrl(String tickerUrl) {
            this.tickerUrl = tickerUrl;
        }

        public String getTickerBackupUrl() {
            return tickerUrl;
        }

        public URI getTickerBackupUri() throws URISyntaxException {
            return new URI(tickerBackupUrl);
        }

        public void setTickerBackupUrl(String tickerBackupUrl) {
            this.tickerBackupUrl = tickerBackupUrl;
        }

        public Integer getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(Integer connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public Integer getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(Integer readTimeout) {
            this.readTimeout = readTimeout;
        }

        public String getRestUrl() {
            return restUrl;
        }

        public URI getRestUri() throws URISyntaxException {
            return new URI(restUrl);
        }

        public void setRestUrl(String restUrl) {
            this.restUrl = restUrl;
        }
    }

    public static class Mail {

        private boolean processorEnabled = false;
        private boolean send = false;
        private String recipient = "";
        private String subject = "";
        private String paymentText = "";

        public boolean isProcessorEnabled() {
            return processorEnabled;
        }

        public void setProcessorEnabled(boolean processorEnabled) {
            this.processorEnabled = processorEnabled;
        }

        public boolean isSend() {
            return send;
        }

        public void setSend(boolean send) {
            this.send = send;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getPaymentText() {
            return paymentText;
        }

        public void setPaymentText(String paymentText) {
            this.paymentText = paymentText;
        }
    }

    public static class Twitter {

        private boolean processorEnabled = false;
        private String consumerKey;
        private String consumerSecret;
        private String accessToken;
        private String accessTokenSecret;

        public boolean isProcessorEnabled() {
            return processorEnabled;
        }

        public void setProcessorEnabled(boolean processorEnabled) {
            this.processorEnabled = processorEnabled;
        }

        public String getConsumerKey() {
            return consumerKey;
        }

        public void setConsumerKey(String consumerKey) {
            this.consumerKey = consumerKey;
        }

        public String getConsumerSecret() {
            return consumerSecret;
        }

        public void setConsumerSecret(String consumerSecret) {
            this.consumerSecret = consumerSecret;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessTokenSecret() {
            return accessTokenSecret;
        }

        public void setAccessTokenSecret(String accessTokenSecret) {
            this.accessTokenSecret = accessTokenSecret;
        }
    }

    public static class Shop {

        // if there are no opening hours set, open the shop always (run it as a 24/7 shop)
        private Map<DayOfWeek, Optional<DailyOpeningHours>> openingHours = OpeningHours.get_Map_24_7();
        private List<Product> products = new ArrayList<>();
        private List<PickupLocation> locations = new ArrayList<>();
        private boolean allowPickupDelay = false;

        public Map<DayOfWeek, Optional<DailyOpeningHours>> getWeeklyOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours.getMap();
        }

        public List<Product> getProducts() {
            return products;
        }

        public Optional<Product> findProductByKey(String key) {
            return products.stream().filter(product -> Objects.equals(product.getProductKey(), key)).findFirst();
        }

        public Product getProductByKey(String key) {
            return findProductByKey(key).orElseThrow(RuntimeException::new);
        }

        public List<PickupLocation> getLocations() {
            return locations;
        }

        public boolean isAllowPickupDelay() {
            return allowPickupDelay;
        }

        public void setAllowPickupDelay(boolean allowPickupDelay) {
            this.allowPickupDelay = allowPickupDelay;
        }
    }

    public static class OpeningHours {

        private WeekDay monday = new WeekDay();
        private WeekDay tuesday = new WeekDay();
        private WeekDay wednesday = new WeekDay();
        private WeekDay thursday = new WeekDay();
        private WeekDay friday = new WeekDay();
        private WeekDay saturday = new WeekDay();
        private WeekDay sunday = new WeekDay();

        // statically return opening hours for a shop that is always open (24/7)
        public static Map<DayOfWeek, Optional<DailyOpeningHours>> get_Map_24_7() {
            Map<DayOfWeek, Optional<DailyOpeningHours>> weekdays = new HashMap<>();

            Arrays.stream(DayOfWeek.values())
                .forEach(
                    day -> weekdays.put(day, Optional.of(getAllDayOpeningHours()))
                );

            return weekdays;
        }

        private static DailyOpeningHours getAllDayOpeningHours() {
            return new DailyOpeningHours(LocalTime.MIN, LocalTime.MAX);
        }

        public Map<DayOfWeek, Optional<DailyOpeningHours>> getMap() {
            Map<DayOfWeek, Optional<DailyOpeningHours>> weekdays = new HashMap<>();

            weekdays.put(DayOfWeek.MONDAY, monday.getOpeningHours());
            weekdays.put(DayOfWeek.TUESDAY, tuesday.getOpeningHours());
            weekdays.put(DayOfWeek.WEDNESDAY, wednesday.getOpeningHours());
            weekdays.put(DayOfWeek.THURSDAY, thursday.getOpeningHours());
            weekdays.put(DayOfWeek.FRIDAY, friday.getOpeningHours());
            weekdays.put(DayOfWeek.SATURDAY, saturday.getOpeningHours());
            weekdays.put(DayOfWeek.SUNDAY, sunday.getOpeningHours());

            return weekdays;
        }

        public void setMonday(WeekDay monday) {
            this.monday = monday;
        }

        public void setTuesday(WeekDay tuesday) {
            this.tuesday = tuesday;
        }

        public void setWednesday(WeekDay wednesday) {
            this.wednesday = wednesday;
        }

        public void setThursday(WeekDay thursday) {
            this.thursday = thursday;
        }

        public void setFriday(WeekDay friday) {
            this.friday = friday;
        }

        public void setSaturday(WeekDay saturday) {
            this.saturday = saturday;
        }

        public void setSunday(WeekDay sunday) {
            this.sunday = sunday;
        }
    }

    public static class WeekDay {

        private LocalTime open = null;
        private LocalTime close = null;

        public Optional<DailyOpeningHours> getOpeningHours() {
            if (open == null ^ close == null) {
                LOGGER.warn("The opening hours settings demand the usage of both the *open* and *close* value per day. Alternatively you may remove these settings from the config.");
            }
            if (open == null || close == null) {
                // If opening hours are not set, return the openingHours as *closed all day*
                return Optional.empty();
            } else {
                return Optional.of(new DailyOpeningHours(open, close));
            }
        }

        public void setOpen(String open) {
            this.open = LocalTime.parse(open);
        }

        public void setClose(String close) {
            this.close = LocalTime.parse(close);
        }
    }

    public static class Product {

        private String title;
        private String productKey;
        private Double price;
        private List<String> options = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public List<String> getOptions() {
            return options;
        }
    }

    private static class PickupLocation {

        private String name;
        private String key;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
