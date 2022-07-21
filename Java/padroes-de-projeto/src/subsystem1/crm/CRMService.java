package subsystem1.crm;

public class CRMService {
    private CRMService(){
        super();
    }

    public static void addCustomer(String name, String zipCode, String city, String state){
        System.out.println("Customer was added to the CRM Systems");
        System.out.println(name);
        System.out.println(zipCode);
        System.out.println(city);
        System.out.println(state);
    }
}
