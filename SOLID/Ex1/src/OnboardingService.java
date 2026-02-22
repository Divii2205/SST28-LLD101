import java.util.*;

import javax.xml.validation.Validator;

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
