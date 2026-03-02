public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        email.send(n);
        sms.send(n);

        SendResult waResult = wa.send(n);
        if(!waResult.success) {
            System.out.println("WA ERROR: " + waResult.message);
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}

// ----------
// ORIGINAL
// ----------

// public class Main {
//     public static void main(String[] args) {
//         System.out.println("=== Notification Demo ===");
//         AuditLog audit = new AuditLog();

//         Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

//         NotificationSender email = new EmailSender(audit);
//         NotificationSender sms = new SmsSender(audit);
//         NotificationSender wa = new WhatsAppSender(audit);

//         email.send(n);
//         sms.send(n);
//         try {
//             wa.send(n);
//         } catch (RuntimeException ex) {
//             System.out.println("WA ERROR: " + ex.getMessage());
//             audit.add("WA failed");
//         }

//         System.out.println("AUDIT entries=" + audit.size());
//     }
// }

// Before the exporter all NotificationSender classes had the same method: send()
// But they behaved differently meaning, 
// EmailSender and SmsSender worked normally but WhatsAppSender threw a RuntimeException

// Because of this, Main had to use try-catch only for WhatsApp
// That means the caller had to treat one subclass differently

// LSP => A subclass should not require more than what the base class requires

// Solution:
// A new class SendResult was created, so everytime an output is returned
// No exception handling is required
