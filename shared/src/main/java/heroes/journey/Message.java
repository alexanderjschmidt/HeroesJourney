package heroes.journey;

public class Message {
    public String text;

    public Message() {
    } // Required for KryoNet

    public Message(String text) {
        this.text = text;
    }
}
