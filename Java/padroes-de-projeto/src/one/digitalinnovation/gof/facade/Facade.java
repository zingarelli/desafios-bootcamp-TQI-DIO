package one.digitalinnovation.gof.facade;

import subsystem1.crm.CRMService;
import subsystem2.zipcode.ZipCodeAPI;

/*
 * Padrão de design Facade: criação de uma classe "intermediadora", que simplifica a 
 * integração entre subsistemas, provendo às aplicações métodos que executam a comunicação
 * entre esses subsistemas, escondendo das aplicações a complexidade em torno disso
 */
public class Facade {
    public void changeCustomerZipCode(String name, String zipCode){
        String city = ZipCodeAPI.getInstance().getCity(zipCode);
        String state = ZipCodeAPI.getInstance().getState(zipCode);

        CRMService.addCustomer(name, zipCode, city, state);
    }
}
