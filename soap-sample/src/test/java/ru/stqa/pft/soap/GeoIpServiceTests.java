package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;



import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        //testGeoIpService(){
        GeoIPService geoIPService = new GeoIPService();
     //   WebServiceFeature features = new ClientProxyFeature();


        helloservice.endpoint.Hello port = service.getHelloPort();

        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("194.28.29.152");
        assertEquals(geoIP.getCountryCode(), "RUS");

    }

 /*   public void proxySetup() {
        HelloService hello = new HelloService();
        HelloPortType helloPort = cliente.getHelloPort();
        org.apache.cxf.xjc.ts. .endpoint.Client client = ClientProxy.getClient(helloPort);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        http.getClient().setProxyServer("proxy");
        http.getClient().setProxyServerPort(8080);
        http.getProxyAuthorization().setUserName("user proxy");
        http.getProxyAuthorization().setPassword("password proxy");
    }*/
}
