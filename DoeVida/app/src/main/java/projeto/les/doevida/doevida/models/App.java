package projeto.les.doevida.doevida.models;

public class App {
    public static void main( String[] args ) {
        System.out.println( "Sending POST to GCM" );

        String apiKey = "AIzaSyBfVuvC5kx2t60PyZqcKGnGYjphyNduqYU";
        Content content = createContent();

        POST2GCM.post(apiKey, content);
    }

    public static Content createContent(){

        Content c = new Content();

        c.addRegId("780650781416");
        c.createData("Test Title", "Test Message");

        return c;
    }
}