package Model.Enigma;

public class Message {

    private String plainText;
    private String cipherText;

    public Message(String plainText, String cipherText) {
        this.plainText = plainText;
        this.cipherText = cipherText;
    }

    public Message(String plainText){
        this.plainText = plainText;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public String getSpacedPlainText(int spacing) {
        return createSpacing(spacing, plainText);
    }

    public String getSpacedCipherText(int spacing) {
        return createSpacing(spacing, cipherText);
    }

    private String createSpacing(int spacing, String message) {

        String temp = "";

        if (spacing == 0){
            message = message.replace(" ", "");
            message = message.trim();
            return message;
        }

        for (int i = 0; i < message.length(); i++) {
            //If the message is at its spacing requirement, add in a space
            if (i % spacing == 0) {
                temp += " ";
            }

            temp += message.charAt(i);
        }

        return temp.trim();
    }
}
