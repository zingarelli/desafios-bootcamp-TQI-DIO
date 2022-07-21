package subsystem2.zipcode;

// aplicando o Singleton Eager
public class ZipCodeAPI {
    private static ZipCodeAPI instance = new ZipCodeAPI();

    private ZipCodeAPI(){
        super();
    }

    public static ZipCodeAPI getInstance(){
        return instance;
    }

    public String getCity(String zipCode) {
        return "Orlando";
    }

    public String getState(String zipCode) {
        return "Florida";
    }
}
