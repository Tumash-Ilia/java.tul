package Bank;

public class Person extends Client {

    public Person(String name) {
        super(name);
    }

    @Override
    public String ClientName() {
        String nameEnd = name.substring(name.length() - 3);
        if (nameEnd.equals("ova")) {
            return "Pani " + name;
        } else {
            return "Pan " + name;
        }
    }
}