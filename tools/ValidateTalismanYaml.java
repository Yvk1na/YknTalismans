import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.yaml.snakeyaml.Yaml;

public final class ValidateTalismanYaml {
    private ValidateTalismanYaml() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: ValidateTalismanYaml <talisman directory>");
        }

        var yaml = new Yaml();
        var parsed = new AtomicInteger();
        var errors = new AtomicInteger();
        try (Stream<Path> paths = Files.walk(Path.of(args[0]))) {
            paths.filter(path -> path.toString().endsWith(".yml"))
                    .filter(path -> !path.getFileName().toString().equals("_example.yml"))
                    .forEach(path -> {
                        try (Reader reader = Files.newBufferedReader(path)) {
                            if (!(yaml.load(reader) instanceof Map<?, ?>)) {
                                throw new IllegalArgumentException("root value is not a map");
                            }
                            parsed.incrementAndGet();
                        } catch (Exception exception) {
                            errors.incrementAndGet();
                            System.err.println(path + ": " + exception.getMessage());
                        }
                    });
        }

        System.out.println("YAML_FILES=" + parsed.get() + " YAML_ERRORS=" + errors.get());
        if (errors.get() != 0) {
            System.exit(1);
        }
    }
}
