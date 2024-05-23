import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {
    @Test
    public void testMessageSenderImplRussia() {
        GeoServiceImpl geo = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geo.byIp("172.0.32.11"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationServiceImpl loc = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(loc.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Map<String, String> headers = new HashMap<>();
        headers.put("x-real-ip", "172.0.32.11");
        String actual = new MessageSenderImpl(geo, loc).send(headers);
        Assertions.assertEquals(actual, "Добро пожаловать");
    }

    @Test
    public void testMessageSenderImplUSA() {
        GeoServiceImpl geo = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geo.byIp("96.44.183.149"))
                .thenReturn(new Location("New York", Country.USA, null,  0));
        LocalizationServiceImpl loc = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(loc.locale(Country.USA)).thenReturn("Welcome");
        Map<String, String> headers = new HashMap<>();
        headers.put("x-real-ip", "96.44.183.149");
        String actual = new MessageSenderImpl(geo, loc).send(headers);
        Assertions.assertEquals(actual, "Welcome");
    }

    @Test
    public void testGeoServiceImpl() {
        Location loc = new GeoServiceImpl().byIp("172.0.32.11");
        Assertions.assertEquals(new Location("Moscow", Country.RUSSIA, "Lenina", 15).getCity(), loc.getCity());
        Assertions.assertEquals(new Location("Moscow", Country.RUSSIA, "Lenina", 15).getCountry(), loc.getCountry());
        Assertions.assertEquals(new Location("Moscow", Country.RUSSIA, "Lenina", 15).getStreet(), loc.getStreet());
        Assertions.assertEquals(new Location("Moscow", Country.RUSSIA, "Lenina", 15).getBuiling(), loc.getBuiling());
    }

    @Test
    public void testLocalizationServiceImpl() {
        String word = "Welcome";
        Assertions.assertEquals(new LocalizationServiceImpl().locale(Country.USA), word);
    }
}
