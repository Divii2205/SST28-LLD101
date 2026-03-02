import java.util.*;

public class OnboardingService {

    private final StudentRepository repository;
    private final Parser parser;
    private final StudentValidator validator;
    private final Printer printer;

    public OnboardingService(StudentRepository repository) {
        this.repository = repository;
        this.parser = new Parser();
        this.validator = new StudentValidator();
        this.printer = new Printer();
    }

    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        Map<String, String> data = parser.parse(raw);

        List<String> errors = validator.validate(data);
        if(!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repository.count());
        StudentRecord rec = new StudentRecord(id, data.get("name"), data.get("email"), data.get("phone"), data.get("program"));

        repository.save(rec);

        printer.printSuccess(id, repository.count(), rec);
    }
}

// import java.util.*;

// public class OnboardingService {
//     private final FakeDb db;

//     public OnboardingService(FakeDb db) {
//         this.db = db;
//     }

//     // Intentionally violates SRP: parses + validates + creates ID + saves + prints.
//     public void registerFromRawInput(String raw) {
//         System.out.println("INPUT: " + raw);

//         // ----------
//         // DATA PARSING
//         // ----------

//         Map<String, String> kv = new LinkedHashMap<>();
//         String[] parts = raw.split(";");
//         for (String p : parts) {
//             String[] t = p.split("=", 2);
//             if (t.length == 2)
//                 kv.put(t[0].trim(), t[1].trim());
//         }

//         String name = kv.getOrDefault("name", "");
//         String email = kv.getOrDefault("email", "");
//         String phone = kv.getOrDefault("phone", "");
//         String program = kv.getOrDefault("program", "");

//         // ----------
//         // VALIDATION
//         // ----------

//         // validation inline, printing inline
//         List<String> errors = new ArrayList<>();
//         if (name.isBlank())
//             errors.add("name is required");
//         if (email.isBlank() || !email.contains("@"))
//             errors.add("email is invalid");
//         if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit))
//             errors.add("phone is invalid");
//         if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE")))
//             errors.add("program is invalid");

//         if (!errors.isEmpty()) {
//             System.out.println("ERROR: cannot register");
//             for (String e : errors)
//                 System.out.println("- " + e);
//             return;
//         }

//         // ----------
//         // ID CREATION
//         // ----------

//         String id = IdUtil.nextStudentId(db.count());

//         // ----------
//         // SAVE / DATA PERSISTENCE
//         // ----------

//         StudentRecord rec = new StudentRecord(id, name, email, phone, program);

//         db.save(rec);

//         // ----------
//         // PRINT
//         // ----------

//         System.out.println("OK: created student " + id);
//         System.out.println("Saved. Total students: " + db.count());
//         System.out.println("CONFIRMATION:");
//         System.out.println(rec);
//     }
// }

// Create a parsing class
// Create a Validation class
// Create a print class
// Create a repo interface, implemented by the FakeDB:
// The onboarding class is directly dependent on the concrete FakeDB class => tight coupling
// We add a layer in between to force abstraction -> Student Repository