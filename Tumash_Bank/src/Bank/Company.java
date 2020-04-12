package Bank;

public class Company extends Client {

    public Company(String name) {
        super(name);
    }


    @Override
    public String ClientName() {
        return "firma " + name;
    }
}
