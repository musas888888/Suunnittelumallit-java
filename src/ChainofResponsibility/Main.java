package ChainofResponsibility;

import java.util.*;

// ===== 1) Viestityypit (enum) =====
enum MessageType {
    COMPENSATION,       // korvausvaatimus
    CONTACT_REQUEST,    // yhteydenottopyyntö
    SUGGESTION,         // kehitysehdotus
    GENERAL             // yleinen palaute
}

// ===== 2) Viesti-luokka =====
class Message {
    private final MessageType type;
    private final String content;
    private final String senderEmail;

    public Message(MessageType type, String content, String senderEmail) {
        this.type = type;
        this.content = content;
        this.senderEmail = senderEmail;
    }

    public MessageType getType()      { return type; }
    public String getContent()        { return content; }
    public String getSenderEmail()    { return senderEmail; }

    @Override
    public String toString() {
        return "[" + type + "] from " + senderEmail + " | " + content;
    }
}

// ===== 3) Chain of Responsibility: perus Handler =====
abstract class Handler {
    protected Handler next; // seuraava lenkki ketjussa

    // ketjun rakentaminen fluently
    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    // perus käsittely: jos tämä handleri hoitaa, prosessoi; muuten eteenpäin
    public String handle(Message msg) {
        if (canHandle(msg)) {
            return process(msg);
        }
        if (next != null) {
            return next.handle(msg);
        }
        return "No handler for: " + msg.toString();
    }

    protected abstract boolean canHandle(Message msg);
    protected abstract String process(Message msg);
}

// ===== 4) Konkreettiset handlerit =====

// 4.1 Korvausvaatimukset
class CompensationHandler extends Handler {
    private final Random rnd = new Random(42); // deterministinen demo

    @Override
    protected boolean canHandle(Message msg) {
        return msg.getType() == MessageType.COMPENSATION;
    }

    @Override
    protected String process(Message msg) {
        // Esimerkkilogiikka: "review" -> satunnainen hyväksyntä/hylkäys
        boolean approved = rnd.nextBoolean();
        String decision = approved ? "APPROVED" : "REJECTED";
        return "Compensation reviewed -> " + decision +
                " | Email to customer: " + msg.getSenderEmail();
    }
}

// 4.2 Yhteydenottopyynnöt
class ContactRequestHandler extends Handler {
    @Override
    protected boolean canHandle(Message msg) {
        return msg.getType() == MessageType.CONTACT_REQUEST;
    }

    @Override
    protected String process(Message msg) {
        // Yksinkertainen reititys avainsanoilla
        String content = msg.getContent().toLowerCase();
        String department = "Customer Support";

        if (content.contains("invoice") || content.contains("billing") || content.contains("lasku"))
            department = "Billing";
        else if (content.contains("bug") || content.contains("error") || content.contains("tech"))
            department = "Tech Support";
        else if (content.contains("offer") || content.contains("price") || content.contains("quote"))
            department = "Sales";

        return "Contact request forwarded to " + department +
                " | Notified: " + msg.getSenderEmail();
    }
}

// 4.3 Kehitysehdotukset
class SuggestionHandler extends Handler {
    @Override
    protected boolean canHandle(Message msg) {
        return msg.getType() == MessageType.SUGGESTION;
    }

    @Override
    protected String process(Message msg) {
        // Yksinkertainen priorisointi avainsanoilla
        String content = msg.getContent().toLowerCase();
        String priority = "Medium";

        if (content.contains("crash") || content.contains("security") || content.contains("payment"))
            priority = "High";
        else if (content.contains("ui") || content.contains("ux") || content.contains("accessibility"))
            priority = "Medium";
        else if (content.contains("nice to have") || content.contains("idea"))
            priority = "Low";

        // "kirjataan backlogiin"
        return "Suggestion logged -> priority: " + priority +
                " | Ticket created for product team | Ack sent to: " + msg.getSenderEmail();
    }
}

// 4.4 Yleinen palaute
class GeneralFeedbackHandler extends Handler {
    @Override
    protected boolean canHandle(Message msg) {
        return msg.getType() == MessageType.GENERAL;
    }

    @Override
    protected String process(Message msg) {
        // Kevyt tunne-analyysi avainsanoilla
        String content = msg.getContent().toLowerCase();
        String tone = "neutral";
        if (content.contains("great") || content.contains("love") || content.contains("kiitos"))
            tone = "positive";
        else if (content.contains("bad") || content.contains("terrible") || content.contains("huono"))
            tone = "negative";

        return "General feedback analyzed (tone: " + tone + ")" +
                " | Thank-you reply sent to: " + msg.getSenderEmail();
    }
}

// ===== 5) Main: luo ketju, tee testiviestit, käsittele ja tulosta =====
public class Main {
    public static void main(String[] args) {
        // Rakennetaan käsittelyketju: Compensation -> Contact -> Suggestion -> General
        Handler chain = new CompensationHandler();
        chain.setNext(new ContactRequestHandler())
                .setNext(new SuggestionHandler())
                .setNext(new GeneralFeedbackHandler());

        // Luodaan erilaisia viestejä
        List<Message> inbox = List.of(
                new Message(MessageType.COMPENSATION,
                        "Parcel arrived damaged, requesting compensation for 49€.",
                        "matti@example.com"),

                new Message(MessageType.CONTACT_REQUEST,
                        "I need help with invoice #1234, please contact me.",
                        "sara@example.com"),

                new Message(MessageType.SUGGESTION,
                        "UI idea: dark mode and improved accessibility.",
                        "li@example.com"),

                new Message(MessageType.GENERAL,
                        "Great service, kiitos paljon!",
                        "ahmed@example.com"),

                new Message(MessageType.SUGGESTION,
                        "Payment flow security improvement suggestion.",
                        "eva@example.com"),

                new Message(MessageType.CONTACT_REQUEST,
                        "Looking for a price quote (offer) for 200 units.",
                        "john@example.com")
        );

        // Käsitellään viestit
        System.out.println("=== Customer Feedback Handling ===");
        for (Message msg : inbox) {
            System.out.println("\nIN:  " + msg);
            String result = chain.handle(msg);
            System.out.println("OUT: " + result);
        }

        System.out.println("\nDone.");
    }
}

